/**
 * 
 */
package main.price;

import java.util.HashMap;

import main.localization.Localization;
import main.logic.Cart;
import main.logic.ServiceElement;
import main.mapping.Mapping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.junit.Assert;

/**
 * @author Mikhail
 *
 */
public class BasePagePriceServices {

    public final int cart_item_description_postfix_length = 2;
    
    protected WebDriver driver;
    protected String PAGE_BASE_URL;
    protected String PAGE_SERVICES_ADDITIONAL_URL;
    protected String PAGE_TITLE;
    
    // Empty cart information
    @FindBy(css = ".HH-PriceCart-Empty") 
    private WebElement emptyCartDisplayed;
    @FindBy(css = ".HH-PriceCart-Empty.g-hidden") 
    private WebElement emptyCartHidden;
    @FindBy(css = ".HH-PriceCart-Empty>div:first-child") 
    private WebElement emptyCartTitle;
    
    // Cart with services information
    protected Cart the_cart;
    
    @FindBy(css = ".HH-PriceCart") 
    private WebElement nonEmptyCartDisplayed;
    @FindBy(css = ".HH-PriceCart.g-hidden") 
    private WebElement nonEmptyCartHidden;

    // Title
    By cartTitleCSSSelector = By.cssSelector(".price-cart__title");
    // Cart item
    By cartItemCSSSelector = By.cssSelector(".price-cart__item");
    // Cart item's title
    By cartItemTitleCSSSelector = By.cssSelector(".price-cart__item-title");
    // Cart item's old cost
    By cartItemOldCostCSSSelector = By.cssSelector(".price-cart__old-cost.HH-PriceCart-Item-OldPrice");
    // Cart item's actual cost
    By cartItemActualCostCSSSelector = By.cssSelector(".price-cart__actual-cost.HH-PriceCart-Item-ActualCost");
    // Cart item's action
    By cartItemRemoveActionCSSSelector = By.cssSelector(".HH-PriceCart-ItemRemover.price-cart__item-remove.g-switcher");
    // Content
    By cartContentCSSSelector = By.cssSelector(".price-cart__contents");
    // Content: resume access
    By cartItemResumeAccessHiddenCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_resumeAccess.g-hidden");
    By cartItemResumeAccessCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_resumeAccess");
    // Content: countable service
    By cartItemCountableServicesHiddenCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_countableService.g-hidden");
    By cartItemCountableServicesCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_countableService");
    // Content: special offer
    By cartItemSpecialOfferHiddenCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_specialOffer.g-hidden");
    By cartItemSpecialOfferCSSSelector = By.cssSelector(".price-cart__items.HH-PriceCart-Items_specialOffer");
    // Total
    By cartTotalCSSSelector = By.cssSelector(".price-cart__total");
    By cartOldTotalCSSSelector = By.cssSelector(".price-cart__total-old-cost.HH-PriceCart-TotalCost-Old");
    By cartOldTotalHiddenCSSSelector = By.cssSelector(".price-cart__total-old-cost HH-PriceCart-TotalCost-Old g-hidden");
    By cartActualTotalCSSSelector = By.cssSelector(".HH-PriceCart-TotalCost-Actual");
    // Gifts
    By cartGiftsCSSSelector = By.cssSelector(".price-cart__gifts.HH-PriceCart-Gifts");
    By cartGiftsHiddenCSSSelector = By.cssSelector(".price-cart__gifts.HH-PriceCart-Gifts.g-hidden");
    By cartGiftCSSSelector = By.cssSelector(".price-cart__gift");
    // Action
    By cartProceedCSSSelector = By.cssSelector(".price-cart__proceed");
    By cartButtonProceedCSSSelector = By.cssSelector(".g-button.m-button_green.m-button_big.price-cart__button");
    
    // Tab that displayed by default
    @FindBy(css = ".flat-tabs__content.HH-PriceCart-Tab.g-expand")
    protected WebElement activeTab;
    @FindBy(css = ".flat-tabs__switcher.HH-PriceCart-TabSwitcher.flat-tabs__switcher_active")
    protected WebElement activeTabHeader;
    
    // Locale
    @FindBy(css = ".navi-flag.navi-flag_RU")
    protected WebElement ruLocale;
    @FindBy(css = ".navi-flag.navi-flag_EN")
    protected WebElement enLocale;
    
    public HashMap<String, String> l;
    public Mapping m;
    
    public BasePagePriceServices(WebDriver driver) {
        this.driver = driver;
        this.PAGE_BASE_URL = "http://www.hh.ru/price";
        this.PAGE_SERVICES_ADDITIONAL_URL = "";
        this.the_cart = new Cart();
        l = new HashMap<String, String>();
        m = new Mapping();
    }
    
    public void loadPage() {
        driver.get(getPageUrl());
        // Initialization according to selected locale
        if (ruLocale != null) {
            l.putAll(new Localization("ru").getLocalizedText());
        }
        else if (enLocale != null) {
            l.putAll(new Localization("en").getLocalizedText());
        }
        this.PAGE_TITLE = l.get("PageTitle");
        Assert.assertEquals("Title is incorrect: ", getPageTitle(), driver.getTitle());
    }
    
    public void clickElement(WebElement element) {
        element.click();
    }
    
    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }
    
    public void setElementText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        Assert.assertEquals("Text has been updated incorrectly", element.getAttribute("value"), text);
    }
    
    public boolean isEmptyCartDisplayed() {
        if (emptyCartDisplayed.isDisplayed() && !nonEmptyCartHidden.isDisplayed()) {
            Assert.assertEquals("Cart's description is incorrect", l.get("EmptyCartTitle"), emptyCartTitle.getText());
            return true;
        }
        return false;
    }
    
    public String getTextOfActiveTab() {
        return activeTabHeader.getText();
    }
    
    // Getter for the PAGE_URL
    public String getPageUrl(){
        return PAGE_BASE_URL + PAGE_SERVICES_ADDITIONAL_URL;
    }
    
    // Getter for the PAGE_TITLE
    public String getPageTitle() {
        return PAGE_TITLE;
    }
    
    public void refreshCart() {
        if (nonEmptyCartDisplayed.isDisplayed()){
            // Title's check
            Assert.assertEquals("Cart title is incorrect", l.get("CartWithItemsTitle"), nonEmptyCartDisplayed.findElement(cartTitleCSSSelector).getText());
            // Gifts
            WebElement gifts = nonEmptyCartDisplayed.findElement(cartGiftsCSSSelector);
            if (gifts != null && gifts.isDisplayed()){
                for(WebElement gift : gifts.findElements(cartGiftCSSSelector)) {
                    the_cart.addGift(getLocaleIdByValue(gift.getText()));
                }
            }
            // Content 
            WebElement content = nonEmptyCartDisplayed.findElement(cartContentCSSSelector);
            if (content != null && content.isDisplayed()) {
                // Resume access
                WebElement resumeAccessContainer = content.findElement(cartItemResumeAccessCSSSelector);
                if (resumeAccessContainer != null) {
                    for (WebElement service: resumeAccessContainer.findElements(cartItemCSSSelector)) {
                        // Title
                        String title = service.findElement(cartItemTitleCSSSelector).getText();
                        // Old cost
                        int old_cost = 0;
                        if (service.findElement(cartItemOldCostCSSSelector).getText().length() != 0) {
                            old_cost = Integer.parseInt(service.findElement(cartItemOldCostCSSSelector).getText().replaceAll("\\D", ""));
                        }
                        // Actual cost
                        int actual_cost = Integer.parseInt(service.findElement(cartItemActualCostCSSSelector).getText().replaceAll("\\D", ""));
                        // Action
                        WebElement action = service.findElement(cartItemRemoveActionCSSSelector);
                        ServiceElement resume_access_element = new ServiceElement(action, 
                                getLocaleIdByValue(title.substring(0, title.length() - cart_item_description_postfix_length)), 
                                actual_cost, 
                                null);
                        if (old_cost != 0) {
                            resume_access_element.setOldPrice(old_cost);
                        }
                        the_cart.addResumeAccessItems(resume_access_element);
                    }
                }
                // Countable Services
                WebElement countableServicesContainer = content.findElement(cartItemCountableServicesCSSSelector);
                if (countableServicesContainer != null) {
                    for (WebElement service: countableServicesContainer.findElements(cartItemCSSSelector)) {
                        // Title
                        String title = service.findElement(cartItemTitleCSSSelector).getText();
                        // Old cost
                        int old_cost = 0;
                        if (service.findElement(cartItemOldCostCSSSelector).getText().length() != 0) {
                            old_cost = Integer.parseInt(service.findElement(cartItemOldCostCSSSelector).getText().replaceAll("\\D", ""));
                        }
                        // Actual cost
                        int actual_cost = Integer.parseInt(service.findElement(cartItemActualCostCSSSelector).getText().replaceAll("\\D", ""));
                        // Action
                        WebElement action = service.findElement(cartItemRemoveActionCSSSelector);
                        ServiceElement countable_services_element = new ServiceElement(action, 
                                getLocaleIdByValue(title.substring(0, title.length() - cart_item_description_postfix_length)), 
                                actual_cost, 
                                null);
                        if (old_cost != 0) {
                            countable_services_element.setOldPrice(old_cost);
                        }
                        the_cart.addCountableServiceItem(countable_services_element);
                    }
                }
                // Special Offer
                WebElement specialOfferContainer = content.findElement(cartItemSpecialOfferCSSSelector);
                if (specialOfferContainer != null) {
                    for(WebElement service: specialOfferContainer.findElements(cartItemCSSSelector)){
                        // Title
                        String title = service.findElement(cartItemTitleCSSSelector).getText();
                        // Old cost
                        int old_cost = 0;
                        if (service.findElement(cartItemOldCostCSSSelector).getText().length() != 0) {
                            old_cost = Integer.parseInt(service.findElement(cartItemOldCostCSSSelector).getText().replaceAll("\\D", ""));
                        }
                        // Actual cost
                        int actual_cost = Integer.parseInt(service.findElement(cartItemActualCostCSSSelector).getText().replaceAll("\\D", ""));
                        // Action
                        WebElement action = service.findElement(cartItemRemoveActionCSSSelector);
                        ServiceElement special_offer_element = new ServiceElement(action, 
                                getLocaleIdByValue(title.substring(0, title.length() - cart_item_description_postfix_length)), 
                                actual_cost, 
                                null);
                        if (old_cost != 0) {
                            special_offer_element.setOldPrice(old_cost);
                        }
                        the_cart.addSpecialOfferItems(special_offer_element);
                    }
                }
            }
            
            // Total
            WebElement total_container = nonEmptyCartDisplayed.findElement(cartTotalCSSSelector);
            if (total_container != null && total_container.isDisplayed()) {
                // Store value of actual price that should be selected in any case
                WebElement actual_total_price = total_container.findElement(cartActualTotalCSSSelector);
                the_cart.setActualTotal(Integer.valueOf(actual_total_price.getText().replaceAll("\\D", "")));
                
                // Check on old price
                if (!total_container.findElements(cartOldTotalHiddenCSSSelector).isEmpty()) {
                    the_cart.setOldTotal(the_cart.getActualTotal());
                } 
                else {
                    WebElement old_total_price = total_container.findElement(cartOldTotalCSSSelector);
                    the_cart.setOldTotal(Integer.valueOf(old_total_price.getText().replaceAll("\\D", "")));
                }
            }
        } 
        else {
            the_cart.Clear();
        }
    }
    
    // Gift
    
    public boolean isGiftInsideTheCart(String gift) {
        return the_cart.isGiftInside(gift);
    }
    
    // Service
    
    public boolean isServiceInsideTheCart(String service) {
        return the_cart.isServiceInside(service);
    }
    
    // Total
    
    // Calculated
    public int getCalculatedOldTotalAmount() {
        return the_cart.getCalculatedOldTotal();
    }
    
    public int getCalcualtedActualTotalAmount() {
        return the_cart.getCalculatedActualTotal();
    }
    
    // Displayed
    public int getDisplayedOldTotalAmount() {
        return the_cart.getOldTotal();
    }
    
    public int getDisplayedActualTotalAmount() {
        return the_cart.getActualTotal();
    }
    
    // Return locale id of text by its value
    protected String getLocaleIdByValue(String value) {
        for(String key: l.keySet()) {
            if (l.get(key).equals(value)) {
                return key;
            }
        }
        
        throw new IndexOutOfBoundsException(String.format("No required value '%s' in locale description", value));
    }

}
