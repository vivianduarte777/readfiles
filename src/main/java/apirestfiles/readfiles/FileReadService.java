package apirestfiles.readfiles;

import apirestfiles.readfiles.model.FilesInformationDto;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.util.*;

@Service
@Scope("prototype") //singleton
public class FileReadService {
    private String urlAddress = null;
    public Map<String, FilesInformationDto> mapFiles = null;
    public List<FilesInformationDto> filesDto = null;

    Logger logger = LoggerFactory.getLogger(FileReadService.class);

    public void init(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    //Insert the Map object throw the File read
    @Async
    public Map<String, FilesInformationDto> buildMapFiles(String urlAddress) {
       long start = System.currentTimeMillis();
        mapFiles = new HashMap<>();
        int cont = 0;
        try {
            Document doc = Jsoup.connect(urlAddress.toString()).get();

            Elements allElements = doc.getAllElements();

            Elements elements = doc.getElementsByClass("js-navigation-open Link--primary");
            //Check all elements to take the files name
            for (Element e : elements) {
                String name = e.attr("title");
                int lgt = name.length();
                int indexOf = name.indexOf(".");
                String extension = name.substring(indexOf, lgt);
                try {
                    String urFileStr = null;
                 if(extension.equals(".xml")) {
                  urFileStr = "https://raw.githubusercontent.com/"+ urlAddress.substring(19,urlAddress.length()) +"main/" +name;
                 }else {
                      urFileStr = urlAddress + "raw/main/" + name;
                 }
                    try {
                        URI uriFile = new URI(urFileStr);
                        URL urlFile = getURL(urFileStr);
                        HttpURLConnection httpConn = (HttpURLConnection) urlFile.openConnection();
                        int responseCode = httpConn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            int lines = returnLines(httpConn,name);
                            int buffers = returnBytesCount(httpConn);
                            //Check if the mapFiles contains the extension
                            if(mapFiles.containsKey(extension)){
                                cont++;
                                FilesInformationDto old = mapFiles.get(extension);
                                FilesInformationDto dto = calculateNewInformationDto(old,extension,cont,lines,buffers);
                                mapFiles.replace("extension",old,dto);
                            }else{
                                cont = 1;
                                String strName = name + " lines = " + lines + " buffers " + buffers;
                                FilesInformationDto dto = buildInformationDto(extension,cont,lines,buffers);
                                mapFiles.put(extension,dto);
                            }
                        }
                        httpConn.disconnect();
                    } catch (Exception errFile) {
                        System.out.println(errFile.getMessage());
                    }
                } catch (Exception errFiles) {
                    System.out.println(errFiles.getMessage());
                }
            }
        }catch(Exception e){
            return null;
        }
        long end = System.currentTimeMillis();
        logger.info("Recovering the Files Map: ",Thread.currentThread().getId());
        logger.info("Time start to: "+start + " and end to: " +end + " taken to: " + (end-start) + " time!");
        return mapFiles;
    }

    //Create the new Dto with the new values
    @Async
    private FilesInformationDto calculateNewInformationDto(FilesInformationDto old, String extension, int contDto, int lines, int bytes) {
        int newLines = lines + old.getLines();
        int newBytes = bytes + old.getBytes();
        FilesInformationDto newDto = new FilesInformationDto(extension,contDto,newLines,newBytes);
        return newDto;

    }

    //Take the URL object from the String url
    @Async
    private URL getURL(String urlAddress) throws MalformedURLException {
        return new URL(urlAddress);
    }
    //Take the number of line from the particular file
    @Async
    private int returnLines(HttpURLConnection httpConn,String fileName) throws IOException {
        int linesNum = 1;

       InputStream inputStream = httpConn.getInputStream();

        Scanner scan = new Scanner(new BufferedInputStream(inputStream), "UTF-8");
        StringBuilder sb = new StringBuilder();

        while (scan.hasNextLine()) {
            System.out.println("FileName " + fileName + " Line " + linesNum);
            sb.append(scan.nextLine());
            sb.append("\n");
             ++linesNum;
        }
        scan.close();

        return linesNum;
    }


    //Take the number of Bytes from the particular file
    @Async
    private int returnBytesCount (HttpURLConnection httpConn) throws IOException {
        InputStream inputStream = new URL(httpConn.getURL().toString()).openStream();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, bas);

        return bas.toByteArray().length;

    }

    //Build InformationDto
    @Async
    private FilesInformationDto buildInformationDto(String extension, int count, int lines, int bytes){
        FilesInformationDto dto = new FilesInformationDto(extension,count, lines, bytes);
        return dto;
    }

    //Build the list FilesInformation Dto throw map
    @Async
    public  List<FilesInformationDto> buildFilesInformationDto(Map<String, FilesInformationDto> mapFiles){
        filesDto = new ArrayList<FilesInformationDto>();
        Iterator it = mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            filesDto.add((FilesInformationDto)pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return filesDto;
    }


}