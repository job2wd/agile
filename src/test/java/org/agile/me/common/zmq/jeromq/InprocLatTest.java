/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, vito. All Rights Reserved.
 */
package org.agile.me.common.zmq.jeromq;

import org.agile.me.BaseJunitTest;
import org.junit.Test;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-8-29 上午10:33:34$
 * @LastChanged $Author:$, $Date:: #$
 */
public class InprocLatTest extends BaseJunitTest {

  @Test
  public void testJeromqInprocLatServer() {
    Ctx ctx = ZMQ.zmq_init(1);
    if (ctx == null) {
      printf("error in zmq_init:");
      return;
    }

    SocketBase s = ZMQ.zmq_socket(ctx, ZMQ.ZMQ_REQ);
    if (s == null) {
      printf("error in zmq_socket: ");
      return;
    }

    boolean rc = ZMQ.zmq_bind(s, "inproc://lat_test");
    if (!rc) {
      printf("error in zmq_bind: ");
      return;
    }

    Msg smsg = ZMQ.zmq_msg_init_size(10);
    
    while (true) {
      int r1 = ZMQ.zmq_sendmsg(s, smsg, 0);
      if (r1 < 0) {
          printf("error in zmq_sendmsg: %s\n");
          return;
      }
      
      Msg msg = ZMQ.zmq_recvmsg(s, 0);
      if (msg == null) {
        printf("error in zmq_recvmsg: %s\n");
        break;
      }

      printf("服务端接收到数据：" + msg.toString());

      /*
      Msg smsg = new Msg();
      smsg.put("OK".getBytes());

      int r = ZMQ.zmq_sendmsg(s, smsg, 0);

      if (r < 0) {
        printf("error in zmq_sendmsg: %s\n");
        return;
      }
      */
    }

  }

  @Test
  public void testJeromqInprocLatClient() {
    Ctx ctx = ZMQ.zmq_init(1);

    SocketBase s = ZMQ.zmq_socket(ctx, ZMQ.ZMQ_REP);
    if (s == null) {
      printf("error in zmq_socket: %s\n");
      return;
    }

    boolean rc = ZMQ.zmq_connect(s, "inproc://lat_test");
    if (!rc) {
      printf("error in zmq_connect: %s\n");
      return;
    }

    Msg msg = new Msg();
    msg.put("12345".getBytes());

    int r = ZMQ.zmq_sendmsg(s, msg, 0);
    if (r < 0) {
      printf("error in zmq_sendmsg: %s\n");
      return;
    }

  }

  private void printf(String string) {
    System.out.println(string);
  }

}
