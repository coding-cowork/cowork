package com.william.core.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.william.core.model.Record;
import com.william.core.service.RecordService;

/**
 * 
 * @author William
 *
 */
@Controller
@RequestMapping("/record")
public class RecordRest {

	private static final Logger log = LoggerFactory.getLogger(RecordRest.class);

	@Autowired
	private RecordService recordService;

	/**
	 * 
	 * create for record
	 * 
	 * @param record
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> createRecord(
			@RequestParam("type") @DefaultValue(value = "") String type,
			@RequestParam("company") @DefaultValue(value = "") String company,
			@RequestParam("item") @DefaultValue(value = "") String item,
			@RequestParam("subItem") @DefaultValue(value = "") String subItem,
			@RequestParam("amount") @DefaultValue(value = "0.00") double amount,
			@RequestParam("personType") @DefaultValue(value = "") String personType,
			@RequestParam("amAmount") @DefaultValue(value = "0") int amAmount,
			@RequestParam("pmAmount") @DefaultValue(value = "0") int pmAmount,
			@RequestParam("middleAmount") @DefaultValue(value = "0") int middleAmount,
			@RequestParam("position") @DefaultValue(value = "") String position,
			@RequestParam("surface") @DefaultValue(value = "") String surface,
			@RequestParam("volume") @DefaultValue(value = "") String volume,
			@RequestParam("area") @DefaultValue(value = "") String area) {
		ResponseEntity<Object> response = null;
		try {
			Record record = new Record();
			record.setAmAmount(amAmount);
			record.setAmount(amount);
			record.setArea(area);
			record.setCompany(company);
			record.setItem(item);
			record.setSubItem(subItem);
			record.setMiddleAmount(middleAmount);
			record.setPersonType(personType);
			record.setPosition(position);
			record.setPmAmount(pmAmount);
			record.setSurface(surface);
			record.setType(type);
			record.setVolume(volume);
			record.setCreateDate(new Date());
			record.setCreateId("101");
			record.setCreateUser("XXXXX");
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
	 * update for record
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> updateRecord(
			@RequestParam("id") @DefaultValue(value = "") String id,
			@RequestParam("type") @DefaultValue(value = "") String type,
			@RequestParam("company") @DefaultValue(value = "") String company,
			@RequestParam("item") @DefaultValue(value = "") String item,
			@RequestParam("subItem") @DefaultValue(value = "") String subItem,
			@RequestParam("amount") @DefaultValue(value = "0.00") double amount,
			@RequestParam("personType") @DefaultValue(value = "") String personType,
			@RequestParam("amAmount") @DefaultValue(value = "0") int amAmount,
			@RequestParam("pmAmount") @DefaultValue(value = "0") int pmAmount,
			@RequestParam("middleAmount") @DefaultValue(value = "0") int middleAmount,
			@RequestParam("position") @DefaultValue(value = "") String position,
			@RequestParam("surface") @DefaultValue(value = "") String surface,
			@RequestParam("volume") @DefaultValue(value = "") String volume,
			@RequestParam("area") @DefaultValue(value = "") String area) {
		ResponseEntity<Object> response = null;
		try {
			Record record = new Record();
			record.set_id(id);
			record.setAmAmount(amAmount);
			record.setAmount(amount);
			record.setArea(area);
			record.setCompany(company);
			record.setItem(item);
			record.setSubItem(subItem);
			record.setMiddleAmount(middleAmount);
			record.setPersonType(personType);
			record.setPosition(position);
			record.setPmAmount(pmAmount);
			record.setSurface(surface);
			record.setType(type);
			record.setVolume(volume);
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

}
