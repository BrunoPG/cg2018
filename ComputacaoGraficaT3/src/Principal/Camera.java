/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author leona
 */
public class Camera {
    
    private float XMax;
    private float XMin;
    private float YMax;
    private float YMin;   
    private int zoom;
    
    public Camera(){
    XMax=-400.0f;
    XMin=400.0f;
    YMax=-400.0f;
    YMin=400.0f;
    zoom=0;      
    }
  
       public void moveCamera(int commando) {
        switch (commando) {
            case 1:
                XMax -= 5.0f;
                XMin -= 5.0f;
                break;
            case 2:
                XMax += 5.0f;
                XMin += 5.0f;
                break;
            case 3:
                YMax += 5.0f;
                YMin += 5.0f;
                break;
            case 4:
                YMax -= 5.0f;
                YMin -= 5.0f;
                break;
            case 5:

                if (zoom<50) {
                    XMax += 5.0f;
                    XMin -= 5.0f;
                    YMax += 5.0f;
                    YMin -= 5.0f;
                    zoom++;
                }
                break;
            case 6:
                if (zoom>-50) {
                    XMax -= 5.0f;
                    XMin += 5.0f;
                    YMax -= 5.0f;
                    YMin += 5.0f;
                    zoom--;
                }
                break;
        }
    }

    public float getXMax() {
        return XMax;
    }

    public float getXMin() {
        return XMin;
    }

    public float getYMax() {
        return YMax;
    }

    public float getYMin() {
        return YMin;
    }
    
}
