package com.iridiumit.gestaopetshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iridiumit.gestaopetshop.uploadfiles.Upload;

@SpringBootApplication
public class GestaoPetShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoPetShopApplication.class, args);
		new Upload();
	}
}
