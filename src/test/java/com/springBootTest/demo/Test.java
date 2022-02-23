package com.springBootTest.demo;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DateUtils;

public class Test {
	public static void main(String[] args) {
		Date date=new Date();
		System.out.println("current: "+date);
		long l=120;
		System.out.println("current: "+DateUtils.addSeconds(date,Math.toIntExact(l)));
		
	}
}
