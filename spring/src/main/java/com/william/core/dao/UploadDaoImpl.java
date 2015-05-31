package com.william.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.william.core.model.Excel;

/**
 * 
 * @author William
 *
 */
@Component
@Repository
public class UploadDaoImpl implements UploadDao {

	private static final Logger log = LoggerFactory
			.getLogger(UploadDaoImpl.class);

	/**
	 * write data into db
	 */
	@Override
	public boolean writeContentIntoDB(Excel excel) {
		// TODO Auto-generated method stub
		return false;
	}

}
