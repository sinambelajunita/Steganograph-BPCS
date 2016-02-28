/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import object.MessageBlock;

/**
 *
 * @author user
 */
public class Tools {
//    public static byte[] pngReader(String path){
//        File file = new File(path);
//
//        BufferedImage bufferedImage;
//        ByteOutputStream bos = null;
//        try {
//            bufferedImage = ImageIO.read(file);
//            bos = new ByteOutputStream();
//            ImageIO.write(bufferedImage, "png", bos);
//        } catch (IOException ex) {
//            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                bos.close();
//            } catch (Exception e) {
//                
//            }
//        }
//        return bos == null ? null : bos.getBytes();
//    }
//    public static File pngWriter(String path, byte[] bytes){
//        
//    }
    public static ArrayList generateRandomSeed(String key, int imageSizeBlock){
        ArrayList randomSeed;
        randomSeed = new ArrayList<>();
        for(int i = 0; i < key.length(); i++){
            randomSeed.add((int) key.charAt(i) % imageSizeBlock);
        }
        return randomSeed;
    }
    
    public static double calculatePSNR(BufferedImage imageMatrix1, BufferedImage imageMatrix2) {
        int total = 0;
        int row;
        int col;

        row = imageMatrix1.getHeight();
        col = imageMatrix1.getWidth();
        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                total += (imageMatrix1.getRGB(i,j) - imageMatrix2.getRGB(i,j))*(imageMatrix1.getRGB(i,j) - imageMatrix2.getRGB(i,j));
                //System.out.println(i + " " + " " + j + " " +  total);
            }
        }

        double rms = Math.sqrt(total/(row*col));

        double result = 20*Math.log10(256/rms);
        return result;
    }
}
