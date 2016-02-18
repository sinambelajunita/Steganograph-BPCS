package com.algoritma.controller;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

public class Playfair{
	
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
			e.printStackTrace();
		}
	}

	
  // length of digraph array
  private int length = 0;
  
  // table for Playfair cipher
  private String [][] table;
  
  // parses any input string to remove numbers, punctuation,
  // replaces any J's with I's, and makes string all caps
  public String parseString(String s){
    s = s.toUpperCase();
    s = s.replaceAll("[^A-Z]", "");
    s = s.replace("J", "");
    return s;
  }
  
  // creates the cipher table based on some input string (already parsed)
  public String[][] cipherTable(String key){
    String[][] playfairTable = new String[5][5];
    String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    
    // fill string array with empty string
    for(int i = 0; i < 5; i++)
      for(int j = 0; j < 5; j++)
        playfairTable[i][j] = "";
    
    for(int k = 0; k < keyString.length(); k++){
      boolean repeat = false;
      boolean used = false;
      for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
          if(playfairTable[i][j].equals("" + keyString.charAt(k))){
            repeat = true;
          }else if(playfairTable[i][j].equals("") && !repeat && !used){
            playfairTable[i][j] = "" + keyString.charAt(k);
            used = true;
          }
        }
      }
    }
    return playfairTable;
  }
  
  // cipher: takes input (all upper-case), encodes it, and returns output
  public String cipher(String textOri, String key, String path, String inputModel, String resultModel){
	
	  if(path!="")
		  textOri = readFile(path);
  	
  	switch(inputModel){
		case "0":
			break;
		case "1":
			textOri = textOri.replaceAll(" ","");
			break;
		case "2":
			textOri = deletePeriodically(textOri, " ", 5);
		default:	
		}
	  
	textOri = parseString(textOri);
    key = parseString(key);
    
    table = this.cipherTable(key);
	    
	length = (int) textOri.length() / 2 + textOri.length() % 2;
    
    // insert x between double-letter digraphs & redefines "length"
    for(int i = 0; i < (length - 1); i++){
      if(textOri.charAt(2 * i) == textOri.charAt(2 * i + 1)){
    	  textOri = new StringBuffer(textOri).insert(2 * i + 1, 'Z').toString();
        length = (int) textOri.length() / 2 + textOri.length() % 2;
      }
    }
    
    // adds an x to the last digraph, if necessary
    String[] digraph = new String[length];
    for(int j = 0; j < length ; j++){
      if(j == (length - 1) && textOri.length() / 2 == (length - 1))
    	  textOri = textOri + "Z";
      digraph[j] = textOri.charAt(2 * j) +""+ textOri.charAt(2 * j + 1);
    }
    
    // encodes the digraphs and returns the output
    String out = "";
    String[] encDigraphs = new String[length];
    encDigraphs = encodeDigraph(digraph);
    for(int k = 0; k < length; k++)
      out = out + encDigraphs[k];
    

	switch(resultModel){
		case "1":
			out = out.replaceAll(" ","");
			break;
		case "2":
			out = insertPeriodically(out, " ", 5);
			break;
		default:	
	}
    
    writeFile(out);
    
    return out;
  }
  
  // encodes the digraph input with the cipher's specifications
  public String[] encodeDigraph(String di[]){
    String[] enc = new String[length];
    for(int i = 0; i < length; i++){
      char a = di[i].charAt(0);
      char b = di[i].charAt(1);
      int r1 = (int) getPoint(a).getX();
      int r2 = (int) getPoint(b).getX();
      int c1 = (int) getPoint(a).getY();
      int c2 = (int) getPoint(b).getY();
      
      // case 1: letters in digraph are of same row, shift columns to right
      if(r1 == r2){
        c1 = (c1 + 1) % 5;
        c2 = (c2 + 1) % 5;
        
      // case 2: letters in digraph are of same column, shift rows down
      }else if(c1 == c2){
        r1 = (r1 + 1) % 5;
        r2 = (r2 + 1) % 5;
      
      // case 3: letters in digraph form rectangle, swap first column # with second column #
      }else{
        int temp = c1;
        c1 = c2;
        c2 = temp;
      }
      
      //performs the table look-up and puts those values into the encoded array
      enc[i] = table[r1][c1] + "" + table[r2][c2];
    }
    return enc;
  }
  
  // decodes the output given from the cipher and decode methods (opp. of encoding process)
  public String decode(String textOri, String key, String path, String inputModel, String resultModel){
	  if(path!="")
		  textOri = readFile(path);
  	
  	switch(inputModel){
		case "0":
			break;
		case "1":
			textOri = textOri.replaceAll(" ","");
			break;
		case "2":
			textOri = deletePeriodically(textOri, " ", 5);
		default:	
		}
	  
	  textOri = parseString(textOri);
    key = parseString(key);
    table = this.cipherTable(key);
	String decoded = "";
    for(int i = 0; i < textOri.length() / 2; i++){
      char a = textOri.charAt(2*i);
      char b = textOri.charAt(2*i+1);
      int r1 = (int) getPoint(a).getX();
      int r2 = (int) getPoint(b).getX();
      int c1 = (int) getPoint(a).getY();
      int c2 = (int) getPoint(b).getY();
      if(r1 == r2){
        c1 = (c1 + 4) % 5;
        c2 = (c2 + 4) % 5;
      }else if(c1 == c2){
        r1 = (r1 + 4) % 5;
        r2 = (r2 + 4) % 5;
      }else{
        int temp = c1;
        c1 = c2;
        c2 = temp;
      }
      decoded = decoded + table[r1][c1] + table[r2][c2];
    }
    

	switch(resultModel){
		case "1":
			decoded = decoded.replaceAll(" ","");
			break;
		case "2":
			decoded = insertPeriodically(decoded, " ", 5);
			break;
		default:	
	}
    
    writeFile(decoded);
    return decoded;
  }
  
  // returns a point containing the row and column of the letter
  private Point getPoint(char c){
    Point pt = new Point(0,0);
    for(int i = 0; i < 5; i++)
      for(int j = 0; j < 5; j++)
        if(c == table[i][j].charAt(0))
          pt = new Point(i,j);
    return pt;
  }
  

	public String insertPeriodically( String text, String insert, int period){
	    StringBuilder builder = new StringBuilder(
	         text.length() + insert.length() * (text.length()/period)+1);

	    int index = 0;
	    String prefix = "";
	    while (index < text.length()) {
	        builder.append(prefix);
	        prefix = insert;
	        builder.append(text.substring(index, 
	            Math.min(index + period, text.length())));
	        index += period;
	    }
	    return builder.toString();
	}
	
  
	public String deletePeriodically( String text, String delete, int period){
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