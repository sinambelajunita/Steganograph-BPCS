package com.algoritma.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

public class VigenereCipher {
	
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
	
	public void writeFile(String str){
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:/outputTextFile.txt", "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public String encrypt(String text, final String key, String path, String inputModel, String resultModel) {
    	if(path!="")
    		text = readFile(path);
    	
    	switch(inputModel){
		case "0":
			break;
		case "1":
			text = text.replaceAll(" ","");
			break;
		case "2":
			text = deletePeriodically(text, " ", 5);
		default:	
		}
    	
        String res = "";
        text = text.toLowerCase();
       // return text;
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if( c == ' ')
            	res += " ";
            else if (c >= 'a' && c <= 'z'){
            	res += (char)((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
            	j = ++j % key.length();
            }
        }
        
		switch(resultModel){
			case "1":
				res = res.replaceAll(" ","");
				break;
			case "2":
				res = insertPeriodically(res, " ", 5);
				break;
			default:	
		}
        
        writeFile(res);
        return res;
    }

    public String decrypt(String text, final String key, String path, String inputModel, String resultModel) {
    	if(path!="")
    		text = readFile(path);
    	
    	switch(inputModel){
		case "0":
			break;
		case "1":
			text = text.replaceAll(" ","");
			break;
		case "2":
			text = deletePeriodically(text, " ", 5);
		default:	
		}
    	
        String res = "";
        text = text.toLowerCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if( c == ' ')
            	res += " ";
            else if (c >= 'a' && c <= 'z'){
                res += (char)((c - key.charAt(j) + 26) % 26 + 'a');
                j = ++j % key.length();
            }
        }
        

		switch(resultModel){
			case "1":
				res = res.replaceAll(" ","");
				break;
			case "2":
				res = insertPeriodically(res, " ", 5);
				break;
			default:	
		}
        
        writeFile(res);
        return res;
    }
    


	public String insertPeriodically( String text, String insert, int period)
	{
	    StringBuilder builder = new StringBuilder(
	         text.length() + insert.length() * (text.length()/period)+1);

	    int index = 0;
	    String prefix = "";
	    while (index < text.length())
	    {
	        // Don't put the insert in the very first iteration.
	        // This is easier than appending it *after* each substring
	        builder.append(prefix);
	        prefix = insert;
	        builder.append(text.substring(index, 
	            Math.min(index + period, text.length())));
	        index += period;
	    }
	    return builder.toString();
	}
	
    
	public String deletePeriodically( String text, String delete, int period)
	{
	    StringBuilder builder = new StringBuilder(
		         text.length() - delete.length() * (text.length()/period)-1);
	    int index = period;
		while (index < text.length())
	    {
	        builder.append(text.substring(index-period, index));
	        index += period+1;
	    }
		if(index < text.length()+period)
	        builder.append(text.substring(index-period, text.length()));
		
	    return builder.toString();
	}
}
