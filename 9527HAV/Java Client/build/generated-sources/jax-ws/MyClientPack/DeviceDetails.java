
package MyClientPack;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deviceDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sensor" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="th" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deviceStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="feedback" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allControl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auto" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceDetails", propOrder = {
    "sensor",
    "th",
    "deviceStatus",
    "feedback",
    "allControl",
    "userId",
    "auto"
})
public class DeviceDetails {

    @XmlElement(nillable = true)
    protected List<Integer> sensor;
    @XmlElement(nillable = true)
    protected List<Integer> th;
    protected int deviceStatus;
    protected String feedback;
    protected String allControl;
    protected String userId;
    @XmlElement(nillable = true)
    protected List<String> auto;

    /**
     * Gets the value of the sensor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sensor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSensor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getSensor() {
        if (sensor == null) {
            sensor = new ArrayList<Integer>();
        }
        return this.sensor;
    }

    /**
     * Gets the value of the th property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the th property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTh().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getTh() {
        if (th == null) {
            th = new ArrayList<Integer>();
        }
        return this.th;
    }

    /**
     * Gets the value of the deviceStatus property.
     * 
     */
    public int getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Sets the value of the deviceStatus property.
     * 
     */
    public void setDeviceStatus(int value) {
        this.deviceStatus = value;
    }

    /**
     * Gets the value of the feedback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the value of the feedback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeedback(String value) {
        this.feedback = value;
    }

    /**
     * Gets the value of the allControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllControl() {
        return allControl;
    }

    /**
     * Sets the value of the allControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllControl(String value) {
        this.allControl = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the auto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the auto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAuto() {
        if (auto == null) {
            auto = new ArrayList<String>();
        }
        return this.auto;
    }

}
