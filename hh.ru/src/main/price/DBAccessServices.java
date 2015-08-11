/**
 * 
 */
package main.price;

import org.openqa.selenium.WebDriver;

/**
 * @author Mikhail
 *
 */
public class DBAccessServices extends BasePagePriceServices {

    public DBAccessServices(WebDriver driver) {
        super(driver);
        PAGE_SERVICES_ADDITIONAL_URL = "#dbaccess";
    }

}
