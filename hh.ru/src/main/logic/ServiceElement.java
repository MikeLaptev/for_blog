/**
 * 
 */
package main.logic;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

/**
 * @author Mikhail
 *
 */
public class ServiceElement {
    // Action that can be applied to service:
    // 1. if it is element in the cart - remove from the cart
    // 2. if it is element in the list - add to cart
    private WebElement action;
    // Description of the service
    private String description;
    // Old price for the service (if possible). Null if no such price
    private Integer old_price;
    // Actual price for the service
    private int actual_price;
    // Gifts that applied to the service
    private String special_offer;
    // ID of the service that unique for it across the system.
    // Description of the service can be changed, but id should be the same
    // TODO: currently is not used
    private int id;
    
    public ServiceElement(WebElement action, String description, int actual_price, String special_offer) {
        this.action = action;
        this.description = description;
        this.actual_price = actual_price;
        this.special_offer = special_offer;
    }

    // Action
    
    public boolean isActionPossible() {
        return action.isEnabled();
    }
    
    public void applyAction() throws NotFoundException {
        if (isActionPossible()) {
            action.click();
        }
        else {
            throw new NotFoundException("Unable to click on item to execute specific item");
        }
    }
    
    public String getActionTitle() {
        if (action.isDisplayed()) {
            return action.getText();
        }
        
        throw new ElementNotVisibleException("Element with action is not displayed.");
    }
    
    // Description
    
    public String getDescription() {
        return description;
    }
    
    public int getActualPrice() {
        return actual_price;
    }
    
    public String getSpecialOffer() {
        return special_offer;
    }
    
    // Old price
    public void setOldPrice(int old_price) {
        this.old_price = new Integer(old_price);
    }
    
    public boolean hasOldPrice() {
        return this.old_price == null ? false : true;
    }
    
    public int getOldPrice() throws NullPointerException {
        if (this.old_price != null) {
            return this.old_price.intValue();
        }
        
        throw new NullPointerException("Attempt to get old price that was not set for the service item.");
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id - the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
