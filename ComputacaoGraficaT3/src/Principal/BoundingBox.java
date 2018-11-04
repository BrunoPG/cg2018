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
    /**
     * Criar boundbox com as variaveis X e Y maior e menor passados por parametros.
     * @param smallerX menor posição X
     * @param smallerY menor posição Y
     * @param smallerZ menor posição Z
     * @param greaterX maior posição X
     * @param greaterY maior posição Y
     * @param greaterZ maior posição Z
     */
    public BoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
        this.menorX = smallerX;
        this.menorY = smallerY;
        this.menorZ = smallerZ;
        this.maiorX = greaterX;
        this.maiorY = greaterY;
        this.maiorZ = greaterZ;
    }
    
    /**
     * movimentar a boundbox pelas posições passadas por parâmetro
     * @param tx posição de X
     * @param ty posição de Y
     * @param tz posição de Z
     */
    public void translatarBox(double tx, double ty, double tz) {
        Transformacao4D matrizTranslate = new Transformacao4D();
        matrizTranslate.atribuirTranslacao(tx, ty, tz);
        matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);
    }

    /**
     * aumenta ou diminui o tamanho da bound box
     * @param escala escala da BoundBox
     */
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

    /**
     * Rotaciona a boundbox pelo angulo passado como parâmetro     
     * @param angulo angulo da rotação
     */
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

    /**
     * Atribui os valores passados por parâmetro para para a boundbox e seta o
     * centro da bound box.
     *
     * @param smallerX menor posição X
     * @param smallerY menor posição Y
     * @param smallerZ menor posição Z
     * @param greaterX maior posição X
     * @param greaterY maior posição Y
     * @param greaterZ maior posição Z
     */
    public void atribuirBoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
        this.menorX = smallerX;
        this.menorY = smallerY;
        this.menorZ = smallerZ;
        this.maiorX = greaterX;
        this.maiorY = greaterY;
        this.maiorZ = greaterZ;
        processarCentroBBox();
    }

    /**
     * Atualiza a boundBox para as posições do ponto 4D     
     * @param point Ponto4D
     */
    public void atualizarBBox(Ponto4D point) {
        atualizarBBox(point.obterX(), point.obterY(), point.obterZ());
    }

    /**
     * Atualiza a boundBox para as posições passadas por parâmetro     
     * @param x posição de X
     * @param y posição de Y
     * @param z posição de Z
     */
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

    /**
     * setar o centro da BoundBox com as maiores posições dos eixos + a menor / 2
     */
    public void processarCentroBBox() {
        centro.atribuirX((maiorX + menorX) / 2);
        centro.atribuirY((maiorY + menorY) / 2);
        centro.atribuirZ((maiorZ + menorZ) / 2);
    }

    /**
     * desenha uma BoundBox
     * @param gl Objeto open GL
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

    /**
     *
     * @return Obter menor valor X da BBox.
     */
    public double obterMenorX() {
        return menorX;
    }

    /**
     *
     * @return Obter menor valor Y da BBox.
     */
    public double obterMenorY() {
        return menorY;
    }

    /**
     *
     * @return Obter menor valor Z da BBox.
     */
    public double obterMenorZ() {
        return menorZ;
    }

    /**
     *
     * @return Obter maior valor X da BBox.
     */
    public double obterMaiorX() {
        return maiorX;
    }

    /**
     *
     * @return Obter maior valor Y da BBox.
     */
    public double obterMaiorY() {
        return maiorY;
    }

    /**
     *
     * @return Obter maior valor Z da BBox.
     */
    public double obterMaiorZ() {
        return maiorZ;
    }

    /**
     *
     * @return Obter ponto do centro da BBox.
     */
    public Ponto4D obterCentro() {
        return centro;
    }

    /**
     * verifica se o ponto4D esta dentro do objeto grafico.
     * @param obj Objeto grafico
     * @param posicao posição clicada Ponto4D
     * @return retorna true se tiver dentro dentro do objeto grafico
     */
    public boolean contains(ObjetoGrafico obj, Ponto4D posicao) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = obj.vertices.size() - 1; i < obj.vertices.size(); j = i++) {
            if ((obj.vertices.get(i).obterY() > posicao.obterY()) != (obj.vertices.get(j).obterY() > posicao.obterY())
                    && (posicao.obterX() < (obj.vertices.get(j).obterX() - obj.vertices.get(i).obterX()) * (posicao.obterY() - obj.vertices.get(i).obterY()) / (obj.vertices.get(j).obterY() - obj.vertices.get(i).obterY()) + obj.vertices.get(i).obterX())) {
                result = !result;
            }
        }
        return result;
    }
}
