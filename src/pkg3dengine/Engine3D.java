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
    
    private Device3D device;
    private Mesh3D mesh;
    private ArrayList<Mesh3D> meshes;
    
    public Engine3D(ImageView img){
	 //create a cube
        mesh = new Mesh3D("Cube");
        mesh.add(new Vector3D(-1, 1, 1));
        mesh.add(new Vector3D(1, 1, 1));
        mesh.add(new Vector3D(-1, -1, 1));
        mesh.add(new Vector3D(-1, -1, -1));
        mesh.add(new Vector3D(-1, 1, -1));
        mesh.add(new Vector3D(1, 1, -1));
        mesh.add(new Vector3D(1, -1, 1));
        mesh.add(new Vector3D(1, -1, -1));
        //create the camera
        Camera3D camera = new Camera3D();
        camera.setPosition(new Vector3D(0, 0, 10.0));
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

    public final void move(){
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X() + 0.1, mesh.getRotation().Y(), mesh.getRotation().Z()));
    }
}
