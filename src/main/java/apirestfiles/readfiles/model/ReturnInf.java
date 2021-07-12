package apirestfiles.readfiles.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReturnInf {
    @Autowired
    private String information;
    @Autowired
    private String urladdress;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrladdress() {
        return urladdress;
    }

    public void setUrladdress(String urladdress) {
        this.urladdress = urladdress;
    }
}
