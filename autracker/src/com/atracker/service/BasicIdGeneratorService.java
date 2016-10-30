package com.atracker.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.Locale;

public class BasicIdGeneratorService {

	private String hostname;
	private final SecureRandom rnd; 
	private long count;

	public BasicIdGeneratorService() {
		try {
			this.setHostname(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException ex) {
			this.setHostname("localhost");
		}
		try {
			this.rnd = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException ex) {
			throw new Error(ex);
		}
		this.rnd.setSeed(System.currentTimeMillis());
	}

	public synchronized void generate(StringBuilder buffer) {
		this.count += 1L;
		int rndnum = this.rnd.nextInt();
		buffer.append(System.currentTimeMillis()); 
		Formatter formatter = new Formatter(buffer, Locale.US);
		formatter.format( "%1$02x%2$08x", new Object[] { Long.valueOf(this.count),
						Integer.valueOf(rndnum) });
		formatter.close(); 
	}

	public     String generate() {
		StringBuilder buffer = new StringBuilder();
		generate(buffer);
		return buffer.toString();
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
