package no.ntnu.asd.prosjektfil;

/**
 * Created by ingalill on 04/11/2016.
 */

public class Employer {

    private String name;
    private String company;
    private Long employerId;

    public Employer(Long employerId, String name, String company){
        this.name = name;
        this.company = company;
        this.employerId = employerId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getCompany(){
        return company;
    }

    public Long getEmployerId(){
        return employerId;
    }


} // end of class
