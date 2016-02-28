/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

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
    
    public MessageBlock(byte[] bytes) {
        int length = bytes.length;
        this.size = (length+7)/8;
        bitplanes = new BitPlane[this.size];
        for(int i=0; i<size; i++) {
            bitplanes[i] = new BitPlane();
            int[] matrix = new int[8];
            for(int j=0; j<8; j++) {
                if(8*i+j<bytes.length) {
                    matrix[j] = (int)(bytes[8*i+j]);
                }
            }
            bitplanes[i].setBitMatrix(matrix);
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
            bitplanes[i] = new BitPlane();
            int[] matrix = new int[8];
            for(int j=0; j<8; j++) {
                if(8*i+j<bytes.length) {
                    matrix[j] = (int)(bytes[8*i+j]);
                }
            }
            bitplanes[i].setBitMatrix(matrix);
        }
        return bytes;
    }
}
