package org.qamation.wmq.wrapper.client;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueue;
import com.ibm.msg.client.wmq.WMQConstants;


import javax.jms.*;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by Pavel.Gouchtchine on 10/16/2016.
 */
public class JMSConnection {
    private static JMSConnection jmsConnection = null;

    public static JMSConnection getJMSConnectionToMQQueueManager(String mqHost, String mqPort, String mqQueueManager, String mqChannel) {
        if (jmsConnection == null) {
            jmsConnection = new JMSConnection(mqHost, mqPort, mqQueueManager, mqChannel);
        }
        return jmsConnection;
    }

    private Connection connection = null;
    private JMSConnection(String mqHost, String mqPort, String mqQueueManager, String mqChannel) {
        try {
            MQConnectionFactory factory = new MQConnectionFactory();
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setHostName(mqHost);
            factory.setPort(Integer.parseInt(mqPort));
            factory.setQueueManager(mqQueueManager);
            factory.setChannel(mqChannel);
            connection = factory.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void finalize() {
        try {
            super.finalize();
            closeJMSConnection();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void closeJMSConnection() {
        try {
            if (connection != null) connection.close();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public TextMessage sendMessageToMQQueue(String message, String queueName) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MQQueue outQueue = new MQQueue(queueName);
            MessageProducer producer = session.createProducer(outQueue);
            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setJMSCorrelationID(UUID.randomUUID().toString());
            producer.send(textMessage);
            return textMessage;
        }
        catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    public String getMessagePropertiesAsString(Message message)  {
        try {
            StringBuffer buff = new StringBuffer();
            Enumeration<String> en = message.getPropertyNames();
            while (en.hasMoreElements()) {
                String name = en.nextElement();
                String value = message.getStringProperty(name);
                buff.append("\n"+name+" = "+value);
            }
            return buff.toString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to collect message properties",e);
        }
    }



}
