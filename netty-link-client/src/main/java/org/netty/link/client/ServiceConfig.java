package org.netty.link.client;

public class ServiceConfig {

	private String url;
	private int port;

	public ServiceConfig(String url, int port) {
		super();
		this.url = url;
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
