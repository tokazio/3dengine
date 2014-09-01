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
    
    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public Vector3D(double x, double y, double z) {
        Fx=x;
        Fy=y;
        Fz=z;
    }
    
    /**
     * X du vecteur
     * @return 
     */
    public final double X(){
        return Fx;
    }
    
    /**
     * Y du vecteur
     * @return 
     */
    public final double Y(){
        return Fy;
    }
    
    /**
     * Z du vecteur
     * @return 
     */
    public final double Z(){
        return Fz;
    }
    
    /**
     * 
     * @return Un vecteur avec x,y et z à 0
     */
    public static Vector3D zero(){
        return new Vector3D(0.0,0.0,0.0);
    }
    
    /**
     * http://msdn.microsoft.com/en-us/library/windows/desktop/bb153109(v=vs.85).aspx
     * Transforme un point 3D en point 2D selon une matrice
     * @param b Matrice de transformation
     * @return Le nouveau point après transformation
     */
    public final Vector3D transformCoordinate(Matrix3D b){
	double d=X()*b.get(0,0)+Y()*b.get(1,0)+Z()*b.get(2,0)+b.get(3,0);
	double e=X()*b.get(0,1)+Y()*b.get(1,1)+Z()*b.get(2,1)+b.get(3,1);
	double f=X()*b.get(0,2)+Y()*b.get(1,2)+Z()*b.get(2,2)+b.get(3,2);
	double g=X()*b.get(0,3)+Y()*b.get(1,3)+Z()*b.get(2,3)+b.get(3,3);
        return new Vector3D(d/g,e/g,f/g);
    }

    /**
     * dot product of 2 vectors a & b
     * @param a a vector
     * @param b a vector
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
     * @param a a vector
     * @param b a vector
     * @return a vector
     */
    public static Vector3D cross(Vector3D a, Vector3D b) {
        return new Vector3D(a.Y()*b.Z() - a.Z()*b.Y(), a.Z()*b.X() - a.X()*b.Z(),a.X()*b.Y() - a.Y()*b.X());
    }
    
    /**
     * 
     * @return La longueur d'un vecteur
     */
    public double length(){
        return Math.sqrt((Fx * Fx) + (Fy * Fy) + (Fz * Fz));
    }
    
    /**
     * Addition des vecteurs: a+b
     * @param a Un vecteur
     * @param b Un vecteur
     * @return Un vecteur
     */
    public static Vector3D add(Vector3D a,Vector3D b){
        return new Vector3D(a.X()+b.X(),a.Y() +b.Y(),a.Z()+b.Z());
    }
    
    /**
     * Soustraction des vecteurs: a-b
     * @param a Un vecteur
     * @param b Un vecteur
     * @return Un vecteur
     */
    public static Vector3D sub(Vector3D a,Vector3D b){
        return new Vector3D(a.X()-b.X(),a.Y()-b.Y(),a.Z()-b.Z());
    }
    
    public final boolean equals(Vector3D v){
	return (Fx==v.X() && Fy==v.Y() && Fz==v.Z());
    }
}
