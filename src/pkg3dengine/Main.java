/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rpetit
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
        Mesh3D mesh = new Mesh3D("Cube");
        mesh.add(new Vector3D(-1, 1, 1));
        mesh.add(new Vector3D(1, 1, 1));
        mesh.add(new Vector3D(-1, -1, 1));
        mesh.add(new Vector3D(-1, -1, -1));
        mesh.add(new Vector3D(-1, 1, -1));
        mesh.add(new Vector3D(1, 1, -1));
        mesh.add(new Vector3D(1, -1, 1));
        mesh.add(new Vector3D(1, -1, -1));
        
        Device3D device=new Device3D(800,600);
        Camera3D camera = new Camera3D();
        camera.setPosition(new Vector3D(0, 0, 10.0f));
        camera.setTarget(Vector3D.zero());
        
        ArrayList<Mesh3D> meshes=new ArrayList();
        meshes.add(mesh);
        
        device.clear(0, 0, 0,255);
        // rotating slightly the cube during each frame rendered
        mesh.setRotation(new Vector3D(mesh.getRotation().X() + 0.01f, mesh.getRotation().Y() + 0.01f, mesh.getRotation().Z()));
        // Doing the various matrix operations
        device.render(camera, meshes);
        // Flushing the back buffer into the front buffer
        device.present();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
