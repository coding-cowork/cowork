package com.william.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.william.core.dao.ValidatedDao;
import com.william.core.model.User;

/**
 * 
 * @author William
 *
 */
@Component
@Service
public class ValidatedServiceImpl implements ValidatedService {

	@Autowired
	private ValidatedDao validator;

	@Override
	public boolean checkUser(User user) {
		return validator.validatedUser(user);
	}

}
