package org.netty.link.client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.netty.channel.ChannelFuture;

public class Client4Test {
	
	public List<ChannelFuture> futrueList = new ArrayList<ChannelFuture>();
	
	public int currentChannelIndex = -1;
	
	public synchronized ChannelFuture getChannelFuture() throws Exception {

		if (futrueList == null || futrueList.size() == 0) {
			throw new Exception("没有可用的连接");
		}

		if (currentChannelIndex >= (futrueList.size() - 1)) {
			currentChannelIndex = -1;
		}

		currentChannelIndex++;

		ChannelFuture channelFuture = futrueList.get(currentChannelIndex);
		System.out.println("select_channelFuture_number_currentChannelIndex:" + currentChannelIndex);

		return channelFuture;
	}
	
	public void abc () {
		
		List<ServiceConfig> serviceList = Arrays.asList(new ServiceConfig[] {new ServiceConfig("127.0.0.1", 8866), new ServiceConfig("127.0.0.1", 8867), new ServiceConfig("127.0.0.1", 8868)});
		CountDownLatch cd = new CountDownLatch(serviceList.size());
		
		for (ServiceConfig sc : serviceList) {
			Client4 c = new Client4(cd, sc, futrueList);
			c.start();
		}
		
		try {
			cd.await();
			int i = 0 ;
			while (i++ <10) {
				this.getChannelFuture().channel().writeAndFlush("[pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + i + "]");
				Thread.sleep(1000 * 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Client4Test().abc();
	}

}
