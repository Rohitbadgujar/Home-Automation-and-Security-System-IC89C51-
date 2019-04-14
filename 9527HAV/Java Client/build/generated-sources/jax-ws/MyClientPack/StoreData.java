
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StoreData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StoreData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fromClient" type="{http://MyServerPack/}homeAutomationUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StoreData", propOrder = {
    "fromClient"
})
public class StoreData {

    protected HomeAutomationUser fromClient;

    /**
     * Gets the value of the fromClient property.
     * 
     * @return
     *     possible object is
     *     {@link HomeAutomationUser }
     *     
     */
    public HomeAutomationUser getFromClient() {
        return fromClient;
    }

    /**
     * Sets the value of the fromClient property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeAutomationUser }
     *     
     */
    public void setFromClient(HomeAutomationUser value) {
        this.fromClient = value;
    }

}
