/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, vito. All Rights Reserved.
 */
package org.agile.me.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.agile.me.BaseJunitTest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;


/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-8-9 上午10:21:01$
 * @LastChanged $Author:$, $Date::                    #$
 */
public class DigestTest extends BaseJunitTest {
  
  @Test(timeout=1000000000000L)
  @Ignore
  public void testMD5Hex() throws Exception {
    String fileName = "D:/Tools/OS/Windows8.1/cn_windows_8.1_with_update_x64_dvd_4048046.iso";
    
    String md5Hex = DigestUtils.md5Hex(FileUtils.openInputStream(new File(fileName)));
    
    System.out.println("File:[" + fileName + "] \nMD5 Hex:[" + md5Hex.toUpperCase() + "]");
  }
  
  @Test(timeout=1000000000000L)
  @Ignore
  public void testSHA1Hex() throws Exception {
    String fileName = "D:/Tools/OS/Windows8.1/cn_windows_8.1_with_update_x64_dvd_4048046.iso";
    
    String sha1Hex = DigestUtils.sha1Hex(FileUtils.openInputStream(new File(fileName)));
    
    System.out.println("File:[" + fileName + "] \nSHA1 Hex:[" + sha1Hex.toUpperCase() + "]");
  }
      
  @Test
  public void testCheckDupFiles() throws Exception {
    String srcPath = "E:\\David\\百度云\\百度云同步盘";
    String dupPath = "E:/dup.properties";
    
    Iterator<File> iter = FileUtils.iterateFiles(new File(srcPath), new IOFileFilter(){

      @Override
      public boolean accept(File file) {
        return true;//!file.getName().endsWith(".txt");
      }

      @Override
      public boolean accept(File dir, String name) {
        return true;
      }
      
    },
    new IOFileFilter(){

      @Override
      public boolean accept(File file) {
        return !file.getAbsolutePath().contains(".baohe.cache");
      }

      @Override
      public boolean accept(File dir, String name) {
        return !name.contains(".baohe.cache");
      }
      
    });

    Map<String, File> okFiles = new HashMap<String, File>();
    List<String> dupFiles = new ArrayList<String>();
    
    System.out.println("开始检查重复文件....");
    
   int count = 0;
   while(iter.hasNext()) {
     File f = iter.next();
     
     String md5Hex = DigestUtils.md5Hex(FileUtils.openInputStream(f));
     
     if(!okFiles.containsKey(md5Hex)) {
       okFiles.put(md5Hex, f);
     } else {
       File f1 = okFiles.get(md5Hex);
       
       dupFiles.add(StringUtils.rightPad(f1.getAbsolutePath(), 100) + f.getAbsolutePath());
              
       System.out.println("文件【" + f1 + "】和【" + f + "】重复。");
     }
    
     count++;
   }
    
   System.out.println("检查重复文件结束。" + "共检查文件数量：【" + count + "】个，其中正常文件：【" + okFiles.size() + "】个；重复文件：【" + dupFiles.size() + "】个");
   
   okFiles.clear();
   
   //FileUtils.cleanDirectory(new File(dupPath));
   
   System.out.println("开始写入重复文件记录....");

   FileUtils.writeLines(new File(dupPath), dupFiles);

   System.out.println("写入重复文件记录结束。");
  }
  
  @Test
  public void testMoveDupFiles() throws Exception {
    String dupPath = "E:/dup.properties";
    String destDir = "E:/David/百度云/百度云本地备份/被移除的重复文件";
    
    List<String> lines = FileUtils.readLines(new File(dupPath));
    
    for(String line : lines) {
      if(StringUtils.isNotBlank(line)) {
        //line = line.trim();
        
        File srcFile = new File(line);
        //srcFile.getCanonicalPath();
        
        if(!srcFile.exists()) {
          System.out.println("file [" + srcFile + "] is not exists.");
          continue;
        }
        
        String df = srcFile.getParent();
        
        df = df.replaceAll("E:\\\\David\\\\百度云\\\\百度云同步盘", "");
        File destDirf = new File(destDir + df);
        
        System.out.println("开始移动重复文件：" + srcFile + " ---> " + destDirf);
        
        FileUtils.moveFileToDirectory(srcFile, destDirf, true);//copyFileToDirectory(srcFile, destDirf);
        
        System.out.println("移动文件结束！");
      }
    }
    
  }
  
  @Test
  public void testStringUtils() throws Exception {
    System.out.println(StringUtils.rightPad("a", 10));
    System.out.println(new File("E:/David/Baby/重复的文件/dup.txt").getAbsolutePath());
    String p = "E:\\David\\百度云\\百度云同步盘\\Job\\Book\\编程之美\\task manager.pdf";
    System.out.println(p.replaceAll("E:\\\\David\\\\百度云", ""));
    p = "E:/David/百度云/百度云同步盘/Job/Book/编程之美/task manager.pdf";
    File srcFile = new File(p);
    File destDir = new File("E:/");
    //FileUtils.moveFileToDirectory(srcFile, destDir, true);
    FileUtils.copyFileToDirectory(srcFile, destDir);
  }
  
}
