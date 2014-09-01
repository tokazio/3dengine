/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 *
 * @author rpetit
 */
public class FXMLDocumentController implements Initializable {

    private Engine3D engine;

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	engine = new Engine3D(img);
    }

    @FXML
    private void rotxplus(ActionEvent event) {
	engine.rotX();
	engine.render();
    }

    @FXML
    private void rotyplus(ActionEvent event) {
	engine.rotY();
	engine.render();
    }

    @FXML
    private void rotzplus(ActionEvent event) {
	engine.rotZ();
	engine.render();
    }

}
