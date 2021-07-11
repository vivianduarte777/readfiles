package apirestfiles.readfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages={"apirestfiles.readfiles.controller"})
public class ReaFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaFileApplication.class, args);
	}

}
