/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.Bpcs;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class TestBpcsEncode {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String imagepath;
        String messagepath;
        String key;
        
        // input image path
        System.out.print("Path to stego-image :");
        imagepath = scanner.nextLine();
        
        // input message file path
        System.out.print("Path to message-file :");
        messagepath = scanner.nextLine();
        Path path = Paths.get(messagepath);
        byte[] bytes = Files.readAllBytes(path);
        
        // input key
        System.out.print("Key :");
        key = scanner.nextLine();
        
        Bpcs bpcs = new Bpcs();
        String newpath = bpcs.insertFile(imagepath, bytes, key);
    }
}
