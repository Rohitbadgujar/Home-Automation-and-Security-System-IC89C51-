
package MyClientPack;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updataJavaData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updataJavaData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceData" type="{http://MyServerPack/}deviceDetails" minOccurs="0"/>
 *         &lt;element name="imageArray" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updataJavaData", propOrder = {
    "deviceData",
    "imageArray",
    "state"
})
public class UpdataJavaData {

    protected DeviceDetails deviceData;
    @XmlElementRef(name = "imageArray", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> imageArray;
    protected int state;

    /**
     * Gets the value of the deviceData property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceDetails }
     *     
     */
    public DeviceDetails getDeviceData() {
        return deviceData;
    }

    /**
     * Sets the value of the deviceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceDetails }
     *     
     */
    public void setDeviceData(DeviceDetails value) {
        this.deviceData = value;
    }

    /**
     * Gets the value of the imageArray property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getImageArray() {
        return imageArray;
    }

    /**
     * Sets the value of the imageArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setImageArray(JAXBElement<byte[]> value) {
        this.imageArray = ((JAXBElement<byte[]> ) value);
    }

    /**
     * Gets the value of the state property.
     * 
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     */
    public void setState(int value) {
        this.state = value;
    }

}
