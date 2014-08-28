/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

/**
 *
 * @author rpetit
 */
public class Vector2D {
    private double Fx,Fy,Fz;
    private RGBA Fcolor;
    
    public Vector2D(double x, double y) {
        Fx=x;
        Fy=y;
    }
    
    public final double X(){
        return Fx;
    }
    
    public final double Y(){
        return Fy;
    }
    
}
