/**
 * 
 */
package main.price;

import java.util.ArrayList;
import java.util.List;

import main.logic.ServiceElement;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Mikhail
 *
 */
public class RecommendedServices extends BasePagePriceServices {

    private By divTagName = By.tagName("div");
    private By serviceActualPriceCSSSelector = By.cssSelector(".price-spoffers__actual-price");
    private By serviceOldPriceCSSSelector = By.cssSelector(".price-spoffers__old-price");
    private By divActionCSSSelector = By.cssSelector(".HH-Price-SpecialOffer-AddToCartButton");
    private By divDescriptionCSSSelector = By.cssSelector(".price-spoffers__special-offer-title");
    private By divSpecialOfferCSSSelector = By.cssSelector(".price-spoffers__special-offer-plus");
    private final String serviceClassPattern = "g\\-col\\d m\\-colspan\\d";
    public final int special_offer_text_shift_size = 1; 
    
    public RecommendedServices(WebDriver driver) {
        super(driver);
        PAGE_SERVICES_ADDITIONAL_URL = "#recommended";
    }

    public List<ServiceElement> getListOfRecommendedServices() {
        List<ServiceElement> recommended_services = new ArrayList<ServiceElement>();

        for(WebElement required_div : activeTab.findElements(divTagName)) {
            if (required_div.getAttribute("class").matches(serviceClassPattern)) {
                // Special offer
                StringBuilder special_offer_text = new StringBuilder();
                List<WebElement> special_offers = required_div.findElements(divSpecialOfferCSSSelector);
                if (!special_offers.isEmpty()) {
                    for(WebElement special_offer : special_offers) {
                        special_offer_text.append(special_offer.getText());
                    }
                }
                // Prepare base information about the element
                ServiceElement elem = new ServiceElement(
                        required_div.findElement(divActionCSSSelector),
                        getLocaleIdByValue(required_div.findElement(divDescriptionCSSSelector).getText()), 
                        Integer.valueOf(required_div.findElement(serviceActualPriceCSSSelector).getText().split("\\s+")[0]),
                        getLocaleIdByValue(special_offer_text.toString().substring(special_offer_text_shift_size)));
                // Get information about old price
                if(!required_div.findElements(serviceOldPriceCSSSelector).isEmpty()) {
                    elem.setOldPrice(Integer.valueOf(required_div.findElement(serviceOldPriceCSSSelector).getText().replaceAll("\\D", "")));
                }
                recommended_services.add(elem);
            }
        }
        Assert.assertEquals("Amount of recommended services has been calculated incorrectly", 2, recommended_services.size());
        return recommended_services;
    }
    
    // Service
    
    public boolean isRecommendedServiceInsideTheCart(String service) {
        return the_cart.isServiceInsideSpecialOffer(service);
    }
    
    public boolean isRecommendedServiceHasRequiredActualPriceInsideTheCart(String service, int price) {
        return the_cart.isSpecialOfferServiceHasPrice(service, price);
    }
    
    public boolean isRecommendedServiceHasRequiredOldPriceInsideTheCart(String service, int price) {
        return the_cart.isSpecialOfferServiceHasOldPrice(service, price);
    }
}
