package com.iridiumit.gestaopetshop.uploadfiles;

import org.springframework.web.multipart.MultipartFile;


public class UploadForm {
 
    private String contentType;
 
    // Upload files.
    private MultipartFile file;
 
    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
 
}
