package com.algoritma.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VigenereCipherExtended {

	String path = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/";

	public ArrayList<Integer> readFile(String path, String inputModel){
		FileInputStream fis = null;
		ArrayList<Integer> myList = new ArrayList<Integer>();
    	try {
			fis = new FileInputStream(path);
			int content;
			while ((content = fis.read()) != -1) {
	            myList.add(content);
	            if(inputModel.compareTo("2")==0 && myList.size()%5==0)
	            	fis.skip(1);
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
	
	public void writeEncryptedFileTubes1(ArrayList<Integer> myList , String filename){
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(
			        new FileOutputStream(new File(path + "encrypted"+filename)));
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
	
	public void writeDecryptedFileTubes1(ArrayList<Integer> myList , String filename){
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(
			        new FileOutputStream(new File(path + "decrypted"+filename)));
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
	
	 public String encrypt(String text, final String key, String path, String inputModel, String resultModel) {
	        String res = "";        
    		ArrayList<Integer> myList = new ArrayList<Integer>();
        	if(path == ""){
    			int indexSkip = 5;
	    		for (int i = 0, j = 0; i < text.length(); i++) {
  	            	if(inputModel.compareTo("2")==0 && res.length()==indexSkip){
  	            		//skip space
	  	            	indexSkip += 5;
  	            	}else{
	  		            char c = text.charAt(i);
	  	            	int idx = ((c + key.charAt(j)) % 256 );
	  	            	res += (char) idx;
	  	            	myList.add(idx);
	  	            	if(resultModel.compareTo("2")==0 && (i+1)%5==0){
	  	            		myList.add((int) ' ');
	  	            		res += ' ';
	  	            	}
	  	            	j = ++j % key.length();
  	            	}
  		        }
        	}else{
        		ArrayList<Integer> listInt = readFile(path, inputModel); 
  				int j = 0;
        		for (int i = 0; i < listInt.size(); i++) {
		            char c = (char) listInt.get(i).intValue();
  	            	int idx = ((c + key.charAt(j)) % 256 );
  	            	myList.add(idx);
		            res += (char)idx;
  	            	if(resultModel.compareTo("2")==0 && (i+1)%5==0){
  	            		myList.add((int) ' ');
  	            		res += ' ';
  	            	}
		            j = ++j % key.length();
        		}
        	}
			writeFile(myList);
	        return res;
	    }
	
	    public String decrypt(String text, final String key, String path, String inputModel,  String resultModel) {
	        String res = "";
    		ArrayList<Integer> myList = new ArrayList<Integer>();
	    	if(path != ""){
	    		ArrayList<Integer> listInt = readFile(path, inputModel); 
  				int j = 0;
        		for (int i = 0; i < listInt.size(); i++) {
		            char c = (char) listInt.get(i).intValue();
	            	int idx = ((c - key.charAt(j) + 256) % 256 );
  	            	myList.add(idx);
		            res += (char)idx;
  	            	if(resultModel.compareTo("2")==0 && (i+1)%5==0){
  	            		myList.add((int) ' ');
  	            		res += ' ';
  	            	}
		            j = ++j % key.length();
        		}
	    	}else{
    			int indexSkip = 5;
	    		for (int i = 0, j = 0; i < text.length(); i++) {
  	            	if(inputModel.compareTo("2")==0 && res.length()==indexSkip){
  	            		//skip space
	  	            	indexSkip += 5;
  	            	}else{
	  		            char c = text.charAt(i);
		            	int idx = ((c - key.charAt(j) + 256) % 256 );
	  	            	res += (char) idx;
	  	            	myList.add(idx);
	  	            	if(resultModel.compareTo("2")==0 && (i+1)%5==0){
	  	            		myList.add((int) ' ');
	  	            		res += ' ';
	  	            	}
	  	            	j = ++j % key.length();
  	            	}
  		        }
	    	}
			writeFile(myList);
		    return res;//"�����ג���و���������";
	    }
	    
	    public String encryptTubes1( String key, String filePath, String filename) {
	        String res = "";        
    		ArrayList<Integer> myList = new ArrayList<Integer>();
        	
    		ArrayList<Integer> listInt = readFile(filePath+filename, "0"); 
			int j = 0;
    		for (int i = 0; i < listInt.size(); i++) {
	            char c = (char) listInt.get(i).intValue();
            	int idx = ((c + key.charAt(j)) % 256 );
            	myList.add(idx);
	            res += (char)idx;
	            j = ++j % key.length();
        		
        	}
    		writeEncryptedFileTubes1(myList, filename);
	        return filePath+"encrypted"+filename;
	    }
	
	    public String decryptTubes1(String key, String filePath, String filename) {
	        String res = "";
    		ArrayList<Integer> myList = new ArrayList<Integer>();

    		ArrayList<Integer> listInt = readFile(filePath+filename, "0"); 
				int j = 0;
    		for (int i = 0; i < listInt.size(); i++) {
	            char c = (char) listInt.get(i).intValue();
            	int idx = ((c - key.charAt(j) + 256) % 256 );
	            	myList.add(idx);
	            res += (char)idx;
	            j = ++j % key.length();
    		}
    		writeDecryptedFileTubes1(myList, filename);
		    return filePath+"decrypted"+filename;//"�����ג���و���������";
	    }
}
