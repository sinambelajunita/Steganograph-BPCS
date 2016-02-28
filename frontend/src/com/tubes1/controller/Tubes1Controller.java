package com.tubes1.controller;

import java.io.File;
import java.text.Normalizer;

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

@Controller
@RequestMapping("/tubes1")
public class Tubes1Controller {
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
	public String startExecuting(@RequestParam("textInput") String textInput,
			@RequestParam("fileInput") String fileInput,
			@RequestParam("operationType") String operationType,
			@RequestParam("key") String key
			) {

		switch(operationType){
		case "0": // encryption
			
			break;
		case "1": // decryption
			
			break;
		default: 
		}
		return "bla bla";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody ModelAndView provideUploadInfo() {

		ModelAndView model = new ModelAndView("upload");
		return model;
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
            	
                byte[] bytes = file.getBytes();
                FileUtils.writeByteArrayToFile(new File("/media/daniar/My Passport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/"+name), bytes);
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
