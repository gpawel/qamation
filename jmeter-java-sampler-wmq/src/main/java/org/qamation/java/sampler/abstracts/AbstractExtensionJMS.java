package org.qamation.java.sampler.abstracts;

import org.apache.jmeter.config.Arguments;
import org.qamation.wmq.wrapper.client.JMSConnection;

/**
 * Created by Pavel.Gouchtchine on 10/16/2016.
 */
public abstract class AbstractExtensionJMS extends AbstractExtention{
    public static final String JMS_CONNECTION_NAME = "ENTER VARIABLE NAME TO STORE JMS CONNECTION";

    protected String jmsConnectionVariableName;

    protected JMSConnection jmsConnection = null;

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(JMS_CONNECTION_NAME,"");
        return defaultParameters;
    }

    protected void readSamplerParameters() {
        super.readSamplerParameters();
        jmsConnectionVariableName = getSamplerParameterValue(JMS_CONNECTION_NAME);
        if (jmsConnectionVariableName.isEmpty()) throw new RuntimeException("Variable name for the JMS connection cannot be empty");
    }

    protected JMSConnection getJMSConnectionFromContext() {
        Object obj = getObjectFromVariables(jmsConnectionVariableName);
        if (obj == null) throw new RuntimeException("No JMS connection instance with name: "+jmsConnectionVariableName);
        return (JMSConnection) obj;
    }

}
