package org.qamation.java.sampler.wmq;

import org.qamation.java.sampler.abstracts.AbstractExtensionJMS;
import org.qamation.utils.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;

import javax.jms.TextMessage;

/**
 * Created by pavel.gouchtchine on 10/17/2016.
 */
public class SendJMSMessage extends AbstractExtensionJMS {
    public static final String MQ_QUEUE_NAME = "ENTER QUEUE NAME";
    public static final String MESSAGE_TO_SEND = "ENTER A MESSAGE TO SEND";


    private String mqQueueName;
    private String messageToSend;
    private TextMessage sentMessage = null;


    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(MESSAGE_TO_SEND,"${MQ_MESSAGE}");
        defaultParameters.addArgument(MQ_QUEUE_NAME,"${MQ_QUEUE_NAME}");
        return defaultParameters;
    }

    protected void readSamplerParameters() {
        super.readSamplerParameters();
        messageToSend = getSamplerParameterValue(MESSAGE_TO_SEND);
        mqQueueName = getSamplerParameterValue(MQ_QUEUE_NAME);
    }

    protected void toDo() {
        jmsConnection = getJMSConnectionFromContext();
        sentMessage = jmsConnection.sendMessageToMQQueue(messageToSend,mqQueueName);
    }

    protected SampleResult assembleTestResult() {
        if (sentMessage == null) throw new RuntimeException("Sent message is null");
        return setSuccess(null,"Message:\n"+messageToSend+"\nis sent",jmsConnection.getMessagePropertiesAsString(sentMessage));
    }

    protected SampleResult assembleTestFailure(Exception e) {
        return setFailure(null,"Message:\n"+messageToSend+"\ncould not be sent.", StringUtils.getStackTrace(e));
    }
}
