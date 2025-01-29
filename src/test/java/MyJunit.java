import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.*;
import static org.openqa.selenium.By.xpath;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunit {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headed");
        driver = new ChromeDriver(ops);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }


    @Test
    @Order(1)
    @DisplayName("Check if website name is showing properly")
    public void getTittle() {
        driver.get("https://demoqa.com/");
        String titleActual = driver.getTitle();
        String titleExpected = "DEMOQA";
        System.out.println(titleActual);
        Assertions.assertTrue(titleActual.contains(titleExpected));

    }

    @AfterAll
    public void closeDriver() {
        //driver.quit();
    }

    @Test
    @Order(2)
    public void submitForm() {
        driver.get("https://demoqa.com/text-box");
        //driver.findElement(By.id("userName")).sendKeys("Jerin");//using id
        // List<WebElement> textBox = driver.findElements(By.className("form-control")); //using class name
        // textBox.get(0).sendKeys("Test user");
        // textBox.get(1).sendKeys("user@test.com");
        //textBox.get(2).sendKeys("Kuril");
        //textBox.get(3).sendKeys("Dhaka");

        //WebElement firstNameElem=driver.findElement(By.cssSelector("[type=text]"));
        // firstNameElem.sendKeys("Test user");
        // List<WebElement> textBox=driver.findElements(By.tagName("input"));
        // textBox.get(0).sendKeys("User 1");
        //  textBox.get(1).sendKeys("User@test.com");
        List<WebElement> textBox = driver.findElements(By.className("form-control")); //using class name
        textBox.get(0).sendKeys("Test user");
        textBox.get(1).sendKeys("user@test.com");
        textBox.get(2).sendKeys("Kuril");
        textBox.get(3).sendKeys("Dhaka");

        //JavascriptExecutor js=(JavascriptExecutor)driver;//reusable korar jonno class kore then kora
        //js.executeScript("window.scrollBy(0,500)");
        Utils.scroll(driver, 500);


        driver.findElement(By.className("btn-primary")).click();

        List<WebElement> resultElem = driver.findElements(By.tagName("p"));
        String r1Actual = resultElem.get(0).getText();
        String r2Actual = resultElem.get(1).getText();
        String r3Actual = resultElem.get(2).getText();
        String r4Actual = resultElem.get(3).getText();

        String r1Expected = "Test user";
        String r2Expected = "user@test.com";
        String r3Expected = "Kuril";
        String r4Expected = "Dhaka";

        Assertions.assertTrue(r1Actual.contains(r1Expected));
        Assertions.assertTrue(r2Actual.contains(r2Expected));
        Assertions.assertTrue(r3Actual.contains(r3Expected));
        Assertions.assertTrue(r4Actual.contains(r4Expected));


    }

    @Test
    @Order(3)
    public void clickButtons() {
        driver.get("https://demoqa.com/buttons");
        List<WebElement> btnElements = driver.findElements(By.className("btn-primary"));
        Actions actions = new Actions(driver);
        actions.doubleClick(btnElements.get(0)).perform();
        actions.contextClick(btnElements.get(1)).perform();
        actions.click(btnElements.get(2)).perform();


    }
    //@Test
   //@Order(4)

    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();
    }

   // @Test
   // @Order(5)
    public void selectDate() {
        driver.get("https://demoqa.com/date-picker");
        WebElement txtCalenderElem = driver.findElement(By.id("datePickerMonthYearInput"));
        txtCalenderElem.click();
        txtCalenderElem.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        txtCalenderElem.sendKeys("10/05/2024", Keys.ENTER);


    }
    //@Test
   // @Order(6)
    public void selectDropdown() {
        driver.get("https://demoqa.com/select-menu");
        Select options = new Select(driver.findElement(By.id("oldSelectMenu")));
        options.selectByVisibleText("Black");
        options.selectByValue("1");
    }
   // @Test
  //  @Order(7)
    public void selectDynamicDropdown() {
        driver.get("https://demoqa.com/select-menu");
        List<WebElement> dropdownElem = driver.findElements(By.className("css-1hwfws3"));
        dropdownElem.get(1).click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();
    }

    @Test
    @Order(8)
    public void mouseHovering() {
        driver.get("https://www.aiub.edu/");
        List<WebElement> menuElem = driver.findElements(By.xpath("//a[contains(text(),\"About\")]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElem.get(1)).perform();
    }

   // @Test
    //@Order(9)
    public void handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
       // System.out.println(driver.getWindowHandles());
        ArrayList<String> arrayList=new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(arrayList.get(1));//switching korar method
        String text= driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(text);
        //assertion kora lagbe
        driver.close();
        driver.switchTo().window(arrayList.get(0));
    }

   // @Test
  // @Order(10)
    public void handleMultipleWindow(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("windowButton")).click();
        String mainWindowHandle= driver.getWindowHandle();//1 no shomikoron type
        Set<String> allWindowHandles= driver.getWindowHandles();

        Iterator<String> iterator= allWindowHandles.iterator();
        while(iterator.hasNext()){
            String childWindow= iterator.next();//2 no shomikoron type
            if (!mainWindowHandle.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                System.out.println(text);

            }

        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);



    }

    @Test
    @Order(11)
    public void uploadImage(){
        driver.get("https://demoqa.com/upload-download");
        System.out.println(System.getProperty("user.dir")+"/src/test/resources/sqa.jpg");
        driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/sqa.jpg");
    }

    @Test
    @Order(12)
    public void scrapWebTableData(){
        driver.get("https://demoqa.com/webtables");
       WebElement table= driver.findElement(By.className("rt-tbody"));
       table.findElements(By.className("rt-tbody"));
       List<WebElement>allRows=table.findElements(By.cssSelector("[role=rowgroup]"));
       for (WebElement row:allRows){
           List<WebElement> cells=row.findElements(By.cssSelector("[role=gridcell]"));
           for(WebElement cell:cells){
               System.out.println(cell.getText());
           }
       }

    }
}


