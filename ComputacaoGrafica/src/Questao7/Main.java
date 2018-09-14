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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

    private GL gl;
    private GLU glu;
    private GLAutoDrawable glDrawable;
    private Ponto4D p0 = new Ponto4D(100.0, 100.0, 0.0, 0.0);
    private Ponto4D pivo = new Ponto4D(0.0, 0.0, 0.0, 0.0);
    private Ponto4D spoint = new Ponto4D(0.0, 0.0, 0.0, 0.0);
    private double qtdPontos = 10.0;

    public void init(GLAutoDrawable drawable) {
        System.out.println(" --- init ---");
        glDrawable = drawable;
        gl = drawable.getGL();
        glu = new GLU();
        glDrawable.setGL(new DebugGL(gl));
        System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    //exibicaoPrincipal
    public void display(GLAutoDrawable arg0) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-400.0f, 400.0f, -400.0f, 400.0f);

        SRU();

        // circulo grande
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int x = 0; x < 360; x++) {
            gl.glVertex2d(RetornaX(x, 100.0) + 100, RetornaY(x, 100.0) + 100);
        }
        gl.glEnd();
        // ponto
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glPointSize(7.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(p0.obterX(), p0.obterY());
        gl.glEnd();
        // circulo pequeno
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int x = 0; x < 360; x++) {
            gl.glVertex2d(RetornaX(x, 10.0)+100, RetornaY(x, 10.0) + 100);
        }
        gl.glEnd();
        gl.glFlush();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:

                glDrawable.display();
                break;

        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
//        System.out.println("x=" + e.getX() + "y=" + e.getY());
//        System.out.println("x=" + e.getXOnScreen() + "y=" + e.getYOnScreen());
        pivo.atribuirX(e.getX());
        pivo.atribuirY(e.getY());
        //antigoX = e.getX();
        //antigoY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println(" --- mouseDragged ---");

        double movtoX = e.getX() - pivo.obterX();
        double movtoY = pivo.obterY() - e.getY();
        p0.atribuirX(p0.obterX() + movtoX);
        p0.atribuirY(p0.obterY() + movtoY);

        pivo.atribuirX(e.getX());
        pivo.atribuirY(e.getY());

        glDrawable.display();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println(" --- reshape ---");
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);
    }

    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
        System.out.println(" --- displayChanged ---");
    }

    public void keyReleased(KeyEvent arg0) {
        System.out.println(" --- keyReleased ---");
    }

    public void keyTyped(KeyEvent arg0) {
        System.out.println(" --- keyTyped ---");
    }

    public void SRU() {
//		gl.glDisable(gl.GL_TEXTURE_2D);
//		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and texture is enabled

        // eixo x
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glLineWidth(1.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-200.0f, 0.0f);
        gl.glVertex2f(200.0f, 0.0f);
        gl.glEnd();
        // eixo y
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(0.0f, -200.0f);
        gl.glVertex2f(0.0f, 200.0f);
        gl.glEnd();
    }

    public double RetornaX(double angulo, double raio) {
        return (raio * Math.cos(Math.PI * angulo / 180.0));
    }

    public double RetornaY(double angulo, double raio) {
        return (raio * Math.sin(Math.PI * angulo / 180.0));
    }

}
