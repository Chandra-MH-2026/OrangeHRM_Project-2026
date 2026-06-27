package com.orangehrm.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer
{

	int count = 0;
	 static final int maxTry = 1; // Run the failed test 1 more time
	@Override
	public boolean retry(ITestResult result) {
		
		if(count < maxTry)
		{
			count++;
			return true;
		}
		return false;
	}
	

}
