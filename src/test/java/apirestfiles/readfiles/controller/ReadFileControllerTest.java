package apirestfiles.readfiles.controller;

import apirestfiles.readfiles.model.ReturnInf;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = ReadFileController.class)
class ReadFileControllerTest {

    @Autowired
    //private MockMvc mockMvc;
    private TestRestTemplate restTemplate;


    //Tests if the object Model returned is not null or is not empty
    @Test
    public void tesModel() throws  Exception{
        ReturnInf model = this.restTemplate//
        .getForObject(new URI("https://github.com"), ReturnInf.class);
        Assert.assertNotNull(model);
        Assert.assertNotEquals(model,model.getFileInformation().toString().isEmpty());
    }

    //Tests if the response of the HttpStatus is Ok
    @Test
    public void testAddressResponse(){
       String urlAddress = "https://github.com";
       ResponseEntity<String> response = restTemplate.getForEntity(urlAddress+"/1/",String.class);
       Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    //Tests if the address informed is empty
    @Test
    public void testAddress(){
        String urlAddress = "https://github.com";
        Assert.assertNotEquals(urlAddress,urlAddress.isEmpty());
    }
}
