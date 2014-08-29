/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
/**
 *
 * @author rpetit
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView img;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         //create a cube
        Mesh3D mesh = new Mesh3D("Cube");
        mesh.add(new Vector3D(-1, 1, 1));
        mesh.add(new Vector3D(1, 1, 1));
        mesh.add(new Vector3D(-1, -1, 1));
        mesh.add(new Vector3D(-1, -1, -1));
        mesh.add(new Vector3D(-1, 1, -1));
        mesh.add(new Vector3D(1, 1, -1));
        mesh.add(new Vector3D(1, -1, 1));
        mesh.add(new Vector3D(1, -1, -1));
        //create the engine
        Device3D device = new Device3D(img);
        //create the camera
        Camera3D camera = new Camera3D();
        camera.setPosition(new Vector3D(0, 0, 10.0f));
        camera.setTarget(Vector3D.zero());
        //add cube to engine
        ArrayList<Mesh3D> meshes = new ArrayList();
        meshes.add(mesh);
        
        //
        device.clear(0, 0, 0, 255);
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X() + 0.01f, mesh.getRotation().Y() + 0.01f, mesh.getRotation().Z()));
        // Doing the various matrix operations
        device.render(camera, meshes);
        // Flushing the back buffer into the front buffer
        device.present();
        
    }    
    
}
