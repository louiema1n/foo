package com.lm.controller;

import com.lm.domain.Class;
import com.lm.domain.Schedule;
import com.lm.domain.shiro.User;
import com.lm.service.ClassService;
import com.lm.service.ScheduleService;
import com.lm.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导入导出相关
 * Created by Louie on 2017-08-22.
 */
@RequestMapping("/excel")
@Controller
public class ExcelController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    // 下载文件路径
    @Value("${web.dwd-path}")
    private String dwdpath;

    // 上传文件路径
    @Value("${web.upd-path}")
    private String updpath;

    /**
     * 根据时间段导出排班记录并转换成Excel，然后下载
     *
     * @param response
     * @param beginDay
     * @param endDay
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping("/download/{beginDay}/{endDay}")
    private void getExcel(HttpServletResponse response,
                          @PathVariable("beginDay") String beginDay,
                          @PathVariable("endDay") String endDay) throws ParseException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("考勤至" + endDay);
        // 根据时间段查询数据
        List<Schedule> schedules = this.scheduleService.queryAll(
                new SimpleDateFormat("yyyy-MM-dd").parse(beginDay),
                new SimpleDateFormat("yyyy-MM-dd").parse(endDay));

        // 添加表头
        // 创建sheet行
        HSSFRow row = sheet.createRow(0);
        // 设置列宽，arg2要乘以256
//        sheet.setColumnWidth(2, 256 * 12);
        // 设置居中加粗
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        // 创建title字段
        HSSFCell cell;
        cell = row.createCell(0);

        // 重新整理数据,大Map的key为姓名；大map的value为小map；小map的key为日期，value为班次
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < schedules.size(); i++) {
            String firstNickName = schedules.get(0).getUser().getNickname();    // 第一个名字
            String nickName = schedules.get(i).getUser().getNickname();         // 每一个名字
            // 放入map
            map.put(nickName, new LinkedHashMap<String, String>());
            if (i != 0 && nickName.equals(firstNickName)) {
                break;
            }
        }
        // 遍历schedules，进行数据整理
        for (Schedule schedule : schedules) {
            String nickname = schedule.getUser().getNickname();     // 姓名
            Date schtime = schedule.getSchtime();                   // 排班时间
            String cnickname = schedule.getaClass().getCnickname(); // 班次名称
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (nickname.equals(key)) {
                    // 将日期和班次存入value
                    Map<Date, String> strMap = (Map<Date, String>) map.get(key);
                    strMap.put(schtime, cnickname);
                    break;
                }
            }
        }
        // 遍历整理后的数据，赋值到单元格
        // 设置姓名列
        int rowNum = 1, flag = 0;
        for (String key : map.keySet()) {
            Map<Date, String> liteMap = (Map<Date, String>) map.get(key);
            int cellNum = 0;
            // 设置表头日期
            if (flag == 0) {
                for (Date date : liteMap.keySet()) {
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    cell.setCellStyle(style);
                }
                flag = 1;
                cellNum = 0;
            }
            // 设置表头列宽
            for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
                // 第一列自定义
                sheet.setColumnWidth(0, 8 * 265);
                sheet.autoSizeColumn(i);
            }
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum);
            // 设置名字
            cell.setCellValue(key);
            // 遍历小map
            for (Date date : liteMap.keySet()) {
                cellNum++;
                cell = row.createCell(cellNum);
                String value = liteMap.get(date);
                // 获取calendar实例
                Calendar calendar = Calendar.getInstance();
                // 设置日历时间
                calendar.setTime(date);
                // 获取当前日期是周几
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                // 周五、六的夜班为小夜班
                if ((weekDay == 5 || weekDay == 6) && value.equals("夜")) {
                    value = "小夜";
                }
                cell.setCellValue(value);
            }
        }
        String fileName = "病理技术室" + new SimpleDateFormat("MM月份").format(new SimpleDateFormat("yyyy-MM-dd").parse(endDay)) + "考勤表.xls";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());


        File file = new File(dwdpath + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    /**
     * 导入excel至考勤表
     *
     * @param file
     */
    @RequestMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extend = fileName.substring(fileName.lastIndexOf("."));
        Integer integer = 0;
        if (!extend.equals(".xls")) {
            // 文件非法
        } else {
            // 上传文件
            File newFile = new File(updpath + fileName);
            if (!newFile.getParentFile().exists()) {
                // 不存在，则创建
                newFile.getParentFile().mkdirs();
            }
            BufferedOutputStream bos = null;
            try {
                // 创建缓冲输出流
                bos = new BufferedOutputStream(new FileOutputStream(newFile));
                bos.write(file.getBytes());
                bos.flush();
                bos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 读取Excel文件
            HSSFWorkbook workbook;
            FileInputStream fis = null;
            List<Schedule> schedules = new ArrayList<>();
            List<User> users = this.userService.queryAll();
            List<Class> classes = this.classService.queryAll();
            try {
                fis = new FileInputStream(new File(updpath + fileName));
                workbook = new HSSFWorkbook(fis);
                // 获取第一个sheet
                HSSFSheet sheet = workbook.getSheetAt(0);
                if (sheet != null) {
                    // 遍历第一行数据
                    HSSFRow row = sheet.getRow(0);
                    // 从第二个单元格开始（getPhysicalNumberOfCells获取不为空的单元格数；getLastCellNum最后一个不为空的列的index）
                    for (int i = 1; i < row.getLastCellNum(); i++) {
                        // 获取单元格的值-->schtime
                        Cell cell = row.getCell(i);
                        String schtime = cell.toString();
                        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                            Schedule schedule = new Schedule();
                            // 格式化成sql date
                            schedule.setSchtime(java.sql.Date.valueOf(schtime));
                            // 第二行的第一个单元格数据-->uid
                            String nickName = sheet.getRow(j).getCell(0).toString();
                            for (User user : users) {
                                if (nickName.equals(user.getNickname())) {
                                    schedule.setUid(user.getUid());
                                    break;
                                }
                            }
                            // 第二行的第二个单元格数据-->cid
                            String cnickName = sheet.getRow(j).getCell(i).toString();
                            for (Class clazz : classes) {
                                if (cnickName.equals(clazz.getCname())) {
                                    schedule.setCid(clazz.getCid());
                                    break;
                                }
                            }
                            // 添加至list
                            schedules.add(schedule);
                        }
                    }
                }
                // 插入数据库
                integer = this.scheduleService.batchAdd(schedules);
                System.out.println("共插入" + integer + "条数据");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (integer > 0) {
            return "redirect:/schedule";
        }
        return "redirect:/error";
    }
}
