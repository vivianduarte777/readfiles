package apirestfiles.readfiles.model;


import org.springframework.web.bind.annotation.ResponseBody;

public class ReturnInf {
    private String information;
    private String urlAddress;

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @ResponseBody
    public String getFileInformation() {
        return information;
    }

    public void setFileInformation(String fileInformation) {
        this.information = fileInformation;
    }
}
