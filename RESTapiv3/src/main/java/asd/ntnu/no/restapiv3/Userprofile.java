/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @version 
 * @author inga lill
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD) // test
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userprofile.findAll", query = "SELECT u FROM Userprofile u")
    , @NamedQuery(name = "Userprofile.findById", query = "SELECT u FROM Userprofile u WHERE u.id = :id")
    , @NamedQuery(name = "Userprofile.findByFirstname", query = "SELECT u FROM Userprofile u WHERE u.firstname = :firstname")
    , @NamedQuery(name = "Userprofile.findByLastname", query = "SELECT u FROM Userprofile u WHERE u.lastname = :lastname")
    , @NamedQuery(name = "Userprofile.findByHome", query = "SELECT u FROM Userprofile u WHERE u.home = :home")
    , @NamedQuery(name = "Userprofile.findByInformation", query = "SELECT u FROM Userprofile u WHERE u.information = :information")
    , @NamedQuery(name = "Userprofile.findByPhone", query = "SELECT u FROM Userprofile u WHERE u.phone = :phone")})
public class Userprofile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private Long id;
    
    @Size(max = 20)
    private String firstname;

    @Size(max = 20)
    private String lastname;
    
    @Size(max = 50)
    private String home;

    @Size(max = 100)
    private String information;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 10)
    private String phone;

    public Userprofile() {
    }

    public Userprofile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userprofile)) {
            return false;
        }
        Userprofile other = (Userprofile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "asd.ntnu.no.restapiv3.Userprofile[ id=" + id + " ]";
    }
    
}
