package org.qamation.wmq.wrapper.client;

/**
 * Created by pavel.gouchtchine on 10/14/2016.
 */

    /* This class provides access to the MQSeries queue facilities.  Once
instantiated, the main application program need only call the 'send' and
'receive' methods to put & get strings. The send method returns an MQMessage
object the handle of which is used to retrieve the reply. */

import java.io.*;
import com.ibm.mq.*;					        // Include the MQ package

    public class MQCommunicator {
        private String                  hostname;               // Define the name of your host to connect to
        // Note. assumes MQ Server is listening on
        // the default TCP/IP port of 1414
        private String                  qManager;               // Define name of queue manager object to connect to.

        private MQQueueManager          qMgr;                   // define a queue manager object
        private byte                    sentmessage[];
        private MQQueue                 requestQueue;
        private MQQueue                 replyQueue;
        public  String                  replyQueueName; 	// Holds reply queue name for use in MQ message options
        private int                     openOptions;
        private int                     getOptions;


        private final static String     bufferFront =   "NACT02  " +
                "V1A" +
                "E" +
                "    " +
                "    " +
                "     ",
                bufferEnd   =   "                                                  " +
                        "                                                  " +
                        "                                                  " +
                        "                                                  " +
                        "                                                  " +
                        "                                                  " +
                        "                                                  " +
                        "                            ";
        private MQMessage		storedMessage;
        // Constructor - takes MQ details as parameters
        public MQCommunicator(String hostname, String channel, String qManager, String aRequestQueue, String aReplyQueue) {

            try {
                MQEnvironment.hostname = hostname;
                MQEnvironment.channel  = channel;
                MQEnvironment.port = 1414;                              // Hard code port

                // MQEnvironment.enableTracing(1, System.out);           //  Set MQ tracing on
                // Create a reference to the Q Manager

                try{
                    qMgr = new MQQueueManager(qManager);
                }catch(MQException e){
                    print("An MQException occurred trying to connect to the QManager.");
                    Object o = e.exceptionSource;
                    print("MQException originated from object '" + o.toString());
                    print("Completion code = " + e.completionCode);
                    System.exit(1);
                }
                print("Connected to QManager " + qManager);

                // Set up the options on the queue we wish to open...
                // Note. All MQ Options are prefixed with MQC in Java.

                //Store the reply queue name
                replyQueueName = aReplyQueue;

                // If the name of the request queue is the same as the reply queue...
                if(aRequestQueue.equals(aReplyQueue)){
                    openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
                }
                else{
                    openOptions = MQC.MQOO_OUTPUT ;         // Open queue to perform MQPUTs
                }

                // Now specify the queue that we wish to open, and the open options...
                requestQueue = qMgr.accessQueue(aRequestQueue, openOptions,
                        null,           // default q manager
                        null,           // no dynamic q name
                        null);          // no alternate user id

                // If the name of the request queue is the same as the reply queue...(again...)
                if (aRequestQueue.equals(aReplyQueue)){
                    replyQueue = requestQueue;                     // Assign same name to replyQ varaible
                }
                else{
                    openOptions = MQC.MQOO_INPUT_AS_Q_DEF ;
                    replyQueue = qMgr.accessQueue(aReplyQueue, openOptions,
                            null,           // default q manager
                            null,           // no dynamic q name
                            null);          // no alternate user id
                }

            }catch (MQException ex){
                print("MQCommunicator.constructor - MQException occurred : Completion code " +
                        ex.completionCode + "\n>MQStatus: Reason code " + ex.reasonCode);
                System.exit(1);
            }

            catch (Exception e){
                print("MQCommunicator.constructor - Exception occurred - " + e.toString());
                System.exit(1);
            }
        }

        // This method called to send MQ message to the CICS OS390 machine
        // RECEIVES a message STRING and returns a message object (used as a reference for the reply)
        public MQMessage send(String customerNumber) {

            try {
                MQMessage sendMessage = null;

                try{
                    // Create new MQMessage object
                    sendMessage = new MQMessage();
                }catch(NullPointerException e){
                    print("Unable to create new MQMessage");
                    return null;
                }
                print("MQMessage created");

                sendMessage.format = MQC.MQFMT_STRING;  // Set message format to MQC.MQFMT_STRING for use without MQCIH header
                // NB. Change to 'MQCICS  ' if using header !!!

                String str = "AMQ!NEW_SESSION_CORRELID";
                byte byteArray[] = str.getBytes();
                sendMessage.correlationId = byteArray;//str;

                // Set request type
                sendMessage.messageType = MQC.MQMT_REQUEST;

                // Set reply queue
                sendMessage.replyToQueueName = replyQueueName;

                // Set message text
                String buffer = new String(bufferFront + customerNumber + bufferEnd);
                sendMessage.writeString(buffer);

                // Specify the message options...(default)
                MQPutMessageOptions pmo = new MQPutMessageOptions();

                // Put the message on the queue using default options
                try{
                    requestQueue.put(sendMessage, pmo);
                }catch(NullPointerException e){
                    print("Request Q is null - cannot put message");
                    return null;
                }
                print("Message placed on queue");

                // Store the messageId for future use...
                // Define a MQMessage object to store the message ID as a correlation ID
                // so we can retrieve the correct reply message later.
                storedMessage = new MQMessage();

                // Copy current message ID across to the correlation ID
                storedMessage.correlationId = sendMessage.messageId;

                print("Message ID for sent message = " + sendMessage.messageId.toString());
                print("Correlation ID stored = " + storedMessage.correlationId.toString());

                return storedMessage;

            }catch (MQException ex){
                print("MQCommunicator.send - MQException occurred : Completion code " +
                        ex.completionCode + " Reason code " + ex.reasonCode);
                return null;
            }catch (java.io.IOException ex){
                print("MQCommunicator.send - IOException occurred: " + ex);
                return null;
            }catch (Exception ex){
                print("MQCommunicator.send - General Exception occurred: " + ex);
                return null;
            }
        }


        // Get the reply from MVS containing account information
        public String receive(MQMessage replyMessage) {
            try{
                // Construct new MQGetMessageOptions object
                MQGetMessageOptions gmo = new MQGetMessageOptions();

                // Set the get message options.. specify that we want to wait for reply message
                // AND *** SET OPTION TO CONVERT CHARS TO RIGHT CHAR SET ***
                gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_CONVERT;

                // Specify the wait interval for the message in milliseconds
                gmo.waitInterval = 300000;

                print("Current Msg ID used for receive: " + replyMessage.messageId);
                print("Correlation ID to use for receive: " + replyMessage.correlationId);
                print("Supported character set to use for receive: " + replyMessage.characterSet);

                // Following test lines will cause any message on the queue to be received regardless of
                // whatever message ID or correlation ID it might have
                //replyMessage.messageId = MQC.MQMI_NONE;
                //replyMessage.correlationId = MQC.MQCI_NONE;

                //The replyMessage will have the correct correlation id for the message we want to get.
                // Get the message off the queue..
                replyQueue.get(replyMessage,gmo);
                // And prove we have the message by displaying the message text
                print("The receive message character set is: " + replyMessage.characterSet);

                // Find and store message length
                int  msglen = replyMessage.getMessageLength();

                // Read string length of message
                String msgText = replyMessage.readString(msglen);
                print("message length = " + msglen);
                print("message is = \n'" + msgText +"'");

                // Return the string
                return msgText;

            }catch (MQException ex){
                print("MQCommunicator.receive - MQ error occurred : Completion code " +
                        ex.completionCode + "\n>MQStatus: Reason code " + ex.reasonCode);
            }catch (java.io.IOException ex){
                print("MQCommunicator.receive - error occurred: " + ex);
            }catch (Exception ex){
                print("MQCommunicator.receive - general error occurred: " + ex);
            }

            // Return null if unsucessful
            return null;
        }


        // Include a 'finalise()' method to ensure we leave MQSeries in a stable state...
        public void finalise() {
            try {
                // Closing the queues
                requestQueue.close();

                if (requestQueue != replyQueue)  replyQueue.close();

                // Disconnect from the queue manager
                qMgr.disconnect();

            }catch (MQException ex){
                print("MQCommunicator.finalise - MQException occurred : Completion code " +
                        ex.completionCode + ">MQStatus: Reason code " + ex.reasonCode);
            }
        }


        public void print(String msg){
            PrintWriter message = new PrintWriter(System.out, true);
            message.println(">MQStatus: " + msg);
        }


    }  // Class Ends

