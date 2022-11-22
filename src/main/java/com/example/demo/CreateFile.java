package com.example.demo;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @author ：lixinyu
 * @version ：M1.0
 * @program ：vevor-research
 * @date ：Created in 2020/9/11 9:59
 * @description ：java图片处理类，包括java图片处理,java图片水印,java图片切割,java图片缩放,java图片格式转换,java图片等比例缩小
 * Image是一个抽象类，BufferedImage是其实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
 * （BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便地操作这个图片），
 * 提供获得绘图对象、图像缩放、选择图像平滑度等功能，通常用来做图片大小变换、图片变灰、设置透明不透明等。
 */

public class CreateFile {


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        for (int i = 1; i < 300; i++) {
            LocalDate tenDaysAhead= localDate.plusDays(i);//2020-03-22

            File file = new File("C:\\Users\\39805\\Desktop\\公众号\\" + tenDaysAhead);

            file.mkdir();
        }

    }

    public void get11() {


        //获得当前日期
        LocalDate localDate = LocalDate.now();//2020-04-11

        //获得固定日期
        LocalDate selfLocalDate = LocalDate.of(2020, 04, 01);//2020-04-01
        //获取年份
        int year = selfLocalDate.getYear(); // 2020
        //获取月份
        int month = selfLocalDate.getMonthValue(); // 04
        //获取日期
        int day = selfLocalDate.getDayOfMonth(); // 01
        //获取星期
        DayOfWeek weekday = selfLocalDate.getDayOfWeek();
        int value = weekday.getValue(); //3

        //日期加十天
        LocalDate tenDaysLater = selfLocalDate.plusDays(10);//2020-04-11
        int yearPlus = tenDaysLater.getYear(); // 2020
        int monthPlus = tenDaysLater.getMonthValue(); // 04
        int dayPlus = tenDaysLater.getDayOfMonth(); // 11
        DayOfWeek weekdayPlus = tenDaysLater.getDayOfWeek();
        int valuePlus = weekdayPlus.getValue(); //6


        //日期减十天
        LocalDate tenDaysAhead = selfLocalDate.minusDays(10);//2020-03-22
        int yearAhead = tenDaysLater.getYear(); // 2020
        int monthAhead = tenDaysLater.getMonthValue(); // 03
        int dayAhead = tenDaysLater.getDayOfMonth(); // 22
        DayOfWeek weekdayAhead = tenDaysAhead.getDayOfWeek();
        int valueAhead = weekdayAhead.getValue(); //7

    }

}
