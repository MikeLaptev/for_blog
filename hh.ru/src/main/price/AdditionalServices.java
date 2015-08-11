/**
 * 
 */
package main.price;

import org.openqa.selenium.WebDriver;

/**
 * @author Mikhail
 *
 */
public class AdditionalServices extends BasePagePriceServices {

    public AdditionalServices(WebDriver driver) {
        super(driver);
        PAGE_SERVICES_ADDITIONAL_URL = "#additional";
    }

}
