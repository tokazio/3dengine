/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

/**
 *
 * @author romainpetit
 */
public class Face3D {
    private int Fa;
    private int Fb;
    private int Fc;
    
    public Face3D(){
	
    }
    
    public Face3D(int a,int b,int c){
	Fa=a;
	Fb=b;
	Fc=c;
    }
    
    public final int A(){
	return Fa;
    }
    
    public final int B(){
	return Fb;
    }
    
    public final int C(){
	return Fc;
    }
    
    public final void setA(int i){
	Fa=i;
    }
    
    public final void setB(int i){
	Fb=i;
    }
    
    public final void setC(int i){
	Fc=i;
    }
}
