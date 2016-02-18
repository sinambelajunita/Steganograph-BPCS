package com.algoritma.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;


public class CaesarCipher {
	

	public String readFile(String path){
		String everything = "";
		try {
			FileInputStream inputStream = new FileInputStream(path);
		    everything = IOUtils.toString(inputStream);
		    inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return everything;
	}
	
	
	public String analyzePlayFair(String text, final String key, String path, String inputModel, String resultModel) {
		if(path!="")
    		text = readFile(path);
		String[] elements = text.split(" ");
		int len = elements.length;

		Map<String, Integer> numChars = new HashMap<String, Integer>(len);
		
		for (int i = 0; i < len; ++i)
		{
		    String charAt = elements[i];

		    if (!numChars.containsKey(charAt))
		    {
		        numChars.put(charAt, 1);
		    }
		    else
		    {
		        numChars.put(charAt, numChars.get(charAt) + 1);
		    }
		}
		return sortByValue(numChars).toString();
	}

	public String analyze(String text, final String key, String path, String inputModel, String resultModel) {
		if(path!="")
    		text = readFile(path);
		text =text.replaceAll("[^a-zA-Z]", "");
		int len = text.length();
		Map<Character, Integer> numChars = new HashMap<Character, Integer>(Math.min(len, 26));

		for (int i = 0; i < len; ++i)
		{
		    char charAt = text.charAt(i);

		    if (!numChars.containsKey(charAt))
		    {
		        numChars.put(charAt, 1);
		    }
		    else
		    {
		        numChars.put(charAt, numChars.get(charAt) + 1);
		    }
		}
		return sortByValue(numChars).toString();
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> 
    sortByValue( Map<K, V> map )
{
    List<Map.Entry<K, V>> list =
        new LinkedList<Map.Entry<K, V>>( map.entrySet() );
    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
    {
        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
        {
            return (o1.getValue()).compareTo( o2.getValue() );
        }
    } );

    Map<K, V> result = new LinkedHashMap<K, V>();
    for (Map.Entry<K, V> entry : list)
    {
        result.put( entry.getKey(), entry.getValue() );
    }
    return result;
}
	
}
