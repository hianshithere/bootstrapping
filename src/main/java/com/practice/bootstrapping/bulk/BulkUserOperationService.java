package com.practice.bootstrapping.bulk;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.helper.ExcelHelper;
import com.practice.bootstrapping.repositories.UserRepository;

@Component
@SuppressWarnings({ "unused", "rawtypes" })
public class BulkUserOperationService {

	private  final UserRepository userRepository;
	public BulkUserOperationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Iterable bulkUserProcessing(MultipartFile file) {
		try {
			List<User> excelToUsers = ExcelHelper.excelToUsers(file.getInputStream());
			return userRepository.saveAll(excelToUsers);
		} catch (IOException e) {
			throw new RuntimeException("File is not readable!");
		}
	}

}
