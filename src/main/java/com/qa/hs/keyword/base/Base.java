package com.qa.hs.keyword.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
/**
 * 
 * @author Sarang.Batra
 *
 */
public class Base {
	
	
	
	
	public WebDriver driver;
	public Properties prop;
	
	public WebDriver init_driver(String browserName){
		if(browserName.equals("CHROME")){
			System.setProperty("webdriver.chrome.driver", "C:\\Work\\Softwares_Setup\\Chrome Browser\\chromedriver.exe");
			if(prop.getProperty("HEADLESS").equals("YES")){
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver = new ChromeDriver(options);
			}else{
				driver = new ChromeDriver();
			}
		} else if(browserName.equals("FIREFOX")){
			System.setProperty("webdriver.gecko.driver", "/Users/sarang.batra/Downloads/geckodriver");
			driver = new FirefoxDriver();
		}
		return driver;
	}
	
	public Properties init_properties(){
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("/Users/sarang.batra/github/Keyword-Driven-Web-UI-Framework-master"
					+ "/src/main/java/com/qa/hs/keyword/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	
	
	

}
