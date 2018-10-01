/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao6;

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
    private Ponto4D p0 = new Ponto4D(-100.0, -100.0, 0.0, 0.0);
    private Ponto4D p1 = new Ponto4D(-100.0, 100.0, 0.0, 0.0);
    private Ponto4D p2 = new Ponto4D(100.0, 100.0, 0.0, 0.0);
    private Ponto4D p3 = new Ponto4D(100.0, -100.0, 0.0, 0.0);
    private Ponto4D pp = p0;
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

        // linha azul
        gl.glColor3f(0.0f, 1.0f, 1.0f);
        gl.glLineWidth(3.0f);
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex2d(p0.obterX(), p0.obterY());
        gl.glVertex2d(p1.obterX(), p1.obterY());
        gl.glVertex2d(p2.obterX(), p2.obterY());
        gl.glVertex2d(p3.obterX(), p3.obterY());
        gl.glEnd();
        // linha amarela
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_LINE_STRIP);
        //System.out.println(qtdPontos);
        double pace = 1 / qtdPontos;
        System.out.println("pace" + pace);
        for (double t = 0.0; t <= 1.01; t = t + pace) {
            System.out.println("t:" + t);
            gl.glVertex2d(splines(t).obterX(), splines(t).obterY());
        }
        gl.glEnd();
        // ponto vermelho
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glPointSize(7.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(pp.obterX(), pp.obterY());
        gl.glEnd();
        gl.glFlush();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                pp = p0;
                glDrawable.display();
                break;
            case KeyEvent.VK_2:
                pp = p1;
                glDrawable.display();
                break;
            case KeyEvent.VK_3:
                pp = p2;
                glDrawable.display();
                break;
            case KeyEvent.VK_4:
                pp = p3;
                glDrawable.display();
                break;
            case KeyEvent.VK_Z:
                if (qtdPontos > 1.0) {
                    qtdPontos--;
                }
                glDrawable.display();
                break;
            case KeyEvent.VK_X:
                if (qtdPontos < 10.0) {
                    qtdPontos++;
                }
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
        pp.atribuirX(pp.obterX() + movtoX);
        pp.atribuirY(pp.obterY() + movtoY);

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

    public Ponto4D splines(double t) {
        //Math.pow(valor,expoente)
        spoint.atribuirX((Math.pow(1.0 - t, 3.0) * p0.obterX())
                + (3.0 * t * Math.pow(1.0 - t, 2.0) * p1.obterX())
                + (3 * Math.pow(t, 2.0) * (1.0 - t) * p2.obterX())
                + (Math.pow(t, 3.0) * p3.obterX()));
        spoint.atribuirY((Math.pow(1.0 - t, 3.0) * p0.obterY())
                + (3.0 * t * Math.pow(1.0 - t, 2.0) * p1.obterY())
                + (3 * Math.pow(t, 2.0) * (1.0 - t) * p2.obterY())
                + (Math.pow(t, 3.0) * p3.obterY()));
        return spoint;
    }

}
