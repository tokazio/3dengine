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

    public Device3D(double width, double height, Camera3D camera) {
	Fwidth = width;
	Fheight = height;
	init(camera);
    }

    public Device3D(ImageView i, Camera3D camera) {
	Fimg = i;
	Fwidth = i.getFitWidth();
	Fheight = i.getFitHeight();

	init(camera);
    }

    // This method is called to clear the back buffer with a specific color
    public final void clear(int r, int g, int b, int a) {
	for (int x = 0; x < Fwidth; x++) {
	    for (int y = 0; y < Fheight; y++) {
		FpixelWriter.setColor(x, y, new RGBA(r, g, b, a).toColor());
	    }
	}
	/*
	// Clearing Depth Buffer
	for (int index = 0; index < depthBuffer.Length; index++) {
	    depthBuffer.put(index) = Double.MAX_VALUE;
	}
	*/
    }

    // Called to put a pixel on screen at a specific X,Y coordinates
    public final void putPixel(double x, double y, RGBA color) {
	FpixelWriter.setColor((int) x, (int) y, color.toColor());
    }

    public final void drawLine(Vector2D point0, Vector2D point1) {
	double dist = Vector2D.sub(point1, point0).length();
	// If the distance between the 2 points is less than 2 pixels
	// We're exiting
	if (dist < 2) {
	    return;
	}
	// Find the middle point between first & second point
	Vector2D middlePoint = Vector2D.add(point0, Vector2D.sub(point1, point0).mul(0.5));
	// We draw this point on screen
	drawPoint(middlePoint);
	// Recursive algorithm launched between first & middle point
	// and between middle & second point
	drawLine(point0, middlePoint);
	drawLine(middlePoint, point1);
    }

    public final void drawBLine(Vector2D point0, Vector2D point1) {
	double w = point1.X() - point0.X();
	double h = point1.Y() - point0.Y();
	int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
	if (w < 0) {
	    dx1 = -1;
	} else if (w > 0) {
	    dx1 = 1;
	}
	if (h < 0) {
	    dy1 = -1;
	} else if (h > 0) {
	    dy1 = 1;
	}
	if (w < 0) {
	    dx2 = -1;
	} else if (w > 0) {
	    dx2 = 1;
	}
	double longest = Math.abs(w);
	double shortest = Math.abs(h);
	double x = point0.X();
	double y = point0.Y();
	if (!(longest > shortest)) {
	    longest = Math.abs(h);
	    shortest = Math.abs(w);
	    if (h < 0) {
		dy2 = -1;
	    } else if (h > 0) {
		dy2 = 1;
	    }
	    dx2 = 0;
	}
	double numerator = bitRightShift(longest, 1.0);
	for (int i = 0; i <= longest; i++) {
	    drawPoint(new Vector2D(x, y));
	    numerator += shortest;
	    if (!(numerator < longest)) {
		numerator -= longest;
		x += dx1;
		y += dy1;
	    } else {
		x += dx2;
		y += dy2;
	    }
	}
    }

    private static double bitRightShift(double a, double b) {
	return Double.longBitsToDouble(Double.doubleToRawLongBits(a) >> Double.doubleToRawLongBits(b));
    }

    // Project takes some 3D coordinates and transform them
    // in 2D coordinates using the transformation matrix
    public final Vector3D project(Vector3D coord, Matrix3D transMat) {
	// transforming the coordinates
	Vector3D point = coord.transformCoordinate(transMat);
	// The transformed coordinates will be based on coordinate system
	// starting on the center of the screen. But drawing on screen normally starts
	// from top left. We then need to transform them again to have x:0, y:0 on top left.
	double x = point.X() * Fwidth + Fwidth / 2.0;
	double y = -point.Y() * Fheight + Fheight / 2.0;
	return (new Vector3D(x, y, coord.Z()));
    }

    // DrawPoint calls PutPixel but does the clipping operation before
    public final void drawPoint(Vector2D point) {
	// Clipping what's visible on screen
	if (point.X() >= 0 && point.Y() >= 0 && point.X() < Fwidth && point.Y() < Fheight) {
	    putPixel(point.X(), point.Y(), new RGBA(255, 255, 255, 1));
	}
    }

    public final void init(Camera3D camera) {
	FwImage = new WritableImage((int) Fwidth, (int) Fheight);
	FpixelWriter = FwImage.getPixelWriter();
	FviewMatrix = Matrix3D.lookAtLH(camera.getPosition(), camera.getTarget(), camera.getHead());
	FprojectionMatrix = Matrix3D.perspectiveFovLH(45.0, Fwidth / Fheight, 0.01, 100.0);
    }

    // The main method of the engine that re-compute each vertex projection
    // during each frame
    public final void render(ArrayList<Mesh3D> meshes) {

	for (int i = 0; i < meshes.size(); i++) {
	    Mesh3D mesh = meshes.get(i);
	    // Beware to apply rotation before translation 
	    Matrix3D rotMat = Matrix3D.rotation(mesh.getRotation());//.X(), mesh.getRotation().Y(), mesh.getRotation().Z());
	    Matrix3D transMat = Matrix3D.translation(mesh.getPosition());
	    Matrix3D[] t;
	    t = new Matrix3D[]{rotMat, transMat};
	    Matrix3D worldMatrix = Matrix3D.multiply(t);
	    t = new Matrix3D[]{worldMatrix, FviewMatrix, FprojectionMatrix};
	    Matrix3D transformMatrix = Matrix3D.multiply(t);

	    /*
	     //mode points
	     for (int j = 0; j < mesh.getVertices().size(); j++) {
	     Vector3D vertex = mesh.getVertices().get(j);
	     // First, we project the 3D coordinates into the 2D space
	     Vector2D point = project(vertex, transformMatrix);
	     // Then we can draw on screen
	     drawPoint(point);
	     }
	     */
	    /*
	     //mode line
	     Vector3D vertex;
	     for (int j = 0; j < mesh.getVertices().size() - 1; j++) {
	     vertex = mesh.getVertices().get(j);

	     Vector2D point0 = project(vertex, transformMatrix);
	     vertex = mesh.getVertices().get(j + 1);
	     Vector2D point1 = project(vertex, transformMatrix);
	     drawBLine(point0, point1);
	     }
	     */
	    /*
	     //mode face
	     for (int indexFaces = 0; indexFaces < mesh.getVertices().size(); indexFaces++) {
	     Face3D currentFace = mesh.getFaces().get(indexFaces);
	     Vector3D vertexA = mesh.getVertices().get(currentFace.A());
	     Vector3D vertexB = mesh.getVertices().get(currentFace.B());
	     Vector3D vertexC = mesh.getVertices().get(currentFace.C());
	     Vector2D pixelA = project(vertexA, transformMatrix);
	     Vector2D pixelB = project(vertexB, transformMatrix);
	     Vector2D pixelC = project(vertexC, transformMatrix);
	     drawLine(pixelA, pixelB);
	     drawLine(pixelB, pixelC);
	     drawLine(pixelC, pixelA);
	     }
	     */
	    //mode shadow 1
	    int faceIndex = 0;
	    for (int j = 0; j < mesh.getFaces().size(); j++) {
		Face3D face = mesh.getFaces().get(j);
		Vector3D vertexA = mesh.getVertices().get(face.A());
		Vector3D vertexB = mesh.getVertices().get(face.B());
		Vector3D vertexC = mesh.getVertices().get(face.C());

		Vector3D pixelA = project(vertexA, transformMatrix);
		Vector3D pixelB = project(vertexB, transformMatrix);
		Vector3D pixelC = project(vertexC, transformMatrix);

		//var color = 0.25 + ((indexFaces % cMesh.Faces.length) / cMesh.Faces.length) * 0.75;
		double color = 0.25 + (faceIndex % mesh.getFaces().size()) / mesh.getFaces().size() * 0.75;
		drawTriangle(pixelA, pixelB, pixelC, new RGBA(color * 255, color * 255, color * 255, 1));
		faceIndex++;
	    }
	}
    }

    public final void present() {
	Fimg.setImage(FwImage);
    }

    public static double clamp(double value, double min, double max) {
	return Math.max(min, Math.min(value, max));
    }

    // Interpolating the value between 2 vertices 
    // min is the starting point, max the ending point
    // and gradient the % between the 2 points
    public static double Interpolate(double min, double max, double gradient) {
	return min + (max - min) * clamp(gradient, 0, 1);
    }

    // drawing line between 2 points from left to right
// papb -> pcpd
// pa, pb, pc, pd must then be sorted before
    public final void processScanLine(int y, Vector3D pa, Vector3D pb, Vector3D pc, Vector3D pd, RGBA color) {
	// Thanks to current Y, we can compute the gradient to compute others values like
	// the starting X (sx) and ending X (ex) to draw between
	// if pa.Y == pb.Y or pc.Y == pd.Y, gradient is forced to 1
	double gradient1 = pa.Y() != pb.Y() ? (y - pa.Y()) / (pb.Y() - pa.Y()) : 1;
	double gradient2 = pc.Y() != pd.Y() ? (y - pc.Y()) / (pd.Y() - pc.Y()) : 1;

	int sx = (int) Interpolate(pa.X(), pb.X(), gradient1);
	int ex = (int) Interpolate(pc.X(), pd.X(), gradient2);

	// drawing a line from left (sx) to right (ex) 
	for (int x = sx; x < ex; x++) {
	    drawPoint(new Vector2D(x, y, color));
	}
    }

    public void drawTriangle(Vector3D p1, Vector3D p2, Vector3D p3, RGBA color) {
	// Sorting the points in order to always have this order on screen p1, p2 & p3
	// with p1 always up (thus having the Y the lowest possible to be near the top screen)
	// then p2 between p1 & p3
	if (p1.Y() > p2.Y()) {
	    Vector3D temp = p2;
	    p2 = p1;
	    p1 = temp;
	}

	if (p2.Y() > p3.Y()) {
	    Vector3D temp = p2;
	    p2 = p3;
	    p3 = temp;
	}

	if (p1.Y() > p2.Y()) {
	    Vector3D temp = p2;
	    p2 = p1;
	    p1 = temp;
	}

	// inverse slopes
	double dP1P2, dP1P3;

	// http://en.wikipedia.org/wiki/Slope
	// Computing inverse slopes
	if (p2.Y() - p1.Y() > 0) {
	    dP1P2 = (p2.X() - p1.X()) / (p2.Y() - p1.Y());
	} else {
	    dP1P2 = 0;
	}

	if (p3.Y() - p1.Y() > 0) {
	    dP1P3 = (p3.X() - p1.X()) / (p3.Y() - p1.Y());
	} else {
	    dP1P3 = 0;
	}

	// First case where triangles are like that:
	// P1
	// -
	// -- 
	// - -
	// -  -
	// -   - P2
	// -  -
	// - -
	// -
	// P3
	if (dP1P2 > dP1P3) {
	    for (int y = (int) p1.Y(); y <= (int) p3.Y(); y++) {
		if (y < p2.Y()) {
		    processScanLine(y, p1, p3, p1, p2, color);
		} else {
		    processScanLine(y, p1, p3, p2, p3, color);
		}
	    }
	} // First case where triangles are like that:
	//       P1
	//        -
	//       -- 
	//      - -
	//     -  -
	// P2 -   - 
	//     -  -
	//      - -
	//        -
	//       P3
	else {
	    for (int y = (int) p1.Y(); y <= (int) p3.Y(); y++) {
		if (y < p2.Y()) {
		    processScanLine(y, p1, p2, p1, p3, color);
		} else {
		    processScanLine(y, p2, p3, p1, p3, color);
		}
	    }
	}
    }

}
