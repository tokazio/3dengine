/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

/**
 *
 * @author romainpetit
 */
public class Engine3D {
    
    private final Device3D device;
    private final Mesh3D mesh;
    private final ArrayList<Mesh3D> meshes;
    
    public Engine3D(ImageView img){
	 //create a cube
        mesh = new Mesh3D("Cube");
	//org
	//mesh.add(new Vector3D(0.0, 0.0, 0.0));
	//points
	mesh.add(new Vector3D(-1.0, 1.0, 1.0));//e 0
        mesh.add(new Vector3D(1.0, 1.0, 1.0));//f 1
        mesh.add(new Vector3D(-1.0, -1.0, 1.0));//g 2
        mesh.add(new Vector3D(1.0, -1.0, 1.0));//h 3
	mesh.add(new Vector3D(-1.0, 1.0, -1.0));//a 4
        mesh.add(new Vector3D(1.0, 1.0, -1.0));//c 5
        mesh.add(new Vector3D(1.0, -1.0, -1.0));//d 6
        mesh.add(new Vector3D(-1.0, -1.0, -1.0));//b 7
	//Faces	
	mesh.add(new Face3D (4, 7, 6 ));//abd
	mesh.add(new Face3D (4, 6, 5 ));//adc
	mesh.add(new Face3D (0, 3, 2 ));//ehg
	mesh.add(new Face3D (0, 1, 3 ));//efh
	mesh.add(new Face3D (4, 0, 1 ));//aef
	mesh.add(new Face3D (4, 5, 1 ));//acf
	mesh.add(new Face3D (7, 2, 6 ));//bgd
	mesh.add(new Face3D (2, 3, 6 ));//ghd
	mesh.add(new Face3D (5, 1, 3 ));//cfh
	mesh.add(new Face3D (5, 6, 3 ));//cdh
	mesh.add(new Face3D (4, 0, 2 ));//aeg
	mesh.add(new Face3D (4, 7, 2 ));//abg
	
        
        //create the camera
        Camera3D camera = new Camera3D();
        camera.setPosition(new Vector3D(0.0, 0.0, -10.0));
        camera.setTarget(Vector3D.zero());
	//create the device
        device = new Device3D(img,camera);
	//add cube to engine
        meshes = new ArrayList();
        meshes.add(mesh);        
        //
	render();
	}
    
    public final void render(){
	device.clear(0, 0, 0, 255);
	// Doing the various matrix operations
        device.render(meshes);
    }
    
    public final void flush(){
	device.present();
    }

    public final void rotX(){
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X() + Matrix3D.degToRad(10.0), mesh.getRotation().Y(), mesh.getRotation().Z()));
    }
    
    public final void rotY(){
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X(), mesh.getRotation().Y() + Matrix3D.degToRad(10.0), mesh.getRotation().Z()));
    }
    
    public final void rotZ(){
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X(), mesh.getRotation().Y() , mesh.getRotation().Z()+ Matrix3D.degToRad(10.0)));
    }
    
    /*
    http://people.sc.fsu.edu/~jburkardt/data/stla/stla.html
    solid cube_corner
          facet normal 0.0 -1.0 0.0
            outer loop
              vertex 0.0 0.0 0.0
              vertex 1.0 0.0 0.0
              vertex 0.0 0.0 1.0
            endloop
          endfacet
          facet normal 0.0 0.0 -1.0
            outer loop
              vertex 0.0 0.0 0.0
              vertex 0.0 1.0 0.0
              vertex 1.0 0.0 0.0
            endloop
          endfacet
          facet normal -1.0 0.0 0.0
            outer loop
              vertex 0.0 0.0 0.0
              vertex 0.0 0.0 1.0
              vertex 0.0 1.0 0.0
            endloop
          endfacet
          facet normal 0.577 0.577 0.577
            outer loop
              vertex 1.0 0.0 0.0
              vertex 0.0 1.0 0.0
              vertex 0.0 0.0 1.0
            endloop
          endfacet
    */
    public final void loadSTL(){
	//cherche la 1ère ligne contenant vertex
	//les 2 suivantes cotiennent aussi vertex
	addFace(s0,s1,s2);
    }
    
    
    private final void addFace(String s0,String s1,String s2){
	String s;
	Face3D f=new Face3D();
	s=s0.replaceAll("vertex", "");
	f.setA(addPoint(s));
	s=s1.replaceAll("vertex", "");
	f.setB(addPoint(s));
	s=s2.replaceAll("vertex", "");
	f.setC(addPoint(s));
    }
    
    //vertex 0.0 0.0 0.0
    private final int addPoint(String s){
	String[]c=s.split("\\s");
	double x=Double.parseDouble(c[0]);
	double y=Double.parseDouble(c[1]);
	double z=Double.parseDouble(c[2]);
	Vector3D v=new Vector3D(x, y, z);
	//si non présent
	int i=mesh.containVertice(v);
	if(i<0){
	    mesh.add(v);
	    return mesh.getVertices().size()-1;
	}else{
	    return i;
	}
    }
}
