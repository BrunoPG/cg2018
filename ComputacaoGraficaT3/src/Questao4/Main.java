/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao4;

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
    private double ax = 0.0;
    private double ay = 0.0;
    private double bx = 70.71067811865476;
    private double by = 70.71067811865476;
    private double angle = 45.0;
    private double thunder = 100.0;
    private double extraX = 0.0;
    private double extraY = 0.0;
    private double oax, oay, obx, oby = 0.0;
    private double rax, ray, rbx, rby = 0.0;

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

        // seu desenho ...
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(3.0f);
        gl.glPointSize(5.0f);//mudar tamanho ponto
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(ax, ay);
        gl.glVertex2d(bx, by);
        //System.out.println("bx="+bx+"by="+by);
        gl.glEnd();
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(ax, ay);
        gl.glVertex2d(bx, by);
        gl.glEnd();
        gl.glFlush();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                extraX = extraX - 10.0;
                ax = ax - 10.0;
                bx = bx - 10.0;
                glDrawable.display();
                break;
            case KeyEvent.VK_W:
                extraX = extraX + 10.0;
                ax = ax + 10.0;
                bx = bx + 10.0;
                glDrawable.display();
                break;
            case KeyEvent.VK_E:
                extraY = extraY - 10.0;
                ay = ay - 10.0;
                by = by - 10.0;
                glDrawable.display();
                break;
            case KeyEvent.VK_R:
                extraY = extraY + 10.0;
                ay = ay + 10.0;
                by = by + 10.0;
                glDrawable.display();
                break;
            case KeyEvent.VK_A:
                if (thunder > 10) {
                    thunder = thunder - 10.0;
                }
                bx = RetornaX(angle, thunder) + extraX;
                by = RetornaY(angle, thunder) + extraY;
                glDrawable.display();
                break;
            case KeyEvent.VK_S:
                thunder = thunder + 10.0;
                bx = RetornaX(angle, thunder) + extraX;
                by = RetornaY(angle, thunder) + extraY;
                glDrawable.display();
                break;
            case KeyEvent.VK_Z:
                angle = angle + 5.0;
                if (angle == 360.0) {
                    angle = 0.0;
                }
                bx = RetornaX(angle, thunder) + extraX;
                by = RetornaY(angle, thunder) + extraY;
                glDrawable.display();
                break;
            case KeyEvent.VK_X:
                angle = angle - 5.0;
                if (angle == 0.0) {
                    angle = 360.0;
                }
                bx = RetornaX(angle, thunder) + extraX;
                by = RetornaY(angle, thunder) + extraY;
                glDrawable.display();
                break;
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
//	    if ((e.getModifiers() & e.BUTTON1_MASK) != 0) {
        // System.out.println(" --- mousePressed ---");
        obx = e.getX();
        oby = e.getY();
        oax = e.getX();
        oay = e.getY();
        rbx = bx;
        rby = by;
        rax = ax;
        ray = ay;
//	    }
    }

    public void mouseReleased(MouseEvent e) {
        bx = rbx;
        by = rby;
        ax = rax;
        ay = ray;
        glDrawable.display();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println(" --- mouseDragged ---");
        double movtoX = e.getX() - obx;
        double movtoY = oby - e.getY();

        bx += movtoX;
        by += movtoY;
        movtoX = e.getX() - oax;
        movtoY = oay - e.getY();

        ax += movtoX;
        ay += movtoY;

        //Dump ...
        //System.out.println("posMouse: " + bx + " / " + by);
        obx = e.getX();
        oby = e.getY();
        oax = e.getX();
        oay = e.getY();

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
