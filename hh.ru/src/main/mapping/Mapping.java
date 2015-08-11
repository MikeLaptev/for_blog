/**
 * 
 */
package main.mapping;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Mikhail
 * This class is required to map between description of the service on page and 
 * description of the service when it was added to cart
 */
public class Mapping {
    
    public class ServiceMapping {
        public String on_page_locale_id;
        public String in_cart_locale_id;
        
        public ServiceMapping() {
            
        }
        
        public ServiceMapping(String on_page_locale_id, String in_cart_locale_id) {
            this.on_page_locale_id = on_page_locale_id;
            this.in_cart_locale_id = in_cart_locale_id;
        }
    }

    // Main data structure for mapping
    private HashMap<String, ServiceMapping> mapping;
    
    // Required tags of XML with mapping
    private final String service_tag  = "service";
    private final String onpage_tag   = "onpage";
    private final String incart_tag   = "incart";
    private final String service_id_attribute = "id";
    // Path to the file
    private final String file_path = "data/mapping/mapping.xml";
    
    public Mapping() {
        this.mapping = new HashMap<String, ServiceMapping>();
        UploadMapping(file_path);
    }
    
    private void UploadMapping(String file_path) {
        // Get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File file = new File(file_path);
        try {
            // Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Parse using builder to get DOM representation of the XML file
            Document dom = db.parse(file.getAbsolutePath());
            // Get the root element
            Element root = dom.getDocumentElement();
            
            // Get a nodes of elements
            NodeList services = root.getElementsByTagName(service_tag);
            if(services != null && services.getLength() > 0) {
                for(int i = 0; i < services.getLength(); i++) {
                    // Get the element
                    NodeList service_desc = services.item(i).getChildNodes();

                    // id
                    StringBuilder service_id = new StringBuilder(services.item(i).getAttributes().getNamedItem(service_id_attribute).getTextContent());

                    StringBuilder on_page_locale_id = new StringBuilder();
                    StringBuilder in_cart_locale_id = new StringBuilder();
                    for (int j = 0; j < service_desc.getLength(); j++) {
                        Node service_desc_element = service_desc.item(j);
                        // on page
                        if (service_desc_element.getNodeName().equals(onpage_tag)) {
                            on_page_locale_id = new StringBuilder(service_desc_element.getTextContent());
                        }
                        // in cart
                        if (service_desc_element.getNodeName().equals(incart_tag)) {
                            in_cart_locale_id = new StringBuilder(service_desc_element.getTextContent());
                        }
                    }
                    
                    mapping.put(service_id.toString(), new ServiceMapping(on_page_locale_id.toString(), in_cart_locale_id.toString()));
                }
            }
        }
        catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch(SAXException se) {
            se.printStackTrace();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
    }

    public String getElementKeyForPageByID(String id) {
        if (mapping.containsKey(id)) {
            return mapping.get(id).on_page_locale_id;
        }
        
        throw new IndexOutOfBoundsException(String.format("No required id in mapping: %s", id));
    }
    
    public String getElementKeyForCartByID(String id) {
        if (mapping.containsKey(id)) {
            return mapping.get(id).in_cart_locale_id;
        }
        
        throw new IndexOutOfBoundsException(String.format("No required id in mapping: %s", id));
    }
    
    public String getIDByElementKeyForPage(String element_key) {
        for (String id : mapping.keySet()) {
            if (mapping.get(id).on_page_locale_id.equals(element_key)) {
                return id;
            }
        }
        throw new IndexOutOfBoundsException(String.format("No required key for 'on page' description in mapping: %s", element_key));
    }
    
    public String getIDByElementKeyForCart(String element_key) {
        for (String id : mapping.keySet()) {
            if (mapping.get(id).in_cart_locale_id.equals(element_key)) {
                return id;
            }
        }
        throw new IndexOutOfBoundsException(String.format("No required key for 'in cart' description in mapping: %s", element_key));
    }
    
    public String getElementKeyForPageByElementKeyForCart(String element_key) {
        return getElementKeyForPageByID(getIDByElementKeyForCart(element_key));
    }
    
    public String getElementKeyForCartByElementKeyForPage(String element_key) {
        return getElementKeyForCartByID(getIDByElementKeyForPage(element_key));
    }
}
