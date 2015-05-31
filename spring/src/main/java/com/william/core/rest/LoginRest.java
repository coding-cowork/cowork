package com.william.core.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.william.core.model.User;
import com.william.core.service.ValidatedService;

/**
 * 
 * @author William
 *
 */
@RestController
public class LoginRest {

	private static final Logger log = LoggerFactory.getLogger(LoginRest.class);

	@Autowired
	private ValidatedService validator;

	/**
	 * check user valiadation
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/check/user", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> checkUser(@ModelAttribute User user) {
		ResponseEntity<Object> response = null;
		try {
			boolean flag = validator.checkUser(user);
			response = new ResponseEntity<Object>(flag, HttpStatus.OK);
		} catch (Exception e) {
			log.error("create error", e);
			response = new ResponseEntity<Object>("create failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

}
