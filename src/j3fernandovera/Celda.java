/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3fernandovera;

/**
 *
 * @author DAW
 */
public class Celda {

    private static final String textonormal = "\033[0m";
    private Valores valor = Valores.VACIA;
    private boolean visible = false;
    private int numMinasAlrededor;

    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    public void setVisible() {
        visible = true;
    }

    public void setNoVisible() {
        visible = false;
    }

    public void setMina() {
        valor = Valores.MINA;
    }

    public Valores getValor() {
        return valor;
    }

    @Override
    public String toString() {
        String resultado = "";

        if (visible) {
            resultado = valor.toString();
            if (resultado.equals("MINA")) {
                resultado = "\033[31m" + "M" + textonormal;
            } else {
                if (numMinasAlrededor == 0) {
                    resultado = "\033[2;32m" + "0" + textonormal;
                } else {
                    resultado = String.valueOf(numMinasAlrededor);
                }
            }

        } else {
            resultado = "\033[34m" + "?" + textonormal;
        }
        return resultado;
    }

}
