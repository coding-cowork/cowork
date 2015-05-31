package com.william.core.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import com.william.core.component.ReportComponent;
import com.william.core.model.Material;
import com.william.core.model.Record;

/**
 * 
 * @author William
 *
 */
@Component
@Repository
public class RecordDaoImpl implements RecordDao {

	private static final Logger log = LoggerFactory
			.getLogger(RecordDaoImpl.class);

	private static final int CREATE = 1;
	private static final int UPDATE = 2;

	private URLDecoder decoder;

	@Autowired
	private MongoConnection mongo;

	@Autowired
	private ReportComponent report;

	public RecordDaoImpl() {
		super();
		decoder = new URLDecoder();
	}

	/**
	 * create record
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean createRecord(Record record) {
		boolean flag = false;
		try {
			DBCollection coll = mongo.getDB().getCollection("records");
			DBObject obj = new BasicDBObject();
			setValue(record, obj, CREATE);
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
	 * search record
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<Record> readRecord(Record record) {
		List<Record> resultList = new ArrayList<Record>();
		try {
			DBCollection coll = mongo.getDB().getCollection("records");
			BasicDBObject query = new BasicDBObject();
			query.put("type", record.getType());
			query.put("item", record.getItem());
			query.put("company", record.getCompany());
			query.put("position", record.getPosition());
			query.append("create_date", new BasicDBObject("$gte", new Date(
					record.getStartDate())).append("$lte",
					new Date(record.getEndDate())));
			DBCursor cursor = coll.find(query);
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				Record records = new Record();
				records.setType((String) obj.get("type"));
				records.set_id((String) obj.get(("_id")));
				records.setAmount((double) obj.get("amount"));
				records.setAmAmount((int) obj.get("am_amount"));
				records.setPmAmount((int) obj.get("pm_amount"));
				records.setMiddleAmount((int) obj.get("middle_amount"));
				records.setArea((String) obj.get("area"));
				records.setVolume((String) obj.get("volume"));
				records.setPosition((String) obj.get("position"));
				records.setSurface((String) obj.get("surface"));
				records.setCompany((String) obj.get("company"));
				records.setItem((String) obj.get("item"));
				records.setSubItem((String) obj.get("sub_item"));
				records.setCreateUser((String) obj.get("create_user"));
				records.setCreateId((String) obj.get("create_id"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				records.setInputDate(sdf.format((Date) obj.get("create_date")));
				resultList.add(records);
			}
		} catch (Exception e) {
			log.error("dao find", e);
			throw e;
		}
		return resultList;
	}

	/**
	 * search today all record
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<Record> readTodayAllRecord() {
		List<Record> resultList = new ArrayList<Record>();
		try {
			DBCollection coll = mongo.getDB().getCollection("records");
			BasicDBObject query = new BasicDBObject();
			Date today = new Date();
			query.append("create_date", new BasicDBObject("$gte",
					new Date(today.getYear(), today.getMonth(),
							today.getDate(), 0, 0, 0)).append("$lte", new Date(
					today.getYear(), today.getMonth(), today.getDate(), 23, 59,
					59)));
			DBCursor cursor = coll.find(query);
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				Record records = new Record();
				records.setType((String) obj.get("type"));
				records.set_id((String) obj.get(("_id")));
				records.setAmount((double) obj.get("amount"));
				records.setAmAmount((int) obj.get("am_amount"));
				records.setPmAmount((int) obj.get("pm_amount"));
				records.setMiddleAmount((int) obj.get("middle_amount"));
				records.setArea((String) obj.get("area"));
				records.setVolume((String) obj.get("volume"));
				records.setPosition((String) obj.get("position"));
				records.setSurface((String) obj.get("surface"));
				records.setCompany((String) obj.get("company"));
				records.setItem((String) obj.get("item"));
				records.setSubItem((String) obj.get("sub_item"));
				records.setCreateUser((String) obj.get("create_user"));
				records.setCreateId((String) obj.get("create_id"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				records.setInputDate(sdf.format((Date) obj.get("create_date")));
				resultList.add(records);
			}
		} catch (Exception e) {
			log.error("dao find", e);
			throw e;
		}
		return resultList;
	}

	/**
	 * update record
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean updateRecord(Record record) {
		boolean flag = false;
		try {
			DBCollection coll = mongo.getDB().getCollection("records");
			BasicDBObject source = new BasicDBObject();
			source.put("_id", record.get_id());
			BasicDBObject target = new BasicDBObject();
			target.append("$set", setValue(record, new BasicDBObject(), UPDATE));
			coll.update(source, target);
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
	 * delete record
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean deleteRecord(String id) {
		boolean flag = false;
		try {
			DBCollection coll = mongo.getDB().getCollection("records");
			BasicDBObject source = new BasicDBObject();
			source.put("_id", id);
			coll.remove(source);
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
	 * @param record
	 * @param obj
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private DBObject setValue(Record record, DBObject obj, int type)
			throws UnsupportedEncodingException {

		if (!StringUtils.isBlank(record.get_id())) {
			obj.put("_id", record.get_id());
		} else {
			obj.put("_id", UUID.randomUUID().toString());
		}

		if (!StringUtils.isBlank(record.getCompany())) {
			obj.put("company", decoder.decode(record.getCompany(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getItem())) {
			obj.put("item", decoder.decode(record.getItem(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getPersonType())) {
			obj.put("person_type",
					decoder.decode(record.getPersonType(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getSubItem())) {
			obj.put("sub_item", decoder.decode(record.getSubItem(), "UTF-8"));
		}

		obj.put("amount", record.getAmount());
		obj.put("am_amount", record.getAmAmount());
		obj.put("pm_amount", record.getPmAmount());
		obj.put("middle_amount", record.getMiddleAmount());
		// for insert
		if (type == CREATE) {
			obj.put("create_date", record.getCreateDate());
			obj.put("create_user", record.getCreateUser());
			obj.put("create_id", record.getCreateId());
		}
		// for update
		if (type == UPDATE) {
			obj.put("update_date", new Date());
			obj.put("update_user", record.getUpdateUser());
		}

		if (!StringUtils.isBlank(record.getType())) {
			obj.put("type", decoder.decode(record.getType(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getArea())) {
			obj.put("area", decoder.decode(record.getArea(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getPosition())) {
			obj.put("position", decoder.decode(record.getPosition(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getVolume())) {
			obj.put("volume", decoder.decode(record.getVolume(), "UTF-8"));
		}

		if (!StringUtils.isBlank(record.getSurface())) {
			obj.put("surface", decoder.decode(record.getSurface(), "UTF-8"));
		}

		return obj;
	}
}
