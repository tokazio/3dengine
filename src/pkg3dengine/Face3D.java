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
    private int Fa=-1;
    private int Fb=-1;
    private int Fc=-1;
    
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
    
    /**
     * Défini l'index du vertex utilisé pour le point A du triangle dans la liste des vertex
     * @param i 
     */
    public final void setA(int i){
	Fa=i;
    }
    
    /**
     * Défini l'index du vertex utilisé pour le point B du triangle dans la liste des vertex
     * @param i 
     */
    public final void setB(int i){
	Fb=i;
    }
    
    /**
     * Défini l'index du vertex utilisé pour le point C du triangle dans la liste des vertex
     * @param i 
     */
    public final void setC(int i){
	Fc=i;
    }
    
    public final void print(){
        System.out.println("A:"+Fa+" B:"+Fb+" C:"+Fc);
    }
}
