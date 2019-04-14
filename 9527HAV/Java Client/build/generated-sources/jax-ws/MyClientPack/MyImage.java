
package MyClientPack;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for myImage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="myImage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="img" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ww" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hh" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "myImage", propOrder = {
    "img",
    "ww",
    "hh"
})
public class MyImage {

    @XmlElement(nillable = true)
    protected List<Integer> img;
    protected int ww;
    protected int hh;

    /**
     * Gets the value of the img property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the img property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getImg() {
        if (img == null) {
            img = new ArrayList<Integer>();
        }
        return this.img;
    }

    /**
     * Gets the value of the ww property.
     * 
     */
    public int getWw() {
        return ww;
    }

    /**
     * Sets the value of the ww property.
     * 
     */
    public void setWw(int value) {
        this.ww = value;
    }

    /**
     * Gets the value of the hh property.
     * 
     */
    public int getHh() {
        return hh;
    }

    /**
     * Sets the value of the hh property.
     * 
     */
    public void setHh(int value) {
        this.hh = value;
    }

}
