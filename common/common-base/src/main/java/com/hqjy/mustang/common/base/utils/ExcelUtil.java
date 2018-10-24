package com.hqjy.mustang.common.base.utils;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/6/5 11:37
 */
@Slf4j
public class ExcelUtil<T1, T2> implements Serializable {
    private static final long serialVersionUID = 551970754610248636L;

    private Class<T1> clazz1;
    private Class<T2> clazz2;

    public ExcelUtil(Class<T1> clazz1, Class<T2> clazz2) {
        this.clazz1 = clazz1;
        this.clazz2 = clazz2;
    }

    /**
     * 将excel表单数据源的数据导入到list
     *
     * @param sheetName 工作表的名称
     * @param input     java输入流
     */
    public List<T1> getExcelToList(String sheetName, InputStream input) {
        List<T1> list = new ArrayList<>();

        try {
            HSSFWorkbook book = new HSSFWorkbook(input);
            HSSFSheet sheet = null;
            // 如果指定sheet名,则取指定sheet中的内容.
            if (StringUtils.isNotBlank(sheetName)) {
                sheet = book.getSheet(sheetName);
            }
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            if (sheet == null) {
                sheet = book.getSheetAt(0);
            }
            // 得到数据的行数
            int rows = sheet.getLastRowNum();
            // 有数据时才处理
            if (rows > 0) {
                // 得到类的所有field
                Field[] allFields = clazz1.getDeclaredFields();
                // 定义一个map用于存放列的序号和field
                Map<Integer, Field> fieldsMap = new HashMap<>(10);
                for (int i = 0, index = 0; i < allFields.length; i++) {
                    Field field = allFields[i];
                    // 将有注解的field存放到map中
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {
                        // 设置类的私有字段属性可访问
                        field.setAccessible(true);
                        fieldsMap.put(index, field);
                        index++;
                    }
                }
                // 从第2行开始取数据,默认第一行是表头
                for (int i = 1; i < rows + 1; i++) {
                    // 得到一行中的所有单元格对象.
                    T1 entity = this.getExcelData(sheet, fieldsMap, i);
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(StatusCode.IMPORT_EXCEL_EXCEPTION);
        }
        return list;
    }

    private T1 getExcelData(HSSFSheet sheet, Map<Integer, Field> fieldsMap, int i) throws InstantiationException, IllegalAccessException, ParseException {
        HSSFRow row = sheet.getRow(i);
        Iterator<Cell> cells = row.cellIterator();
        T1 entity = null;
        while (cells.hasNext()) {
            // 单元格中的内容.
            String c;
            Cell cell = cells.next();
            int index = cell.getColumnIndex();
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    c = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    //如果EXCEL单元格的数据为数字是，正常读取的话,POI需要用getNumericCellValue()来获得值
                    // 这样一来取得的值会是以科学技术法表示的一串数值.
                    // 如果我们想要获取单元格中的原样数值的话，需要做如下处理
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    c = String.valueOf(cell.getStringCellValue());
                    break;
                default:
                    c = null;
            }
            if (StringUtils.isEmpty(c)) {
                continue;
            }
            // 如果不存在实例则新建
            entity = (entity == null ? clazz1.newInstance() : entity);
            // 从map中得到对应列的field
            Field field = fieldsMap.get(index);
            if (field == null) {
                continue;
            }
            // 取得类型,并根据对象类型设置值.
            Class<?> fieldType = field.getType();
            if (fieldType == null) {
                continue;
            }
            if (String.class == fieldType) {
                field.set(entity, String.valueOf(c));
            } else if (BigDecimal.class == fieldType) {
                c = c.contains("%") ? c.replace("%", "") : c;
                field.set(entity, BigDecimal.valueOf(Double.valueOf(c)));
            } else if (Date.class == fieldType) {
                field.set(entity, DateUtils.parseDate(c));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(entity, Integer.parseInt(c));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(entity, Long.valueOf(c));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(entity, Float.valueOf(c));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(entity, Short.valueOf(c));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(entity, Double.valueOf(c));
            } else if (Character.TYPE == fieldType) {
                if (c.length() > 0) {
                    field.set(entity, c.charAt(0));
                }
            }
        }
        return entity;
    }

    /**
     * 将list数据源的数据导入到excel表单
     *
     * @param list      数据源
     * @param sheetName 表头标题
     * @param output    java输出流
     */
    public void getListToExcel(List<T1> list, String sheetName, Object countObj, OutputStream output) {
        try {
            // excel中每个sheet中最多有65536行
            int sheetSize = 65536;
            // 得到所有定义字段
            Field[] allFields = clazz1.getDeclaredFields();
            List<Field> fields = new ArrayList<>();
            // 得到所有field并存放到一个list中
            for (Field field : allFields) {
                if (field.isAnnotationPresent(ExcelAttribute.class)) {
                    fields.add(field);
                }
            }
            // 产生工作薄对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 取出一共有多少个sheet
            int listSize = 0;
            if (list != null && list.size() > 0) {
                listSize = list.size();
            }
            double sheetNo = Math.ceil(listSize / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                int startRow = 0;
                // 产生工作表对象
                HSSFSheet sheet = workbook.createSheet();
                HSSFRow row;
                if (StringUtils.isNotBlank(sheetName)) {
                    sheet.createRow(0).createCell(fields.size() / 2).setCellValue(sheetName);
                    startRow = 1;
                }
                row = sheet.createRow(startRow);
                /* *********普通列样式********* */
                HSSFFont font = workbook.createFont();
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                font.setFontName("Arail narrow");
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                /* *********标红列样式********* */
                HSSFFont newFont = workbook.createFont();
                HSSFCellStyle newCellStyle = workbook.createCellStyle();
                newFont.setFontName("Arail narrow");
                newFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                /* *************创建列头名称*************** */
                this.setHeadField(fields, sheet, row, font, cellStyle);
                /* *************创建内容列*************** */
                this.setData(list, sheetSize, fields, workbook, listSize, index, startRow, sheet, newFont, newCellStyle);
                if (countObj != null) {
                    this.setCount(fields, sheet, countObj);
                }
            }
            output.flush();
            workbook.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(StatusCode.EXPORT_EXCEL_EXCEPTION);
        }

    }

    private void setHeadField(List<Field> fields, HSSFSheet sheet, HSSFRow row, HSSFFont font, HSSFCellStyle cellStyle) {
        HSSFCell cell;
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
            int col = i;
            // 根据指定的顺序获得列号
            if (StringUtils.isNotBlank(attr.column())) {
                col = getExcelCol(attr.column());
            }
            // 创建列
            cell = row.createCell(col);
            // 字体颜色
            font.setColor(HSSFFont.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, (int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 2.5 * 256));
            // 设置列中写入内容为String类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            // 写入列名
            cell.setCellValue(attr.name());
        }
    }

    private void setData(List<T1> list, int sheetSize, List<Field> fields, HSSFWorkbook workbook, int listSize, int index, int startRow, HSSFSheet sheet, HSSFFont newFont, HSSFCellStyle newCellStyle) throws IllegalAccessException {
        HSSFFont font;
        HSSFCellStyle cellStyle;
        HSSFRow row;
        HSSFCell cell;
        font = workbook.createFont();
        cellStyle = workbook.createCellStyle();
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, listSize);
        // 写入各条记录,每条记录对应excel表中的一行
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 + startRow - startNo);
            T1 vo = (T1) list.get(i);
            for (int j = 0; j < fields.size(); j++) {
                // 获得field
                Field field = fields.get(j);
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                int col = j;
                // 根据指定的顺序获得列号
                if (StringUtils.isNotBlank(attr.column())) {
                    col = getExcelCol(attr.column());
                }
                // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                if (attr.isExport()) {
                    // 创建cell
                    cell = row.createCell(col);
                    if (attr.isMark()) {
                        newFont.setColor(HSSFFont.COLOR_RED);
                        newCellStyle.setFont(newFont);
                        cell.setCellStyle(newCellStyle);
                    } else {
                        font.setColor(HSSFFont.COLOR_NORMAL);
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                    }
                    // 如果数据存在就填入,不存在填入空格
                    Class<?> classType = (Class<?>) field.getType();
                    String value = null;
                    if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
                        value = DateUtils.format((Date) field.get(vo), DateUtils.DATE_TIME_PATTERN);
                    }
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(field.get(vo) == null ? "" : value == null ? String.valueOf(field.get(vo)) : value);
                }
            }
        }
    }

    private void setCount(List<Field> fields, HSSFSheet sheet, Object countObj) {
        T2 obj = (T2) countObj;
        Field[] allFields = clazz2.getDeclaredFields();
        List<Field> f = new ArrayList<>();
        // 得到所有field并存放到一个list中
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelAttribute.class)) {
                f.add(field);
            }
        }
        HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        fields.forEach(y -> {
            ExcelAttribute b = y.getAnnotation(ExcelAttribute.class);
            if ("A".equals(b.column())) {
                HSSFCell sumCell = lastRow.createCell(getExcelCol(b.column()));
                sumCell.setCellValue(new HSSFRichTextString("合计"));
            }
            f.forEach(x -> {
                x.setAccessible(true);
                ExcelAttribute a = x.getAnnotation(ExcelAttribute.class);
                try {
                    if (a.column().equals(b.column())) {
                        HSSFCell sumCell = lastRow.createCell(getExcelCol(a.column()));
                        sumCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        sumCell.setCellValue(x.get(obj) == null ? "0" : String.valueOf(x.get(obj)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col 列
     */
    private static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }


}
