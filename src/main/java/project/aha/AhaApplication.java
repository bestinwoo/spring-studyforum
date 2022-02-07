package project.aha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = AhaApplication.class)
@SpringBootApplication
public class AhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhaApplication.class, args);
	}

}
