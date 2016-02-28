/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author hp
 */
public class TestImage {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String imagepath1;
        String imagepath2;
        
        System.out.print("Path to image1 :");
        imagepath1 = scanner.nextLine();
        System.out.print("Path to image2 :");
        imagepath2 = scanner.nextLine();
        
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        
        try {
            image1 = ImageIO.read(new File(imagepath1));
            image2 = ImageIO.read(new File(imagepath2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image loaded");

        double psnr = calculatePSNR(image1, image2);
    }
    
    public static double calculatePSNR(BufferedImage imageMatrix1, BufferedImage imageMatrix2) {
        int total = 0;
        int row;
        int col;

        row = imageMatrix1.getHeight();
        col = imageMatrix1.getWidth();
        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                int value1 = imageMatrix1.getRGB(j,i);
                int value2 = imageMatrix2.getRGB(j,i);
                if(value1 != value2) {
                    int alpha1 = (value1 >> 24) & 0xff;
                    int red1 = (value1 >> 16) & 0xff;
                    int green1 = (value1 >> 8) & 0xff;
                    int blue1 = (value1) & 0xff;
                    
                    int alpha2 = (value2 >> 24) & 0xff;
                    int red2 = (value2 >> 16) & 0xff;
                    int green2 = (value2 >> 8) & 0xff;
                    int blue2 = (value2) & 0xff;
                    
                    System.out.println("Found difference in cell (" + Integer.toString(i) + "," + Integer.toString(j) + ") with RGB : " );
                    System.out.println(Integer.toString(alpha1) + " " + Integer.toString(red1) + " " + Integer.toString(green1) + " " + Integer.toString(blue1));
                    System.out.println(Integer.toString(alpha2) + " " + Integer.toString(red2) + " " + Integer.toString(green2) + " " + Integer.toString(blue2));
                    System.out.println();
                    
                    
                }   
                total += (value1 - value2)*(value1 - value2);
                //System.out.println(i + " " + " " + j + " " +  total);
            }
        }

        double rms = Math.sqrt(total/(row*col));

        double result = 20*Math.log10(256/rms);
        return result;
    }
}
