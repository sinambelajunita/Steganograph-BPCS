package com.tubes1.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;

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

import algorithm.Bpcs;

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
			@RequestParam("operationType") String operationType,
			@RequestParam("key") String key
			) {
		VigenereCipherExtended VChipExt = new VigenereCipherExtended();
		Bpcs objBPCS = new Bpcs();
		String result = new String("");
		
		switch(operationType){
			case "0": // embed file to image
				try {
			        String pathEncryptedSecretFIle = VChipExt.encryptTubes1( key, path, filename);
			        Path file_path = Paths.get(pathEncryptedSecretFIle);
			        byte[] message;
					message = Files.readAllBytes(file_path);
			        objBPCS.insertFile(path+imagename, message, key);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break; 
			case "1": // get file from image
				String pathEncryptedSecretFIle = objBPCS.getFile(path+imagename, key);
				result =  VChipExt.decryptTubes1( key, pathEncryptedSecretFIle, filename);
				break; 
			default: 
		}
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
