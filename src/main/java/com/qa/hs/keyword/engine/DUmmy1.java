package com.qa.hs.keyword.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hs.keyword.base.Base;


/**
 * 
 * @author Sarang.Batra
 *
 */
public class DUmmy1 {

	public static Workbook book;
	public static Sheet sheet;
	public static Row row;
	public static XSSFCell Cell;

	public Base base;
	public WebElement element;

	public void startExecution(String sheetName, WebDriver driver, Properties prop) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(prop.getProperty("TESTDATA"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
        int k = 0;
		for (int i=4; i < sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			
			if (row.getCell(k+2).toString().equals("Y")) {
				for (int j=4; j < row.getLastCellNum(); j++) {
				
				try {
				  	String locatorAction = sheet.getRow(1).getCell(j).toString().trim();
					String locatorValue = sheet.getRow(2).getCell(j).toString().trim();
					String locatorType = sheet.getRow(3).getCell(j).toString().trim();
					String value = sheet.getRow(i).getCell(j).toString().trim();

				switch (locatorAction) {
				
				case "CLICK":
		        	try {
		                 element=driver.findElement(getObject(prop, locatorValue, locatorType));
		           	     element.click();
		             }catch (Exception e) {
		                 System.out.println("Error with CLICK Action:" + e);
		             }
		            break; 
		            
				case "CLEARSETTEXT":
					try {
						element=driver.findElement(getObject(prop, locatorValue, locatorType));
						element.clear();
			           	element.sendKeys(value);
					}catch (Exception e) {
			              System.out.println("Error with SETTEXT Action:" + e);
			        }
					break;
					
				case "GETTEXT":
					try {
						element=driver.findElement(getObject(prop, locatorValue, locatorType));
						element.getText().toString();
					}catch (Exception e) {
			              System.out.println("Error with GETTEXT Action:" + e);
			        }
					break;
				 default:
					break;
				}
			} catch (Exception e) {
				System.out.println("Error with Action:" );
				 e.printStackTrace();
			}

		}
				}
			}
    }
	
	    public By getObject(Properties prop,String locatorValue,String locatorType) throws Exception{
	    	
		 if(locatorType.equalsIgnoreCase("XPATH")){
            return By.xpath(prop.getProperty(locatorValue));
            
        }else if(locatorType.equalsIgnoreCase("CLASSNAME")){
            return By.className(prop.getProperty(locatorValue));
        }
  
        else if(locatorType.equalsIgnoreCase("NAME")){
            return By.name(prop.getProperty(locatorValue));
        }
        
        else if(locatorType.equalsIgnoreCase("ID")){
            return By.id(prop.getProperty(locatorValue));
        }

        else if(locatorType.equalsIgnoreCase("CSS")){
            return By.cssSelector(prop.getProperty(locatorValue));
        }
   
        else if(locatorType.equalsIgnoreCase("LINK")){
            return By.linkText(prop.getProperty(locatorValue));
        }
  
        else if(locatorType.equalsIgnoreCase("PARTIALLINK")){
            return By.partialLinkText(prop.getProperty(locatorValue));
        }else{
            throw new Exception("Wrong object type");
        }
    }
	   
}
	