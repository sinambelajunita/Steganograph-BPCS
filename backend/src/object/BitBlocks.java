package object;

import java.util.Arrays;

/**
 *
 * @author hp
 */
public class BitBlocks {
    private BitPlane[] bitplanes;
    
    public BitBlocks() {
        this.bitplanes = new BitPlane[24];
        for(int i=0; i<24; i++) {
            this.bitplanes[i] = new BitPlane();
        }
    }
    
    public void setAllBitPlanes(int[][] intMatrix) {
        for(int pos=0; pos<8; pos++) {
            for(int color=0; color<3; color++) {           
                int[] bitmatrix = new int[8];
                
                for(int i=0; i<8; i++) {
                    bitmatrix[i] = 0;
                    for(int j=0; j<8; j++) {
                        int bit = (((intMatrix[i][j] >> (8*color)) & (1<<pos)) >> pos);
                        bitmatrix[i] = bitmatrix[i] | (bit << j);
                    }
                }
                this.bitplanes[3*pos+color].setBitMatrix(bitmatrix);
            }
        }
    }
    
    public void setBitPlane(int i, int j, BitPlane bitplane) {
        this.bitplanes[3*i+j].setBitMatrix(bitplane.getBitMatrix());
    }
    
    public BitPlane getBitPlane(int i, int j) {
        return this.bitplanes[3*i+j];
    }
    
    public void convertAllToCGC() {
        for(int i=0; i<24; i++) {
            bitplanes[i].convertToCGC();
        }
    }
    
    public void convertAllToPCB() {
        for(int i=0; i<24; i++) {
            bitplanes[i].convertToPCB();
        }
    }
    
    public int checkAllNoiseLike(double threshold) {
        int result = 0;
        for(int i=0; i<24; i++) {
            bitplanes[i].checkNoiseLike(threshold);
            if(bitplanes[i].getNoiseLike()) {
                result++;
            }
        }
        return result;
    }
    
    public int[][] getColorMatrix() {
        int[][] colormatrix = new int[8][8];
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                int colors = 0;
                for(int c=0; c<3; c++) {
                    int color = 0;
                    for(int b=0; b<8; b++) {
                        color = color | (bitplanes[3*b+c].getBit(i, j)) << b;
                    }
                    colors = (colors << (8*c)) | color;
                }
                colormatrix[i][j] = colors;
            }
        }
        return colormatrix;
    }
    
}
