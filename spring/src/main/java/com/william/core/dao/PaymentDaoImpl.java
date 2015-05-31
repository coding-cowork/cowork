package com.william.core.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.william.core.component.MongoConnection;
import com.william.core.model.Payment;
import com.william.core.model.Record;

/**
 * 
 * @author William
 *
 */
@Component
@Repository
public class PaymentDaoImpl implements PaymentDao {

	private static final Logger log = LoggerFactory
			.getLogger(PaymentDaoImpl.class);

	private URLDecoder decoder;

	@Autowired
	private MongoConnection mongo;

	public PaymentDaoImpl() {
		super();
		decoder = new URLDecoder();
	}

	/**
	 * create payment
	 */
	@Override
	public boolean createPayment(Payment payment) {
		boolean flag = false;
		try {
			DBCollection coll = mongo.getDB().getCollection("payment");
			DBObject obj = new BasicDBObject();
			setPaymentValue(payment, obj);
			coll.insert(obj);
			flag = true;
		} catch (Exception e) {
			flag = false;
			log.error("dao create", e);
			throw e;
		} finally {
			return flag;
		}
	}

	/**
	 * setter
	 * 
	 * @param payment
	 * @param obj
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private DBObject setPaymentValue(Payment payment, DBObject obj)
			throws UnsupportedEncodingException {

		if (!StringUtils.isBlank(payment.getId())) {
			obj.put("_id", payment.getId());
		} else {
			obj.put("_id", UUID.randomUUID().toString());
		}

		if (!StringUtils.isBlank(payment.getDebitCompany())) {
			obj.put("debit_company",
					decoder.decode(payment.getDebitCompany(), "UTF-8"));
		}

		if (!StringUtils.isBlank(payment.getDescription())) {
			obj.put("description",
					decoder.decode(payment.getDescription(), "UTF-8"));
		}

		if (!StringUtils.isBlank(payment.getItem())) {
			obj.put("item", decoder.decode(payment.getItem(), "UTF-8"));
		}

		if (!StringUtils.isBlank(payment.getPayCompany())) {
			obj.put("pay_company",
					decoder.decode(payment.getPayCompany(), "UTF-8"));
		}

		if (!StringUtils.isBlank(payment.getUnit())) {
			obj.put("unit", decoder.decode(payment.getUnit(), "UTF-8"));
		}

		obj.put("count", payment.getCount());
		obj.put("debit", payment.getDebit());
		obj.put("payment", payment.getPayment());
		obj.put("price", payment.getPrice());
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		obj.put("create_date",
				new Date(date.getYear(), date.getMonth(), date.getDate(), 0, 0,
						0));

		return obj;
	}
}
