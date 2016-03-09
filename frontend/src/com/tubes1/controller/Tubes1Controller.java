package com.tubes1.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.algoritma.controller.*;
import com.tugas1.controller.InvalidSizeException;

import algorithm.Bpcs;
import object.MessageBlock;

@Controller
@RequestMapping("/tubes1")
public class Tubes1Controller {
	String path = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/";
	
	@RequestMapping("/")
	public ModelAndView tugas1Controller() {

		ModelAndView model = new ModelAndView("Tubes1Page");
		model.addObject("msg","Tubes 1");
		model.addObject("folderLevel","../");
		return model;
	}

	@Autowired
    ServletContext context; 
	
	@RequestMapping(value="/execute",  method=RequestMethod.POST)
	@ResponseBody
	public String startExecuting(@RequestParam("fileInputUntukDiSisipkan") String filename,
			@RequestParam("fileInput") String imagename,
			@RequestParam("capacity") String strCapacity,
			@RequestParam("operationType") String operationType,
			@RequestParam("key") String key
			) {
		VigenereCipherExtended VChipExt = new VigenereCipherExtended();
		Bpcs objBPCS = new Bpcs();
		int capacity = Integer.parseInt(strCapacity);
		String result = new String("");
		
		switch(operationType){
			case "0": // embed file to image
				try {
					String str = filename.length()+" "+filename;
					byte[] b = str.getBytes();
			        String pathEncryptedSecretFIle = VChipExt.encryptTubes1( key, path, filename);
			        Path file_path = Paths.get(pathEncryptedSecretFIle);
			        byte[] message;
					message = Files.readAllBytes(file_path);
//					
					byte[] bytes = message;
			        MessageBlock messageblock = new MessageBlock(bytes);
			        int messageSize = messageblock.getSize() ;
			        if(messageSize > capacity){
			        	result = "300";
			        }else{
			        	result = objBPCS.insertFile(imagename, path, message, key);
			        }
				} catch (IOException e) {
					result = e.getMessage();
				} catch (InvalidSizeException e) {
					result = e.getMessage();
				}
				break; 
			case "1": // get file from image
				try {
			        Bpcs bpcs = new Bpcs();
			        byte[] bytes = bpcs.getFile(path+imagename, key);
//			        String file_path = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/encryptedfileSecret.txt";//scanner.nextLine();
//			        Path path2 = Paths.get(file_path);

//					byte[] message = Files.readAllBytes(path2);
			        // input message file path
			        FileUtils.writeByteArrayToFile(new File(path+"encryptedResult"+filename), bytes);
			        
					result =  VChipExt.decryptTubes1( key, path,"encryptedResult"+filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break; 
			default: 
		}
		return result;
	}

	@RequestMapping(value="/compareimage",  method=RequestMethod.POST)
	@ResponseBody
	public String compareImage(
			@RequestParam("fileInput") String imagename
			) {
		VigenereCipherExtended VChipExt = new VigenereCipherExtended();
		Bpcs objBPCS = new Bpcs();
		String result = objBPCS.compareImage(path+imagename, path+"New"+imagename);
		return result;
	}
	
	@RequestMapping(value="/checkcapacity",  method=RequestMethod.POST)
	@ResponseBody
	public String checkCapacity( @RequestParam("fileInput") String imagename
			) {
		VigenereCipherExtended VChipExt = new VigenereCipherExtended();
		Bpcs objBPCS = new Bpcs();
		String result = objBPCS.checkCapacity(path+imagename);
		return result;
	}
	
	
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
            	
                byte[] bytes = file.getBytes();
                FileUtils.writeByteArrayToFile(new File(path+name), bytes);
//                System.out.println(getClass().getResource());
               
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
    
    
}
