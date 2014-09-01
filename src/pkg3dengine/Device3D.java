/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author rpetit
 */
public class Device3D {

    private double Fwidth;
    private double Fheight;
    private ImageView Fimg;
    private Matrix3D FviewMatrix;
    private Matrix3D FprojectionMatrix;
    private WritableImage FwImage;
    private PixelWriter FpixelWriter;

    public Device3D(double width, double height,Camera3D camera) {
	Fwidth = width;
	Fheight = height;
	FwImage = new WritableImage((int) Fwidth, (int) Fheight);
	FpixelWriter = FwImage.getPixelWriter();
	init(camera);
    }

    public Device3D(ImageView i,Camera3D camera) {
	Fimg = i;
	Fwidth = i.getFitWidth();
	Fheight = i.getFitHeight();
	FwImage = new WritableImage((int) Fwidth, (int) Fheight);
	FpixelWriter = FwImage.getPixelWriter();
	init(camera); 
    }

    // This method is called to clear the back buffer with a specific color
    public final void clear(int r, int g, int b, int a) {
	for (int x = 0; x < Fwidth; x++) {
	    for (int y = 0; y < Fheight; y++) {
		FpixelWriter.setColor(x, y, new RGBA(r, g, b, a).toColor());
	    }
	}
    }

    // Called to put a pixel on screen at a specific X,Y coordinates
    public final void putPixel(double x, double y, RGBA color) {
	FpixelWriter.setColor((int)x, (int)y, color.toColor());
    }

    // Project takes some 3D coordinates and transform them
    // in 2D coordinates using the transformation matrix
    public final Vector2D project(Vector3D coord, Matrix3D transMat) {
	// transforming the coordinates
	Vector3D point = coord.transformCoordinate(transMat);
            // The transformed coordinates will be based on coordinate system
	// starting on the center of the screen. But drawing on screen normally starts
	// from top left. We then need to transform them again to have x:0, y:0 on top left.
	double x = point.X() * Fwidth + Fwidth / 2.0;
	double y = -point.Y() * Fheight + Fheight / 2.0;
	return (new Vector2D(x, y));
    }

    // DrawPoint calls PutPixel but does the clipping operation before
    public final void drawPoint(Vector2D point) {
	// Clipping what's visible on screen
	if (point.X() >= 0 && point.Y() >= 0 && point.X() < Fwidth && point.Y() < Fheight) {
	    putPixel(point.X(), point.Y(), new RGBA(255, 255, 255, 1));
	}
    }

    public final void init(Camera3D camera) {
	FviewMatrix = Matrix3D.lookAtLH(camera.getPosition(), camera.getTarget(), camera.getHead());
	FprojectionMatrix = Matrix3D.perspectiveFovLH(45, Fwidth / Fheight, 0.01, 1.0);
    }

        // The main method of the engine that re-compute each vertex projection
    // during each frame
    public final void render(ArrayList<Mesh3D> meshes) {

	for (int i = 0; i < meshes.size(); i++) {
	    Mesh3D mesh = meshes.get(i);
	    // Beware to apply rotation before translation 
	    Matrix3D rotMat = Matrix3D.rotation(mesh.getRotation().X(), mesh.getRotation().Y(), mesh.getRotation().Z());
	    Matrix3D transMat = Matrix3D.translation(mesh.getPosition());
	    Matrix3D[] t;
	    t = new Matrix3D[]{rotMat, transMat};
	    Matrix3D worldMatrix = Matrix3D.multiply(t);
	    t = new Matrix3D[]{worldMatrix, FviewMatrix, FprojectionMatrix};
	    Matrix3D transformMatrix = Matrix3D.multiply(t);

	    for (int j = 0; j < mesh.getVertices().size(); j++) {
		Vector3D vertex = mesh.getVertices().get(j);
		// First, we project the 3D coordinates into the 2D space
		Vector2D point = project(vertex, transformMatrix);
		// Then we can draw on screen
		drawPoint(point);
	    }
	}

    }

    public final void present() {
	Fimg.setImage(FwImage);
    }

}
