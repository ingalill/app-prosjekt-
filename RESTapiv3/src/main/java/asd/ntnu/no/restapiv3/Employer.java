/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @version 07 november 2016
 * @author inga lill bjølstad
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employer.findAll", query = "SELECT e FROM Employer e")
    , @NamedQuery(name = "Employer.findById", query = "SELECT e FROM Employer e WHERE e.id =:id")
    , @NamedQuery(name = "Employer.findByName", query ="SELECT e FROM Employer e WHERE e.name =:name")    
    , @NamedQuery(name = "Employer.findByPhone", query = "SELECT e FROM Employer e WHERE e.phone =:phone")
    , @NamedQuery(name = "Employer.findByCompany", query = "SELECT e FROM Employer e WHERE e.company =:company")})
public class Employer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = 40)
    private String name;

    @Size(max = 50)
    private String company;

    @Size(max = 10)
    private String phone;

    public Employer() {
    }

    public Employer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

} // end of class
