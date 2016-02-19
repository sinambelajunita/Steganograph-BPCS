/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.Arrays;

/**
 *
 * @author hp
 */
public class BitPlane {
    
    private int[] matrix;
    private final int[] dr = {1, -1, 0, 0};
    private final int[] dc = {0, 0, 1, -1};
    private boolean noiselike;
    
    public BitPlane() {
        matrix = new int[8];
        for(int i=0; i<8; i++)
            matrix[i] = 0;
        noiselike = false;
    }
    
    public BitPlane(int[] matrix) {
        System.arraycopy(matrix, 0, this.matrix, 0, 8);
        noiselike = false;
    }
    
    public void checkNoiseLike(double threshold) {
        noiselike = isComplex(threshold);
    }
    
    public void setNoiseLike(boolean noiselike) {
        this.noiselike = noiselike;
    }
    
    public boolean getNoiseLike() {
        return noiselike;
    }
    
    public void setBit(int x, int y, int bit) {
        matrix[x] = (matrix[x] - (matrix[x] & (1<<y))) | (bit<<y);
    }
    
    public int getBit(int x, int y) {
        return (matrix[x] & (1<<y)) >> y;
    }
    
    public void setBitRow(int x, int bitrow) {
        matrix[x] = bitrow;
    }
    
    public int getBitRow(int x) {
        return matrix[x];
    }
    
    public void setBitMatrix(int[] matrix) {
        System.arraycopy(matrix, 0, this.matrix, 0, 8);
    }
    
    public int[] getBitMatrix() {
        return matrix;
    }
    
    public BitPlane getConjugate() {
        BitPlane bpConjugate = new BitPlane(this.matrix);
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(i+j%2==0)
                    bpConjugate.setBit(i, j, (bpConjugate.getBit(i, j)^1));
            }
        }
        return bpConjugate;
    }
    
    public double getComplexity() {
        double complexity = 0.0;
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                for(int a=0; a<4; a++) {
                    if((i+dr[a] >= 0) && (i+dr[a] < 8) && (j+dc[a] >= 0) && (j+dc[a] < 8)) {
                        if(getBit(i, j) != getBit(i+dr[a], j+dc[a])) {
                            complexity++;
                        }
                    }
                }
            }
        }
        
        complexity = complexity / (2*112.0);
        
        return complexity;
    }
    
    public boolean isComplex(double threshold) {
        return (getComplexity() > threshold);
    }
    
    public void convertToCGC() {
        for(int i=0; i<8; i++) {
            for(int j=1; j<8; j++) {
                int res = this.getBit(i, j-1) ^ this.getBit(i, j);
                this.setBit(i, j, res);
            }
        }
    }
    
    public void convertToPCB() {
        for(int i=0; i<8; i++) {
            for(int j=1; j<8; j++) {
                int res = this.getBit(i, j-1) ^ this.getBit(i, j);
                this.setBit(i, j, res);
            }
        }
    }
    
}
