/**
 * 
 */
package test.price;

import main.price.BasePagePriceServices;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Mikhail
 *
 */
public class BasePagePriceServicesTests {

    private static WebDriver driver;
    private BasePagePriceServices bp;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Before
    public void setUpBefore() {
        bp = PageFactory.initElements(driver, BasePagePriceServices.class);
        bp.loadPage();
    }

    @After
    public void tearDownAfter() {
        driver.manage().deleteAllCookies();
        driver.get(bp.getPageUrl());
    }

    @AfterClass
    public static void tearDownAfterClass() {
        driver.quit();
    }

    // Tests
    @Test
    public final void testBasePagePriceServicesCartIsEmptyByDefault() {
        Assert.assertTrue("Cart should be empty", bp.isEmptyCartDisplayed());
    }

    @Test
    public final void testBasePagePriceRecommendedTabDisplayedByDefault() {
        Assert.assertEquals("Recommended tab should be active by default", bp.l.get("RecommendedTabName"), bp.getTextOfActiveTab());
    }

}
