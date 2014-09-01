/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg3dengine;

/**
 *
 * @author rpetit
 */
public class Camera3D {
    private Vector3D Fposition;
    private Vector3D Ftarget;
    private Vector3D Fhead;
    
    
    public Camera3D(){
        // Head is up (set to 0,-1,0 to look upside-down)
	Fposition=Vector3D.zero();
	Ftarget=Vector3D.zero();
        Fhead=new Vector3D(0.0,-1.0,0.0);
    }
    
    public final Vector3D getPosition(){
        return Fposition;
    }
    
    public final Vector3D getTarget(){
        return Ftarget;
    }
    
    public final Vector3D getHead(){
        return Fhead;
    }

    public final void setPosition(Vector3D v) {
        Fposition=v;
    }
    
    public final void setTarget(Vector3D v) {
        Ftarget=v;
    }
    
}
