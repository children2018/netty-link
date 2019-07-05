package org.netty.link.client;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client4 extends Thread {

	private ServiceConfig sc;
	public CountDownLatch cd;
	public List<ChannelFuture> futrueList = new ArrayList<ChannelFuture>();

	public Client4(CountDownLatch cd, ServiceConfig sc, List<ChannelFuture> futrueList ) {
		this.cd = cd;
		this.sc = sc;
		this.futrueList = futrueList;
	}

	@Override
	public void run() {

		// worker负责读写数据
		EventLoopGroup worker = new NioEventLoopGroup();

		try {
			// 辅助启动类
			Bootstrap bootstrap = new Bootstrap();
			// 设置线程池
			bootstrap.group(worker);
			// 设置socket工厂
			bootstrap.channel(NioSocketChannel.class);
			// 设置管道
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					// 获取管道
					ChannelPipeline pipeline = socketChannel.pipeline();
					// 字符串解码器
					pipeline.addLast(new StringDecoder());
					// 字符串编码器
					pipeline.addLast(new StringEncoder());
					//pipeline.addLast(new FixedLengthFrameDecoder(128));
					// 处理类
					pipeline.addLast(new ClientHandler4());
				}
			});
			// 发起异步连接操作
			ChannelFuture futrue = bootstrap.connect(new InetSocketAddress(sc.getUrl(), sc.getPort())).sync();
			futrueList.add(futrue);
			cd.countDown();
			// 等待客户端链路关闭
			futrue.channel().closeFuture().sync();
		} catch (Exception e) {
			System.out.println("create.conection.error:" + e.getMessage());
			cd.countDown();
		} finally {
			// 优雅的退出，释放NIO线程组
			worker.shutdownGracefully();
		}
	}

}

