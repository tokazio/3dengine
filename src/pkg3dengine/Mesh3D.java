/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author rpetit
 */
public class Mesh3D {
    private String Fname;
    private final ArrayList<Vector3D> Fvertices;
    private final ArrayList<Face3D> Ffaces;
    private Vector3D Fposition;
    private Vector3D Frotation;
    
    /**
     * 
     * @param aName 
     */
    public Mesh3D(String aName) {
        Fname=aName;
        Fvertices = new ArrayList();
	Ffaces=new ArrayList();
        Fposition=Vector3D.zero();
        Frotation=Vector3D.zero();
    }
    
    /**
     * 
     * @param aVector 
     */
    public final void add(Vector3D aVector){
        Fvertices.add(aVector);
    }
    
    /**
     * 
     * @param aFace 
     */
    public final void add(Face3D aFace){
	Ffaces.add(aFace);
    }
    
    /**
     * 
     * @return 
     */
    public final ArrayList<Vector3D> getVertices(){
        return Fvertices;
    }
    
    /**
     * 
     * @return 
     */
    public final ArrayList<Face3D> getFaces(){
	return Ffaces;
    }
    
    /**
     * 
     * @return 
     */
    public final Vector3D getRotation(){
        return Frotation;
    }
    
    /**
     * 
     * @return 
     */
    public final Vector3D getPosition(){
        return Fposition;
    }
    
    /**
     * 
     * @param v 
     */
    public final void setPosition(Vector3D v){
        Fposition=v;
    }
    
    /**
     * 
     * @param v 
     */
    public final void setRotation(Vector3D v){
        Frotation=v;
    }
    
    /**
     * 
     * @param v
     * @return 
     */
    public final int containVertice(Vector3D v){
	for(int i=0;i<Fvertices.size();i++)
	    if(Fvertices.get(i).equals(v))
		return i;
	return -1;
    }
    
    /**
     * 
     * @param aFileName
     * @return 
     */
    public final Mesh3D loadSTLfromFile(String aFileName){
        Fname=aFileName;
        try{
            BufferedReader br = new BufferedReader(new FileReader(aFileName));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    if(line.contains("vertex")){
                        String l0=line;
                        String l1=br.readLine();
                        String l2=br.readLine();
                        addFace(l0,l1,l2);
                    }
                    line = br.readLine();
                }
            } finally {
                br.close();
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return this;
    }
    
    private void addFace(String s0,String s1,String s2){
	String s;
	Face3D f=new Face3D();
	s=s0.replaceAll("vertex", "");
	f.setA(addPoint(s.trim()));
	s=s1.replaceAll("vertex", "");
	f.setB(addPoint(s.trim()));
	s=s2.replaceAll("vertex", "");
	f.setC(addPoint(s.trim()));
        add(f);
    }
    
    //
    private int addPoint(String s){
	String[]c=s.replaceAll("e", "E").split("\\s");
        //System.out.println(Arrays.toString(c));
	double x=Double.valueOf(c[0]).doubleValue();
	double y=Double.valueOf(c[1]).doubleValue();
	double z=Double.valueOf(c[2]).doubleValue();
	Vector3D v=new Vector3D(x, y, z);
        //v.print();
	int i=containVertice(v);
        //si non présent, ajoute le point dans la liste puis renvoi son index
	if(i<0){
	    add(v);
	    return Fvertices.size()-1;
	}else{
           //si déjà présent, renvoi l'index du point sans l'ajouter (plus rapide et léger)
	   return i;
	}
    }
    
    /**
     * 
     */
    public final void print(){
        System.out.println(Fname+": "+Fvertices.size()+" points et "+Ffaces.size()+" faces.");
        System.out.println("Vertices: ");
        for(int i=0;i<Fvertices.size();i++)
            Fvertices.get(i).print();
        System.out.println("Faces: ");
        for(int i=0;i<Ffaces.size();i++)
            Ffaces.get(i).print();
    }
    
}
