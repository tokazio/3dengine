/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author rpetit
 */
public class Device3D {
    
    //x,y
    private final RGBA[][] FbackBuffer;
    private double Fwidth;
    private double Fheight;
    private ImageView Fimg;
    
    public Device3D(double width,double height){
        Fwidth=width;
        Fheight=height;
        // the back buffer size is equal to the number of pixels to draw
        // on screen (width*height) * 4 (R,G,B & Alpha values). 
        FbackBuffer=new RGBA[(int)width][(int)height];
    }
    
    public Device3D(ImageView i){
        Fimg=i;
        Fwidth=i.getFitWidth();
        Fheight=i.getFitHeight();
        // the back buffer size is equal to the number of pixels to draw
        // on screen (width*height) * 4 (R,G,B & Alpha values). 
        FbackBuffer=new RGBA[(int)Fwidth][(int)Fheight];
    }
   
    // This method is called to clear the back buffer with a specific color
    public final void clear(int r, int g, int b, int a){
        for (int x = 0; x < Fwidth; x++){
            for (int y = 0; y < Fheight; y++){
                FbackBuffer[x][y] = new RGBA(r,g,b,a);
            }
        }
    }
    
    // Called to put a pixel on screen at a specific X,Y coordinates
    public final void putPixel(double x, double y, RGBA color) {
        FbackBuffer[(int)x][(int)y] = color;
    }
    
    // Project takes some 3D coordinates and transform them
        // in 2D coordinates using the transformation matrix
        public final Vector2D project(Vector3D coord, Matrix3D transMat)
        {
            // transforming the coordinates
            Vector2D point = coord.transformCoordinate(transMat);
            // The transformed coordinates will be based on coordinate system
            // starting on the center of the screen. But drawing on screen normally starts
            // from top left. We then need to transform them again to have x:0, y:0 on top left.
            double x = point.X() * Fwidth + Fwidth / 2.0;
            double y = -point.Y() * Fheight + Fheight / 2.0;
            return (new Vector2D(x, y));
        }
        
        // DrawPoint calls PutPixel but does the clipping operation before
        public final void drawPoint(Vector2D point)
        {
            // Clipping what's visible on screen
            if (point.X() >= 0 && point.Y() >= 0 && point.X() < Fwidth && point.Y() < Fheight)
            {
                // Drawing a yellow point
                putPixel(point.X(), point.Y(), new RGBA(255, 255, 255, 1));
            }
        }
        
        // The main method of the engine that re-compute each vertex projection
        // during each frame
        public final void render(Camera3D camera, ArrayList<Mesh3D> meshes)
        {
            // To understand this part, please read the prerequisites resources
            Matrix3D viewMatrix = Matrix3D.lookAtRH(camera.getPosition(), camera.getTarget(), camera.getHead());
            Matrix3D projectionMatrix = Matrix3D.perspectiveFovRH(45,Fwidth / Fheight,0.01, 1.0);

            for(int i=0;i<meshes.size();i++){
                Mesh3D mesh=meshes.get(i);
                // Beware to apply rotation before translation 
                Matrix3D rotMat=Matrix3D.rotation(mesh.getRotation().X(),mesh.getRotation().Y(), mesh.getRotation().Z());
                Matrix3D transMat=Matrix3D.translation(mesh.getPosition());
                Matrix3D[] t;
                t=new Matrix3D[]{rotMat,transMat};
                Matrix3D worldMatrix = Matrix3D.multiply(t);
                t=new Matrix3D[]{worldMatrix ,viewMatrix , projectionMatrix};
                Matrix3D transformMatrix = Matrix3D.multiply(t);

                for(int j=0;j<mesh.getVertices().size();j++)
                {
                    Vector3D vertex=mesh.getVertices().get(j);
                    // First, we project the 3D coordinates into the 2D space
                    Vector2D point = project(vertex, transformMatrix);
                    // Then we can draw on screen
                    drawPoint(point);
                }
            }
            
        }

        public final void present(){
            WritableImage wImage = new WritableImage((int)Fwidth, (int)Fheight);
            PixelWriter pixelWriter = wImage.getPixelWriter();
            
            // Determine the color of each pixel in a specified row
            for(int x=0; x<Fwidth;x++){
                for(int y=0;y<Fheight;y++){
                    pixelWriter.setColor(x,y,FbackBuffer[x][y].toColor());
                }
            }
            Fimg.setImage(wImage);
        }
            
        
}
