package org.netty.link.service;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler4 extends SimpleChannelInboundHandler<String> {
	
	private int num = 0;
	
    //读取客户端发送的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client response :"+msg);
        ctx.channel().writeAndFlush("i am server !" + (num++) + "\r\n");

//        ctx.writeAndFlush("i am server !").addListener(ChannelFutureListener.CLOSE);
    }

    //新客户端接入
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
    	String clientIp = ipSocket.getAddress().getHostAddress();
        System.out.println("channelActive_client_ip:" + clientIp + ",port:" + ipSocket.getPort());
    }

    //客户端断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	//此处应该用数据库记录客户端的状态
        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.channel().close();
        //打印异常
        cause.printStackTrace();
    }
}