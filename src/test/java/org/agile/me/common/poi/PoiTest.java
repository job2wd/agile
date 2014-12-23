/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, vito. All Rights Reserved.
 */
package org.agile.me.common.poi;

import org.junit.Test;


/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-8-29 上午9:44:32$
 * @LastChanged $Author:$, $Date::                    #$
 */
public class PoiTest {
  
  @Test
  public void test() {
    org.apache.poi.ss.usermodel.Sheet t = null;
    org.apache.poi.hssf.usermodel.HSSFWorkbook w = null;
    
    w.getSheetIndex(t);
    
    org.apache.poi.hssf.usermodel.HSSFSheet hs = null;
    w.getSheetIndex(hs);
  }
  
}
