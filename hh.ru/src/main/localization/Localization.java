/**
 * 
 */
package main.localization;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 *
 */
public class Localization {

    private String locale;
    private Map<String, Map<String, String>> locales;
    
    private final String keyword_tag = "keyword";
    private final String id_tag = "id";
    private final String value_tag = "value";
    private final String root_path = "data/localization";
    
    public Localization(String locale) {
        this.locale = locale;
        this.locales = new HashMap<String, Map<String,String>>();
        UpdateLocales(root_path);
    }
    
    private void UpdateLocales(String files_path) {
        File[] files = new File(files_path).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    String locale_id = file.getName().substring(0, file.getName().length() - 4);
                    if (!locales.containsKey(locale_id)) {
                        locales.put(locale_id, new HashMap<String, String>());
                        // Get the factory
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
                        try {
                            // Using factory get an instance of document builder
                            DocumentBuilder db = dbf.newDocumentBuilder();
                            // Parse using builder to get DOM representation of the XML file
                            Document dom = db.parse(file.getAbsolutePath());
                            // Get the root element
                            Element root = dom.getDocumentElement();

                            // Get a nodes of elements
                            NodeList keywords = root.getElementsByTagName(keyword_tag);
                            if(keywords != null && keywords.getLength() > 0) {
                                for(int i = 0; i < keywords.getLength(); i++) {
                                    // Get the element
                                    NodeList keyword_desc = keywords.item(i).getChildNodes();
                                    
                                    StringBuilder key = new StringBuilder();
                                    StringBuilder value = new StringBuilder(); 
                                    for (int j = 0; j < keyword_desc.getLength(); j++) {
                                        Node keyword_desc_element = keyword_desc.item(j);
                                        // id
                                        if (keyword_desc_element.getNodeName().equals(id_tag)) {
                                            key = new StringBuilder(keyword_desc_element.getTextContent());
                                        }
                                        // value
                                        if (keyword_desc_element.getNodeName().equals(value_tag)) {
                                            value = new StringBuilder(keyword_desc_element.getTextContent());
                                        }
                                    }
                                    locales.get(locale_id).put(key.toString(), value.toString());
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
                }
            }
        }
    }
    
    public Map<String, String> getLocalizedText() {
        if (locales.containsKey(locale)) {
            return locales.get(locale);
        }
        
        return null;
    }
}
