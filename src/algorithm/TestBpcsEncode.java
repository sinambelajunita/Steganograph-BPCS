package algorithm;
import algorithm.Bpcs;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.tugas1.controller.InvalidSizeException;

/**
 *
 * @author hp
 */
public class TestBpcsEncode {
    public static void main(String[] args) throws IOException, InvalidSizeException {
        Scanner scanner = new Scanner(System.in);
        String imagepath;
        String messagepath;
        String key;
        
        // input image path
        System.out.print("Path to stego-image :");
        imagepath = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/barbara.bmp";//scanner.nextLine();
        
        // input message file path
        System.out.print("Path to message-file :");
        messagepath = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/ssplain.txt";//scanner.nextLine();
        Path path = Paths.get(messagepath);
        byte[] bytes = Files.readAllBytes(path);
        
        // input key
        System.out.print("Key :");
        key = "ariswakhyudin";//scanner.nextLine();
        
        Bpcs bpcs = new Bpcs();
//        String newpath = bpcs.insertFile(imagepath, bytes, key);
    }
}
