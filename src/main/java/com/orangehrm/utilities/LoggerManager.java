package com.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
	
	//This class will provide logger instance to the test classes
	public static Logger getLogger(Class<?> clazz)
	{
		return LogManager.getLogger();
	}
}
