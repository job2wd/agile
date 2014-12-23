/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, LJ. All Rights Reserved.
 */
package org.agile.me.common;

import java.io.File;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.agile.me.BaseJunitTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Chaos - n.混乱，紊乱； （天地未出现的）浑沌世界； 〈古〉无底深渊； 一团糟
                                       网 络
                                      混沌；混乱；骚乱；紊乱
 * @author wangwd
 * @version $Revision:$, $Date: 2014-9-22 下午12:05:08$
 * @LastChanged $Author:$, $Date::                    #$
 */
public class ChaosTest extends BaseJunitTest {
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    //PRINT_STATISTICS_LOG = false;
    BaseJunitTest.setUpBeforeClass();
  }
  
  @Ignore("Data too large.")
  @Test(timeout=10 * 1000L, expected=java.lang.OutOfMemoryError.class)//10 秒
  public void testPerformance() throws Exception {
    int count = 100 * 10000;//测试 100 万次循环耗时
    count = count * 100;//测试 1亿次循环耗时
    
    int c = 0;
    List<Integer> list = new ArrayList<Integer>();
    
    for(int i = 1; i <= count; i++) {
      //System.out.println(i);
      c++;
      list.add(c);
    }
    //System.out.println(Integer.MAX_VALUE);
    System.out.println("c=" + c);
  }
  
  @Test(timeout=10 * 1000L)
  public void testDoubleAndFloat() throws Exception {
    float f = -123.9999f;
    double d = 123.999999d;
    
    System.out.println("f=" + f);
    System.out.println("d=" + d);
  }
  
  @Test(timeout=10 * 1000L)
  public void testMathRandom() throws Exception {
    for(int i=0;i<10;i++) {
      int c_index = (int)(Math.random() * 100 * 2.5);
      System.out.println(c_index);
    }
  }
    
  @Test(timeout=10 * 1000L)
  public void testGenerateConnectStatisticsSQL() throws Exception {
    String sql = "INSERT INTO `fba_connect_statistic` (`id`, `statistic_date`, `level`, `connect_count`, `create_time`) VALUES (14108832000004, 20140919, 0, 965, '2014-09-18 00:00:00');";
    
    int[] datas = {20140919, 20140920, 20140921, 20140922, 20140923, 20140924, 20140925, 20140926, 20140927, 20140928, 20140929, 20140930};
    
    long id = 14108832000004l;
    int day = 20;
    
    for(int i=0;i<datas.length;i++) {
      int num1 = (int)(Math.random() * 1000 * (i + 1));
      int num2 = (int)(Math.random() * 1000 * (i + 1));
      int num3 = (int)(Math.random() * 1000 * (i + 1));
      
      sql = "INSERT INTO `fba_connect_statistic` (`id`, `statistic_date`, `level`, `connect_count`, `create_time`) " +
      		"VALUES (" + (id + (i + 1)) + ", " + datas[i] + ", 0, " + num1 + ", '2014-09-" + day + " 00:00:00');";
      System.out.println(sql);
      
      id += 1;
      sql = "INSERT INTO `fba_connect_statistic` (`id`, `statistic_date`, `level`, `connect_count`, `create_time`) " +
          "VALUES (" + (id + (i + 1)) + ", " + datas[i] + ", 1, " + num2 + ", '2014-09-" + day + " 00:00:00');";
      System.out.println(sql);
      
      id += 1;
      sql = "INSERT INTO `fba_connect_statistic` (`id`, `statistic_date`, `level`, `connect_count`, `create_time`) " +
          "VALUES (" + (id + (i + 1)) + ", " + datas[i] + ", 2, " + num3 + ", '2014-09-" + day + " 00:00:00');";
      System.out.println(sql);
      
      day +=1;
    }
  }
  
  @Test(timeout=10 * 1000L)
  public void testChineseAndEngnish() throws Exception {
    String s1 = "好";
    String s2 = "h";
    
    System.out.println(s1.length());
    System.out.println(s2.length());
    
    System.out.println(s1.getBytes(Charset.forName("GB2312")).length);
    System.out.println(s2.getBytes(Charset.forName("UTF-8")).length);
    
    System.out.println(ChineseDistinguisher.isChinese(s1.charAt(0)));
    System.out.println(ChineseDistinguisher.isChinese(s2.charAt(0)));
  }
  
  @Test(timeout=10 * 1000L)
  public void testChineseDistinguisher() throws Exception {
    List<String> lines = FileUtils.readLines(new File("D:\\Work\\IP地址库和经纬度\\国家与首都中英文对照表.txt"), Charset.forName("GB2312"));
    
    int size = lines.size();
    for(int i=0;i<size;i++) {
      String line = lines.get(i);
      
      if(StringUtils.isBlank(line)) {
        continue;
      }
      
      String[] v = line.trim().split("[ ]");
      
      //System.out.println("[" + i + "].length=" + v.length);
      
      int len = v.length;
      String res = "";
      
      for(int j=0;j<len-1;j++) {
        if(j == len-2) {
          res += v[len-1];
        }
        if(ChineseDistinguisher.isChinese(v[j].charAt(v[j].length()-1))) {//是中文
          res += "[" + v[j] + "]";
        } else {//是英文
          if(!ChineseDistinguisher.isChinese(v[j+1].charAt(0))) {//下一个的第一个是否也是英文
            res += v[j] + " " + v[j+1];
          } else {//下一个的第一个是中文
            res += v[j];
          }
        }
      }
      System.out.println(res);
    }
    
  }
  
  @Test(timeout=10 * 1000L)
  public void testInteger() {
    System.out.println("Integer.MAX_VALUE=" + Integer.MAX_VALUE);
    System.out.println("Integer.MIN_VALUE=" + Integer.MIN_VALUE);
    
    System.out.println("Long.MAX_VALUE=" + Long.MAX_VALUE);
    System.out.println("Long.MIN_VALUE=" + Long.MIN_VALUE);
    
    System.out.println((int)14062794600002l);
  }
  
  @Test(timeout=10 * 1000L)
  public void testMessageFormat() {
    System.out.println(MessageFormat.format("hello {0}!", "wwd"));
    
    MessageFormat mf = new MessageFormat("hello {name} {date}!");
    
    //mf.format(arguments, result, pos);
  }
  
  @Test(timeout=10 * 1000L)
  public void testSigar() {
    try {
      Sigar sigar = new Sigar();
      FileSystem fslist[] = sigar.getFileSystemList();
      
      for(FileSystem fs : fslist) {
        System.out.println("type=" + fs.getType());
        System.out.println("flags=" + fs.getFlags());
        System.out.println("sysTypeName=" + fs.getSysTypeName());
        System.out.println("typeName=" + fs.getTypeName());
        System.out.println("devName=" + fs.getDevName());
        System.out.println("dirName=" + fs.getDirName());
        System.out.println("options=" + fs.getOptions());
        
        FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
        
        System.out.println("Avail=" + usage.getAvail());
        System.out.println("DiskQueue=" + usage.getDiskQueue());
        System.out.println("DiskReadBytes=" + usage.getDiskReadBytes());
        System.out.println("DiskReads=" + usage.getDiskReads());
        System.out.println("DiskServiceTime=" + usage.getDiskServiceTime());
        System.out.println("DiskWriteBytes=" + usage.getDiskWriteBytes());
        System.out.println("DiskWrites=" + usage.getDiskWrites());
        System.out.println("Files=" + usage.getFiles());
        System.out.println("Free=" + usage.getFree());
        System.out.println("FreeFiles=" + usage.getFreeFiles());
        System.out.println("Total=" + usage.getTotal());
        System.out.println("Used=" + usage.getUsed());
        System.out.println("UsePercent=" + usage.getUsePercent());
        
        System.out.println("==========================");
      }
      
      }catch(Exception e){
        e.printStackTrace();
      }
  }
  
}
