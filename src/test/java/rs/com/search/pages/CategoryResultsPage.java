package rs.com.search.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;
import testUtils.RandomUtil;
import testUtils.SeleniumDriverHelper;
import testUtils.StringUtil;

import java.util.List;

import static testUtils.SeleniumDriverHelper.*;

public class CategoryResultsPage {
    private static WebDriver driver;
    private static WebElement randomCategoryLocator;
    private static int randomCatInt;
    private static String breadcrumb;

    BreadCrumb breadCrumb1 = new BreadCrumb();

//    @FindBy(xpath = "//div[contains(@id,'categories')]")
//    List<WebElement> categoriesList;

    @FindBy(css = "#categories-grid >div")
    static List<WebElement> categoriesList;

    @FindBy(css = "div#categories-grid")
    private static List<WebElement> categoryGrid;

    private static final Logger logger = LoggerFactory.getLogger(CategoryResultsPage.class);

    public CategoryResultsPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);

        isAlertPresent();
    }

    public static void getCategoryList(){
        List<WebElement> categoryList = SeleniumDriverHelper.findElements(By.cssSelector("#categories-grid >div"));
        categoryList.stream().forEach(s->s.getAttribute("class"));
        randomCatInt = RandomUtil.getRandomNextInt(categoryList.size()-1);
        System.out.println("randomCategory---"+ randomCatInt);
        if(randomCatInt ==0){
            driver.navigate().refresh();
            randomCategoryLocator = driver.findElement(By.xpath("//*[@id='categories-grid']/div[1]/div/a"));
            SeleniumDriverHelper.scrollIntoElementUsingJs(randomCategoryLocator);
            logger.info("text----"+getTextByXpath("//*[@id='categories-grid']/div[1]/div/a"));

            findElementByXpathAndClickUsingJs("//*[@id='categories-grid']/div[1]/div/a");
        }
        else {
            randomCategoryLocator = driver.findElement(By.xpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            waitForElementToBeClickable(By.xpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            waitForPageLoad();
            SeleniumDriverHelper.scrollIntoElementUsingJs(randomCategoryLocator);
            getTextByXpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a");
            logger.info("text----"+getTextByXpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            findElementByXpathAndClickUsingJs("//*[@id='categories-grid']/div["+(randomCatInt +1)+"]/div/a");
        }

        SeleniumDriverHelper.waitForAjax(500);


        logger.info("-----"+"//*[@id='categories-grid']/div["+ randomCatInt +"]");

        //TO-DO: check the locator - String menuCategory = SeleniumDriverHelper.getTextByXpath("//*[contains(@class,'grid-container')]//li[contains(@class,'active')]");
        String menuCategory = SeleniumDriverHelper.getTextByXpath("//li[contains(@class,'active')]");

        BreadCrumb breadCrumb1 = new BreadCrumb();
        Assert.assertTrue(breadCrumb1.checkBreadCrumb(menuCategory));
    }

    public void selectSubCategoryItem(){
        waitForAjax(1000);
        checkAlertPresentAndDismiss();
        List <WebElement> catGridElements = SeleniumDriverHelper.findElementsByCss("div#categories-grid >div");

        randomCatInt = RandomUtil.generateRandomNumInSpecifiedRange(0,catGridElements.size()-1);

        SeleniumDriverHelper.waitForPageLoad();

        driver.navigate().refresh();

        randomCategoryLocator = SeleniumDriverHelper.findElementByXpath("//*[contains(@id,'categories-grid')]/div["+(randomCatInt+1)+"]/div/a");
        scrollIntoElementUsingJs(randomCategoryLocator);
        waitForAjax(1000);
//        System.out.println("------sub------"+SeleniumDriverHelper.
//                getTextByXpath("//*[contains(@id,'categories-grid')]/div["+randomCatInt+"]/div/a"));
//        //clickElementUsingJs(randomCategoryLocator);

        if(randomCatInt ==0){
            driver.navigate().refresh();
            randomCategoryLocator = driver.findElement(By.xpath("//*[@id='categories-grid']/div[1]/div/a"));
            SeleniumDriverHelper.scrollIntoElementUsingJs(randomCategoryLocator);
            logger.info("text----"+getTextByXpath("//*[@id='categories-grid']/div[1]/div/a"));

            findElementByXpathAndClickUsingJs("//*[@id='categories-grid']/div[1]/div/a");
        }
        else {
            randomCategoryLocator = driver.findElement(By.xpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            waitForElementToBeClickable(By.xpath("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            waitForPageLoad();
            SeleniumDriverHelper.scrollIntoElementUsingJs(randomCategoryLocator);
            breadcrumb = StringUtil.getLettersFromString(getTextByXpath
                    ("//*[@id='categories-grid']/div["+(randomCatInt + 1)+"]/div/a"));
            logger.info("breadcrumb----"+breadcrumb);

            findElementByXpathAndClickUsingJs("//*[@id='categories-grid']/div["+(randomCatInt +1)+"]/div/a");
        }

        String pageHeading = StringUtil.getLettersFromString(SeleniumDriverHelper.getTextByXpath("//h1[contains(@class,'main')]"));
        logger.info("pageHeading--"+pageHeading);


        //TO:DO - string assertions
        //Assert.assertTrue(breadCrumb1.checkBreadCrumb(breadcrumb));
        //Assert.assertTrue(pageHeading.equalsIgnoreCase(breadCrumb1.getBreadcrumbText()));

    }


}
