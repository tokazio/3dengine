/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import javafx.scene.paint.Color;

/**
 *
 * @author rpetit
 */
public class RGBA {
    double Blue=0;
    double Green=0;
    double Red=0;
    double Alpha=1;
    
    /**
     * 
     * @param r
     * @param g
     * @param b
     * @param a 
     */
    public RGBA(double r,double g,double b,double a){
        Blue=b;
        Green=g;
        Red=r;
        Alpha=a;
    }
    
    /**
     * 
     * @return 
     */
    public Color toColor(){
        return new Color(Red,Green,Blue,Alpha);
    }
}
