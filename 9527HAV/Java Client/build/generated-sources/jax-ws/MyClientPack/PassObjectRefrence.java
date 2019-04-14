
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PassObjectRefrence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PassObjectRefrence">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="obj" type="{http://MyServerPack/}homeAutomationUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PassObjectRefrence", propOrder = {
    "obj"
})
public class PassObjectRefrence {

    protected HomeAutomationUser obj;

    /**
     * Gets the value of the obj property.
     * 
     * @return
     *     possible object is
     *     {@link HomeAutomationUser }
     *     
     */
    public HomeAutomationUser getObj() {
        return obj;
    }

    /**
     * Sets the value of the obj property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeAutomationUser }
     *     
     */
    public void setObj(HomeAutomationUser value) {
        this.obj = value;
    }

}
