/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

import java.util.ArrayList;

/**
 *
 * @author rpetit
 */
public class Device3D {
    
    private final int[] FbackBuffer;
    private double Fwidth;
    private double Fheight;
    
    public Device3D(double width,double height){
        Fwidth=width;
        Fheight=height;
        // the back buffer size is equal to the number of pixels to draw
        // on screen (width*height) * 4 (R,G,B & Alpha values). 
        FbackBuffer=new int[(int)(width * height * 4)];
    }
   
    // This method is called to clear the back buffer with a specific color
    public final void clear(int r, int g, int b, int a){
        for (int index = 0; index < FbackBuffer.length; index += 4){
                // BGRA is used by Windows instead by RGBA in HTML5
                FbackBuffer[index] = b;
                FbackBuffer[index + 1] = g;
                FbackBuffer[index + 2] = r;
                FbackBuffer[index + 3] = a;
            }
    }
    
    // Called to put a pixel on screen at a specific X,Y coordinates
    public final void putPixel(double x, double y, RGBA color) {
        // As we have a 1-D Array for our back buffer
            // we need to know the equivalent cell in 1-D based
            // on the 2D coordinates on screen
            int index = (int)((x + y * Fwidth) * 4);

            FbackBuffer[index] = (byte)(color.Blue * 255);
            FbackBuffer[index + 1] = (byte)(color.Green * 255);
            FbackBuffer[index + 2] = (byte)(color.Red * 255);
            FbackBuffer[index + 3] = (byte)(color.Alpha * 255);
    }
    
    // Project takes some 3D coordinates and transform them
        // in 2D coordinates using the transformation matrix
        public final Vector2D project(Vector3D coord, Matrix3D transMat)
        {
            // transforming the coordinates
            Vector2D point = Vector3D.TransformCoordinate(coord, transMat);
            // The transformed coordinates will be based on coordinate system
            // starting on the center of the screen. But drawing on screen normally starts
            // from top left. We then need to transform them again to have x:0, y:0 on top left.
            double x = point.X() * Fwidth + Fwidth / 2.0f;
            double y = -point.Y() * Fheight + Fheight / 2.0f;
            return (new Vector2D(x, y));
        }
        
        // DrawPoint calls PutPixel but does the clipping operation before
        public final void drawPoint(Vector2D point)
        {
            // Clipping what's visible on screen
            if (point.X() >= 0 && point.Y() >= 0 && point.X() < Fwidth && point.Y() < Fheight)
            {
                // Drawing a yellow point
                putPixel(point.X(), point.Y(), new RGBA(1.0f, 1.0f, 0.0f, 1.0f));
            }
        }
        
        // The main method of the engine that re-compute each vertex projection
        // during each frame
        public final void render(Camera3D camera, ArrayList<Mesh3D> meshes)
        {
            // To understand this part, please read the prerequisites resources
            Matrix3D viewMatrix = Matrix3D.LookAtLH(camera.getPosition(), camera.getTarget(), Vector3D.UnitY);
            Matrix3D projectionMatrix = Matrix3D.PerspectiveFovRH(0.78f, 
                                                           Fwidth / Fheight, 
                                                           0.01f, 1.0f);

            for(int i=0;i<meshes.length;i++){
                Mesh3D mesh=meshes[i];
                // Beware to apply rotation before translation 
                Matrix3D worldMatrix = Matrix3D.RotationYawPitchRoll(mesh.getRotation().Y()), 
                                                              mesh.getRotation().X(), mesh.getRotation().Z()) * 
                                  Matrix3D.Translation(mesh.Position);

                Matrix3D transformMatrix = worldMatrix * viewMatrix * projectionMatrix;

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
            
        }
        
}
