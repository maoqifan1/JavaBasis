package com.maoqifan.demo.utils;

import com.maoqifan.demo.pojo.UserDo;
import com.maoqifan.demo.pojo.UserAnnotation;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ExcelExporter {
    public static void export(List<UserDo> users, OutputStream outputStream) {
        try (Workbook workBook = new XSSFWorkbook()) {
            // 创建表格
            Sheet sheet = workBook.createSheet();
            // 写入标题行
            Row row = sheet.createRow(0);
            // 利用反射获取字段
            Field[] declaredFields = UserDo.class.getDeclaredFields();
            // 利用流保留带有注解的字段
            List<String> names = Stream.of(declaredFields)
                    .filter(f -> f.isAnnotationPresent(UserAnnotation.class))
                    .map(f -> f.getAnnotation(UserAnnotation.class).name())
                    .collect(Collectors.toList());
            for (int i = 0; i < names.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(names.get(i));
            }


            // 写入数据行
            int j = 1;
            for (UserDo user : users) {
                Row dataRow = sheet.createRow(j);
                // 写入
                writeRowData(dataRow, user);
            }
            workBook.write(outputStream);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void writeRowData(Row userRow, UserDo user) throws IllegalAccessException {
        Field[] fields = UserDo.class.getDeclaredFields();
        List<Field> filterFields = Arrays.stream(fields)
                .filter(f -> f.isAnnotationPresent(UserAnnotation.class))
                .collect(Collectors.toList());
        for (int i = 0; i < filterFields.size(); i++) {
            Cell cell = userRow.createCell(i);
            Field field = filterFields.get(i);
            field.setAccessible(true); // 设置变量可访问
            // 取annotation,判断dictValue是否为空
            UserAnnotation annotation = field.getAnnotation(UserAnnotation.class);
            if (!StringUtils.isEmpty(annotation.dictValue())) {
                String s = annotation.dictValue();
                String[] kvs = s.split(",");
                Map<String, String> map = new HashedMap<>();
                for (String kv : kvs) {
                    String[] split = kv.split("=");
                    map.put(split[0], split[1]);
                }
                String key = String.valueOf(field.get(user));
                String value = map.get(key);
                cell.setCellValue(value);
                // 写入单元格

            }else {
                cell.setCellValue(String.valueOf(field.get(user)));
            }
            field.setAccessible(false);
        }
    }

    public static void main(final String... args) throws IOException {
        List<UserDo> users = List.of(
                new UserDo("mao", "qq", 1)
        );
        OutputStream outputStream = Files.newOutputStream(Paths.get("users.xlsx"));
        export(users, outputStream);
    }
}
