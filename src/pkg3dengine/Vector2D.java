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
    
    public static Vector2D sub(Vector2D a,Vector2D b){
	return new Vector2D(a.X()-b.X(),a.Y()-b.Y());
    }
    
    public static Vector2D add(Vector2D a,Vector2D b){
	return new Vector2D(a.X()+b.X(),a.Y()+b.Y());
    }
    
    public final Vector2D mul(double d){
	return new Vector2D(X()*d,Y()*d);
    }
    
    public final double length(){
	return Math.sqrt((Fx * Fx) + (Fy * Fy));
    }
}
