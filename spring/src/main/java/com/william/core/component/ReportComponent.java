package com.william.core.component;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

@Component
public class ReportComponent {

	private Logger log = LoggerFactory.getLogger(ReportComponent.class);

	DefaultTableModel tableModel;

	/**
	 * produce pdf report for material
	 * 
	 * @param path
	 * @param dataList
	 */
	public File produceReport(ReportPath path, List<DBObject> dataList) {
		JasperPrint jasperPrint = null;
		File file = null;
		try {
			InputStream filePath = this.getClass().getClassLoader()
					.getResourceAsStream(path.getPath());
			JasperReport compileFile = JasperCompileManager
					.compileReport(filePath);
			jasperPrint = JasperFillManager.fillReport(compileFile,
					new HashMap(), new JRBeanCollectionDataSource(dataList));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

			String finalData = "report.pdf";
			file = new File(finalData);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);

			exporter.exportReport();

		} catch (JRException ex) {
			ex.printStackTrace();
		}
		// finally {
		return file;
		// }
	}
}
