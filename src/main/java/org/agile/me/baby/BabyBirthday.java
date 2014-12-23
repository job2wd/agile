package org.agile.me.baby;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-6-26 下午1:10:26$
 * @LastChanged $Author:$, $Date:: #$
 */
public class BabyBirthday {

  public static void main(String[] args) {
    Date date = new Date();

    date = DateUtils.setMonths(date, 3);
    date = DateUtils.setDays(date, 8);
    date = DateUtils.setHours(date, 3);
    date = DateUtils.setMinutes(date, 55);
    date = DateUtils.setSeconds(date, 0);

    Date now = new Date();
    //now = DateUtils.setHours(now, 3);
    //now = DateUtils.setMinutes(now, 55);

    System.out.println("出生日期：" + DateToString(date, "yyyy-MM-dd HH:mm:ss"));
    System.out.println("当前日期：" + DateToString(now, "yyyy-MM-dd HH:mm:ss"));
    System.out.println("出生天数：" + getBetweenTimes(date, now));//"第 " + (((now.getTime() - date.getTime()) / (24 * 60 * 60 * 1000)) + 1) + " 天");

    date = DateUtils.addDays(date, 100);

    //date = DateUtils.addDays(date, -1);

    System.out.println("百岁日期：" + DateToString(date, "yyyy-MM-dd HH:mm:ss"));
  }

  /***
   * 将日期转化为字符串
   */
  public static String DateToString(Date date, String pattern) {
    String str = "";
    try {
      SimpleDateFormat formater = new SimpleDateFormat(pattern);
      str = formater.format(date);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return str;
  }

  private static String getBetweenTimes(Date startTime, Date now) {
    long startupTime = startTime.getTime();
    long nowTime = now.getTime();

    long days = 0;
    long hours = 0;
    long minutes = 0;
    long seconds = 0;

    long interval = nowTime - startupTime;

    days = interval / (24 * 60 * 60 * 1000);
    hours = (interval % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
    minutes = (((interval % (24 * 60 * 60 * 1000)) % (60 * 60 * 1000))) / (60 * 1000);
    seconds = ((((interval % (24 * 60 * 60 * 1000)) % (60 * 60 * 1000))) % (60 * 1000)) / 1000;

    return days + " 天  " + hours + " 小时  " + minutes + " 分  " + seconds + " 秒";
  }

}
