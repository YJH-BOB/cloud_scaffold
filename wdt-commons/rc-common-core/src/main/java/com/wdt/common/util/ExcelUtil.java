package com.wdt.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

public class ExcelUtil {

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String CONTENT_TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private ExcelUtil() {
    }

    /**
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名
     * @param pojoClass 实体类类型
     * @param fileName  文件名
     * @param response
     * @param <T>       泛型
     * @throws IOException
     */
    public static <T> void exportExcel(List<T> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                       HttpServletResponse response) throws IOException {
        defaultExport(list, title, sheetName, pojoClass, fileName, response);
    }

    public static <T> void exportExcel(List<T> list, String sheetName, Class<?> pojoClass, String fileName,
                                       HttpServletResponse response) throws IOException {
        defaultExport(list, null, sheetName, pojoClass, fileName, response);
    }

    public static <T> void exportExcel(List<T> list, Class<?> pojoClass, String fileName,
                                       HttpServletResponse response) throws IOException {
        defaultExport(list, null, null, pojoClass, fileName, response);
    }

    public static <T> void exportExcel(List<T> list, Class<?> pojoClass,
                                       HttpServletResponse response) throws IOException {
        defaultExport(list, null, null, pojoClass, "导出数据", response);
    }

    private static <T> void defaultExport(List<T> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                          HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName), pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        response.setCharacterEncoding(CHARSET_UTF8);
        response.setContentType(CONTENT_TYPE_EXCEL);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, CHARSET_UTF8));
        workbook.write(response.getOutputStream());
    }

    /**
     * @param file       文件
     * @param titleRows  标题的大小（行数）
     * @param headerRows 头部的大小（行数）
     * @param pojoClass  实体类
     * @param <T>        泛型
     * @return
     * @throws Exception eq:  某市2023年学生信息表  标题行
     *                   姓名  年龄    性别   表头行
     *                   张三   18     男    数据行
     */

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        return defaultImport(file, titleRows, headerRows, pojoClass);
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer headerRows, Class<T> pojoClass) throws Exception {
        return defaultImport(file, 0, headerRows, pojoClass);
    }

    public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws Exception {
        return defaultImport(file, 0, 1, pojoClass);
    }

    private static <T> List<T> defaultImport(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (file == null) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        return ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
    }


}
