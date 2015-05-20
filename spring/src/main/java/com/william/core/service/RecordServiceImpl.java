package com.william.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.william.core.dao.RecordDao;
import com.william.core.model.Record;

/**
 * 
 * @author William
 *
 */
@Component
@Service
public class RecordServiceImpl implements RecordService {

	private static final Logger log = LoggerFactory
			.getLogger(RecordServiceImpl.class);

	@Autowired
	private RecordDao recordDao;

	/**
	 * create record
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean createRecord(Record record) {
		boolean flag = false;
		try {
			flag = recordDao.createRecord(record);
		} catch (Exception e) {
			flag = false;
			throw e;
		} finally {
			return flag;
		}
	}

	/**
	 * search record
	 */
	@Override
	public List<Record> readRecord(Record record) {
		return recordDao.readRecord(record);
	}
	
	/**
	 * search today all record
	 */
	@Override
	public List<Record> readTodayAllRecord() {
		return recordDao.readTodayAllRecord();
	}

	/**
	 * update record
	 */
	@Override
	public boolean updateRecord(Record record) {
		boolean flag = false;
		try {
			flag = recordDao.updateRecord(record);
		} catch (Exception e) {
			flag = false;
			throw e;
		} finally {
			return flag;
		}
	}

	/**
	 * delete record
	 */
	@Override
	public boolean deleteRecord(String id) {
		boolean flag = false;
		try {
			flag = recordDao.deleteRecord(id);
		} catch (Exception e) {
			flag = false;
			throw e;
		} finally {
			return flag;
		}
	}

}
