import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;


public abstract class Base {

    private final static Logger LOGGER = Logger.getLogger(Base.class.getName());

    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite() {

        BasicConfigurator.configure();

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @BeforeClass
    public void beforeClass() {
        driver.get("http://amazon.com");
        driver.manage().window().maximize();

        findSingleElement(By.id("twotabsearchtextbox")).sendKeys("harry potter books");
        findSingleElement(By.xpath("//input[@value='Go']")).click();

    }


    // help methods (in future can be extended for additional verification)

    WebElement findSingleElement(By by) {
        WebElement we = null;
        try {
            we = driver.findElement(by);
        } catch (NoSuchElementException e) {
            e.getMessage();
        }
        return we;
    }

    List<WebElement> findListElements(By by) {
        return driver.findElements(by);
    }

    String parsingOutput(String original, String prefix, String suffix) {
        return original.substring(original.indexOf(prefix), original.indexOf(suffix) + suffix.length());
    }

    void sortPageWithDropDown(String value) {
        Select sortResults = new Select(findSingleElement(By.xpath("//select[@id='sort']")));
        sortResults.selectByValue(value);
    }

}
