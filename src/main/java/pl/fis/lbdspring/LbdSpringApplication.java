package pl.fis.lbdspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan("pl.fis")
@SpringBootApplication
public class LbdSpringApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(LbdSpringApplication.class, args);
	}

}
