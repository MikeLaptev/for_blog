/**
 * 
 */
package main.price;

import org.openqa.selenium.WebDriver;

/**
 * @author Mikhail
 *
 */
public class PublicationsServices extends BasePagePriceServices {

    public PublicationsServices(WebDriver driver) {
        super(driver);
        PAGE_SERVICES_ADDITIONAL_URL = "#publications";
    }

}
