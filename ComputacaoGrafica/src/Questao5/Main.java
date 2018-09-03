/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao5;

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

public class Main implements GLEventListener, KeyListener {

    private GL gl;
    private GLU glu;
    private GLAutoDrawable glDrawable;
    private int pg = GL.GL_POINTS;

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
        //codigo acima controla a camera(esquerda, direita, cima, baixo, zoom in, zoom out)
        SRU();
        gl.glPointSize(5.0f);//mudar tamanho ponto
        gl.glLineWidth(5.0f);
        gl.glBegin(pg);
        //vermelho
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2d(200.0f, -200.0f);
        //verde
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2d(200.0f, 200.0f);
        //azul
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2d(-200.0f, 200.0f);
        //rosa
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glVertex2d(-200.0f, -200.0f);
        gl.glEnd();
        gl.glFlush();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(" --- keyPressed ---");
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            switch (pg) {
                case GL.GL_POINTS:
                    pg = GL.GL_LINES;
                    glDrawable.display();
                    break;

                case GL.GL_LINES:
                    pg = GL.GL_LINE_LOOP;
                    glDrawable.display();
                    break;

                case GL.GL_LINE_LOOP:
                    pg = GL.GL_LINE_STRIP;
                    glDrawable.display();
                    break;

                case GL.GL_LINE_STRIP:
                    pg = GL.GL_TRIANGLES;
                    glDrawable.display();
                    break;

                case GL.GL_TRIANGLES:
                    pg = GL.GL_TRIANGLE_FAN;
                    glDrawable.display();
                    break;

                case GL.GL_TRIANGLE_FAN:
                    pg = GL.GL_TRIANGLE_STRIP;
                    glDrawable.display();
                    break;

                case GL.GL_TRIANGLE_STRIP:
                    pg = GL.GL_QUADS;
                    glDrawable.display();
                    break;

                case GL.GL_QUADS:
                    pg = GL.GL_QUAD_STRIP;
                    glDrawable.display();
                    break;

                case GL.GL_QUAD_STRIP:
                    pg = GL.GL_POLYGON;
                    glDrawable.display();
                    break;

                case GL.GL_POLYGON:
                    pg = GL.GL_POINTS;
                    glDrawable.display();
                    break;
            }
        }
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
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-200.0f, 0.0f);
        gl.glVertex2f(200.0f, 0.0f);
        gl.glEnd();
        // eixo y
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(0.0f, -200.0f);
        gl.glVertex2f(0.0f, 200.0f);
        gl.glEnd();
    }

}
