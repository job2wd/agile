/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, vito. All Rights Reserved.
 */
package org.agile.me.common.zmq.jzmq;

import org.agile.me.BaseJunitTest;
import org.junit.Test;
import org.zeromq.ZMQ;


/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-8-29 上午9:42:43$
 * @LastChanged $Author:$, $Date::                    #$
 */
public class JzmqTest extends BaseJunitTest {

  @Test
  public void testJzmqServer() {
    try {
      jzmqServer();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void jzmqServer() throws Exception {
    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket pull = null;

    try {
      pull = context.socket(ZMQ.PULL);
      pull.bind("ipc:///tmp/sendbb");

      while (true) {
        byte[] datas = pull.recv();
        String actual = new String(datas);
        
        System.out.println("接收到数据：" + actual);
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        pull.close();
      } catch (Exception ignore) {
      }
      try {
        context.term();
      } catch (Exception ignore) {
      }
    }
  }
  
  @Test
  public void testJzmqClient() {
    
  }
  
}
