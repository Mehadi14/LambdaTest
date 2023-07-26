package BrowserSatckApis.DemoApis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApisApplication.class, args);

		String name="Windows 2008";
		String replace = name.replace(" ", "-");
		System.out.println(replace);
	}





}
