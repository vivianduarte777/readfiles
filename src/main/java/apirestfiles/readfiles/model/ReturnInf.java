package apirestfiles.readfiles.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


public class ReturnInf {
    @Autowired
    private String information;
    @Autowired
    private String address;
    @Autowired
public String getInformation() {
        return information;
    }
    @Autowired
    public void setInformation(String information) {
        this.information = information;
    }
    @Autowired
 public String getAddress() {
        return address;
    }
    @Autowired
    public void setAddress(String address) {
        this.address = address;
    }
}
