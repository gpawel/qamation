package org.qamation.java.sampler.wmq;

import org.qamation.java.sampler.abstracts.AbstractExtensionJMS;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.qamation.wmq.wrapper.client.JMSConnection;

/**
 * Created by pavel.gouchtchine on 10/17/2016.
 */
public class CreateJMSConnection extends AbstractExtensionJMS {

    public static final String MQ_HOST = "ENTER MQ HOST NAME";
    public static final String MQ_PORT = "ENTER MQ PORT";
    public static final String MQ_QUEUE_MANAGER = "ENTER MQ QUEUE MANAGER NAME";
    public static final String MQ_CHANNEL = "ENTER MQ CHANNEL ";

    private String mqHost;
    private String mqPort;
    private String mqQueueManager;
    private String mqChannel;

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(MQ_HOST,"${MQ_HOST}");
        defaultParameters.addArgument(MQ_PORT,"${MQ_PORT}");
        defaultParameters.addArgument(MQ_QUEUE_MANAGER,"${MQ_QUEUE_MANAGER}");
        defaultParameters.addArgument(MQ_CHANNEL,"${MQ_CHANNEL}");
        return defaultParameters;
    }

    protected void readSamplerParameters() {
        super.readSamplerParameters();
        mqHost = getSamplerParameterValue(MQ_HOST);
        mqPort = getSamplerParameterValue(MQ_PORT);
        mqQueueManager = getSamplerParameterValue(MQ_QUEUE_MANAGER);
        mqChannel = getSamplerParameterValue(MQ_CHANNEL);
    }

    protected void toDo() {
        try {
            jmsConnection =
               JMSConnection.getJMSConnectionToMQQueueManager(
                       mqHost,
                       mqPort,
                       mqQueueManager,
                       mqChannel);
            setObjectIntoVariables(jmsConnectionVariableName,jmsConnection);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected SampleResult assembleTestResult() {
        String responseMessage = "JMS Connection is created successfully";
        return setSuccess(null,responseMessage,null);
    }

    protected SampleResult assembleTestFailure(Exception e) {
        String message = "Unable to create JMS connection"
                +" for mq host: "+mqHost
                +" mq port: "+mqPort
                +" queue manager: "+mqQueueManager
                +" queue channel: "+mqChannel;
        return setFailure(null, message, message+"\n"+e.toString());
    }
}
