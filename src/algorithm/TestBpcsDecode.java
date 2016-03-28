package algorithm;

import algorithm.Bpcs;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class TestBpcsDecode {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String imagepath;
        String messagepath;
        String key;
        
        // input image path
        System.out.print("Path to stego-image :");
        imagepath =  "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/barbara.bmp.bmp";//scanner.nextLine();
        
        // input key
        System.out.print("Key :");
        key = "ariswakhyudin";//scanner.nextLine();
        
        Bpcs bpcs = new Bpcs();
        byte[] bytes = bpcs.getFile(imagepath, key);
        
        // input message file path
        System.out.print("Decoding success!");
        System.out.print("Path to save message-file :");
        messagepath = "/media/daniar/myPassport/WorkPlace/Windows/Steganograph-BPCS/frontend/WebContent/resources/img/result.txt";//scanner.nextLine();
        Path path = Paths.get(messagepath);
        try (FileOutputStream stream = new FileOutputStream(messagepath)) {
            stream.write(bytes);
        }
    }
}
