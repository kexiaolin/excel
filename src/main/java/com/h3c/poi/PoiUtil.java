package com.h3c.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PoiUtil {
 
    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);
 
    /**
     * 初始化EXCEL(sheet个数和标题)
     *
     * @param totalRowCount 总记录数
     * @param titles        标题集合
     * @return XSSFWorkbook对象
     */
    public static SXSSFWorkbook initExcel(Integer totalRowCount, String[] titles) {
 
        // 在内存当中保持 100 行 , 超过的数据放到硬盘中在内存当中保持 100 行 , 超过的数据放到硬盘中
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
 
        Integer sheetCount = ((totalRowCount % ExcelConstant.PER_SHEET_ROW_COUNT == 0) ?
                (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT) : (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT + 1));
 
        // 根据总记录数创建sheet并分配标题
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet sheet = wb.createSheet("sheet" + (i + 1));
            SXSSFRow headRow = sheet.createRow(0);
 
            for (int j = 0; j < titles.length; j++) {
                SXSSFCell headRowCell = headRow.createCell(j);
                headRowCell.setCellValue(titles[j]);
            }
        }
 
        return wb;
    }


    /**
     * 导出Excel到浏览器
     *

     * @param totalRowCount           总记录数
     * @param fileName                文件名称
     * @param titles                  标题
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static final void exportExcelToWebsite( Integer totalRowCount, String filePath, String[] titles, WriteExcelDataDelegated writeExcelDataDelegated) throws Exception {


        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles);


        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);

            for (int j = 1; j <= ExcelConstant.PER_SHEET_WRITE_COUNT; j++) {

                int currentPage = i * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int endRowCount = startRowCount + pageSize - 1;

                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }
        }
        OutputStream outputStream = Files.newOutputStream(Paths.get(new File(filePath).toURI()));
        wb.write(outputStream);

    }



    public static SXSSFWorkbook createExcelFromTemplate(String tempFilePath , String dstFilePath) {

        File sourceFile = new File(tempFilePath);
        File dstFile = new File(dstFilePath);

        OutputStream outputStream = null;

        if (!dstFile.exists()){
              if (dstFile.delete()){
                  try {
                      dstFile.createNewFile();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
        }

        try {
            FileUtils.copyFile(sourceFile,dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SXSSFWorkbook wb = writeExcel(dstFile);
        return wb;
    }

    private static SXSSFWorkbook writeExcel(File dstFile){

        SXSSFWorkbook wb = null;
        try {
            wb = new SXSSFWorkbook(new XSSFWorkbook(Files.newInputStream(Paths.get(dstFile.toURI()))), 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;

    }


}
