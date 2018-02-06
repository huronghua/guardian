package com.banmatrip.guardian.core.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: 读取excel工具类
 * @create 2017-09-19 17:57
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class ReadExcel {
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    /**
     * 验证EXCEL文件，暂时用不上
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    /**
     * 根据excel里面的内容读取信息
     * @param is 输入流
     * @return
     * @throws IOException
     */
    public  List getExcelInfo(InputStream is) throws RuntimeException{
        List customerList=null;
        try{
            //模板为excel07+版本
            Workbook wb = new XSSFWorkbook(is);
            //读取Excel信息
            customerList=readExcelValue(wb);
        }
        catch (Exception e)  {
            throw new RuntimeException(e.getMessage());
        }
        return customerList;
    }
    /**
     * 读取Excel信息
     * @param wb
     * @return
     */
    private List<Map<String,Object>> readExcelValue(Workbook wb) throws RuntimeException{
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<Map<String,Object>> customerList=new ArrayList();
        Map<String,Object> customer;
        //循环Excel行数,从第二行开始
        try {
            for (int r = 1; r < totalRows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                customer = new HashMap();

                //循环Excel的列
                for (int c = 0; c < this.totalCells; c++) {
                    Cell cell = row.getCell(c);
                    if (null != cell) {
                        if (c == 0) {
                            customer.put("name", cell.getStringCellValue());//姓名
                        } else if (c == 1) {
                            customer.put("account", cell.getStringCellValue());//登录账号
                        } else if (c == 2) {
                            customer.put("position", cell.getStringCellValue());//职位
                        } else if (c == 3) {
                            customer.put("role", cell.getStringCellValue());//角色
                        } else if (c == 4) {
                            customer.put("department_1", cell.getStringCellValue());//一级部门
                        } else if (c == 5) {
                            customer.put("department_2", cell.getStringCellValue());//二级部门
                        } else if (c == 6) {
                            customer.put("department_3", cell.getStringCellValue());//三级部门
                        }else if (c == 7) {
                            customer.put("department", cell.getStringCellValue());//四级部门（原部门）
                        } else if (c == 8) {
                            customer.put("cellphone", cell.getStringCellValue());//手机号
                        } else if (c == 9) {
                            customer.put("email", cell.getStringCellValue());//企业邮箱
                        } else if (c == 10) {
                            customer.put("employeeId", cell.getStringCellValue());//工号
                        } else if (c == 11) {
                            customer.put("ethnic", cell.getStringCellValue());//族群
                        } else if (c == 12) {
                            customer.put("roleType", cell.getStringCellValue());//序列
                        } else if (c == 13) {
                            customer.put("tag", cell.getStringCellValue());//目的地
                        } else if (c == 14) {
                            customer.put("productType", cell.getStringCellValue());//产品类型
                        } else if (c == 15) {
                            customer.put("orderPlatform", cell.getStringCellValue());//渠道
                        } else if (c == 16) {
                            customer.put("resourceType", cell.getStringCellValue());//资源类型
                        }
                    }
                }
                //添加客户
                customerList.add(customer);
            }
        }catch (RuntimeException e){
            throw new RuntimeException("读取excel出错，请检查excel数据内容与格式！" + e.getMessage());
        }
        return customerList;
    }
}
