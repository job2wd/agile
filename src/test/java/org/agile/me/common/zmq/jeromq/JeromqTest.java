/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, vito. All Rights Reserved.
 */
package org.agile.me.common.zmq.jeromq;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.agile.me.BaseJunitTest;
import org.junit.Test;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.PollItem;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMsg;

/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-8-11 下午4:03:40$
 * @LastChanged $Author:$, $Date:: #$
 */
public class JeromqTest extends BaseJunitTest {

  @Test
  //timeout=1000000000000L)
  public void testJeromqServer() {
    String[] args = { "tcp://*:5555" };

    if (args.length < 1) {
      System.out.printf("I: syntax: flserver1 <endpoint>\n");
      System.exit(0);
    }
    ZContext ctx = new ZContext();
    Socket server = ctx.createSocket(ZMQ.ROUTER);
    server.bind(args[0]);

    System.out.printf("I: echo service is ready at %s\n", args[0]);
    
    ByteBuffer buffer = ByteBuffer.allocate(8);
    
    while (true) {
      try {
        String res = server.recvStr();
        System.out.println("服务端接收到数据：\n" + res);
        
        
        //int i = server.recvByteBuffer(buffer, 0);
        //recv2ByteArray(server);
        
        //recv2ByteBuffer(server);
        
        String sendData = "aaaa";
        
        System.out.println("向客户端发送数据：\n" + sendData);
        
        server.send(sendData);
        
        Thread.sleep(2000);
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
    }

    //ctx.destroy();
  }
  
  private void recv2ByteArray(Socket server) throws Exception {
    byte[] data = server.recv();

    byte[] head = Arrays.copyOfRange(data, 0, 32);

    byte[] typea = Arrays.copyOfRange(head, 0, 4);
    byte[] lena = Arrays.copyOfRange(head, 4, 8);

    int type = byteArrayToInt(typea);//Integer.valueOf(new String(typea));
    int len = byteArrayToInt(lena);

    System.out.println("type=" + type);
    System.out.println("len=" + len);
  }
  
  private void recv2ByteBuffer(Socket server) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    
    int resFlag = server.recvByteBuffer(buffer, 1);

    if (resFlag == -1) {//error
      throw new RuntimeException("the number of bytes read, -1 on error");
    }

    int offset = 0;
    int length = 4;

    byte[] dst = new byte[length];

    ByteBuffer bf = buffer.get(dst, offset, length);
    int type = bf.getInt();

    bf = buffer.get(dst, offset + 4, length);
    int len = bf.getInt();
    
    System.out.println("type=" + type);
    System.out.println("len=" + len);
  }
  
  public static int byteArrayToInt(byte[] b) {
    return  b[0] & 0xFF |
            (b[1] & 0xFF) << 8 |
            (b[2] & 0xFF) << 16 |
            (b[3] & 0xFF) << 24;
  }

  private static final int REQUEST_TIMEOUT = 1000;
  private static final int MAX_RETRIES     = 3;   //  Before we abandon

  @Test
  public void testJeromqClient() {
    String[] argv = { "tcp://127.0.0.1:5555" };

    ZContext ctx = new ZContext();
    ZMsg request = new ZMsg();
    request.add("Hello world" + System.currentTimeMillis());
    ZMsg reply = null;

    int count = 1;
    int endpoints = argv.length;
    
    while(count <= 10) {
      if (endpoints == 0) {
        System.out.printf("I: syntax: flclient1 <endpoint> ...\n");
      } else if (endpoints == 1) {
        //  For one endpoint, we retry N times
        int retries;
        for (retries = 0; retries < JeromqTest.MAX_RETRIES; retries++) {
          String endpoint = argv[0];
          reply = tryRequest(ctx, endpoint, request);
          if (reply != null) {
            System.out.println("服务端响应数据：" + reply.toString());
            break; //  Successful
          }
          System.out.printf("W: no response from %s, retrying...\n", endpoint);
        }
      } else {
        //  For multiple endpoints, try each at most once
        int endpointNbr;
        for (endpointNbr = 0; endpointNbr < endpoints; endpointNbr++) {
          String endpoint = argv[endpointNbr];
          reply = tryRequest(ctx, endpoint, request);
          if (reply != null) {
            break; //  Successful
          }
          System.out.printf("W: no response from %s\n", endpoint);
        }
      }
      count++;
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    if (reply != null) {
      System.out.printf("Service is running OK\n");
      reply.destroy();
    }
    request.destroy();
    ;
    ctx.destroy();
  }

  private ZMsg tryRequest(ZContext ctx, String endpoint, ZMsg request) {
    System.out.printf("I: trying echo service at %s...\n", endpoint);
    Socket client = ctx.createSocket(ZMQ.PUSH);
    client.connect(endpoint);

    //  Send request, wait safely for reply
    ZMsg msg = request.duplicate();
    msg.send(client);
    PollItem[] items = { new PollItem(client, ZMQ.Poller.POLLIN) };
    ZMQ.poll(items, JeromqTest.REQUEST_TIMEOUT);
    ZMsg reply = null;
    if (items[0].isReadable()) {
      reply = ZMsg.recvMsg(client);
    }

    //  Close socket in any case, we're done with it now
    ctx.destroySocket(client);
    return reply;
  }

}
