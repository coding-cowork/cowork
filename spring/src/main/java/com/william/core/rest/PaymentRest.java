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

import com.william.core.model.Payment;
import com.william.core.service.PaymentService;

@RestController
public class PaymentRest {

	private static final Logger log = LoggerFactory
			.getLogger(PaymentRest.class);

	@Autowired
	private PaymentService paymentService;

	/**
	 * 
	 * create for customer
	 * 
	 * @param record
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/payment", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> createCustomer(@ModelAttribute Payment payment) {
		ResponseEntity<Object> response = null;
		try {
			paymentService.createRecord(payment);
			response = new ResponseEntity<Object>("Create success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("create error", e);
			response = new ResponseEntity<Object>("create failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

}
