/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 *
 * @author rpetit
 */
public class FXMLDocumentController implements Initializable {

    private Engine3D engine;
    private int xclic,yclic;

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	engine = new Engine3D(img);
        engine.add(new Mesh3D("BALPLATGC").loadSTLfromFile("BALPLATGC.stl"));
        
        img.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                engine.iniRotY();
                engine.iniRotX();
            //    engine.render();
              //  engine.flush();
            }
        }
    }
});
        
        img.setOnScroll(new EventHandler<ScrollEvent>() {
      @Override
      public void handle(ScrollEvent event) {
          engine.camZ(-(int)event.getDeltaY());
        //engine.render();
          //      engine.flush();
      }
    });
        
        img.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
          xclic=(int) event.getX();
          yclic=(int) event.getY();
          img.setCursor(Cursor.MOVE);
      }});
        
        img.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
         img.setCursor(Cursor.DEFAULT);
      }});
        
        img.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
            engine.rotY((int)(event.getX()-xclic)/2);
            engine.rotX((int)(event.getY()-yclic)/2);
            xclic=(int) event.getX();
            yclic=(int) event.getY();
            //engine.render();
            //engine.flush();
      }
    });
        
    }

    @FXML
    private void rotxplus(ActionEvent event) {
	engine.rotX(10);
	engine.render();
	engine.flush();
    }

    @FXML
    private void rotyplus(ActionEvent event) {
	engine.rotY(10);
	engine.render();
	engine.flush();
    }

    @FXML
    private void rotzplus(ActionEvent event) {
	engine.rotZ(10);
	engine.render();
	engine.flush();
    }
    
    private void go(){
	final AnimationTimer timer = new AnimationTimer() {
	    @Override
	    public void handle(long now) {
		//engine.rotY(+1);
		engine.render();
		engine.flush();
	    }
	};
	timer.start();
    }

    @FXML
    private void auto(ActionEvent event) {
	go();
    }

    @FXML
    private void rotxmoins(ActionEvent event) {
        engine.rotX(-10);
	engine.render();
	engine.flush();
    }

    @FXML
    private void rotymoins(ActionEvent event) {
        engine.rotY(-10);
	engine.render();
	engine.flush();
    }

    @FXML
    private void rotzmoins(ActionEvent event) {
        engine.rotZ(-10);
	engine.render();
	engine.flush();
    }

    @FXML
    private void zoommoins(ActionEvent event) {
        engine.camZ(-10);
        engine.render();
	engine.flush();
    }

    @FXML
    private void zoomplus(ActionEvent event) {
        engine.camZ(10);
        engine.render();
	engine.flush();
    }
}


