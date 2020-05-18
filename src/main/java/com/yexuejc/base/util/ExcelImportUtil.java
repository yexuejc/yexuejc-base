package com.yexuejc.base.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * excel 格式验证工具
 *
 * @ClassName: ExcelImportUtils
 * @Description:
 * @author: maxf
 * @date: 2017/12/27 16:42
 */
public class ExcelImportUtil {
    private ExcelImportUtil() {
    }

    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath
     * @return
     * @Description：是否是2003的excel，返回true是2003
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return
     * @Description：是否是2007的excel，返回true是2007
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        boolean b = filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath));
        if (b) {
            return false;
        }
        return true;
    }

    public void readExcel(String path) throws IOException {
        Workbook wb = null;
        if (isExcel2007(path)) {
            wb = new XSSFWorkbook(new FileInputStream(new File(path)));
        } else if (isExcel2003(path)) {
            wb = new HSSFWorkbook(new FileInputStream(new File(path)));
        } else {
            throw new NullPointerException("请上传excel文件");
        }
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 2; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
        }
    }


}