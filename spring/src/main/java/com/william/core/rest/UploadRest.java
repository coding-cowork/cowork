package com.william.core.rest;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.william.core.service.UploadService;

/**
 * 
 * @author William
 *
 */
@Controller
public class UploadRest {

	private Logger log = LoggerFactory.getLogger(UploadRest.class);

	@Autowired
	private UploadService upload;

	/**
	 * upload file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> uploadFileHandler(
			@RequestBody MultipartFile file) throws IOException {
		log.info("~~~Start Uploading~~~");
		if (!file.isEmpty()) {
			try {
				upload.getContentFromExcel(file.getInputStream());
			} catch (Exception e) {

			}
		} else {

		}

		return new ResponseEntity<Object>("", HttpStatus.OK);
	}
}
