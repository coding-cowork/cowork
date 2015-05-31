package com.william.core.service;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.william.core.dao.UploadDao;

/**
 * 
 * @author William
 *
 */
@Component
@Service
public class UploadServiceImpl implements UploadService {

	private Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Autowired
	private UploadDao upload;

	/**
	 * get content from excel
	 */
	@Override
	public boolean getContentFromExcel(InputStream input) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			// get total sheet from current excel
			for (XSSFSheet sheet : workbook) {
				// get excel content
				getCellContentByXlsx(sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("upload exception~~" + e);
		}
		return false;
	}

	/**
	 * get content for xlsx
	 * 
	 * @param sheet
	 * @throws PreingestionException
	 */
	private void getCellContentByXlsx(XSSFSheet sheet) {

		// int current_col = 0;
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			// read data from row 3
			if (row.getRowNum() >= 1) {
				while (cells.hasNext()) {
					Cell cell = cells.next();
					switch (cell.getCellType()) {
					// Number
					case 0:
						double number = cell.getNumericCellValue();
						logger.info("Content---->>" + number);
						break;
					// String
					case 1:
						// get content
						String content = cell.getStringCellValue();
						logger.info("Content---->>" + content);
						break;
					}
				}
			}
		}

	}

}
