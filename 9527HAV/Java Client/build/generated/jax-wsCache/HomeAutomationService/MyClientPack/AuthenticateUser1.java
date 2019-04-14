
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateUser1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateUser1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fromclient" type="{http://MyServerPack/}homeAutomationUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateUser1", propOrder = {
    "fromclient"
})
public class AuthenticateUser1 {

    protected HomeAutomationUser fromclient;

    /**
     * Gets the value of the fromclient property.
     * 
     * @return
     *     possible object is
     *     {@link HomeAutomationUser }
     *     
     */
    public HomeAutomationUser getFromclient() {
        return fromclient;
    }

    /**
     * Sets the value of the fromclient property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeAutomationUser }
     *     
     */
    public void setFromclient(HomeAutomationUser value) {
        this.fromclient = value;
    }

}
