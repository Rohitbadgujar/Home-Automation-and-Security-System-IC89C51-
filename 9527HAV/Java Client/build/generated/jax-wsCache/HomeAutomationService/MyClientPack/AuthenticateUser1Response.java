
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateUser1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateUser1Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://MyServerPack/}homeAutomationUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateUser1Response", propOrder = {
    "_return"
})
public class AuthenticateUser1Response {

    @XmlElement(name = "return")
    protected HomeAutomationUser _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link HomeAutomationUser }
     *     
     */
    public HomeAutomationUser getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeAutomationUser }
     *     
     */
    public void setReturn(HomeAutomationUser value) {
        this._return = value;
    }

}
