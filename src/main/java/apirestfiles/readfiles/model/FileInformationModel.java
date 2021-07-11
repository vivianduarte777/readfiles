package apirestfiles.readfiles.model;


import org.springframework.web.bind.annotation.ResponseBody;

public class FileInformationModel {
    private String fileInformation;
    private String urlAddress;

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @ResponseBody
    public String getFileInformation() {
        return fileInformation;
    }

    public void setFileInformation(String fileInformation) {
        this.fileInformation = fileInformation;
    }
}
