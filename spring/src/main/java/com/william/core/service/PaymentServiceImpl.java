package com.william.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.william.core.dao.PaymentDao;
import com.william.core.model.Payment;

/**
 * 
 * @author William
 *
 */
@Component
@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger log = LoggerFactory
			.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentDao paymentDao;

	/**
	 * create payment
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean createRecord(Payment payment) {
		boolean flag = false;
		try {
			flag = paymentDao.createPayment(payment);
		} catch (Exception e) {
			flag = false;
			throw e;
		} finally {
			return flag;
		}
	}

}
