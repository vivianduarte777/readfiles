package apirestfiles.readfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {EnableAutoConfiguration.class})
public class ReaFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaFileApplication.class, args);
	}

}
