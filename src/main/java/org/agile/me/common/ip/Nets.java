/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, LJ. All Rights Reserved.
 */
package org.agile.me.common.ip;


/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-10-15 下午4:52:12$
 * @LastChanged $Author:$, $Date::                    #$
 */
public class Nets {

  private String StartIP;
  private String EndIP;
  private String NetMask;
  public String getStartIP() {
      return StartIP;
  }
  public void setStartIP(String startIP) {
      StartIP = startIP;
  }
  public String getEndIP() {
      return EndIP;
  }
  public void setEndIP(String endIP) {
      EndIP = endIP;
  }
  public String getNetMask() {
      return NetMask;
  }
  public void setNetMask(String netMask) {
      NetMask = netMask;
  }
  
}
