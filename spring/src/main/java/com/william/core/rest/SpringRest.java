package com.william.core.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HttpServletBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.william.core.component.MongoConnection;
import com.william.core.component.ReportComponent;
import com.william.core.component.ReportPath;
import com.william.core.model.Material;

@Controller
@RequestMapping("/rest")
public class SpringRest {

	private Logger log = LoggerFactory.getLogger(SpringRest.class);

	@Autowired
	private MongoConnection mongo;

	@Autowired
	private ReportComponent report;

	public SpringRest() {
		super();
	}

	/**
	 * update for material
	 * 
	 * @param material
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> update(@RequestBody Material material) {
		ResponseEntity<Object> response = null;
		try {
			DBCollection coll = mongo.getDB().getCollection("material");
			BasicDBObject source = new BasicDBObject();
			source.put("_id", material.get_id());
			BasicDBObject target = new BasicDBObject();
			target.append("$set", setValue(material, new BasicDBObject()));
			coll.update(source, target);
			response = new ResponseEntity<Object>("Update success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Object>("Update failure~~",
					HttpStatus.OK);
		} finally {
			return response;
		}

	}

	/**
	 * 
	 * create for material
	 * 
	 * @param material
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody Material material) {
		ResponseEntity<Object> response = null;
		try {
			DBCollection coll = mongo.getDB().getCollection("material");
			DBObject obj = new BasicDBObject();
			setValue(material, obj);
			coll.insert(obj);
			response = new ResponseEntity<Object>("Create success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Object>("Create failure~~",
					HttpStatus.EXPECTATION_FAILED);
		} finally {
			return response;
		}
	}

	/**
	 * 
	 * delete for material
	 * 
	 * @param material
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> delete(@PathVariable String id) {
		ResponseEntity<Object> response = null;
		try {
			DBCollection coll = mongo.getDB().getCollection("material");
			DBObject obj = new BasicDBObject();
			obj.put("_id", id);
			coll.remove(obj);
			response = new ResponseEntity<Object>("Create success~~",
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Object>("Create failure~~",
					HttpStatus.EXPECTATION_FAILED);
		} finally {
			return response;
		}
	}

	/**
	 * 
	 * search for material
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> search(@PathVariable String id) {
		ResponseEntity<Object> response = null;
		DBCursor cursor = null;
		List<DBObject> objList = new ArrayList<DBObject>();
		try {
			DBCollection find = mongo.getDB().getCollection("material");
			cursor = find.find(new BasicDBObject("_id", id));
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				objList.add(obj);
			}
			response = new ResponseEntity<Object>(objList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Object>("Search failure~~",
					HttpStatus.EXPECTATION_FAILED);
		} finally {
			cursor.close();
			return response;
		}
	}

	/**
	 * 
	 * produce report for material
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/report/material", method = RequestMethod.GET, produces = "application/jdf")
	@ResponseBody
	public ResponseEntity<Object> produceMaterialReport(HttpServletResponse res) {
		ResponseEntity<Object> response = null;
		DBCursor cursor = null;
		try {
			DBCollection find = mongo.getDB().getCollection("material");
			List<DBObject> objList = new ArrayList<DBObject>();
			cursor = find.find();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				objList.add(setReportValue(obj));
			}
			File file = report.produceReport(ReportPath.material, objList);
			res.setContentType("application/pdf");
			res.setHeader("Content-Disposition",
					"attachment;filename=" + file.getName());
			FileInputStream input = new FileInputStream(file);
			IOUtils.copy(input,res.getOutputStream());
			res.flushBuffer();
		    input.close();
			response = new ResponseEntity<Object>(file, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Object>("Search failure~~",
					HttpStatus.EXPECTATION_FAILED);
		} finally {
			cursor.close();
		    return response;
		}
	}

	private DBObject setValue(Material material, DBObject obj) {

		if (!StringUtils.isBlank(material.get_id())) {
			obj.put("_id", material.get_id());
		} else {
			obj.put("_id", UUID.randomUUID().toString());
		}

		if (!StringUtils.isBlank(material.getMaterialName())) {
			obj.put("materialName", material.getMaterialName());
		}

		if (!StringUtils.isBlank(material.getMaterialLocation())) {
			obj.put("materialLocation", material.getMaterialLocation());
		}

		if (!StringUtils.isBlank(material.getRegion())) {
			obj.put("region", material.getRegion());
		}

		if (!StringUtils.isBlank(material.getUnit())) {
			obj.put("unit", material.getUnit());
		}

		obj.put("amount", material.getAmount());
		obj.put("dailyAmount", material.getDailyAmount());
		obj.put("accumulatedAmount", material.getAccumulatedAmount());

		if (!StringUtils.isBlank(material.getComment())) {
			obj.put("comment", material.getComment());
		}

		return obj;
	}

	private DBObject setReportValue(DBObject obj) {

		if (StringUtils.isBlank((String) obj.get("_id"))) {
			obj.put("_id", "");
		}
		if (StringUtils.isBlank((String) obj.get("materialName"))) {
			obj.put("materialName", "");
		}

		if (StringUtils.isBlank((String) obj.get("materialLocation"))) {
			obj.put("materialLocation", "");
		}

		if (StringUtils.isBlank((String) obj.get("region"))) {
			obj.put("region", "");
		}

		if (StringUtils.isBlank((String) obj.get("unit"))) {
			obj.put("unit", "");
		}
		if (obj.get("amount") == null) {
			obj.put("amount", 0.0);
		}
		if (obj.get("dailyAmount") == null) {
			obj.put("dailyAmount", 0);
		}
		if (obj.get("accumulatedAmount") == null) {
			obj.put("accumulatedAmount", 0.0);
		}
		if (StringUtils.isBlank((String) obj.get("comment"))) {
			obj.put("comment", "");
		}

		return obj;
	}

}
