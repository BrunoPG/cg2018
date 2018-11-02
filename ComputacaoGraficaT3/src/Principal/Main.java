package Principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

    private GL gl;
    private GLU glu;
    private GLAutoDrawable glDrawable;
    private int focus = 0;
    private Camera c = new Camera();

    private enum Mode {
        CREATE, ADD, PARENT, MOVE
    };
    private Mode mode = Mode.CREATE;
    //private ObjetoGrafico[] objetos
    //private ObjetoGrafico objeto = new ObjetoGrafico();
    //private ObjetoGrafico[] objetos = {new ObjetoGrafico(), new ObjetoGrafico()};
    ArrayList<ObjetoGrafico> objetos = new ArrayList<ObjetoGrafico>();
//private ObjetoGrafico[] objetos;

    // "render" feito logo apos a inicializacao do contexto OpenGL.
    public void init(GLAutoDrawable drawable) {
        glDrawable = drawable;
        gl = drawable.getGL();
        glu = new GLU();
        glDrawable.setGL(new DebugGL(gl));
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mode = Mode.CREATE;

    }

    // metodo definido na interface GLEventListener.
    // "render" feito pelo cliente OpenGL.
    public void display(GLAutoDrawable arg0) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        glu.gluOrtho2D(c.getXMin(), c.getXMax(), c.getYMin(), c.getYMax());

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glLineWidth(1.0f);
        gl.glPointSize(1.0f);

        desenhaSRU();
        for (byte i = 0; i < objetos.size(); i++) {
            objetos.get(i).desenha();
        }

//		objeto.desenha();
        gl.glFlush();
    }

    public void desenhaSRU() {
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-20.0f, 0.0f);
        gl.glVertex2f(20.0f, 0.0f);
        gl.glEnd();
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(0.0f, -20.0f);
        gl.glVertex2f(0.0f, 20.0f);
        gl.glEnd();
    }

    public void keyPressed(KeyEvent e) {
        if (objetos.size() > 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    objetos.get(focus).exibeVertices();
                    break;
                case KeyEvent.VK_M:
                    objetos.get(focus).exibeMatriz();
                    break;

                case KeyEvent.VK_R:
                    objetos.get(focus).atribuirIdentidade();
                    break;

                case KeyEvent.VK_RIGHT:
                    objetos.get(focus).translacaoXYZ(2.0, 0.0, 0.0);
                    break;
                case KeyEvent.VK_LEFT:
                    objetos.get(focus).translacaoXYZ(-2.0, 0.0, 0.0);
                    break;
                case KeyEvent.VK_UP:
                    objetos.get(focus).translacaoXYZ(0.0, -2.0, 0.0);
                    break;
                case KeyEvent.VK_DOWN:
                    objetos.get(focus).translacaoXYZ(0.0, 2.0, 0.0);
                    break;

                case KeyEvent.VK_PAGE_UP:
                    objetos.get(focus).escalaXYZ(2.0, 2.0);
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    objetos.get(focus).escalaXYZ(0.5, 0.5);
                    break;

                //case KeyEvent.VK_J:
                //objetos.get(focus).rotacaoZ(0);
                //break;
                case KeyEvent.VK_1:
                    objetos.get(focus).escalaXYZPtoFixo(0.5, objetos.get(focus).bBox.obterCentro().inverterSinal(objetos.get(focus).bBox.obterCentro()));
                    break;

                case KeyEvent.VK_2:
                    objetos.get(focus).escalaXYZPtoFixo(2.0, objetos.get(focus).bBox.obterCentro().inverterSinal(objetos.get(focus).bBox.obterCentro()));
                    break;

                case KeyEvent.VK_3:
                    objetos.get(focus).rotacaoZPtoFixo(10.0, objetos.get(focus).bBox.obterCentro().inverterSinal(objetos.get(focus).bBox.obterCentro()));
                    objetos.get(focus).color();
                    break;

                case KeyEvent.VK_4:
                    if (mode == Mode.ADD) {
                        objetos.get(focus).removeVertice();
                    }
                    mode = Mode.CREATE;
                    //objetos.add(new ObjetoGrafico());
                    //focus = objetos.size() - 1;
                    //objetos.get(focus).atribuirGL(gl);
                    objetos.get(focus).primitiva = GL.GL_LINE_LOOP;
                    break;
                case KeyEvent.VK_5:
                    if (objetos.size() > 0) {
                        objetos.remove(focus);
                        focus = objetos.size() - 1;
                    }
                    mode = Mode.CREATE;
                    break;

                case KeyEvent.VK_6:
                    if (focus < objetos.size() - 1) {
                        focus++;
                    }
                    break;
                case KeyEvent.VK_7:
                    if (focus > 0 && objetos.size() > 0) {
                        focus--;
                    }
                    break;
                case KeyEvent.VK_8:
                    //objetos.get(focus).adcionaVertice();
                    break;
                case KeyEvent.VK_9:
                    if (!objetos.get(focus).removeVertice()) {
                        objetos.remove(focus);
                        focus = objetos.size() - 1;
                    }
                    break;
                case KeyEvent.VK_C:
                    objetos.get(focus).color();
                    break;
                case KeyEvent.VK_V:
                    if (mode == Mode.CREATE) {
                        
                        mode = Mode.ADD;
                        
                    } else if (mode == Mode.ADD) {
                        mode = Mode.CREATE;
                    }

                    break;
                case KeyEvent.VK_Z:
                    objetos.get(focus).addFilho(focus, focus);
                    break;
                case KeyEvent.VK_X:
                    objetos.get(focus).removeFilho();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            //objetos.add(new ObjetoGrafico());
            //focus = objetos.size() - 1;
            //objetos.get(focus).atribuirGL(gl);
        }

        glDrawable.display();
    }

    // metodo definido na interface GLEventListener.
    // "render" feito depois que a janela foi redimensionada.
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        // System.out.println(" --- reshape ---");
        //translatar primeiro depois rotacionar
        System.out.println("");
    }
//talvez seja necessário enviar uma cópia do ultimo vertice no topo da lista para fazer o método de arraste de poligonos
    // metodo definido na interface GLEventListener.
    // "render" feito quando o modo ou dispositivo de exibicao associado foi
    // alterado.

    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
        // System.out.println(" --- displayChanged ---");
    }

    public void keyReleased(KeyEvent arg0) {
        // System.out.println(" --- keyReleased ---");
    }

    public void keyTyped(KeyEvent arg0) {
        // System.out.println(" --- keyTyped ---");
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println(e.getButton());
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (mode) {
                case CREATE:
                    mode = mode.ADD;
                    objetos.add(new ObjetoGrafico(e.getX(), e.getY()));
                    focus = objetos.size() - 1;
                    objetos.get(focus).atribuirGL(gl);
                    objetos.get(focus).primitiva = GL.GL_LINE_STRIP;
                    objetos.get(focus).adcionaVertice((double) e.getX(), (double) e.getY());

                    break;
                case ADD:
                    objetos.get(focus).adcionaVertice((double) e.getX(), (double) e.getY());
                    glDrawable.display();
                    break;
            }

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (objetos.size() > 0) {
                for (int i = 0; i < objetos.size(); i++) {
                    objetos.get(i).bBox.desenharOpenGLBBox(gl);
                    if (objetos.get(i).bBox.contains(objetos.get(i), new Ponto4D(e.getX(), e.getY(), 0.0, 1.0))) {
                        System.out.println("DENTRO");
                        focus = i;
                    } else {
                        System.out.println("FORA");
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        //System.out.println("moveu");
        if (mode == Mode.ADD) {
            objetos.get(focus).vertices.get(objetos.get(focus).vertices.size() - 1).atribuirX(e.getX());
            objetos.get(focus).vertices.get(objetos.get(focus).vertices.size() - 1).atribuirY(e.getY());
            glDrawable.display();
        }
    }

}
