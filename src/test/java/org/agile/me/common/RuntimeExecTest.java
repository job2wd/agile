/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, LJ. All Rights Reserved.
 */
package org.agile.me.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-11-11 上午10:12:48$
 * @LastChanged $Author:$, $Date:: #$
 */
public class RuntimeExecTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    args = new String[1];
    args[0] = "dir";
    if (args.length < 1) {
      System.out.println("USAGE: java GoodWindowsExec <cmd>");
      System.exit(1);
    }

    try {
      String osName = System.getProperty("os.name");
      String[] cmd = new String[3];

      if (osName.contains("Windows")) {
        cmd[0] = "cmd.exe";
        cmd[1] = "/C";
        cmd[2] = args[0];
      } else if (osName.equals("Windows 95")) {
        cmd[0] = "command.com";
        cmd[1] = "/C";
        cmd[2] = args[0];
      }

      Runtime rt = Runtime.getRuntime();
      
      System.out.println("availableProcessors:" + rt.availableProcessors());
      System.out.println("freeMemory:" + rt.freeMemory());
      System.out.println("maxMemory:" + rt.maxMemory());
      System.out.println("totalMemory:" + rt.totalMemory());
      
      System.out.println("Execing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
      Process proc = rt.exec(cmd);
      // any error message?
      StreamGobbler errorGobbler = new RuntimeExecTest().new StreamGobbler(proc.getErrorStream(), "ERROR");

      // any output?
      StreamGobbler outputGobbler = new RuntimeExecTest().new StreamGobbler(proc.getInputStream(), "OUTPUT");

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal = proc.waitFor();
      System.out.println("ExitValue: " + exitVal);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  class StreamGobbler extends Thread {

    InputStream is;
    String      type;

   public StreamGobbler(InputStream is, String type) {
      this.is = is;
      this.type = type;
    }

    @Override
    public void run() {
      try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
          System.out.println(type + ">" + new String(line.getBytes(), "GBK"));
        }
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

}
