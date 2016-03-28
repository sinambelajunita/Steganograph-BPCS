/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.awt.image.BufferedImage;

/**
 *
 * @author hp
 */
public class ImageBlock {
    private BitBlock[][] bitblocks;
    private int nrow;
    private int ncol;
    
    public ImageBlock(BufferedImage bimage) {
        int height = bimage.getHeight();
        int width = bimage.getWidth();
        nrow = height / 8;
        ncol = width / 8;
        this.bitblocks = new BitBlock[nrow][ncol];
        for(int row=0; row<nrow; row++) {
            for(int col=0; col<ncol; col++) {
                this.bitblocks[row][col] = new BitBlock();
                int[][] intMatrix = new int[8][8];
                
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        int color = bimage.getRGB((col*8)+j, (row*8)+i);
                        intMatrix[i][j] = color;
                    }
                }
                
                this.bitblocks[row][col].setAllBitPlanes(intMatrix);
            }
        }
    }
    
    public BufferedImage getBufferedImage() {
        BufferedImage imageresult = new BufferedImage(8*this.ncol, 8*this.nrow,  BufferedImage.TYPE_INT_RGB);
        for(int row=0; row<this.nrow; row++) {
            for(int col=0; col<this.ncol; col++) {
                BitBlock bitblocksresult = this.bitblocks[row][col];
                int[][] colormatrix = bitblocksresult.getColorMatrix();
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        int color = colormatrix[i][j];
                        imageresult.setRGB(col*8 + j, row*8 + i, color);
                    }
                }
            }
        }
        
        return imageresult;
    }
    
    public BitPlane getBitPlane(int position) {
        int posInner = position%32;
        int posOuter = position/32;
        int posOuterRow = posOuter/this.ncol;
        int posOuterCol = posOuter%this.ncol;
        return bitblocks[posOuterRow][posOuterCol].getBitPlane(posInner);
    }
    
    public int checkAllNoiseLike(double threshold) {
        int capacity = 0;
        for(int row=0; row<this.nrow; row++) {
            for(int col=0; col<this.ncol; col++) {
                capacity += this.bitblocks[row][col].checkAllNoiseLike(threshold);
            }
        }
        return capacity;
    }
    
    public void convertAllToCGC() {
        for(int row=0; row<this.nrow; row++) {
            for(int col=0; col<this.ncol; col++) {
                this.bitblocks[row][col].convertAllToCGC();
            }
        }
    }
    
    public void convertAllToPCB() {
        for(int row=0; row<this.nrow; row++) {
            for(int col=0; col<this.ncol; col++) {
                this.bitblocks[row][col].convertAllToPCB();
            }
        }
    }
    
    public int getRow() {
        return this.nrow;
    }
    
    public int getCol() {
        return this.ncol;
    }
    
    public int getMaxBitPlanes() {
        return nrow*ncol*32;
    }
}
