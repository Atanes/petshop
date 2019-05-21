package com.iridiumit.gestaopetshop;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iridiumit.gestaopetshop.controller.EquipamentoController;

@SpringBootApplication
public class GestaoPetShopApplication {

	public static void main(String[] args) {
		new File(EquipamentoController.uploadDirectory).mkdir();
		SpringApplication.run(GestaoPetShopApplication.class, args);
	}
}
