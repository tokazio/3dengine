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
    
    public final Vector2D transformCoordinate(Matrix3D transMat){
        double x=0;
        double y=0;
        return new Vector2D(x,y);
    }

    /**
     * dot product of 2 vectors a & b
     * @param a
     * @param b
     * @return a double
     */
    public static double dot(Vector3D a, Vector3D b) {
        return a.X()* b.X() + a.Y() * b.Y() + a.Z() * b.Z();
    }
    
    /**
    * Normalize the vector
     * @return a vector
    */
    public final Vector3D normal() {
        return new Vector3D(Fx/length(),Fy/length(),Fz/length());
    }
    
    /**
     * Cross product of 2 vectors a & b
     * The Cross Product a × b of two vectors is another vector that is at right angles to both
     * @param a
     * @param b
     * @return a vector
     */
    public static Vector3D cross(Vector3D a, Vector3D b) {
        return new Vector3D(a.Y()*b.Z() - a.Z()*b.Y(), a.Z()*b.X() - a.X()*b.Z(),a.X()*b.Y() - a.Y()*b.X());
    }
    
    public double length(){
        return Math.sqrt((Fx * Fx) + (Fy * Fy) + (Fz * Fz));
    }
    
    public static Vector3D add(Vector3D a,Vector3D b){
        return new Vector3D(a.X()+b.X(),a.Y() +b.Y(),a.Z()+b.Z());
    }
    
    public static Vector3D sub(Vector3D a,Vector3D b){
        return new Vector3D(a.X()-b.X(),a.Y() -b.Y(),a.Z()-b.Z());
    }
}
