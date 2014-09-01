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
	mesh.add(new Vector3D(0.0, 0.0, 0.0));
	/*
	//face arrière
	//z<0 ne s'affiche pas?
        mesh.add(new Vector3D(-1.0, 1.0, -1.0));//a
        mesh.add(new Vector3D(1.0, 1.0, -1.0));//b
        mesh.add(new Vector3D(-1.0, -1.0, -1.0));//c
        mesh.add(new Vector3D(1.0, -1.0, -1.0));//d
	
	//face avant
	mesh.add(new Vector3D(-1.0, 1.0, 1.0));//e
        mesh.add(new Vector3D(1.0, 1.0, 1.0));//f
        mesh.add(new Vector3D(-1.0, -1.0, 1.0));//g
        mesh.add(new Vector3D(1.0, -1.0, 1.0));//h
	*/
	//face arrière
	//z<0 ne s'affiche pas?
        mesh.add(new Vector3D(-1.0, 1.0, 3.0));//a
        mesh.add(new Vector3D(1.0, 1.0, 3.0));//b
        mesh.add(new Vector3D(-1.0, -1.0, 3.0));//c
        mesh.add(new Vector3D(1.0, -1.0, 3.0));//d
	
	//face avant
	mesh.add(new Vector3D(-1.0, 1.0, 1.0));//e
        mesh.add(new Vector3D(1.0, 1.0, 1.0));//f
        mesh.add(new Vector3D(-1.0, -1.0, 1.0));//g
        mesh.add(new Vector3D(1.0, -1.0, 1.0));//h
        
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
        // Flushing the back buffer into the front buffer
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
}
