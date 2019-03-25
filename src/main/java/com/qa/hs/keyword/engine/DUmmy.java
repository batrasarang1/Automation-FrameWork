package com.qa.hs.keyword.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hs.keyword.base.Base;


/**
 * 
 * @author Sarang.Batra
 *
 */
public class DUmmy {

	public WebDriver driver;
	public Properties prop;

	public static Workbook book;
	public static Sheet sheet;
	public static Row row;

	public Base base;
	public WebElement element;

	public final String SCENARIO_SHEET_PATH = "C:\\Users\\sarang.batra\\eclipse-workspace\\Keyword-Driven-Web-UI-Framework-master\\Keyword-Driven-Web-UI-Framework-master"
			+ "\\src\\main\\java\\com\\qa\\hs\\keyword\\scenarios\\TESTDATA_BOOK.xlsx";

	public void startExecution(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
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
		        	element=driver.findElement(By.xpath(locatorType));
		        	element.click();
		             }catch (Exception e) {
		              System.out.println(e);
		             }
		            break;
							
				case "WAIT":
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					break;
					
				case "REQUEST":
					BufferedReader bufferReader = null;
				    String readCurrentLine;  
				    try {
					bufferReader = new BufferedReader(new FileReader(prop.getProperty("SW_REQFILELOC")+prop.getProperty("SW_REQSERVICEOWNER") + "01" + ".txt"));
					while ((readCurrentLine = bufferReader.readLine()) != null) 
				    {
				    driver.findElement(By.xpath(prop.getProperty("SW_REQTEXTAREA"))).sendKeys(readCurrentLine);
				    }
				    }catch (IOException e) {
				    e.printStackTrace();
				    } finally {
				    try {
				        if (bufferReader != null)bufferReader.close();
				    } catch (IOException ex) {
				        ex.printStackTrace();
				    }
				   }
				    break;
				    
				case "RESPONSE":
				    WebElement Output= driver.findElement(By.xpath(prop.getProperty("SW_RESPTEXTAREA")));
				    Output.getText();
				    
				    File file1 = new File(prop.getProperty("SW_RESPFILELOC") + prop.getProperty("SW_RESPSERVICEOWNER")+ "01" + ".txt");
				    int increase=1;
				    while(file1.exists()){
				    increase++;
				    file1 = new File(prop.getProperty("SW_RESPFILELOC") + prop.getProperty("SW_RESPSERVICEOWNER") + increase+ ".txt");
				     } 
				    if(!file1.exists()) {
				    try {
				    file1.createNewFile();
				    FileWriter fw = new FileWriter(file1.getAbsoluteFile());
				    BufferedWriter bw = new BufferedWriter(fw);
				    bw.write(Output.getAttribute("textContent")); 
				    bw.close();

				    System.out.println("Done");
				    }catch (IOException e){
				   }
				  }
				    break;
		    
     			default:
					break;
				}
				

				/*switch (locatorType) {
				case "ID":
					element = driver.findElement(By.id(locatorValue));
					if (locatorAction.equalsIgnoreCase("CLEARSETTEXT")) {
						element.clear();
						element.sendKeys(value);
						element.toString();
					} else if (locatorAction.equalsIgnoreCase("CLICK")) {
						element.click();
					} else if (locatorAction.equalsIgnoreCase("ISDISPLAYED")) {
						element.isDisplayed();
					} else if (locatorAction.equalsIgnoreCase("GETTEXT")) {
						element.getText().toString();
					}
					locatorType = null;
					break;

				case "NAME":
					element = driver.findElement(By.name(locatorValue));
					if (locatorAction.equalsIgnoreCase("CLEARSETTEXT")) {
						element.clear();
						element.sendKeys(value);
					} else if (locatorAction.equalsIgnoreCase("CLICK")) {
						element.click();
					} else if (locatorAction.equalsIgnoreCase("ISDISPLAYED")) {
						element.isDisplayed();
					} else if (locatorAction.equalsIgnoreCase("GETTEXT")) {
						element.getText().toString();
					}
					locatorType = null;
					break;

				case "XPATH":
					element = driver.findElement(By.xpath(locatorValue));
					if (locatorAction.equalsIgnoreCase("CLEARSETTEXT")) {
						element.clear();
						element.sendKeys(value);
					} else if (locatorAction.equalsIgnoreCase("CLICK")) {
						element.click();
					} else if (locatorAction.equalsIgnoreCase("ISDISPLAYED")) {
						element.isDisplayed();
					} else if (locatorAction.equalsIgnoreCase("GETTEXT")) {
						element.getText().toString();
					}
					locatorType = null;
					break;

				case "CSS":
					element = driver.findElement(By.cssSelector(locatorValue));
					if (locatorAction.equalsIgnoreCase("CLEARSETTEXT")) {
						element.clear();
						element.sendKeys(value);
					} else if (locatorAction.equalsIgnoreCase("CLICK")) {
						element.click();
					} else if (locatorAction.equalsIgnoreCase("ISDISPLAYED")) {
						element.isDisplayed();
					} else if (locatorAction.equalsIgnoreCase("GETTEXT")) {
						element.getText().toString();
					}
					locatorType = null;
					break;

				case "CLASSNAME":
					element = driver.findElement(By.className(locatorValue));
					if (locatorAction.equalsIgnoreCase("CLEARSETTEXT")) {
						element.clear();
						element.sendKeys(value);
					} else if (locatorAction.equalsIgnoreCase("CLICK")) {
						element.click();
					} else if (locatorAction.equalsIgnoreCase("ISDISPLAYED")) {
						element.isDisplayed();
					} else if (locatorAction.equalsIgnoreCase("GETTEXT")) {
						element.getText().toString();
					}
					locatorType = null;
					break;

				case "LINKTEXT":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorType = null;
					break;

				case "PARTIALLINKTEXT":
					element = driver.findElement(By.partialLinkText(locatorValue));
					element.click();
					locatorType = null;
					break;

				default:
					break;
				}*/
			} catch (Exception e) {
				System.out.println(e);
			}

		}
				}
			}
		
	}
	
	public By getObject(Properties prop,String locatorValue,String locatorType) throws Exception{
 
		base = new Base();
		prop = base.init_properties();
		
        if(locatorType.equalsIgnoreCase("XPATH")){
            return By.xpath(prop.getProperty(locatorValue));
        }

        else if(locatorType.equalsIgnoreCase("CLASSNAME")){
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
         }else
         {
            throw new Exception("Wrong object type");
        }
    }
}
	