package com.challengr.test;

import java.util.zip.CRC32;

public class CRC32Test {
	public static void main(String[] args) {
		
		CRC32 crc32 = new CRC32();
		crc32.update(("网页设计与制作"+"7111452933, 9787111452935").getBytes());
		System.out.println(crc32.getValue());
	}
}
