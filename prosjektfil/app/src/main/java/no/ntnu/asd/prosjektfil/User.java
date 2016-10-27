package no.ntnu.asd.prosjektfil;

import java.io.Serializable;

/**
 * Created by martin on 27.10.2016.
 */

public class User implements Serializable {

    private String firstname;
    private String lastname;
    private Long id;
    private String home;
    private String information;

    public User(String firstname, String lastname,String home, String information){ // Long id
        this.firstname = firstname;
        this.lastname = lastname;
        //this.id = id;
        this.home = home;
        this.information = information;
    }

    public User(Long id,String firstname, String lastname,String home, String information){
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.home = home;
        this.information = information;
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

    public Long getID() {

        return id;
    }

   /*public void setID(Long id) {
        this.id = id;
    } */

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
}