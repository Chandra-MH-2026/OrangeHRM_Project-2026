package com.orangehrm.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	private static final String EXCEL_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/TestData.xlsx";
	
	@DataProvider(name="validLoginData")
	public static Object[][] getValidLoginData()
	{
		return getSheetData("validLoginData");
	}
	
	@DataProvider(name="inValidLoginData")
	public static Object[][] getInValidLoginData()
	{
		return getSheetData("inValidLogindata");
	}
	
	
	private static Object[][] getSheetData(String sheetName)
	{
		List<String[]> dataList = ExcelReaderUtility.getSheetData(EXCEL_FILE_PATH, sheetName);
		
		Object[][] dataArray = new Object[dataList.size()][dataList.get(0).length];
		
		for(int i=0; i<dataList.size(); i++)
		{
			dataArray[i] = dataList.get(i);
		}
		return dataArray;
		
	}

}
