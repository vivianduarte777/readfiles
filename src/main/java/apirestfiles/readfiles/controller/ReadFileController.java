package apirestfiles.readfiles.controller;

import apirestfiles.readfiles.FileReadService;
import apirestfiles.readfiles.model.ReturnInf;
import apirestfiles.readfiles.model.FilesInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReadFileController{

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;
    private String errorAddress =  "Type the address";
    private String errorBinding =  "Error binding";

    @Autowired
    FileReadService service;

   @RequestMapping(value="/templates",method = RequestMethod.GET)

    public ModelAndView index(Model model){
        ReturnInf returninf = new ReturnInf();

        model.addAttribute("returninf",returninf);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject(returninf);
        return modelAndView;
    }

    @RequestMapping(value="result",method = RequestMethod.POST)
    //@ResponseBody
    public ModelAndView postForm(@ModelAttribute("returninf") ReturnInf returninf, Model model, BindingResult bindResult) {
        model.addAttribute(returninf);
        ModelAndView modelAndView = new ModelAndView("result");

        if(bindResult.hasErrors()){
            returninf.setInformation(errorBinding);
            model.addAttribute("information",returninf.getInformation());
            model.addAttribute("address",returninf.getAddress());
            modelAndView.addObject(returninf);
            return modelAndView;
        }
        String urlAddress = returninf.getAddress();

        if(urlAddress!=null &&!urlAddress.isEmpty()) {
            returninf.setInformation(getFilesInformation(urlAddress));
            model.addAttribute("information",returninf.getInformation());
            model.addAttribute("address",returninf.getAddress());
            modelAndView.addObject(returninf);
        }else {
            returninf.setInformation(errorAddress);
            model.addAttribute("information",returninf.getInformation());
            model.addAttribute("address",returninf.getAddress());
            modelAndView.addObject(returninf);
        }
        return modelAndView;

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
                            + dto.getLines() + " Bytes: " + dto.getBytes()+"/n";
                }
                strReturn = strReturn +  "}";
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