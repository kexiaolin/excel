package com.h3c.rs;

import com.h3c.Employee;
import com.h3c.poi.ExcelConstant;
import com.h3c.poi.PoiUtil;
import com.h3c.poi.WriteExcelDataDelegated;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("hello")
public class HelloWord {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public  String getResult(){
        return "hello ni ma ";
    }

    @GET
    @Path("export1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportTest() throws Exception {

        String filePath ="C:\\Users\\Administrator\\Desktop\\222.txt";

        Integer totalCount = 2000012;
       final List<Employee> employees = new ArrayList<>(2000000);
        for (int i =0 ;i < totalCount; i ++ ) {
            Employee employee = new Employee(UUID.randomUUID(),"account:"+i,"passoword:"+i,"2","nickname:"+i, "job:"+i,"mobile:"+i,"email:"+i,UUID.randomUUID(),String.valueOf(System.currentTimeMillis()),UUID.randomUUID());
            employees.add(employee);
        }
        String [] titles = {"账号", "密码", "状态", "昵称", "职位", "手机号", "邮箱",  "创建时间"};
        SXSSFWorkbook wb =  PoiUtil.initExcel(totalCount, titles);

        int sheetCount = wb.getNumberOfSheets();
        int  row = 0;
        for (int k = 0; k < sheetCount; k++) {

            SXSSFSheet eachSheet = wb.getSheetAt(k);
            for (int j = 1; j <= ExcelConstant.PER_SHEET_WRITE_COUNT; j++) {
                //int currentPage = k * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int endRowCount = startRowCount + pageSize - 1;

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);

                    if ( row ++ < employees.size()) {
                        Employee employee = employees.get(i);

                        eachDataRow.createCell(0).setCellValue(employee.getId().toString());
                        eachDataRow.createCell(1).setCellValue(employee.getAccount());
                        eachDataRow.createCell(2).setCellValue(employee.getPassword());
                        eachDataRow.createCell(3).setCellValue(employee.getNickName());
                        eachDataRow.createCell(4).setCellValue(employee.getJob());
                        eachDataRow.createCell(5).setCellValue(employee.getStatus());
                        eachDataRow.createCell(6).setCellValue(employee.getMobile());

                        eachDataRow.createCell(7).setCellValue(employee.getCreateTime());
                    }
                }
            }

        }
        OutputStream outputStream = Files.newOutputStream(Paths.get(new File(filePath).toURI()));
        wb.write(outputStream);
        String fileName = "222.xlsx";
        Response response = Response.ok(new File(filePath)).header("Content-Disposition","attachment;filename = \""+fileName+ "\";filename*=utf-8''"+fileName)
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .build();
        return  response;
    }
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAll(){

        File file = new File("C:\\Users\\Administrator\\Desktop\\222.txt");

        //如果文件不存在，提示404
        if(!file.exists()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String fileName = null;
        try {
            fileName = URLEncoder.encode("下载测试.xls", "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return Response
                .ok(file)
                .header("Content-disposition","attachment;filename=" +fileName)
                .header("Cache-Control", "no-cache").build();
    }

}




