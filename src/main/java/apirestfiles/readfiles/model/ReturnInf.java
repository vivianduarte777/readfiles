package apirestfiles.readfiles.model;


import org.springframework.context.annotation.Bean;


public class ReturnInf {
    private String information;
    private String urlAddress;

    @Bean
    public String getUrlAddress() {
        return urlAddress;
    }

    @Bean
    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Bean
    public String getFileInformation() {
        return information;
    }

    @Bean
    public void setFileInformation(String fileInformation) {
        this.information = fileInformation;
    }
}
