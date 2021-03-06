package com.ducks.goodsduck.admin.util;

import com.ducks.goodsduck.admin.config.ApplicationContextServe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class PropertyUtil {

    public static String getProperty(String propertyName) {

        var applicationContext = ApplicationContextServe.getApplicationContext();

        if (applicationContext != null && applicationContext.getEnvironment().getProperty(propertyName) != null) {
            return applicationContext.getEnvironment().getProperty(propertyName);
        } else if (applicationContext == null) {
            log.debug("applicationContext was not loaded!");
        } else if (applicationContext.getEnvironment().getProperty(propertyName) == null) {
            log.debug(propertyName + " properties was not loaded!");
        }

        return "Invalid";
    }
}
