package com.kirwa.temp;

import org.apache.log4j.Logger;

/**
 * Created by Kirtesh on 08-10-2014.
 */
public class ClassForTest {
    private static Logger LOGGER = Logger.getLogger(ClassForTest.class);
    public static void main(String[] args){
        LOGGER.debug("Print debug");
        LOGGER.info("Print Info");
        LOGGER.error("Print Error");
    }
}
