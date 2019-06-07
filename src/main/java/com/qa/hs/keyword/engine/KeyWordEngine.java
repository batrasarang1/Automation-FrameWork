package com.qa.hs.keyword.engine;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.qa.hs.keyword.base.Base;


/* 
 *   @author Sarang.Batra
 */

public class KeyWordEngine {

	public static Workbook book;
	public static Sheet sheet;
	public static Row row;
	public static XSSFCell Cell;
	public static XSSFSheet ExcelWSheet;
	public Base base;
	public WebElement element;
	public WebElement relatedElement;
	public WebDriver webDriver;
	String splitrelatedElements;
	String[] split;
	String splitelement;
	

	
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
		        		if(!"#SKIP#".equals(value)) {
			           	    element=driver.findElement(getObject(prop, locatorValue, locatorType));
			           	   }
		        	    element.click();
		        	        }catch (Exception e) {
		                 System.out.println("Error with CLICK Action:" + e);
		             }
		            break; 
		            
				case "CLEARSETTEXT":
					try {
						if(!"#SKIP#".equals(value)) {
						element=driver.findElement(getObject(prop, locatorValue, locatorType));
						element.clear();
			           	element.sendKeys(value);
						}
					}catch (Exception e) {
			              System.out.println("Error with SETTEXT Action:" + e);
			        }
					break;
					
				case "SETPARAM":
					try {			
					prop.setProperty(prop.getProperty(locatorValue), row.getCell(j).toString());
					}catch (Exception e) {
						System.out.println("Error with SETPARAM Action:" + e);
					}
					break;
					
				case "MOUSEACTION":
					try {
						if(!"#SKIP#".equals(value)) {
							Actions action = new Actions(driver);
							Thread.sleep(1000);
							relatedElement= driver.findElement(By.xpath(prop.getProperty("HOVER_TICKET")));
							action.moveToElement(relatedElement).perform();
							element = driver.findElement(getObject(prop, locatorValue, locatorType));
							element.click();
						}
					}catch(Exception e) {
			              System.out.println("Error with MOUSEACTION Action:" + e);
					}
					
				case "GETTEXT":
					try {
						if(!"#SKIP#".equals(value)) {
						element=driver.findElement(getObject(prop, locatorValue, locatorType));
						element.getText().toString();
						}
					}catch (Exception e) {
			              System.out.println("Error with GETTEXT Action:" + e);
			        }
					break;
							
				case "COUNTITEMS":
					try {
						if(!"#SKIP#".equals(value)) {
							List<WebElement> countItems = driver.findElements(getObject(prop, locatorValue, locatorType));
					        System.out.println("Total number of banners on home page are : " + countItems.size());
						}
					    }catch (Exception e) {
						System.out.println("Error with COUNTITEMS Action:" + e);
					    }
						break;
						
				case "REQUEST":
					try {
						
					}catch (Exception e) {
						System.out.println("Error with REQUEST Action:" + e);
				    }
					
					break;
					
				case "RESPONSE":
					try {
						
					}catch (Exception e) {
						System.out.println("Error with RESPONSE Action:" + e);
				    }
					
					break;
						
			    default:
					    break;
		     			}
				}catch (Exception e) {
				System.out.println("Error with Action:" );
				 e.printStackTrace();
			    }
		    }
	    }
    }
}
    

	   public String getObjects(Properties prop,String locatorValue) throws Exception{
	     return prop.getProperty(locatorValue).toString();
	   }
	   
	
	    public By getObject(Properties prop,String locatorValue,String locatorType) throws Exception{
	    	
		 if(locatorType.equalsIgnoreCase("XPATH") ){
			 if(locatorValue.contains("||")){
		        	split = locatorValue.split("\\|\\|");
		        	splitelement = getObjects(prop, split[0]);
					splitrelatedElements = getObjects(prop, split[1]);
					prop.setProperty(split[1], splitrelatedElements.replace(split[0] , splitelement));
					locatorValue=split[1];
				}
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
	    
	    public WebElement getElementByXpathContainsText(String xpath)
	    {
	    return webDriver.findElement(By.xpath(xpath));
	    }
	    
	    
	   
}
	