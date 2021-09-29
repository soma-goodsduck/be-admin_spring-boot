package com.ducks.goodsduck.admin.util;

import com.ducks.goodsduck.admin.config.ApplicationContextServe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class PropertyUtil {

    public static String getProperty(String propertyName) {
        return getProperty(propertyName, "Invalid");
    }

    public static String getProperty(String propertyName, String defaultValue) {

        String value = defaultValue;
        ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();

        if(applicationContext.getEnvironment().getRequiredProperty(propertyName) == null) {
            log.warn(propertyName + " properties was not loaded.");
        } else {
            value = applicationContext.getEnvironment().getRequiredProperty(propertyName);
        }

        return value;
    }
}
