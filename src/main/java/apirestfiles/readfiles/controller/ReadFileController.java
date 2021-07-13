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
//@RequestMapping("/api")
@RequestMapping("/")
public class ReadFileController{

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;
    private String errorAddress =  "Type the address";
    private String errorBinding =  "Error binding";

    @Autowired
    FileReadService service;



   // @RequestMapping(value="/",method = RequestMethod.GET)
    @GetMapping("/")
    public ModelAndView index(Model model){
        ReturnInf returnInf = new ReturnInf();
        returnInf.setAddress("https://github.com/vivianduarte777/filesTest/");
       // String information = getFilesInformation(returnInf.getAddress());
        String information = "";
        model.addAttribute("returninf",returnInf);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(model);
        return modelAndView;
   }

    //@RequestMapping(value="result",method = RequestMethod.POST)
    @PostMapping("/result")
    @ResponseBody
    public ModelAndView postForm(@ModelAttribute("returninf") ReturnInf returnInf, Model model, BindingResult bindResult) {
        returnInf.setAddress("https://github.com/vivianduarte777/filesTest/");

        model.addAttribute(returnInf);
        ModelAndView modelAndView = new ModelAndView("result");

        if(bindResult.hasErrors()){
            returnInf.setInformation(errorBinding);
            model.addAttribute("information",returnInf.getInformation());
            model.addAttribute("address",returnInf.getAddress());
            modelAndView.addObject(returnInf);
            return modelAndView;
        }
        String urlAddress = returnInf.getAddress();

        if(urlAddress!=null &&!urlAddress.isEmpty()) {
            returnInf.setInformation(getFilesInformation(urlAddress));
            model.addAttribute("information",returnInf.getInformation());
            model.addAttribute("address",returnInf.getAddress());
            modelAndView.addObject(returnInf);
        }else {
            returnInf.setInformation(errorAddress);
            model.addAttribute("information",returnInf.getInformation());
            model.addAttribute("address",returnInf.getAddress());
            modelAndView.addObject(returnInf);
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