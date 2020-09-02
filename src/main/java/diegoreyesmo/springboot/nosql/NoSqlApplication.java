package diegoreyesmo.springboot.no-sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NoSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoSqlApplication.class, args);
	}

}
