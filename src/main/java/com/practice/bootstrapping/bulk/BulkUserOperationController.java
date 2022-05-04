package com.practice.bootstrapping.bulk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practice.bootstrapping.wrapper.BootstrapResponse;

@RestController
@RequestMapping(path = "/user-bulk")
public class BulkUserOperationController {

	@Autowired
	private BulkUserOperationService service;

	@PostMapping
	public BootstrapResponse bulkInsert(@RequestParam("file") MultipartFile file) {
		return new BootstrapResponse(service.bulkUserProcessing(file), HttpStatus.PROCESSING.name()+"...");
	}

}
