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
    
    public final Vector3D getPosition(){
        return Fposition;
    }
    
    public final Vector3D getTarget(){
        return Ftarget;
    }

    public final void setPosition(Vector3D v) {
        Fposition=v;
    }
    
    public final void setTarget(Vector3D v) {
        Ftarget=v;
    }
    
}
