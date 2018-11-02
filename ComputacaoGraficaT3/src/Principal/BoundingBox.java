package Principal;

import javax.media.opengl.GL;

public final class BoundingBox {

    private double menorX;
    private double menorY;
    private double menorZ;
    private double maiorX;
    private double maiorY;
    private double maiorZ;
    private Ponto4D centro = new Ponto4D();
    private Transformacao4D matrizObjeto = new Transformacao4D();
    private static Transformacao4D matrizTmpTranslacao = new Transformacao4D();
    private static Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
    private static Transformacao4D matrizTmpEscala = new Transformacao4D();
    private static Transformacao4D matrizGlobal = new Transformacao4D();

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
    }

    public void translatarBox(double tx, double ty, double tz) {
        Transformacao4D matrizTranslate = new Transformacao4D();
        matrizTranslate.atribuirTranslacao(tx, ty, tz);
        matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);
    }

    public void escalarBox(double escala) {
        Ponto4D pf = obterCentro().inverterSinal(obterCentro());
        matrizGlobal.atribuirIdentidade();
        matrizTmpTranslacao.atribuirTranslacao(pf.obterX(), pf.obterY(), pf.obterZ());
        matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);
        matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
        matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);
        pf.inverterSinal(pf);
        matrizTmpTranslacaoInversa.atribuirTranslacao(pf.obterX(), pf.obterY(), pf.obterZ());
        matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);
        matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);

    }

    public void rotacionarBox(double angulo) {
        Ponto4D pf = obterCentro().inverterSinal(obterCentro());
        matrizGlobal.atribuirIdentidade();
        matrizTmpTranslacao.atribuirTranslacao(pf.obterX(), pf.obterY(), pf.obterZ());
        matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);
        matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
        matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);
        pf.inverterSinal(pf);
        matrizTmpTranslacaoInversa.atribuirTranslacao(pf.obterX(), pf.obterY(), pf.obterZ());
        matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);
        matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
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

    /*
    hjkhjkh
     */
    public void desenharOpenGLBBox(GL gl) {
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glPushMatrix();
        gl.glMultMatrixd(matrizObjeto.GetDate(), 0);

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
        gl.glPopMatrix();
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


    public boolean contains(ObjetoGrafico pl, Ponto4D test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = pl.vertices.size() - 1; i < pl.vertices.size(); j = i++) {
            if ((pl.vertices.get(i).obterY() > test.obterY()) != (pl.vertices.get(j).obterY() > test.obterY())
                    && (test.obterX() < (pl.vertices.get(j).obterX() - pl.vertices.get(i).obterX()) * (test.obterY() - pl.vertices.get(i).obterY()) / (pl.vertices.get(j).obterY() - pl.vertices.get(i).obterY()) + pl.vertices.get(i).obterX())) {
                result = !result;
            }
        }
        return result;
    }
}
