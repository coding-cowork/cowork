package com.william.core.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.william.core.model.Record;
import com.william.core.service.RecordService;
import com.william.core.service.WeatherService;

/**
 * 
 * @author William
 *
 */
@RestController
@RequestMapping("/record")
public class RecordRest {

	private static final Logger log = LoggerFactory.getLogger(RecordRest.class);

	@Autowired
	private RecordService recordService;

	@Autowired
	private WeatherService weatherService;

	/**
	 * 
	 * create for record
	 * 
	 * @param record
	 * @return
	 */
	@SuppressWarnings({ "finally", "deprecation" })
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> createRecord(@ModelAttribute Record record) {
		ResponseEntity<Object> response = null;
		try {
			Date today = new Date();
			record.setCreateDate(new Date(today.getYear(), today.getMonth(),
					today.getDate(), 23, 0, 0));
			record.setCreateId("101");
			record.setCreateUser("XXXXX");
			record.setLevel(1);
			recordService.createRecord(record);
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

	/**
	 * 
	 * search for record
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> searchRecord(
			@RequestParam("type") @DefaultValue(value = "") String type,
			@RequestParam("item") @DefaultValue(value = "") String item,
			@RequestParam("company") @DefaultValue(value = "") String company,
			@RequestParam("position") @DefaultValue(value = "") String position,
			@RequestParam("startDate") @DefaultValue(value = "") String startDate,
			@RequestParam("endDate") @DefaultValue(value = "") String endDate) {
		List<Record> resultList = null;
		ResponseEntity<Object> response = null;
		try {
			Record record = new Record();
			record.setType(type);
			record.setItem(item);
			record.setCompany(company);
			record.setPosition(position);
			record.setStartDate(startDate);
			record.setEndDate(endDate);
			resultList = recordService.readRecord(record);
			response = new ResponseEntity<Object>(resultList, HttpStatus.OK);
		} catch (Exception e) {
			log.error("search error", e);
			response = new ResponseEntity<Object>("search failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}

	}

	/**
	 * 
	 * search for record
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/find/today/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> searchTotalAllRecord() {
		List<Record> resultList = null;
		ResponseEntity<Object> response = null;
		try {
			resultList = recordService.readTodayAllRecord();
			response = new ResponseEntity<Object>(resultList, HttpStatus.OK);
		} catch (Exception e) {
			log.error("search error", e);
			response = new ResponseEntity<Object>("search failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}

	}

	/**
	 * 
	 * update for record
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> updateRecord(@ModelAttribute Record record) {
		ResponseEntity<Object> response = null;
		try {
			record.setUpdateDate(new Date());
			record.setUpdateUser("ZZZZ");
			recordService.updateRecord(record);
			response = new ResponseEntity<Object>("update success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("update error", e);
			response = new ResponseEntity<Object>("update failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

	/**
	 * 
	 * delete for record
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> deleteRecord(
			@RequestParam("id") @DefaultValue(value = "") String id) {
		ResponseEntity<Object> response = null;
		try {
			recordService.deleteRecord(id);
			response = new ResponseEntity<Object>("delete success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("update error", e);
			response = new ResponseEntity<Object>("delete failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

	/**
	 * 
	 * testing
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> test() {
		ResponseEntity<Object> response = null;
		try {
			weatherService.getWeatherStatus();
			response = new ResponseEntity<Object>("testing success",
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("testing error", e);
			response = new ResponseEntity<Object>("testing failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

	/**
	 * 
	 * testing
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/test/form", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> testForm(
			@RequestBody final MultiValueMap<String, String> form) {
		ResponseEntity<Object> response = null;
		try {
			String f = form.getFirst("name");
			log.info("~~~>>" + f);
			String ff = form.get("list").get(1);
			log.info("~~~>>" + ff);
		} catch (Exception e) {
			log.error("testing form error", e);
			response = new ResponseEntity<Object>("testing failure~~",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

}
