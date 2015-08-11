/**
 * 
 */
package test.price;

import main.logic.ServiceElement;
import main.price.RecommendedServices;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Mikhail
 *
 */
public class RecommendedServicesTests {

    public static WebDriver driver;
    public RecommendedServices rs;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Before
    public void setUpBefore() {
        rs = PageFactory.initElements(driver, RecommendedServices.class);
        rs.loadPage();
    }

    @After
    public void tearDownAfter() {
        driver.manage().deleteAllCookies();
        driver.get(rs.getPageUrl());
    }

    @AfterClass
    public static void tearDownAfterClass() {
        driver.quit();
    }

    // Tests
    
    @Test
    public final void testRecommendedServicesAllGiftsAdded() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // Make sure that cart contains required services
        rs.refreshCart();
        // Check that all gifts have been displayed
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            Assert.assertTrue("Required gift should be displayed", rs.isGiftInsideTheCart(service.getSpecialOffer()));
        }
    }

    @Test
    public final void testRecommendedServicesActionCannotBeAppliedTwice() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // Make sure that cart contains required services
        rs.refreshCart();
        // Check that service's action cannot be applied twice
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            Assert.assertFalse("Action can be applied", service.isActionPossible());
            Assert.assertEquals("Action has incorrect text", rs.l.get("InCartButtonName"), service.getActionTitle());
        }
    }

    @Test
    public final void testRecommendedServicesAllServicesAdded() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // Make sure that cart contains required services
        rs.refreshCart();
        // Check that all services have been added
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            Assert.assertTrue(String.format("Required service '%s' was not added into the cart", 
                    service.getDescription()), 
                    rs.isRecommendedServiceInsideTheCart(rs.m.getElementKeyForCartByElementKeyForPage(service.getDescription())));
        }
    }
    
    @Test
    public final void testRecommendedServicesActualPrice() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // Make sure that cart contains required services
        rs.refreshCart();
        // Check that actual price has been displayed correctly for services
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (rs.isRecommendedServiceInsideTheCart(service.getDescription())) {
                Assert.assertTrue(String.format("Required service '%s' has incorrect actual price", 
                        service.getDescription()), 
                        rs.isRecommendedServiceHasRequiredActualPriceInsideTheCart(service.getDescription(), service.getActualPrice()));
            }
        }
    }

    @Test
    public final void testRecommendedServicesOldPrice() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Make sure that cart contains required services
        rs.refreshCart();

        // Check that old price has been displayed correctly for services
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (rs.isRecommendedServiceInsideTheCart(service.getDescription())) {
                if (service.hasOldPrice()) {
                    Assert.assertTrue(String.format("Required service '%s' has incorrect old price", 
                            service.getDescription()), 
                            rs.isRecommendedServiceHasRequiredOldPriceInsideTheCart(service.getDescription(), service.getOldPrice()));
                } else {
                    Assert.assertFalse(String.format("Required service '%s' should not have old price", 
                            service.getDescription()), 
                            rs.isRecommendedServiceHasRequiredOldPriceInsideTheCart(service.getDescription(), 0));
                }
            }
        }
    }

    @Test
    public final void testRecommendedServicesOldTotalAmount() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Make sure that cart contains required services
        rs.refreshCart();
        
        // Make sure that displayed and calculated old total amounts are the same
        Assert.assertEquals("Old amount has been calcualted incorrectly", rs.getCalculatedOldTotalAmount(), rs.getDisplayedOldTotalAmount());
    }
    
    @Test
    public final void testRecommendedServicesActualTotalAmount() {
        // Adding all services to cart
        for (ServiceElement service : rs.getListOfRecommendedServices()) {
            if (service.isActionPossible()) {
                try {
                    Assert.assertEquals("Action has incorrect title", rs.l.get("AddToCartButtonName"), service.getActionTitle());
                    service.applyAction();
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Make sure that cart contains required services
        rs.refreshCart();
        
        // Make sure that displayed and calculated actual total amounts are the same
        Assert.assertEquals("Actual amount has been calcualted incorrectly", rs.getCalcualtedActualTotalAmount(), rs.getDisplayedActualTotalAmount());
    }

}
