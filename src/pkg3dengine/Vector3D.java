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
public class Vector3D {

    private double Fx,Fy,Fz;
    
    public Vector3D(double x, double y, double z) {
        Fx=x;
        Fy=y;
        Fz=z;
    }
    
    public final double X(){
        return Fx;
    }
    
    public final double Y(){
        return Fy;
    }
    
    public final double Z(){
        return Fz;
    }
    
    public static Vector3D zero(){
        return new Vector3D(0,0,0);
    }
}
