package com.iridiumit.controleos;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iridiumit.controleos.controller.EquipamentoController;

@SpringBootApplication
public class CadastroOrdemServicoApplication {

	public static void main(String[] args) {
		new File(EquipamentoController.uploadDirectory).mkdir();
		SpringApplication.run(CadastroOrdemServicoApplication.class, args);
	}
}
