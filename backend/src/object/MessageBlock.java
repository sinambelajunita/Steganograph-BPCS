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
        this.size = 0;
        this.bitplanes = null;
    }
    
    public MessageBlock(int n) {
        this.size = n;
        this.bitplanes = new BitPlane[size];
        for(int i=0; i<size; i++) {
            this.bitplanes[i] = new BitPlane();
        }
    }
    
    public MessageBlock(BitPlane[] otherbitplanes) {
        this.size = otherbitplanes.length;
        this.bitplanes = new BitPlane[size];
        for(int i=0; i<size; i++) {
            this.bitplanes[i] = new BitPlane();
            this.bitplanes[i].setBitMatrix(otherbitplanes[i].getBitMatrix());
        }
    }
    
    public MessageBlock(ArrayList<BitPlane> otherbitplanes) {
        this.size = otherbitplanes.size();
        this.bitplanes = new BitPlane[size];
        for(int i=0; i<this.size; i++) {
            this.bitplanes[i] = new BitPlane();
            this.bitplanes[i].setBitMatrix(otherbitplanes.get(i).getBitMatrix());
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
        this.size = (total+63)/64 + 1; // ceil(total/64) + 1(bitplane kosong)
        
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
    // TODO benerin
    public byte[] toBytes(){
        ArrayList<Boolean> bitbool = new ArrayList<Boolean>();
        for(int i=0; i<this.size; i++) {
            int[] matrix = bitplanes[i].getBitMatrix();
            for(int j=0; j<8; j++){
                for(int k=0; k<8; k++){ 
                    if(j+k > 0) {
                        int bit = (matrix[j] & (1<<k)) >> k;
                        bitbool.add(bit==1);
                    }
                }
            }
        }
        
        int bytesize = bitbool.size()/8;
        byte[] bytes = new byte[bytesize];
        for(int i=0; i<bytesize; i++) {
            bytes[i] = 0;
            for(int j=0; j<8; j++) {
                bytes[i] = (byte) (((bitbool.get(8*i+j)?1:0) << j) | bytes[i]);
            }
        }
        
        return bytes;
    }
}
