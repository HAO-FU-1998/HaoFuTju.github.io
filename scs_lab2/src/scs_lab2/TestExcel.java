package scs_lab2;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestExcel {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  String driverPath = System.getProperty("user.dir") + "/firefox/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  driver = new FirefoxDriver();
	  baseUrl = "https://www.baidu.com/";
	  driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
  }

  @Test
  public void testExcel() throws Exception {
	  String excelDataStr = Excel.getExcelData();

	  WebElement button;
	  WebElement webGithubURL;
	  WebElement studentId;
	  WebElement studentName;

	  String[] everyoneData = excelDataStr.split("\n");

	  String someoneID = "";
	  String someoneName = "";
	  String someoneGitURL = "";
	  //进入测试网站
      driver.get("http://121.193.130.195:8800/");

      //截取字符串，获取各部分的数据进行测试比对
      for(int i = 0, len = everyoneData.length ; i < len ; i++){
    	  //对每个同学的不同信息数据进一步划分，存入对应的临时信息字符串
   	      String[] someoneData = everyoneData[i].split("\t");
   	      for(int j = 0, len2 = someoneData.length;j < len2; j++){
   		      if(j == 0)
   		    	  someoneID = someoneData[j];
   		      else if(j == 1)
   		    	  someoneName = someoneData[j];
   		      else if(j == 2)
   		    	  someoneGitURL = someoneData[j];
   	      }
   	      //利用selenium进行自动化测试
   	      //截取密码（后六位）
   	      String password = someoneID.substring(4);


   	      driver.findElement(By.name("id")).clear();
          driver.findElement(By.name("id")).sendKeys(someoneID);
          driver.findElement(By.name("password")).clear();
          driver.findElement(By.name("password")).sendKeys(password);

          button = driver.findElement(By.id("btn_login"));
          button.submit();

          studentId = driver.findElement(By.id("student-id"));
          studentName = driver.findElement(By.id("student-name"));
          webGithubURL = driver.findElement(By.id("student-git"));
          assertEquals(someoneID, studentId.getText());
          assertEquals(someoneName, studentName.getText());
          assertEquals(someoneGitURL, webGithubURL.getText());

          driver.findElement(By.id("btn_logout")).sendKeys(Keys.ENTER);
          driver.findElement(By.id("btn_return")).sendKeys(Keys.ENTER);

   	      someoneID = "";
   	      someoneName = "";
   	      someoneGitURL = "";
      }

  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
