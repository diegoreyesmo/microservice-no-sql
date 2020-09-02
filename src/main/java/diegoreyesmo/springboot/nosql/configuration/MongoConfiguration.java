package diegoreyesmo.springboot.nosql.configuration;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;

import diegoreyesmo.springboot.nosql.util.Util;
import lombok.Data;

@Data
@Configuration
public class MongoConfiguration {
	@Value("${mongodb.user}")
	private String user;
	@Value("${mongodb.password}")
	private String password;
	@Value("${mongodb.source}")
	private String source;
	@Value("${mongodb.hostname}")
	private String hostname;
	@Value("${mongodb.port}")
	private int port;

	@Bean
	public List<MongoClient> mongoClient() {
		return new ArrayList<>();
	}

	@Bean
	public Document document() {
		return new Document();
	}

	@Bean
	public Util util() {
		return new Util();
	}
}
