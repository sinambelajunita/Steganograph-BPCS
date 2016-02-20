package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import object.BitBlocks;
import object.BitPlane;
import object.MessageBlocks;

/**
 *
 * @author hp
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String imagepath;
        String message;

        System.out.print("Path to stego-image :");
        imagepath = scanner.nextLine();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image loaded");
        
        
        //1. Bagi cover-image menjadi blok 8 x 8 pixel.
        //2. Bentuk setiap blok 8 x 8 pixel menjadi sistem PBC yang terdiri dari 24 buah bit-plane.
        int height = image.getHeight();
        int width = image.getWidth();
        int nrow = height / 8;
        int ncol = width / 8;
        BitBlocks[][] bitblocks = new BitBlocks[nrow][ncol];
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                bitblocks[row][col] = new BitBlocks();
                int[][] intMatrix = new int[8][8];
                
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        int color = image.getRGB((col*8)+j, (row*8)+i);
                        intMatrix[i][j] = color;
                    }
                }
                
                bitblocks[row][col].setAllBitPlanes(intMatrix);
            }
        }
        
        // 3. Ubah sistem PBC menjadi sistem CGC(Canonical Gray Coding).
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                bitblocks[row][col].convertAllToCGC();
            }
        }
        
        // 4. Tentukan apakah setiap bit-plane merupakan informative region atau noise-like region 
        // dengan menggunakan nilai ambang a0. Nilai default a0 = 0.3. Jika tergolong noise-like region, 
        // maka pesan bisa disisipkan pada bit-plane tersebut, tetapi jika termasuk informative region, 
        // maka tidak dapat digunakan untuk menyisipkan pesan
        double threshold = 0.3;
        int capacity = 0;
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                capacity += bitblocks[row][col].checkAllNoiseLike(threshold);
            }
        }
        System.out.println("Capacity = " + Integer.toString(capacity));
        
        // 5. Bagi pesan menjadi segmen-segmen berukuran 64-bit, lalu nyatakan segmen menjadi blok biner berukuran 8 x 8.
        System.out.print("Message :");
        message = scanner.nextLine();
        int length = message.length();
        int size = (length+7)/8;
        MessageBlocks messageblocks = new MessageBlocks(size);
        for(int i=0; i<size; i++) {
            int[] matrix = new int[8];
            for(int j=0; j<8; j++) {
                if(8*i+j<message.length()) {
                    matrix[j] = (int)(message.charAt(8*i+j));
                }
            }
            messageblocks.getBitPlane(i).setBitMatrix(matrix);
        }
        
        // 6. Jika blok pesan S tidak lebih kompleks dibandingkan dengan nilai ambang a_0
        // (yaitu termasuk kategori informative region), lakukan konyugasi terhadap S 
        // untuk mendapatkan S* yang lebih kompleks.
        for(int i=0; i<size; i++) {
            if ( ! messageblocks.getBitPlane(i).isComplex(threshold)) {
                BitPlane conjugate = messageblocks.getBitPlane(i);
                messageblocks.getBitPlane(i).setBitMatrix(conjugate.getBitMatrix());
            }
        }
        
        // 7. Sisipkan segmen pesan 64-bit ke bit-plane yang merupakan noise-like region
        // dengan cara mengganti seluruh bit pada noise-like region tersebut dengan 64-bit pesan).
        int it=0;
        for(int i=0; i<size; i++) {
            // TODO Sisipkan pesan
        }
            
        // 10. Ubah stego-image dari sistem CGC menjadi sistem PBC.
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                bitblocks[row][col].convertAllToPCB();
            }
        }
        
        // Konversi ke bitmap
        BufferedImage imageresult = new BufferedImage(8*ncol, 8*nrow,  BufferedImage.TYPE_INT_RGB);
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                BitBlocks bitblocksresult = bitblocks[row][col];
                int[][] colormatrix = bitblocksresult.getColorMatrix();
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        int color = colormatrix[i][j];
                        imageresult.setRGB(col*8 + j, row*8 + i, color);
                    }
                }
            }
        }
        
        try {
            ImageIO.write(imageresult,"BMP",new File(imagepath+".bmp"));
        } catch (IOException e) {
        }
        
    }
}
