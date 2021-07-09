package apirestfiles.readfiles.controller;

import apirestfiles.readfiles.FileReadService;
import apirestfiles.readfiles.model.FileInformationModel;
import apirestfiles.readfiles.model.FilesInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReadFileController{
    //private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;

   @Autowired
    FileReadService service;

    @GetMapping("/templates")
    public ModelAndView index(Model model){
        model.addAttribute("infidel",new FileInformationModel());
        return new ModelAndView("index");
    }

    @PostMapping("read")
    public String postForm(@ModelAttribute FileInformationModel infidel, Model model) {
        FileInformationModel m = new FileInformationModel();
        String urlAddress = infidel.getUrlAddress();
        m.setUrlAddress(urlAddress);
        model.addAttribute("urlAddress", m);
        m.setFileInformation(getFilesInformation(infidel.getUrlAddress()));
        model.addAttribute("fileInformation",m.getFileInformation());
        return m.getFileInformation();
    }

    public List<FilesInformationDto> getListDto() {
        return listDto;
    }

    public void setListDto(List<FilesInformationDto> listDto) {
        this.listDto = listDto;
    }

    //Return the String with the Information about the files readed
    private String getFilesInformation(String urlAddress){
        String strReturn = null;
        try {
            service =  new FileReadService();
            this.mapFiles=service.buildMapFiles(urlAddress);
            this.listDto=service.buildFilesInformationDto(mapFiles);

            strReturn = "{";
            for(FilesInformationDto dto: listDto) {
                strReturn = strReturn + "File extension: " + dto.getExtension() + ", Counts: " + dto.getCount() + " Lines: "//
                        + dto.getLines() + " Bytes: " + dto.getBytes() +"}";
            }

        }catch(Exception e){
            return e.getMessage();
        }
        return strReturn;

    }
}
