package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import object.BitPlane;
import object.ImageBlock;
import object.MessageBlock;
import tools.Tools;

/**
 *
 * @author hp
 */
public class TestDecode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String imagepath;
        String message;
        String key;

        System.out.print("Path to stego-image :");
        imagepath = scanner.nextLine();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image loaded");
        
        ImageBlock imageblock = new ImageBlock(image);
        imageblock.convertAllToCGC();
        
        double threshold = 0.3;
        int capacity = imageblock.checkAllNoiseLike(threshold);
        System.out.println("Capacity = " + Integer.toString(capacity));
        
        System.out.print("Key :");
        key = scanner.nextLine();
        
        int seed = Tools.generateRandomSeed2(key);
        Random generator = new Random(seed);
        ArrayList posgenerated;
        posgenerated = new ArrayList<>();
        BitPlane bitplane;
        ArrayList bitplanes = new ArrayList();
        Boolean stop = false;
        int position;
        do {
            Boolean found = false;
            do {
                position = generator.nextInt(imageblock.getMaxBitPlanes());
                //System.out.println(position);
                bitplane = imageblock.getBitPlane(position);
                //System.out.println(bitplane.isComplex(threshold));
                if(bitplane.isComplex(threshold) && !posgenerated.contains(position)) {
                    found = true;
                }
            } while(!found);
            
            posgenerated.add(position);
            
            bitplane.print();
            
            if(bitplane.isConjugated()) {
                bitplane.setBitMatrix(bitplane.getConjugate().getBitMatrix());
            }
            
            if(bitplane.isIdentity()) {
                stop = true;
            } else {
                bitplanes.add(new BitPlane(bitplane.getBitMatrix()));
            }
        } while(!stop);
        
        MessageBlock messageblock = new MessageBlock(bitplanes);
        byte[] bytes = messageblock.toBytes();
        message = new String(bytes);
        System.out.println("Message is : " + message);
    }
}
