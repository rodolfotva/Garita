package br.com.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

@ManagedBean
@SessionScoped
public class ExportBean implements Serializable {

	private Logger logger = Logger.getLogger(getClass());

	public void postProcessXLS(Object document) {
		try{
			HSSFWorkbook wb = (HSSFWorkbook) document;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);
			
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = header.getCell(i);
				cell.setCellStyle(cellStyle);
			}
			
			for(int i=0;i<=sheet.getLastRowNum();i++){
				sheet.getRow(i).getCell(6).setCellValue("");
			}
			logger.info("exportacao bem sucedida");
		}catch(Exception ex){
			logger.error("Erro ao exportar para escel", ex);
		}
	}
}
