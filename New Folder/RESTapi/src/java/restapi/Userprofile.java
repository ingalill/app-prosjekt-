/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapi;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @version 1
 * @author ingalillbjolstad
 */
@Entity
@Table(name = "USERPROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userprofile.findAll", query = "SELECT u FROM Userprofile u")
    , @NamedQuery(name = "Userprofile.findByFirstname", query = "SELECT u FROM Userprofile u WHERE u.firstname = :firstname")
    , @NamedQuery(name = "Userprofile.findById", query = "SELECT u FROM Userprofile u WHERE u.id = :id")
    , @NamedQuery(name = "Userprofile.findByLastname", query = "SELECT u FROM Userprofile u WHERE u.lastname = :lastname")
    , @NamedQuery(name = "Userprofile.findByHome", query = "SELECT u FROM Userprofile u WHERE u.home = :home")
    , @NamedQuery(name = "Userprofile.findByInformation", query = "SELECT u FROM Userprofile u WHERE u.information = :information")})
public class Userprofile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 50)
    @Column(name = "HOME")
    private String home;
    @Size(max = 100)
    @Column(name = "INFORMATION")
    private String information;

    public Userprofile() {
    }

    public Userprofile(Integer id) {
        this.id = id;
    }

    /**
     * Get the first name of the User
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Set the first name of the user
     * @param firstname 
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * Get the id of the user
     * @return id
     */
    public Integer getId() {
        return id;
    }
    /*
    public void setId(Integer id) {
        this.id = id;
    } */
    
    /**
     * Get the last name of the user
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }
    
    /**
     * Set the lastname of the user
     * @param lastname 
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    /**
     * Get the place where the user lives
     * @return home
     */
    public String getHome() {
        return home;
    }
    
    /**
     * Set the place where the user lives
     * @param home 
     */
    public void setHome(String home) {
        this.home = home;
    }
    
    /**
     * Get the information about the user
     * @return information
     */
    public String getInformation() {
        return information;
    }
    
    /**
     * Set the information about the user.
     * @param information 
     */
    public void setInformation(String information) {
        this.information = information;
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
        return "restapi.Userprofile[ id=" + id + " ]";
    }
    
}
