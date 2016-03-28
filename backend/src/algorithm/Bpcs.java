/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import object.BitPlane;
import object.ImageBlock;
import object.MessageBlock;
import tools.Tools;

/**
 *
 * @author user
 */
public class Bpcs {    
    public String insertFile(String imagepath, byte[] message, String key){ 
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //1. Bagi cover-image menjadi blok 8 x 8 pixel.
        //2. Bentuk setiap blok 8 x 8 pixel menjadi sistem PBC yang terdiri dari 24 buah bit-plane.
        ImageBlock imageblock = new ImageBlock(image);
        
        // 3. Ubah sistem PBC menjadi sistem CGC(Canonical Gray Coding).
        imageblock.convertAllToCGC();
        
        // 4. Tentukan apakah setiap bit-plane merupakan informative region atau noise-like region 
        // dengan menggunakan nilai ambang a0. Nilai default a0 = 0.3. Jika tergolong noise-like region, 
        // maka pesan bisa disisipkan pada bit-plane tersebut, tetapi jika termasuk informative region, 
        // maka tidak dapat digunakan untuk menyisipkan pesan
        double threshold = 0.3;
        int capacity = imageblock.checkAllNoiseLike(threshold);
        
        // 5. Bagi pesan menjadi segmen-segmen berukuran 64-bit, lalu nyatakan segmen menjadi blok biner berukuran 8 x 8.
        byte[] bytes = message;
        MessageBlock messageblock = new MessageBlock(bytes);
        
        
        // 6. Jika blok pesan S tidak lebih kompleks dibandingkan dengan nilai ambang a_0
        // (yaitu termasuk kategori informative region), lakukan konyugasi terhadap S 
        // untuk mendapatkan S* yang lebih kompleks.
        for(int i=0; i<messageblock.getSize(); i++) {
            BitPlane bitplane = messageblock.getBitPlane(i);
            
            if ( ! bitplane.isComplex(threshold)) {
                BitPlane conjugate = bitplane.getConjugate();
                bitplane.setBitMatrix(conjugate.getBitMatrix());
            }
        }
        
        // 7. Sisipkan segmen pesan 64-bit ke bit-plane yang merupakan noise-like region
        // dengan cara mengganti seluruh bit pada noise-like region tersebut dengan 64-bit pesan).
        int seed = Tools.generateRandomSeed2(key);
        Random generator = new Random(seed);
        ArrayList posgenerated;
        posgenerated = new ArrayList<>();
        BitPlane bitplane;
        int position;
        for(int i=0; i<messageblock.getSize(); i++) {
            boolean found = false;
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
            bitplane.setBitMatrix(messageblock.getBitPlane(i).getBitMatrix());
            
            // DEBUG
            System.out.println(i + " " + position);
            messageblock.getBitPlane(i).print();
            System.out.println();
        }
            
        // 10. Ubah stego-image dari sistem CGC menjadi sistem PBC.
        imageblock.convertAllToPCB();

        
        // Konversi ke bitmap
        BufferedImage imageresult = imageblock.getBufferedImage();
        
        String newimagepath = imagepath+".bmp";
        try {
            
            ImageIO.write(imageresult,"BMP",new File(newimagepath));
        } catch (IOException e) {
        }
        
        return newimagepath;
    }
    
    public byte[] getFile(String imagepath, String key){

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ImageBlock imageblock = new ImageBlock(image);
        imageblock.convertAllToCGC();
        
        double threshold = 0.3;
        int capacity = imageblock.checkAllNoiseLike(threshold);
                
        int seed = Tools.generateRandomSeed2(key);
        Random generator = new Random(seed);
        ArrayList posgenerated;
        posgenerated = new ArrayList<>();
        BitPlane bitplane;
        ArrayList bitplanes = new ArrayList();
        Boolean stop = false;
        int position;
        int i=0;
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
            
            
            // DEBUG
            System.out.println(i + " " + position);
            i++;
            bitplane.print();
            System.out.println();
            
            
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
        return bytes;
    }
}
