package rs.com.search.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import testUtils.DriverManager;

public class ProductPriceFromResultGrid {
    private WebDriver driver;

    public ProductPriceFromResultGrid() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(DriverManager.getDriver())),
                this);
    }
}
