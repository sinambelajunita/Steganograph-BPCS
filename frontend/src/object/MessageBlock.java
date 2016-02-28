/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class MessageBlock {
    private BitPlane[] bitplanes;
    int size;
    
    public MessageBlock() {
        size = 0;
        bitplanes = null;
    }
    
    public MessageBlock(int n) {
        size = n;
        bitplanes = new BitPlane[size];
        for(int i=0; i<size; i++) {
            bitplanes[i] = new BitPlane();
        }
    }
    
    public MessageBlock(BitPlane[] bitplanes) {
        size = bitplanes.length;
        bitplanes = new BitPlane[size];
        for(int i=0; i<size; i++) {
            bitplanes[i].setBitMatrix(bitplanes[i].getBitMatrix());
        }
    }
    
    public MessageBlock(byte[] bytes) {
        ArrayList<Boolean> bitbool = new ArrayList<Boolean>();
        
        // create array of boolean, so that it insert 0 (false) in front of every 63 boolean;
        int it = 0;
        for(int i=0; i<bytes.length; i++) {
            for (int j=0; j<8; j++) {
                if(it%64 == 0) {
                    bitbool.add(Boolean.FALSE);
                    it++;
                }
                bitbool.add(((bytes[i] & (1 << j)) >> j) == 1);
                it++;
            }
        }
        
        int total = it;
        this.size = (total+63)/64; // ceil(total/64)
        
        this.bitplanes = new BitPlane[this.size];
        it=0;
        for(int i=0; i<size; i++) {
            this.bitplanes[i] = new BitPlane();
            int[] matrix = new int[8];
            for(int j=0; j<8; j++) {
                matrix[j] = 0;
                for(int k=0; k<8; k++) {
                    int bit;
                    if(it < bitbool.size())
                        bit = (bitbool.get(it))?1:0;
                    else
                        bit = 0;
                    it++;
                    matrix[j] = matrix[j] | (bit << k);
                }
            }
            this.bitplanes[i].setBitMatrix(matrix);
        }
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void setBitPlane(BitPlane[] bitplanes){
        this.size = bitplanes.length;
        this.bitplanes = bitplanes;
    }
    
    public void setBitPlane(int i, BitPlane bitplane) {
        this.bitplanes[i].setBitMatrix(bitplane.getBitMatrix());
    }
    
    public BitPlane getBitPlane(int i) {
        return bitplanes[i];
    }
    
    // ubah messageblock ke array of byte message
    public byte[] toBytes(){
        byte[] bytes = new byte[size*8];
        for(int i=0; i<size; i++) {
            int[] matrix = bitplanes[i].getBitMatrix();
            for(int j=0; j<8; j++) {
                if(8*i+j<bytes.length) {
                    bytes[8*i+j] = (byte)(matrix[j]);
                }
            }
        }
        return bytes;
    }
}
