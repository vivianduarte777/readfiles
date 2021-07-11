package apirestfiles.readfiles.model;


import org.springframework.context.annotation.Bean;


public class ReturnInf {
    private String information;
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
