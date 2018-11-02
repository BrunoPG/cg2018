package Principal;

import java.util.ArrayList;
import javax.media.opengl.GL;

public final class ObjetoGrafico {

    GL gl;
    //private GLAutoDrawable glDrawable;
    private float r = 0.0f;
    private float g = 0.0f;
    private float b = 1.0f;
    private float tamanho = 2.0f;
    private int color, focus = 0;
    
    public int primitiva = GL.GL_LINE_STRIP;
    public BoundingBox bBox;
    private Transformacao4D matrizObjeto = new Transformacao4D();
    /// Matrizes temporarias que sempre sao inicializadas com matriz Identidade entao podem ser "static".
    private static Transformacao4D matrizTmpTranslacao = new Transformacao4D();
    private static Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
    private static Transformacao4D matrizTmpEscala = new Transformacao4D();
//	private static Transformacao4D matrizTmpRotacaoZ = new Transformacao4D();
    private static Transformacao4D matrizGlobal = new Transformacao4D();
//	private double anguloGlobal = 0.0;
    ArrayList<ObjetoGrafico> filhos = new ArrayList<ObjetoGrafico>();
    ArrayList<Ponto4D> vertices = new ArrayList<Ponto4D>();

//	private Ponto4D[] vertices = { new Ponto4D(10.0, 10.0, 0.0, 1.0) };	
    public ObjetoGrafico(double x, double y) {
        this.vertices.add(new Ponto4D(x, y, 0.0, 1.0));

        bBox = new BoundingBox(getBl().obterX(), getBl().obterY(), 0.0, getTr().obterX(), getTr().obterY(), 0.0);

    }

    public void addFilho(double x, double y) {
        filhos.add(new ObjetoGrafico(x, y));
        focus = filhos.size() - 1;
        filhos.get(focus).atribuirGL(gl);
    }

    public void removeFilho() {
        if (filhos.size() > 0) {
            filhos.remove(focus);
            focus = filhos.size() - 1;
        }
    }

    public void color() {

        switch (color) {
            case 0:
                r = 1.0f;
                g = 0.5f;
                b = 0.5f;

                break;
            case 1:
                r = 0.5f;
                g = 1.0f;
                b = 0.5f;
                break;
            case 2:
                r = 0.5f;
                g = 0.5f;
                b = 1.0f;
                break;
            case 3://preto
                r = 0.0f;
                g = 0.0f;
                b = 0.0f;
                break;
            case 4:
                r = 1.0f;
                g = 1.0f;
                b = 0.0f;
                break;
            case 5:
                r = 1.0f;
                g = 0.0f;
                b = 1.0f;
                break;
            case 6:
                r = 0.0f;
                g = 1.0f;
                b = 1.0f;
                break;
            case 7://vermelho
                r = 1.0f;
                g = 0.0f;
                b = 0.0f;
                break;
            case 8://verde
                r = 0.0f;
                g = 1.0f;
                b = 0.0f;
                break;
            case 9://azul
                r = 0.0f;
                g = 0.0f;
                b = 1.0f;
                break;
        }
        desenha();
        if (color < 9) {
            color++;
        } else {
            color = 0;
        }
        for (byte i = 0; i < filhos.size(); i++) {
            filhos.get(i).color();
        }
    }

    public void alteraVertice(Integer i, Double x, Double y, Double z) {
        this.vertices.get(i).atribuirX(x);
        this.vertices.get(i).atribuirY(y);
        this.vertices.get(i).atribuirZ(z);
        desenha();
    }

    public void adcionaVertice(Double x, Double y) {
        this.vertices.add(new Ponto4D(x, y, 0.0, 1.0));
        bBox.atualizarBBox(x, y, 0.0);
        desenha();

    }

    public boolean removeVertice() {
        if (vertices.size() > 1) {

            vertices.remove(vertices.size() - 1);
            desenha();
            return true;
        } else {
            return false;
        }
    }

    public void atribuirGL(GL gl) {
        this.gl = gl;
    }

    public double obterTamanho() {
        return tamanho;
    }

    public double obterPrimitava() {
        return primitiva;
    }

    public void desenha() {
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glColor3f(r, g, b);
        gl.glLineWidth(tamanho);
        gl.glPointSize(tamanho);
        gl.glPushMatrix();
        gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
        gl.glBegin(primitiva);
        for (byte i = 0; i < vertices.size(); i++) {
            gl.glVertex2d(vertices.get(i).obterX(), vertices.get(i).obterY());
        }
        gl.glEnd();
        for (byte i = 0; i < filhos.size(); i++) {
            filhos.get(i).desenha();
        }
        gl.glPopMatrix();
        bBox.atribuirBoundingBox(getBl().obterX(), getBl().obterY(), 0.0, getTr().obterX(), getTr().obterY(), 0.0);
        bBox.desenharOpenGLBBox(gl);
    }

    public void translacaoXYZ(double tx, double ty, double tz) {
        Transformacao4D matrizTranslate = new Transformacao4D();
        matrizTranslate.atribuirTranslacao(tx, ty, tz);
        matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);
        for (byte i = 0; i < filhos.size(); i++) {
            filhos.get(i).translacaoXYZ(tx, ty, tz);
        }

        bBox.atribuirBoundingBox(getBl().obterX(), getBl().obterY(), 0.0, getTr().obterX(), getTr().obterY(), 0.0);
        bBox.translatarBox(tx, ty, tz);
        bBox.desenharOpenGLBBox(gl);
        desenha();
    }

    public void escalaXYZ(double Sx, double Sy) {
        Transformacao4D matrizScale = new Transformacao4D();
        matrizScale.atribuirEscala(Sx, Sy, 1.0);
        matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
        for (byte i = 0; i < filhos.size(); i++) {
            filhos.get(i).escalaXYZ(Sx, Sy);
        }
    }

    ///TODO: erro na rotacao
    //public void rotacaoZ(double angulo) {
//		anguloGlobal += 10.0; // rotacao em 10 graus
//		Transformacao4D matrizRotacaoZ = new Transformacao4D();		
//		matrizRotacaoZ.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
//		matrizObjeto = matrizRotacaoZ.transformMatrix(matrizObjeto);
    //  }
    public void atribuirIdentidade() {
//		anguloGlobal = 0.0;
        matrizObjeto.atribuirIdentidade();
    }

    public void escalaXYZPtoFixo(double escala, Ponto4D ptoFixo) {
        matrizGlobal.atribuirIdentidade();
        matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(), ptoFixo.obterY(), ptoFixo.obterZ());
        matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);
        matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
        matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);
        ptoFixo.inverterSinal(ptoFixo);
        matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(), ptoFixo.obterY(), ptoFixo.obterZ());
        matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);
        matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);

        for (byte i = 0; i < filhos.size(); i++) {

            filhos.get(i).escalaXYZPtoFixo(escala, filhos.get(i).bBox.obterCentro().inverterSinal(filhos.get(i).bBox.obterCentro()));
        }
        bBox.atribuirBoundingBox(getBl().obterX(), getBl().obterY(), 0.0, getTr().obterX(), getTr().obterY(), 0.0);
        bBox.escalarBox(escala);
        bBox.desenharOpenGLBBox(gl);
        desenha();

    }

    public void rotacaoZPtoFixo(double angulo, Ponto4D ptoFixo) {
        matrizGlobal.atribuirIdentidade();
        matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(), ptoFixo.obterY(), ptoFixo.obterZ());
        matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);
        matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
        matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);
        ptoFixo.inverterSinal(ptoFixo);
        matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(), ptoFixo.obterY(), ptoFixo.obterZ());
        matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);
        matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
        //ptoFixo.atribuirX(0.0);
        //ptoFixo.atribuirY(0.0);
        for (byte i = 0; i < filhos.size(); i++) {
            filhos.get(i).rotacaoZPtoFixo(angulo, filhos.get(i).bBox.obterCentro().inverterSinal(filhos.get(i).bBox.obterCentro()));
        }
        bBox.atribuirBoundingBox(getBl().obterX(), getBl().obterY(), 0.0, getTr().obterX(), getTr().obterY(), 0.0);
        bBox.rotacionarBox(angulo);
        bBox.desenharOpenGLBBox(gl);
        desenha();
    }

    public void exibeMatriz() {
        matrizObjeto.exibeMatriz();
    }

    public void exibeVertices() {
        System.out.println("----------------------------------------------------------------------------------\n");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("P" + i + "[" + vertices.get(i).obterX() + "," + vertices.get(i).obterY() + "]");
        }
//System.out.println("anguloGlobal:" + anguloGlobal);
    }

    public Ponto4D getTl() {
        double x = vertices.get(0).obterX();
        double y = vertices.get(0).obterY();

        for (int i = 0; i < vertices.size(); i++) {
            if (x > vertices.get(i).obterX()) {
                x = vertices.get(i).obterX();
            }
            if (y < vertices.get(i).obterY()) {
                y = vertices.get(i).obterY();
            }
        }
        return new Ponto4D(x, y, 0.0, 1.0);
    }

    public Ponto4D getTr() {
        double x = vertices.get(0).obterX();
        double y = vertices.get(0).obterY();

        for (int i = 0; i < vertices.size(); i++) {
            if (x < vertices.get(i).obterX()) {
                x = vertices.get(i).obterX();
            }
            if (y < vertices.get(i).obterY()) {
                y = vertices.get(i).obterY();
            }
        }
        return new Ponto4D(x, y, 0.0, 1.0);
    }

    public Ponto4D getBl() {
        double x = vertices.get(0).obterX();
        double y = vertices.get(0).obterY();

        for (int i = 0; i < vertices.size(); i++) {
            if (x > vertices.get(i).obterX()) {
                x = vertices.get(i).obterX();
            }
            if (y > vertices.get(i).obterY()) {
                y = vertices.get(i).obterY();
            }
        }
        return new Ponto4D(x, y, 0.0, 1.0);
    }

    public Ponto4D getBr() {
        double x = vertices.get(0).obterX();
        double y = vertices.get(0).obterY();

        for (int i = 0; i < vertices.size(); i++) {
            if (x < vertices.get(i).obterX()) {
                x = vertices.get(i).obterX();
            }
            if (y > vertices.get(i).obterY()) {
                y = vertices.get(i).obterY();
            }
        }
        return new Ponto4D(x, y, 0.0, 1.0);
    }

}
