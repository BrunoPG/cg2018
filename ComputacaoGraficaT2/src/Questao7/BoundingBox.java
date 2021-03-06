/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao7;

/**
 *
 * @author Bruno Gibicoski
 */
import javax.media.opengl.GL;

public final class BoundingBox {

    private double menorX;
    private double menorY;
    private double menorZ;
    private double maiorX;
    private double maiorY;
    private double maiorZ;
    private Ponto4D centro = new Ponto4D();
//    private Color color;

    public BoundingBox() {
        this(0, 0, 0, 0, 0, 0);
    }

    public BoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
        this.menorX = smallerX;
        this.menorY = smallerY;
        this.menorZ = smallerZ;
        this.maiorX = greaterX;
        this.maiorY = greaterY;
        this.maiorZ = greaterZ;
       // System.out.println("maiorx=" + maiorX + ", menorx=" + menorX + ", maiory=" + maiorY + ", menory=" + menorY);
    }

    public void atribuirBoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
        this.menorX = smallerX;
        this.menorY = smallerY;
        this.menorZ = smallerZ;
        this.maiorX = greaterX;
        this.maiorY = greaterY;
        this.maiorZ = greaterZ;
        processarCentroBBox();
    }

    public void atualizarBBox(Ponto4D point) {
        atualizarBBox(point.obterX(), point.obterY(), point.obterZ());
    }

    public void atualizarBBox(double x, double y, double z) {
        if (x < menorX) {
            menorX = x;
        } else if (x > maiorX) {
            maiorX = x;
        }
        if (y < menorY) {
            menorY = y;
        } else if (y > maiorY) {
            maiorY = y;
        }
        if (z < menorZ) {
            menorZ = z;
        } else if (z > maiorZ) {
            maiorZ = z;
        }
    }

    public void processarCentroBBox() {
        centro.atribuirX((maiorX + menorX) / 2);
        centro.atribuirY((maiorY + menorY) / 2);
        centro.atribuirZ((maiorZ + menorZ) / 2);
    }

    public void desenharOpenGLBBox(GL gl) {
        //gl.glColor3f(1.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3d(menorX, maiorY, menorZ);
        gl.glVertex3d(maiorX, maiorY, menorZ);
        gl.glVertex3d(maiorX, menorY, menorZ);
        gl.glVertex3d(menorX, menorY, menorZ);
        gl.glEnd();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3d(menorX, menorY, menorZ);
        gl.glVertex3d(menorX, menorY, maiorZ);
        gl.glVertex3d(menorX, maiorY, maiorZ);
        gl.glVertex3d(menorX, maiorY, menorZ);
        gl.glEnd();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3d(maiorX, maiorY, maiorZ);
        gl.glVertex3d(menorX, maiorY, maiorZ);
        gl.glVertex3d(menorX, menorY, maiorZ);
        gl.glVertex3d(maiorX, menorY, maiorZ);
        gl.glEnd();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3d(maiorX, menorY, menorZ);
        gl.glVertex3d(maiorX, maiorY, menorZ);
        gl.glVertex3d(maiorX, maiorY, maiorZ);
        gl.glVertex3d(maiorX, menorY, maiorZ);
        gl.glEnd();
    }

    /// Obter menor valor X da BBox.
    public double obterMenorX() {
        return menorX;
    }

    /// Obter menor valor Y da BBox.
    public double obterMenorY() {
        return menorY;
    }

    /// Obter menor valor Z da BBox.
    public double obterMenorZ() {
        return menorZ;
    }

    /// Obter maior valor X da BBox.
    public double obterMaiorX() {
        return maiorX;
    }

    /// Obter maior valor Y da BBox.
    public double obterMaiorY() {
        return maiorY;
    }

    /// Obter maior valor Z da BBox.
    public double obterMaiorZ() {
        return maiorZ;
    }

    /// Obter ponto do centro da BBox.
    public Ponto4D obterCentro() {
        return centro;
    }

    public boolean isIn(double x, double y) {
        return !(x > maiorX || y > maiorY || x < menorX || y < menorY);
    }
}
