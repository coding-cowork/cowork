package com.william.core.test;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.read(new File("E:/output.xls"));
	}

	public void read(File file) {
		try {
			Workbook book = Workbook.getWorkbook(file);
			int length = book.getSheets().length;

			for (int i = 0; i < length; i++) {
				Sheet sheet = book.getSheet(i);
				int row = book.getSheet(i).getRows();
				int col = book.getSheet(i).getColumns();
				for (int j = 0; j < row; j++) {
					for (int k = 0; k < col; k++) {
						Cell cell = sheet.getCell(k, j);
						System.out.println("Type--->>" + cell.getType());
					}
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(File file) {
		try {
			// 創建WritableWorkbook
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			// 創建WritableSheet
			WritableSheet sheet = workbook.createSheet("我的工作表1", 0);
			Label label = new Label(0, 1, "Name");
			sheet.addCell(label);
			Number number = new Number(2, 0, 1.414);
			sheet.addCell(number);
			workbook.write();
			workbook.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
