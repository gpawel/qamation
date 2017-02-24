package org.qamation.wmq.wrapper.client;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueue;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by pavel.gouchtchine on 10/13/2016.
 */
public class MQPointToPointTest {

    String mqHost = "s0966-ftapq01";
    String mqPort = "1414";
    String mqQueueManager = "CTR0966";
    String mqInboundQueue = "INTARCH.MB.SS.ROUTER";
    String mqOutboundQueue =  "INTARCH.MB.ROUTER.STORE";
    String mqErrorQueue = "INTARCH.MB.ROUTER.STORE.ERROR";
    String mqChannel = "MQMON.DEF.SVRCONN";
    String mqUser = "";
    String mqPassword = "";
    Connection connection = null;
    String updateProdMessage = "PRDDESLG00000001270000000000 20160229172855                                     0171705ENGLISH 1 DESCRIPTION WITH SPECIAL CHARACTERS #$%&*()@        French Description 1 with Special Characters #$%&*()@ *       ";

    @Before
    public void setUp() {
        try {
            connection = createConnection();
            connection.start();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void tearDown() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Test
    public void sendMessage() {
        try {
            TextMessage message = sendMessage(updateProdMessage);
            String messageId = message.getJMSMessageID();
            Assert.assertTrue(messageId != null);
            System.out.println("Message id: "+messageId);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void readResponse() {
        try {
            TextMessage message = sendMessage("message "+System.currentTimeMillis());
            Message response = readFromQueue(mqInboundQueue, 2000L);
            Assert.assertTrue(response != null);
            Message errorResponse = readFromQueue(mqErrorQueue, 2000L);
            Assert.assertTrue(errorResponse != null);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void readErrorUsingCorrelationID() {
        try {
            TextMessage message = sendMessage("message "+System.currentTimeMillis());
            Message errorResponse = readFromQueueUsingCorrelationID(mqErrorQueue,message.getJMSCorrelationID(), 2000L);
            Assert.assertTrue(errorResponse != null);
            System.out.println("request correlation id: " + message.getJMSCorrelationID());
            System.out.println("response correlation id: " + errorResponse.getJMSCorrelationID());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void readFromErrorQueue() {
        try {
            //TextMessage message = sendMessage("message "+System.currentTimeMillis());
            Message errorMessage = readFromQueue(mqErrorQueue,2000L);
            Assert.assertTrue(errorMessage != null);
            printMessageProperties(errorMessage);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void readFromErrorQueueUsingCorrelationID() {
        try {
            TextMessage message = sendMessage("message "+System.currentTimeMillis());
            Message errorResponse = readFromQueueUsingCorrelationID(mqErrorQueue, message.getJMSCorrelationID(), 2000L);
            Assert.assertTrue(errorResponse != null);
            System.out.println("request correlation id: " + message.getJMSCorrelationID());
            System.out.println("response correlation id: " + errorResponse.getJMSCorrelationID());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testMQCommunicator() {
        try {
            MQCommunicator comm = new MQCommunicator(mqHost, mqChannel, mqQueueManager, mqInboundQueue, mqErrorQueue);
            MQMessage message = comm.send("asfasdfasdfa");
            String str = comm.receive(message);
            comm.print(str);


        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }


    private TextMessage sendMessage(String message) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue outQueue = new MQQueue(mqOutboundQueue);
        MessageProducer producer = session.createProducer(outQueue);
        TextMessage textMessage = session.createTextMessage(message);
        textMessage.setJMSCorrelationID(UUID.randomUUID().toString());
        producer.send(textMessage);
        return textMessage;
    }

    private Message readFromQueue(String queueName, long waitTime) throws JMSException {
        // NEED TO BE CORRELATED SOME HOW. THE BELOW DOES NOT HAVE CORRELATION.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue inQueue = new MQQueue(queueName);
        MessageConsumer consumer = session.createConsumer(inQueue);
        Message response = null;
        response = consumer.receive(2000L);
        return response;
    }

    private Message readFromQueueUsingCorrelationID(String queueName, String correlationId, long waitTime) throws JMSException {
        // NEED TO BE CORRELATED SOME HOW. THE BELOW DOES NOT HAVE CORRELATION.
        MQGetMessageOptions options = new MQGetMessageOptions();
        MQMessage responseMessage = new MQMessage();

        options.matchOptions = CMQC.MQMO_MATCH_CORREL_ID;
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue queue = new MQQueue(mqQueueManager,queueName);
        MessageConsumer consumer = session.createConsumer(queue,correlationId);
        Message response = consumer.receive(waitTime);
        return response;
    }

    private Connection createConnection() {
        try {
            MQConnectionFactory factory = new MQConnectionFactory();
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setHostName(mqHost);
            factory.setPort(Integer.parseInt(mqPort));
            factory.setQueueManager(mqQueueManager);
            factory.setChannel(mqChannel);
            Connection connection = factory.createConnection();
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void printMessageProperties(Message message) throws JMSException {
        StringBuffer buff = new StringBuffer();
        Enumeration<String>en = message.getPropertyNames();
        while (en.hasMoreElements()) {
            String name = en.nextElement();
            String value = message.getStringProperty(name);
            buff.append("\n"+name+" = "+value);
        }
        System.out.println(buff.toString());
    }
}
