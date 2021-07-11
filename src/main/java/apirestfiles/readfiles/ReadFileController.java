package apirestfiles.readfiles;

import apirestfiles.readfiles.FileReadService;
import apirestfiles.readfiles.model.ReturnInf;
import apirestfiles.readfiles.model.FilesInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
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

   @Autowired
    FileReadService service;

    @GetMapping("/templates")
    public ModelAndView index(Model model){
        model.addAttribute("returninf",new ReturnInf());

      return new ModelAndView("index");
    }

   @RequestMapping(value = "read",method = RequestMethod.POST)
    public ReturnInf postForm(@ModelAttribute("returninf") ReturnInf returninf) {
        String urlAddress = returninf.getUrlAddress();

        if(urlAddress==null ||urlAddress.isEmpty()){
            returninf.setFileInformation(errorAddress);
         //   model.addAttribute(infidel);
            return returninf;
       }

       returninf.setFileInformation(getFilesInformation(returninf.getUrlAddress()));
       // model.addAttribute(infidel);
       //returninf.setFileInformation("teste");
        return returninf;
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
