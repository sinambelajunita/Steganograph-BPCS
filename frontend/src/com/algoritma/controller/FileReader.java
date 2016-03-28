package com.algoritma.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {
	

	public ArrayList<Integer> readFile(String path){
		FileInputStream fis = null;
		ArrayList<Integer> myList = new ArrayList<Integer>();
    	try {
			fis = new FileInputStream(path);
			int content;
			while ((content = fis.read()) != -1) {
	            myList.add(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return myList;
	}
	
	public void writeFile(ArrayList<Integer> myList ){
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(
			        new FileOutputStream(new File("D:/outputBinaryFile.txt")));
			for (int i = 0; i < myList.size(); i++) {
            	bos.write(myList.get(i).intValue());
    		}
	        bos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	 public String encrypt(String text, final String key, String path) {
	        String res = "";        
    		ArrayList<Integer> myList = new ArrayList<Integer>();
        	if(path == ""){
	    		for (int i = 0, j = 0; i < text.length(); i++) {
  		            char c = text.charAt(i);
  	            	int idx = ((c + key.charAt(j)) % 256 );
  	            	res += (char) idx;
  	            	myList.add(idx);
  	            	j = ++j % key.length();
            	}
  		        
        	}else{
        		ArrayList<Integer> listInt = readFile(path); 
  				int j = 0;
        		for (int i = 0; i < listInt.size(); i++) {
		            char c = (char) listInt.get(i).intValue();
  	            	int idx = ((c + key.charAt(j)) % 256 );
  	            	myList.add(idx);
		            res += (char)idx;
		            j = ++j % key.length();
        		}
        	}
			writeFile(myList);
	        return res;
	    }
	
	    public String decrypt(String text, final String key, String path) {
	        String res = "";
    		ArrayList<Integer> myList = new ArrayList<Integer>();
	    	if(path != ""){
	    		ArrayList<Integer> listInt = readFile(path); 
  				int j = 0;
        		for (int i = 0; i < listInt.size(); i++) {
		            char c = (char) listInt.get(i).intValue();
	            	int idx = ((c - key.charAt(j) + 256) % 256 );
  	            	myList.add(idx);
		            res += (char)idx;
		            j = ++j % key.length();
        		}
	    	}else{
	    		for (int i = 0, j = 0; i < text.length(); i++) {
		            char c = text.charAt(i);
	            	int idx = ((c - key.charAt(j) + 256) % 256 );
	            	res += (char) idx;
	            	myList.add(idx);
	            	j = ++j % key.length();
  	            	
  		        }
	    	}
			writeFile(myList);
		    return res;//"ÚÊÕÎÏ×’ÍÈÛÙˆÐçè×ÐÆåÆà";
	    }
}
