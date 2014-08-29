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
    private Vector3D Fposition;
    private Vector3D Frotation;
    
    public Mesh3D(String aName) {
        Fname=aName;
        Fvertices = new ArrayList();
        Fposition=Vector3D.zero();
        Frotation=Vector3D.zero();
    }
    
    public final void add(Vector3D aVector){
        Fvertices.add(aVector);
    }
    
    public final ArrayList<Vector3D> getVertices(){
        return Fvertices;
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
    
}
