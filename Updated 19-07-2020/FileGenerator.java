package com.ust;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileGenerator {
	public static void main (String args[])
	{
		//String fileName = args[0];
		//long fileSize = args[1];
		String fileName="H:/Java/file1.txt";
		//long fileSize=3221225472L;		
		//int RAMsize=1073741824;
		long fileSize=100000L;		
		int RAMsize=100;
		generateLargerFileByDemand(fileName, fileSize, RAMsize);		
	}
	
	private static void generateLargerFileByDemand(String fileName, long fileSize, long RAMsize) {
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			long pendingLength=fileSize;
			while (pendingLength > 0)
			{
				writer.write(generateRandomString(RAMsize));
				pendingLength = pendingLength - RAMsize;
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error occured when writing into file :: " + e.getMessage());
		}
	}
	
	private static String generateRandomString(long n) {
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		String res = "";
		for (int i = 0; i < n; i++)
			res = res + alphabet[(int) (Math.random() * 26)];
		return res;
	}
}
