package selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
public class TestSeleniumIdeTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    ClassLoader classLoader = TestSelenium2.class.getClassLoader();
    File file = new File(classLoader.getResource("chromedriver_win32.exe").getFile());
    String absolutePath = file.getAbsolutePath();
    System.setProperty("webdriver.chrome.driver",absolutePath);
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testSeleniumIde2() {
    driver.get("https://www.google.it/");
    driver.manage().window().setSize(new Dimension(654, 752));
    driver.findElement(By.name("q")).sendKeys("Selenium ide");
    driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    {
      WebElement element = driver.findElement(By.cssSelector(".tF2Cxc > .yuRUbf .eFM0qc > .fl > span"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.linkText("Commands"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    js.executeScript("window.scrollTo(0,900)");
    driver.findElement(By.cssSelector(".tF2Cxc > .yuRUbf .LC20lb")).click();
    driver.findElement(By.linkText("Docs")).click();
    driver.findElement(By.linkText("Commands")).click();
    driver.findElement(By.linkText("Getting Started")).click();
    driver.findElement(By.linkText("Plugins")).click();
    driver.findElement(By.linkText("Help")).click();
    driver.findElement(By.linkText("Blog")).click();
  }
}
