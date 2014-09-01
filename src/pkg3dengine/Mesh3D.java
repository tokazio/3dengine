/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.util.ArrayList;

/**
 *
 * @author rpetit
 */
public class Mesh3D {
    private final String Fname;
    private final ArrayList<Vector3D> Fvertices;
    private final ArrayList<Face3D> Ffaces;
    private Vector3D Fposition;
    private Vector3D Frotation;
    
    public Mesh3D(String aName) {
        Fname=aName;
        Fvertices = new ArrayList();
	Ffaces=new ArrayList();
        Fposition=Vector3D.zero();
        Frotation=Vector3D.zero();
    }
    
    public final void add(Vector3D aVector){
        Fvertices.add(aVector);
    }
    
    public final void add(Face3D aFace){
	Ffaces.add(aFace);
    }
    
    public final ArrayList<Vector3D> getVertices(){
        return Fvertices;
    }
    
    public final ArrayList<Face3D> getFaces(){
	return Ffaces;
    }
    
    public final Vector3D getRotation(){
        return Frotation;
    }
    
    public final Vector3D getPosition(){
        return Fposition;
    }
    
    public final void setPosition(Vector3D v){
        Fposition=v;
    }
    
    public final void setRotation(Vector3D v){
        Frotation=v;
    }
    
    public final int containVertice(Vector3D v){
	for(int i=0;i<Fvertices.size();i++)
	    if(Fvertices.get(i).equals(v))
		return i;
	return -1;
    }
    
}
