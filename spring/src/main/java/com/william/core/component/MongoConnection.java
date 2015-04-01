package com.william.core.component;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Component
public class MongoConnection {

	private String host;

	private int port;

	private String dbName;

	private String userName;

	private String password;

	private MongoConnection() {

		this.host = "192.168.0.13";
		this.port = 27017;
		this.dbName = "test";
		this.userName = "william";
		this.password = "0355125";

	}

	public DB getDB() {
		DB db = null;
		try {
			MongoCredential credential = MongoCredential
					.createScramSha1Credential(userName, dbName,
							password.toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(host,
					port), Arrays.asList(credential));
			db = mongoClient.getDB(dbName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return db;
		}
	}

}
