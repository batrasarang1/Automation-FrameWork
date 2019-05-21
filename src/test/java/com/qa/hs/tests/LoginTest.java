package com.qa.hs.tests;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.hs.keyword.base.Base;
import com.qa.hs.keyword.engine.*;
/**
 * 
 * @author Sarang.Batra
 *
 */
public class LoginTest {
	
	public static KeyWordEngine keyWordEngine;
	public static Base base;
	public static Properties prop;
	public static WebDriver driver;
		
	
	@BeforeTest
	public void beforetest() {
	base = new Base();
	prop = base.init_properties();
	driver = base.init_driver(prop.getProperty("BROWSER"));
	driver.manage().window().maximize();
	driver.get(prop.getProperty("APPURL"));
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@Test
	public void loginTest(){
	keyWordEngine = new KeyWordEngine();
    keyWordEngine.startExecution("PMC_PORTAL", driver , prop);
	}
	
	@AfterMethod
	public void writeResult(ITestResult result)
    {
        try
     {
            if(result.getStatus() == ITestResult.SUCCESS)
            {
            	System.out.println("\nLog Message:: Test Cases has Passed");    
            }
            else if(result.getStatus() == ITestResult.FAILURE)
            {
                 System.out.println("\nLog Message:: Test Cases has Failed");
               
            }
        }
        catch(Exception e)
        {
            System.out.println("\nLog Message::");
            e.printStackTrace();
        }
    }
	
	@AfterTest
	public void teardown() {
		driver.close();
	}
	 
    
}
