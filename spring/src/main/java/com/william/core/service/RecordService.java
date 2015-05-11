package com.william.core.service;

import java.util.List;

import com.william.core.model.Record;

/**
 * 
 * @author William
 *
 */
public interface RecordService {

	public boolean createRecord(Record record);

	public List<Record> readRecord(Record record);

	public boolean updateRecord(Record record);

	public boolean deleteRecord(String id);

}
