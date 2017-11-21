package com.amazonaws.xray.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.util.EC2MetadataUtils;

/**
 * A plugin, for use with the {@code AWSXRayRecorderBuilder} class, which will add EC2 instance information to segments generated by the built {@code AWSXRayRecorder} instance.
 * 
 * @see com.amazonaws.xray.AWSXRayRecorderBuilder#withPlugin(Plugin)
 *
 */
public class EC2Plugin implements Plugin {
    private static final Log logger = LogFactory.getLog(EC2Plugin.class);

    private static final String SERVICE_NAME = "ec2";

    private HashMap<String, Object> runtimeContext;

    public EC2Plugin() {
        runtimeContext = new HashMap<>();
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    public void populateRuntimeContext() {
        if (null != EC2MetadataUtils.getInstanceId()) {
            runtimeContext.put("instance_id", EC2MetadataUtils.getInstanceId());
        }
        if (null != EC2MetadataUtils.getAvailabilityZone()) {
            runtimeContext.put("availability_zone", EC2MetadataUtils.getAvailabilityZone());
        }
    }

    public Map<String, Object> getRuntimeContext() {
        populateRuntimeContext();
        return runtimeContext;
    }

    private static final String ORIGIN = "AWS::EC2::Instance";
    @Override
    public String getOrigin() {
        return ORIGIN;
    }

}