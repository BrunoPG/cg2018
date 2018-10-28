package Principal;

import java.util.ArrayList;
import javax.media.opengl.GL;

public final class ObjetoGrafico {

    GL gl;
    //bruno do futuro, substituir o gl pelo drawable(igual no main)
    //private GLAutoDrawable glDrawable;
    private float tamanho = 2.0f;
    private int primitiva = GL.GL_LINE_LOOP;
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

//	private int primitiva = GL.GL_POINTS;
//	private Ponto4D[] vertices = { new Ponto4D(10.0, 10.0, 0.0, 1.0) };	
    public ObjetoGrafico() {
        vertices.add(new Ponto4D(10.0, 10.0, 0.0, 1.0));
        vertices.add(new Ponto4D(20.0, 10.0, 0.0, 1.0));
        vertices.add(new Ponto4D(20.0, 20.0, 0.0, 1.0));
        vertices.add(new Ponto4D(10.0, 20.0, 0.0, 1.0));

    }

    public void alteraVertice(Integer i, Double x, Double y, Double z) {
        vertices.get(i).atribuirX(x);
        vertices.get(i).atribuirY(y);
        vertices.get(i).atribuirZ(z);
        desenha();
    }

    public void adcionaVertice() {
        vertices.add(new Ponto4D(10.0, 10.0, 10.0, 1.0));
        desenha();
    }

    public void removeVertice(Integer i) {
        vertices.remove(i);
        desenha();
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
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glLineWidth(tamanho);
        gl.glPointSize(tamanho);
        gl.glPushMatrix();
        gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
        gl.glBegin(primitiva);
        for (byte i = 0; i < vertices.size(); i++) {
            gl.glVertex2d(vertices.get(i).obterX(), vertices.get(i).obterY());
        }
        gl.glEnd();
        //////////// ATENCAO: chamar desenho dos filhos... 
        gl.glPopMatrix();
    }

    public void translacaoXYZ(double tx, double ty, double tz) {
        Transformacao4D matrizTranslate = new Transformacao4D();
        matrizTranslate.atribuirTranslacao(tx, ty, tz);
        matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);
    }

    public void escalaXYZ(double Sx, double Sy) {
        Transformacao4D matrizScale = new Transformacao4D();
        matrizScale.atribuirEscala(Sx, Sy, 1.0);
        matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
    }

    ///TODO: erro na rotacao
    public void rotacaoZ(double angulo) {
//		anguloGlobal += 10.0; // rotacao em 10 graus
//		Transformacao4D matrizRotacaoZ = new Transformacao4D();		
//		matrizRotacaoZ.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
//		matrizObjeto = matrizRotacaoZ.transformMatrix(matrizObjeto);
    }

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
    }

    public void exibeMatriz() {
        matrizObjeto.exibeMatriz();
    }

    public void exibeVertices() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("P" + i + "[" + vertices.get(i).obterX() + "," + vertices.get(i).obterY() + "]");
        }
//System.out.println("anguloGlobal:" + anguloGlobal);
    }

}
