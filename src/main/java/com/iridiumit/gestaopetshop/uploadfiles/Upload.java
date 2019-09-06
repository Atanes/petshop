package com.iridiumit.gestaopetshop.uploadfiles;

import static java.nio.file.FileSystems.getDefault;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class Upload {

	private UploadForm uploadForm;

	private Path local;
	private Path localTemporario;

	public Upload() {
		criarPastas();
	}

	private void criarPastas() {
		
		Map<String, String> map = System.getenv();
        map.entrySet().forEach(System.out::println);
        
		try {
			this.local = getDefault().getPath(System.getenv("USERPROFILE"), "//animaisfotos");
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);

			System.out.println("Pastas criadas com sucesso!!" + this.local);
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	public boolean gravarArquivo(UploadForm uploadForm) {
		
		this.uploadForm = uploadForm;
		

		File uploadRootDir = new File(this.local.toString());
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile file = this.uploadForm.getFile();

		// Client File Name
		String name = file.getOriginalFilename();
		System.out.println("Nome do arquivo = " + name);

		// Client File Content Type
		String contentType = file.getContentType();
		System.out.println("Tipo de arquivo: " + contentType);

		if (name != null && name.length() > 0) {
			try {
				// Create the file at server
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.close();

				System.out.println("Arquivo gravado: " + serverFile);
			} catch (Exception e) {
				System.out.println("Erro ao gravar arquivo: " + name);
				return false;
			}
		}

		return true;
	}

}
