package no.ntnu.asd.prosjektfil;

/**
 * Created by martin on 27.10.2016.
 */

public class User {

    private String FIRSTNAME;
    private String LASTNAME;
    private Long ID;
    private String HOME;
    private String INFORMATION;

    public User(String FIRSTNAME, String LASTNAME, Long ID, String HOME, String INFORMATION){
        this.FIRSTNAME = FIRSTNAME;
        this.LASTNAME = LASTNAME;
        this.ID = ID;
        this.HOME = HOME;
        this.INFORMATION = INFORMATION;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getHOME() {
        return HOME;
    }

    public void setHOME(String HOME) {
        this.HOME = HOME;
    }

    public String getINFORMATION() {
        return INFORMATION;
    }

    public void setINFORMATION(String INFORMATION) {
        this.INFORMATION = INFORMATION;
    }
}
