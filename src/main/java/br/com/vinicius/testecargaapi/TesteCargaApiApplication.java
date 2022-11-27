package br.com.vinicius.testecargaapi;

import br.com.vinicius.testecargaapi.dataprovider.model.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "br.com.vinicius.testecargaapi.dataprovider.model")
@SpringBootApplication
public class TesteCargaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteCargaApiApplication.class, args);
	}

}
