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
public class MessageBlocks {
    private BitPlane[] bitplanes;
    
    public MessageBlocks(int n) {
        bitplanes = new BitPlane[n];
        for(int i=0; i<n; i++) {
            bitplanes[i] = new BitPlane();
        }
    }
    
    public void setBitPlane(int i, BitPlane bitplane) {
        this.bitplanes[i].setBitMatrix(bitplane.getBitMatrix());
    }
    
    public BitPlane getBitPlane(int i) {
        return bitplanes[i];
    }
}
