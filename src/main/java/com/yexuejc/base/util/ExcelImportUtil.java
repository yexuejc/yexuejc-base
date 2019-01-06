package com.yexuejc.base.util;

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


}