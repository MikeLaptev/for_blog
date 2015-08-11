/**
 * 
 */
package main.logic;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * @author Mikhail
 *
 */
public class Cart {
    private List<ServiceElement> countable_services_items;
    private List<ServiceElement> resume_access_items;
    private List<ServiceElement> special_offer_items;
    private int cart_actual_total;
    private int cart_old_total;
    private List<String> gifts;
    private WebElement pay_action;
    
    public Cart() {
        countable_services_items = new ArrayList<ServiceElement>();
        resume_access_items = new ArrayList<ServiceElement>();
        special_offer_items = new ArrayList<ServiceElement>();
        gifts = new ArrayList<String>();
    }
    
    // Clear the cart
    
    public void Clear() {
        countable_services_items.clear();
        resume_access_items.clear();
        special_offer_items.clear();
        cart_actual_total = 0;
        cart_old_total = 0;
        gifts.clear();
        pay_action = null;
    }
    
    // Actual total
    
    public void setActualTotal(int actual_total) {
        cart_actual_total = actual_total;
    }
    
    public int getActualTotal() {
        return cart_actual_total;
    }
    
    public int getCalculatedActualTotal() {
        int calculated_actual_total = 0;
        
        for(ServiceElement service : countable_services_items) {
            calculated_actual_total += service.getActualPrice();
        }
        
        for(ServiceElement service : resume_access_items) {
            calculated_actual_total += service.getActualPrice();
        }
        
        for(ServiceElement service : special_offer_items) {
            calculated_actual_total += service.getActualPrice();
        }
        
        return calculated_actual_total;
    }
    
    // Old total
    
    public void setOldTotal(int old_total) {
        cart_old_total = old_total;
    }
    
    public int getOldTotal() {
        return cart_old_total;
    }
    
    public int getCalculatedOldTotal() {
        int calculated_old_total = 0;
        
        for(ServiceElement service : countable_services_items) {
            calculated_old_total += service.hasOldPrice() ? service.getOldPrice() : service.getActualPrice();
        }
        
        for(ServiceElement service : resume_access_items) {
            calculated_old_total += service.hasOldPrice() ? service.getOldPrice() : service.getActualPrice();
        }
        
        for(ServiceElement service : special_offer_items) {
            calculated_old_total += service.hasOldPrice() ? service.getOldPrice() : service.getActualPrice();
        }
        
        return calculated_old_total;
    }
    
    // Countable service items
    
    public void addCountableServiceItem(ServiceElement service) {
        countable_services_items.add(service);
    }
    
    // Resume access items
    
    public void addResumeAccessItems(ServiceElement service) {
        resume_access_items.add(service);
    }
    
    // Special offer items
    
    public void addSpecialOfferItems(ServiceElement service) {
        special_offer_items.add(service);
    }
    
    // Gifts
    
    public void addGift(String gift) {
        gifts.add(gift);
    }
    
    public boolean isGiftInside(String gift) {
        return gifts.contains(gift);
    }
    
    // Action
    
    public void setPayAction(WebElement action) {
        pay_action = action;
    }
    
    public WebElement getPayAction(){
        return pay_action;
    }

    // Service
    
    public boolean isServiceInside(String service) {

        // Resume access
        if (isServiceInsideResumeAccessItems(service)) {
            return true;
        }

        // Countable Services
        if (isServiceInsideCountableServices(service)) {
            return true;
        }

        // Special Offer
        if (isServiceInsideSpecialOffer(service)) {
            return true;
        }

        
        return false;
    }
    
    // Service: resume access
    
    public boolean isServiceInsideResumeAccessItems(String service) {
        for (ServiceElement s : resume_access_items) {
            if (service.startsWith(s.getDescription())) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isResumeAccessServiceHasPrice(String service, int price) {
        for (ServiceElement s : resume_access_items) {
            if (service.startsWith(s.getDescription())) {
                if (s.getActualPrice() == price) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Service: countable service
    
    public boolean isServiceInsideCountableServices(String service) {
        for (ServiceElement s : countable_services_items) {
            if (service.startsWith(s.getDescription())) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isCountableServiceHasPrice(String service, int price) {
        for (ServiceElement s : countable_services_items) {
            if (service.startsWith(s.getDescription())) {
                if (s.getActualPrice() == price) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Service: special offer
    
    public boolean isServiceInsideSpecialOffer(String service) {
        for (ServiceElement s : special_offer_items) {
            if (service.startsWith(s.getDescription())) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isSpecialOfferServiceHasPrice(String service, int price) {
        for (ServiceElement s : special_offer_items) {
            if (service.startsWith(s.getDescription())) {
                if (s.getActualPrice() == price) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public boolean isSpecialOfferServiceHasOldPrice(String service, int price) {
        for (ServiceElement s : special_offer_items) {
            if (service.startsWith(s.getDescription())) {
                if (s.hasOldPrice() && s.getOldPrice() == price) {
                    return true;
                } 
                else {
                    return false;
                }
            }
        }
        
        return false;
    }
    
}
