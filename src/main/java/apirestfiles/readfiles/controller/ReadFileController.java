package apirestfiles.readfiles.controller;

import apirestfiles.readfiles.FileReadService;
import apirestfiles.readfiles.model.FileInformationModel;
import apirestfiles.readfiles.model.FilesInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReadFileController{

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;
    private String errorAddress =  "Type the address";

   @Autowired
    FileReadService service;

    @GetMapping("/templates")
    public ModelAndView index(Model model){
        model.addAttribute("infidel",new FileInformationModel());

      return new ModelAndView("index");
    }

   @GetMapping("read")
    public FileInformationModel postForm(@ModelAttribute("infidel") FileInformationModel infidel,Model model) {
        String urlAddress = infidel.getUrlAddress();

        if(urlAddress==null ||urlAddress.isEmpty()){
            infidel.setFileInformation(errorAddress);
            return infidel;
       }

        infidel.setFileInformation(getFilesInformation(infidel.getUrlAddress()));
        model.addAttribute(infidel);
        return infidel;
    }


    //Return the String with the Information about the files readed
    @Async
    private String getFilesInformation(String urlAddress){
        String strReturn = null;
        try {
            service =  new FileReadService();
            this.mapFiles=service.buildMapFiles(urlAddress);
            this.listDto=service.buildFilesInformationDto(mapFiles);

            if(!this.listDto.isEmpty()&&this.listDto!=null) {
                strReturn = "{";
                for (FilesInformationDto dto : listDto) {
                    strReturn = strReturn + "File extension: " + dto.getExtension() + ", Counts: " + dto.getCount() + " Lines: "//
                            + dto.getLines() + " Bytes: " + dto.getBytes() + "}";
                }
            }

        }catch(Exception e){
            return e.getMessage();
        }
        if(strReturn == null){
            strReturn = "There is no file in the address. Check if you typed correctly with '/' at the end.";
        }
        return strReturn;

    }
}
