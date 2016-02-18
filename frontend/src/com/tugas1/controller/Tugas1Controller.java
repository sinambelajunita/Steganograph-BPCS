package com.tugas1.controller;

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
@RequestMapping("/tugas1")
public class Tugas1Controller {
	
	@RequestMapping("/")
	public ModelAndView tugas1Controller() {

		ModelAndView model = new ModelAndView("Tugas1Page");
		model.addObject("msg","Tugas 1");
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
			@RequestParam("selectedAlgorithm") String selectedAlgorithm,
			@RequestParam("key") String key,
			@RequestParam("resultModel") String resultModel,
			@RequestParam("inputModel") String inputModel
			) {
		String textOri = "";
		String path = "";
		if(fileInput.length()==0)
			textOri = textInput;
		else
			path = "D:/"+fileInput+".txt";
		
		String result;
		
		switch(operationType){
		case "0": // encryption
			switch(selectedAlgorithm){
				case "0": //Vigenere Cipher
					String withoutAccent = Normalizer.normalize(textOri, Normalizer.Form.NFD);
					textOri = withoutAccent.replaceAll("[^a-zA-Z ]", "");
					VigenereCipher VChip = new VigenereCipher();
			        result = VChip.encrypt(textOri, key, path, inputModel, resultModel);
					break;
				case "1": // Vigenere Cipher Extended
					VigenereCipherExtended VChipExt = new VigenereCipherExtended();
			        result = VChipExt.encrypt(textOri, key, path, inputModel, resultModel);
					break; 
				case "2": // Playfair Cipher
				    Playfair pf = new Playfair();
				    result = pf.cipher(textOri, key, path, inputModel, resultModel);
					break;
				case "3": // Playfair Cipher
				    CaesarCipher cc = new CaesarCipher();
				    result = cc.analyzePlayFair(textOri, key, path, inputModel, resultModel);
					break;
				default:
					result = "algorithm error";
			};
			break;
		case "1": // decryption
			switch(selectedAlgorithm){
				case "0": //Vigenere Cipher
					VigenereCipher VChip = new VigenereCipher();
//					result = textOri;
			        result = VChip.decrypt(textOri, key, path, inputModel, resultModel);
					break;
				case "1": // Vigenere Cipher Extended
					VigenereCipherExtended VChipExt = new VigenereCipherExtended();
			        result =  VChipExt.decrypt(textOri, key, path, inputModel, resultModel);
					break; 
				case "2": // Playfair Cipher
				    Playfair pf = new Playfair();
				    result = pf.decode(textOri, key, path, inputModel, resultModel);
					break;
				default:
					result = "algorithm error";
			};
			break;
		default: 
			result = "error";
		}

	    return result;
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
                FileUtils.writeByteArrayToFile(new File("D:/"+name+".txt"), bytes);
                System.out.println(bytes);
               
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
    
}

