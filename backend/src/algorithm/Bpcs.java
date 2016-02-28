/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public String encrypt(String imagepath, String message, String key){
        ArrayList conjugationmap;
        conjugationmap = new ArrayList<>();
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
        System.out.println("Capacity = " + Integer.toString(capacity));
        
        // 5. Bagi pesan menjadi segmen-segmen berukuran 64-bit, lalu nyatakan segmen menjadi blok biner berukuran 8 x 8.
        MessageBlock messageblock = new MessageBlock(message);
        
        // 5a. Generate random seed
        ArrayList randomseed = Tools.generateRandomSeed(key, imageblock.getCol()*imageblock.getRow());
        
        // 6. Jika blok pesan S tidak lebih kompleks dibandingkan dengan nilai ambang a_0
        // (yaitu termasuk kategori informative region), lakukan konyugasi terhadap S 
        // untuk mendapatkan S* yang lebih kompleks.
        for(int i=0; i<messageblock.getSize(); i++) {
            if ( ! messageblock.getBitPlane(i).isComplex(threshold)) {
                BitPlane conjugate = messageblock.getBitPlane(i);
                messageblock.getBitPlane(i).setBitMatrix(conjugate.getBitMatrix());
                conjugationmap.add(i);
            }
        }
        
        // 7. Sisipkan segmen pesan 64-bit ke bit-plane yang merupakan noise-like region
        // dengan cara mengganti seluruh bit pada noise-like region tersebut dengan 64-bit pesan).
        // TODO Sisipkan pesan
        for(int i = 1; i <= messageblock.getSize(); i++){
            
        }
        
        // 8. Jika bloks S dikonyugasi, simpan pesan pada “conjugation map”.
        
        // 9. Sisipkan juga pemetaan konyugasi yang telah dibuat.
        
            
        // 10. Ubah stego-image dari sistem CGC menjadi sistem PBC.
        imageblock.convertAllToPCB();

        
        // Konversi ke bitmap
        BufferedImage imageresult = imageblock.getBufferedImage();
        
        // 
        try {
            ImageIO.write(imageresult,"BMP",new File(imagepath+"temp.bmp"));
        } catch (IOException e) {
        }
        return(imagepath+"temp.bmp");
    }
    public String decrypt(String imagepath, String message, String key){
        // 1. Bagi stego-image menjadi blok 8 x 8 pixel.
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 2. Bentuk setiap blok 8 x 8 pixel menjadi sistem PBC yang terdiri dari 8 buah bit-plane.
        ImageBlock imageblock = new ImageBlock(image);
        
        // 3. Ubah sistem PBC menjadi sistem CGC (Canonical Gray Coding).
        imageblock.convertAllToCGC();
        
        // 4. Hitung kompleksitas setiap bit-plane. Jika kompleksitasnya di atas nilai ambang
        // 0, maka bit-plane tersebut bagian dari pesan. Tabel konyugasi yang disisipkan juga dibaca untuk
        // melihat proses konyugas yang perlu dilakukan pada tiap blok pesan.
        for(int i = 0; i < imageblock.getCol(); i++){
            for(int j = 0; j < imageblock.getCol(); j++){
                for (int k = 0; k < 8; k++){
                    
                }
            }
        }
        return null;
    }
}
