package com.iridiumit.gestaopetshop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.iridiumit.gestaopetshop.model.Animal;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${petshop.s3.endpointUrl}")
	private String endpointUrl;
	@Value("${petshop.s3.bucket}")
	private String bucketName;
	@Value("${petshop.aws.access_key_id}")
	private String accessKey;
	@Value("${petshop.aws.secret_access_key}")
	private String secretKey;
	@Value("${petshop.s3.region}")
	private String regiao;
	

	@PostConstruct
	private void initializeAmazon() {

		//BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		
		BasicAWSCredentials credentials = new BasicAWSCredentials("AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY");
		this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(this.regiao)
				.build();
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart, Animal animal) {
		return animal.getCliente().getId() + "-" + animal.getNome().replace(" ", "_") + "-" + new Date().getTime();
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public String uploadFile(MultipartFile multipartFile, Animal animal) {

		String fileUrl = "";
		String fileName = "";
		
		try {
			File file = convertMultiPartToFile(multipartFile);
			fileName = generateFileName(multipartFile, animal);
			fileUrl = endpointUrl + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			file.delete();
		} catch (Exception e) {
			System.out.println("Erro ao gravar o arquivo: " + fileName);
			System.out.println(e.getMessage());
			return "erro";
		}
		
		return fileUrl;
	}

	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		return fileName + " - Successfully deleted";
	}
	
}
