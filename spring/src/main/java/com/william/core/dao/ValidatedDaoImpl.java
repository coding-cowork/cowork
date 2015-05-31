package com.william.core.dao;

import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.william.core.component.MongoConnection;
import com.william.core.model.User;

/**
 * 
 * @author William
 *
 */
@Component
@Repository
public class ValidatedDaoImpl implements ValidatedDao {

	private static final Logger log = LoggerFactory
			.getLogger(ValidatedDaoImpl.class);

	@Autowired
	private MongoConnection mongo;

	private URLDecoder decoder;

	public ValidatedDaoImpl() {
		super();
		decoder = new URLDecoder();
	}

	/**
	 * check user permission
	 */
	@Override
	public boolean validatedUser(User user) {
		boolean flag = false;
		try {
			DBCollection coll = mongo.getDB().getCollection("user");
			BasicDBObject query = new BasicDBObject();
			query.append("account", user.getAccount());
			query.append("password", user.getPassword());
			DBCursor cursor = coll.find(query);
			while (cursor.hasNext()) {

			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			log.error("dao create", e);
			throw e;
		} finally {
			return flag;
		}
	}

}
