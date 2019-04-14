
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pass complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pass">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://MyServerPack/}deviceDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pass", propOrder = {
    "parameter"
})
public class Pass {

    protected DeviceDetails parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceDetails }
     *     
     */
    public DeviceDetails getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceDetails }
     *     
     */
    public void setParameter(DeviceDetails value) {
        this.parameter = value;
    }

}
