
package MyClientPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateAndroidUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateAndroidUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fromClient" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateAndroidUser", propOrder = {
    "fromClient"
})
public class AuthenticateAndroidUser {

    protected String fromClient;

    /**
     * Gets the value of the fromClient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromClient() {
        return fromClient;
    }

    /**
     * Sets the value of the fromClient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromClient(String value) {
        this.fromClient = value;
    }

}
