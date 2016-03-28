package object;

import java.util.Arrays;

/**
 *
 * @author hp
 */
public class BitBlock {
    private BitPlane[] bitplanes;
    
    public BitBlock() {
        this.bitplanes = new BitPlane[32];
        for(int i=0; i<32; i++) {
            this.bitplanes[i] = new BitPlane();
        }
    }
    
    public void setAllBitPlanes(int[][] intMatrix) {
        for(int pos=0; pos<8; pos++) {
            for(int colortype=0; colortype<4; colortype++) {           
                int[] bitmatrix = new int[8];
                
                for(int i=0; i<8; i++) {
                    bitmatrix[i] = 0;
                    for(int j=0; j<8; j++) {
                        int bit = (((intMatrix[i][j] >> (8*colortype)) & (1<<pos)) >> pos);
                        bitmatrix[i] = bitmatrix[i] | (bit << j);
                    }
                }
                this.bitplanes[4*pos+colortype].setBitMatrix(bitmatrix);
            }
        }
    }
    
    public void setBitPlane(int position, BitPlane bitplane) {
        this.bitplanes[position].setBitMatrix(bitplane.getBitMatrix());
    }
    
    public BitPlane getBitPlane(int position) {
        return this.bitplanes[position];
    }
    
    public void convertAllToCGC() {
        for(int i=0; i<32; i++) {
            bitplanes[i].convertToCGC();
        }
    }
    
    public void convertAllToPCB() {
        for(int i=0; i<32; i++) {
            bitplanes[i].convertToPCB();
        }
    }
    
    public int checkAllNoiseLike(double threshold) {
        int result = 0;
        for(int i=0; i<32; i++) {
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
                for(int c=0; c<4; c++) {
                    int color = 0;
                    for(int b=0; b<8; b++) {
                        color = color | ((bitplanes[4*b+c].getBit(i, j)) << b);
                    }
                    colors = colors | (color << (8*c));
                }
                colormatrix[i][j] = colors;
            }
        }
        return colormatrix;
    }
    
}
