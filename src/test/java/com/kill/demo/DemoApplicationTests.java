package com.kill.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() throws IOException, InterruptedException {
		String[] args1 = new String[]{"python", "provider/src/main/resources/spider/spider.py", "跳蛋"};
		System.out.println("spider start");
		Process pr = Runtime.getRuntime().exec(args1);
		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "gb2312"));
		String line;
		while((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
		pr.waitFor();
		System.out.println("spider end");
	}

}
