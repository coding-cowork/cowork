package com.william.core.component;

public enum ReportPath {

	material("reports/material.jrxml");

	private final String path;

	private ReportPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
}
