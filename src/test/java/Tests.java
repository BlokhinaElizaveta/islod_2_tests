import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import static java.lang.Thread.sleep;

public class Tests {

    private static final int sleepTimeout = 6000;
    private static final String url = "http://els-rc.naumen.ru";
    private static final String login = "otolstykh@naumen.ru";
    private static final String password = "qwer1234";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver//chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void Should_CreateApplication() throws InterruptedException {
        login();

        String href = "/organizations";
        clickOnTagWithLink(href);

        String organizationName = "Байкальский институт управления";
        writeToInput("title", organizationName);
        driver.findElement(By.id("title")).sendKeys(Keys.ENTER);
        sleep(sleepTimeout);

        WebElement organization = driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]", organizationName)));
        href = organization.getAttribute("href").split("/", 4)[3];
        organization.click();
        sleep(sleepTimeout);

        href ="/" + href + "/addRequest";
        clickOnTagWithLink(href);

        driver.findElement(By.xpath("//span[@data-dropdown=\"dropdown\"]")).click();
        driver.findElement(By.xpath("//a[@data-id=\"bef70ad3504d46999db9ea296dc3b258\"]")).click();
        driver.findElement(By.xpath("//input[@value=\"rs_101\"]")).click();

        writeToInput("ogrn", "8501648273807");
        writeToInput("inn", "8437193805");
        writeToInput("kpp", "500013801");

        driver.findElement(By.xpath("//button[contains(text(),'Завершить')]")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Ок')]")).click();
        sleep(sleepTimeout);

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("/card"));
    }

    @Test
    public void Should_DeclineWord() throws InterruptedException {
        login();
        driver.findElement(By.xpath("//li[@class=\"has-sub \"][4]")).click();

        String href = "/admin/systemactions";
        scrollToLink(href);
        clickOnTagWithLink(href);

        writeToInput("nominative", "озеро");
        driver.findElement(By.className("get-declensions")).click();
        sleep(sleepTimeout);

        List<WebElement> declensions = driver.findElement(By.className("table-hover"))
                                             .findElement(By.tagName("tbody"))
                                             .findElements(By.tagName("tr"));
        checkDeclension(declensions.get(1), "Родительный", "озера");
        checkDeclension(declensions.get(2), "Дательный", "озеру");
    }

    private void login() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("loginbutton")).click();
        sleep(sleepTimeout);
    }

    private void writeToInput(String id, String content) {
        driver.findElement(By.id(id)).clear();
        driver.findElement(By.id(id)).sendKeys(content);
    }

    private void clickOnTagWithLink(String href) throws InterruptedException {
        driver.findElement(By.xpath(String.format("//a[@href=\"%s\"]", href))).click();
        sleep(sleepTimeout);
    }

    private void scrollToLink(String href) throws InterruptedException {
        WebElement adminSystemActions = driver.findElement(By.xpath(String.format("//a[@href=\"%s\"]", href)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", adminSystemActions);
        sleep(sleepTimeout);
    }

    private void checkDeclension(@NotNull WebElement declensionTr, String caseOfWord, String expectedWord) {
        List<WebElement> caseAndWord = declensionTr.findElements(By.tagName("td"));
        String currentCaseOfWord = caseAndWord.get(0).getText();
        String word = caseAndWord.get(1).getText();
        if (currentCaseOfWord.equals(caseOfWord))
            Assert.assertEquals(word, expectedWord);
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        sleep(sleepTimeout);
        driver.close();
    }
}