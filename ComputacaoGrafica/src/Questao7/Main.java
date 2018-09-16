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
    private Ponto4D pi = new Ponto4D(100.0, 100.0, 0.0, 0.0);
    private Ponto4D pivo = new Ponto4D(0.0, 0.0, 0.0, 0.0);
    private BoundingBox bBox;
    private boolean limit = false;

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
        // quadrado vermelho
        bBox = new BoundingBox(RetornaX(225, 100.0) + 100, RetornaY(225, 100.0) + 100, 0.0, RetornaX(45, 100.0) + 100, RetornaY(45, 100.0) + 100, 0.0);
        // circulo grande
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int x = 0; x < 360; x++) {
            gl.glVertex2d(RetornaX(x, 100.0) + 100, RetornaY(x, 100.0) + 100);
        }
        gl.glEnd();
        // ponto
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glPointSize(3.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(p0.obterX(), p0.obterY());
        gl.glEnd();
        // circulo pequeno
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int x = 0; x < 360; x++) {
            gl.glVertex2d(RetornaX(x, 30.0) + p0.obterX(), RetornaY(x, 30.0) + p0.obterY());
        }
        gl.glEnd();
        //System.out.println("x=" + p0.obterX() + " ,y=" + p0.obterY());
        //System.out.println(bBox.isIn(p0.obterX(), p0.obterY()));
        //quadrado
        if (limit) {
            gl.glColor3f(1.0f, 0.0f, 1.0f);
        } else if (bBox.isIn(p0.obterX(), p0.obterY())) {
            gl.glColor3f(1.0f, 0.0f, 0.0f);
        } else {
            gl.glColor3f(0.0f, 1.0f, 1.0f);
        }
        bBox.desenharOpenGLBBox(gl);
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
        p0.atribuirX(pi.obterX());
        p0.atribuirY(pi.obterY());
        limit = false;
        glDrawable.display();
    }

    public void mouseClicked(MouseEvent e) {
    }
    private double px;
    private double py;
    private double lx;
    private double ly;

    public void mouseDragged(MouseEvent e) {
        //System.out.println(" --- mouseDragged ---");

        double movtoX = e.getX() - pivo.obterX();
        double movtoY = pivo.obterY() - e.getY();
        p0.atribuirX(p0.obterX() + movtoX);
        p0.atribuirY(p0.obterY() + movtoY);

        pivo.atribuirX(e.getX());
        pivo.atribuirY(e.getY());
        px = p0.obterX();
        py = p0.obterY();

        for (int x = 0; x < 360; x++) {
            //if ((p0.obterX() == (RetornaX(x, 100.0) + 100)) || (p0.obterY() == (RetornaY(x, 100.0) + 100))) {
            lx = RetornaX(x, 100.0) + 100;
            ly = RetornaY(x, 100.0) + 100;
System.out.println("mouse x,y="+px+","+py);
            if (px == lx || py == ly) {
                limit = true;
            }
        }
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
