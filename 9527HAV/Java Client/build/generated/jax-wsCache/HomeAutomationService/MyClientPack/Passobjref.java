
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for passobjref complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="passobjref">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter1" type="{http://MyServerPack/}homeAutomationUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "passobjref", propOrder = {
    "parameter1"
})
public class Passobjref {

    protected HomeAutomationUser parameter1;

    /**
     * Gets the value of the parameter1 property.
     * 
     * @return
     *     possible object is
     *     {@link HomeAutomationUser }
     *     
     */
    public HomeAutomationUser getParameter1() {
        return parameter1;
    }

    /**
     * Sets the value of the parameter1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeAutomationUser }
     *     
     */
    public void setParameter1(HomeAutomationUser value) {
        this.parameter1 = value;
    }

}
