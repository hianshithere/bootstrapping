package com.practice.bootstrapping.bulk;

import java.io.IOException;
import java.lang.module.FindException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.helper.ExcelHelper;
import com.practice.bootstrapping.repositories.UserRepository;

@Component
@SuppressWarnings({ "unused", "rawtypes" })
public class BulkUserOperationService {

	@Autowired
	private UserRepository userRepository;

	public Iterable bulkUserProcessing(MultipartFile file) {
		try {
			List<User> excelToUsers = ExcelHelper.excelToUsers(file.getInputStream());
			
			return userRepository.saveAll(excelToUsers);
			
		} catch (IOException e) {
			throw new FindException("File is not readable!");
		}
	}

}
