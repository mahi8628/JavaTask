package com.ust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SubStringFileSearch {
	public static void main(String args[])
	{
		//String fileName = args[0];
		//long fileSize = args[1];
		//long subStringMAXSize = args[2];
		String fileName="H:/Java/file1.txt";
		//long fileSize=3221225472L;		
		//int RAMsize=1073741824;
		long fileSize=100000L;		
		int RAMsize=100;
		int subStringMAXSize=30;
		
		String subStringforSearch = getSubStringForSearch(fileName, subStringMAXSize);
		HashMap<String, Double> valueMap = searchforSubStrings(fileName, RAMsize, subStringforSearch);
		reverseMapAndDisplayOutput(valueMap);
	}
	
	private static String getSubStringForSearch(String fileName, int subStringMAXSize)
	{
		char[] chars = new char[subStringMAXSize];
		String strFetchContents = null;
		int charsRead = -1;
		try {
			Reader reader = new InputStreamReader(new FileInputStream(fileName));
			if ((charsRead = reader.read(chars)) != -1)
			{
				strFetchContents	= new String(chars);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return strFetchContents;
	}
	
	private static HashMap<String, Double> searchforSubStrings(String fileName, int RAMsize, String subStringforSearch)
	{
		long frequency = 0;
		double value = 0;
		String subString = null;
		HashMap<String, Double> valueMap = new HashMap<String, Double>();
		for (int length = 1; length <= subStringforSearch.length(); length++)
		{
			for (int iteration = 0; iteration  <= subStringforSearch.length() - length; iteration++)
			{
				subString = subStringforSearch.substring(iteration, iteration+length);
				if (valueMap.containsKey(subString))
				{
					continue;
				}
				frequency = getFrequency(fileName, RAMsize, subString);
				value = calculateValue(length,frequency);
				valueMap.put(subString, value);
			}
		}
		return valueMap;
	}
	
	private static long getFrequency(String fileName, int RAMsize, String searchStr)
	{
		long frequency = 0;
		int index = 0;
		
		char[] chars = new char[RAMsize];
		int charsRead = -1;
		try {
			Reader reader = new InputStreamReader(new FileInputStream(fileName));
			
			while ((charsRead = reader.read(chars)) != -1) {
				String strFetchContents	= new String(chars);
				index = 0;
				while(index != -1 && index < strFetchContents.length())
				{
					index = strFetchContents.indexOf(searchStr, index);
					if (index != -1) 
					{
						frequency++;
						index++;
					}	
				}
			}			
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return frequency;
	}	
	
	private static double calculateValue(int length, long frequency)
	{
		return Math.pow(length, 2) * Math.pow((frequency - 1), 0.33);
	}
	
	private static void reverseMapAndDisplayOutput(HashMap<String, Double> valueMap)
	{
		LinkedHashMap<String, Double> descOrderByValueMap = new LinkedHashMap<String, Double>();
		valueMap.entrySet().stream()
						   .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						   .forEachOrdered(x -> descOrderByValueMap.put(x.getKey(), x.getValue()));
		
		Iterator i = descOrderByValueMap.entrySet().iterator();		
		double d = 0;
	    while(i.hasNext()) {
	    	Map.Entry me = (Map.Entry)i.next();
	    	if (d != 0 && d > (double)me.getValue()) break;
	    	System.out.println(me.getKey() + ": " + me.getValue());
	      	d = (double)me.getValue();
	    }	
	}
}

