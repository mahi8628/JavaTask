package com.ust;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class JavaAssign {

	static int MAX = 26;
	private static Reader innerReader;

	public static void main(String[] args) throws IOException {
		
		int contentSize = 100000;
		int fileSize = 100;
		
		generateLargerFileByDemand(fileSize, contentSize);
		
		Reader reader = new InputStreamReader(new FileInputStream("Path of the file"), "UTF-8");
		
		int subSeqLens = 3000;
		int subSeqLensInFile = 3000;
		char[] chars = new char[subSeqLens];
		int charsRead = -1;
		int maxCharFrequency = 0;
		
		while ((charsRead = read(reader, chars)) != -1) {
			
			System.out.println("subsequence chars :: " + new String(chars));
			
			innerReader = new InputStreamReader(new FileInputStream("Path of the file"), "UTF-8");
			char[] innerChars = new char[subSeqLensInFile];
			innerReader.read(innerChars);
			
			System.out.println("whole file read chars :: " + new String(innerChars));
			
			String maxChar = maximumOccurringCharsWithFrequency(new String(innerChars),true);
			int maxcharFreqLen = maxChar.split("@")[1] != null ? Integer.valueOf(maxChar.split("@")[1]) : 0;
			if(maxcharFreqLen > maxCharFrequency ){
				maxCharFrequency = maxcharFreqLen;
			}
			subSeqLensInFile = subSeqLensInFile + subSeqLens;
		}
		
		System.out.println("Formula Result :: " + Math.pow(subSeqLens, 4) * Math.pow((maxCharFrequency-1), 0.33) );
	}
	
	public static int read(Reader reader, char[] chars) throws IOException {
	    return reader.read(chars);
	}

	private static void generateLargerFileByDemand(int fileSize, int contentSize) {
		File file = new File("Path of the file");
		try {
			FileWriter writer = new FileWriter(file);

			System.out.print("Writing into file randomly generated lower case alphabets...");
			for (int i = 0; i < fileSize; i++) {
				write(generateRandomString(contentSize), writer);
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error occured when writing into file :: " + e.getMessage());
		}
	}

	private static void write(String randomString, Writer writer)throws IOException {
		
		long start = System.currentTimeMillis();
		writer.write(randomString);

		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000f + " seconds");
		
	}
	
	private static String generateRandomString(int n) {
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		String res = "";
		for (int i = 0; i < n; i++)
			res = res + alphabet[(int) (Math.random() * 10 % MAX)];

		return res;
	}
	
	// set skipSpaces true if you want to skip spaces
    public static String maximumOccurringCharsWithFrequency(String str, Boolean skipSpaces) {
    	
        Map<Character, Integer> map = new HashMap<>();
        String occurrences = null;
        int maxOccurring = 0;

        // creates map of all characters
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (skipSpaces && ch == ' ')      // skips spaces if needed
                continue;

            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }

            if (map.get(ch) > maxOccurring) {
                maxOccurring = map.get(ch);         // saves max occurring
            }
        }

        // finds all characters with maxOccurring and adds it to occurrences List
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxOccurring) {
                occurrences = entry.getKey().toString() + "@" + entry.getValue();
            }
        }
        return occurrences;
    }
	

}
