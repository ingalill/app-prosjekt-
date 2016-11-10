package no.ntnu.asd.prosjektfil;

/**
 * Created by ingalill on 04/11/2016.
 */

public class Employer {

    private String name;
    private String company;
    private Long employerId;
    private String phone;

    public Employer(Long employerId, String name, String company, String phone){
        this.name = name;
        this.company = company;
        this.phone = phone;
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
    public void setPhone(String newPhone){
        this.phone = newPhone;
    }
    public String getPhone(){
        return phone;
    }


} // end of class
