/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3fernandovera;

import java.util.Random;

/**
 *
 * @author DAW
 */
public class Tablero {

    private int x;
    private Celda[][] tableroDeJuego;
    private int minasTotales;
    public int contadorPosicionesCorrectas;

    public void setX(int x) {
        this.x = x;
    }

    public boolean crearTablero() {
        minasTotales = 0;
        contadorPosicionesCorrectas = 0;
        boolean sePuedeCrear = false;
        if (x > 1) {
            sePuedeCrear = true;
            tableroDeJuego = new Celda[x][x];
            for (int i = 0; i < tableroDeJuego.length; i++) {
                for (int j = 0; j < tableroDeJuego[i].length; j++) {
                    Celda celda = new Celda();
                    generarMinas(celda);
                    tableroDeJuego[i][j] = celda;
                }
            }
            if (minasTotales == 0) {
                crearTablero();
            }
            definirValorDeMinaPorCelda();
        } else {
            System.out.println("\033[31m" + "Introduce un valor mas grande para crear el tablero" + "\033[0m" + System.lineSeparator());
        }
        return sePuedeCrear;
    }

    private Celda generarMinas(Celda celda) {
        Random r = new Random();
        int valor = r.nextInt(8);
        if (valor == 3) {
            celda.setMina();
            minasTotales++;
        }
        return celda;
    }

    public void hacerTableroVisible() {
        for (int i = 0; i < tableroDeJuego.length; i++) {
            for (int j = 0; j < tableroDeJuego[i].length; j++) {
                tableroDeJuego[i][j].setVisible();
            }
        }
    }

    public void ocultarTablero() {
        for (int i = 0; i < tableroDeJuego.length; i++) {
            for (int j = 0; j < tableroDeJuego[i].length; j++) {
                tableroDeJuego[i][j].setNoVisible();
            }
        }
    }

    public boolean isVisible(int x, int y) {
        boolean visible = tableroDeJuego[x][y].isVisible();

        return visible;
    }

    public int posicionElegida(int x, int y) {
        int numCasillasSinMinas = Math.abs((tableroDeJuego.length * tableroDeJuego[0].length) - minasTotales);
        int seguir = 1;
        if (x < tableroDeJuego.length && y < tableroDeJuego[0].length && x >= 0 && y >= 0 && !(tableroDeJuego[x][y].isVisible())) {
            contadorPosicionesCorrectas++;    // Para saber si ha acertado todas.
            tableroDeJuego[x][y].setVisible();
            if (tableroDeJuego[x][y].getValor() == Valores.MINA) {
                seguir = 0;             //Game Over
            } else if (numCasillasSinMinas == contadorPosicionesCorrectas) {
                seguir = 2;            // Win
            } else if (tableroDeJuego[x][y].getNumMinasAlrededor() == 0) {
                abrirTablero(x, y);
                abrirAlrededorCeros();
            }
        } else if (x < tableroDeJuego.length && y < tableroDeJuego[0].length && x >= 0 && y >= 0 && tableroDeJuego[x][y].isVisible()) {
            System.out.println("\033[31m" + "Posicion ya visible, introduce otra" + "\033[0m" + System.lineSeparator());
        } else {
            System.out.println("\033[31m" + "Introduce una posicion correcta" + "\033[0m" + System.lineSeparator());
        }
        return seguir;
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < tableroDeJuego.length; i++) {
            resultado.append("[");
            for (int j = 0; j < tableroDeJuego[i].length; j++) {
                resultado.append(tableroDeJuego[i][j]);
                if (j < tableroDeJuego[i].length - 1) {
                    resultado.append(" ");
                }
            }
            resultado.append("]");
            resultado.append(System.lineSeparator());
        }
        String resultadofinal;
        resultadofinal = resultado.toString();
        return resultadofinal;
    }

    private void abrirTablero(int x, int y) {
        int ejeX = x;
        int ejeY = y;
        tableroDeJuego[x][y].setVisible();
        if (ejeX != 0 && ejeY != 0 && ejeX != tableroDeJuego.length - 1 && ejeY != tableroDeJuego[0].length - 1) {
            for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                    if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY)) && (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible())) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == 0 && ejeY == 0) {
            for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == 0 && ejeY == tableroDeJuego.length - 1) {
            for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == 0) {
            for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == tableroDeJuego.length - 1) {
            for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == 0) {
            for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeY == 0) {
            for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeX == tableroDeJuego[0].length - 1) {
            for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego.length; ejeXmenor++) {
                for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        } else if (ejeY == tableroDeJuego.length - 1) {
            for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                    if (tableroDeJuego[ejeXmenor][ejeYmenor].getNumMinasAlrededor() == 0 && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() != Valores.MINA && !tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                        if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY))) {
                            abrirTablero(ejeXmenor, ejeYmenor);
                        }
                        contadorPosicionesCorrectas++;

                        tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                    }
                }
            }
        }
    }

    private void abrirAlrededorCeros() {
        for (int ejeX = 0; ejeX < tableroDeJuego.length; ejeX++) {
            for (int ejeY = 0; ejeY < tableroDeJuego[ejeX].length; ejeY++) {
                if (tableroDeJuego[ejeX][ejeY].isVisible() && tableroDeJuego[ejeX][ejeY].getNumMinasAlrededor() == 0) {
                    if (ejeX != 0 && ejeY != 0 && ejeX != tableroDeJuego.length - 1 && ejeY != tableroDeJuego[0].length - 1) {
                        for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                            for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                                if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY && tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()))) {

                                    if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                                        contadorPosicionesCorrectas++;
                                    }
                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }
                            }
                        }
                    } else if (ejeX == 0 && ejeY == 0) {
                        for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                            for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {

                                    contadorPosicionesCorrectas++;

                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }
                            }
                        }
                    } else if (ejeX == 0 && ejeY == tableroDeJuego.length - 1) {
                        for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                            for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {

                                    contadorPosicionesCorrectas++;

                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == 0) {
                        for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                            for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {

                                    contadorPosicionesCorrectas++;

                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == tableroDeJuego.length - 1) {
                        for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                            for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                                    contadorPosicionesCorrectas++;
                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    } else if (ejeX == 0) {
                        for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                            for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                                    contadorPosicionesCorrectas++;
                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }
                            }
                        }
                    } else if (ejeY == 0) {
                        for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                            for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                                    contadorPosicionesCorrectas++;
                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    } else if (ejeX == tableroDeJuego[0].length - 1) {
                        for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego.length; ejeXmenor++) {
                            for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {

                                    contadorPosicionesCorrectas++;

                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    } else if (ejeY == tableroDeJuego.length - 1) {
                        for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                            for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                                if (!tableroDeJuego[ejeXmenor][ejeYmenor].isVisible()) {
                                    contadorPosicionesCorrectas++;

                                    tableroDeJuego[ejeXmenor][ejeYmenor].setVisible();

                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public int getMinasAlrededor(int x, int y) {
        int minasAlrededor = 0;
        if ((tableroDeJuego[x][y].getValor()) != Valores.MINA) {
            minasAlrededor = tableroDeJuego[x][y].getNumMinasAlrededor();

        }
        return minasAlrededor;
    }

    public int hasMina(int x, int y) {
        int min = 0;
        if ((tableroDeJuego[x][y].getValor()) == Valores.MINA) {
            min = 1;
        }
        return min;
    }

    private void definirValorDeMinaPorCelda() {    // DEFINIR EL NUMERO DE MINAS EN CADA CELDA
        int minas = 0;
        for (int ejeX = 0; ejeX < tableroDeJuego.length; ejeX++) {
            for (int ejeY = 0; ejeY < tableroDeJuego[ejeX].length; ejeY++) {
                if (ejeX != 0 && ejeY != 0 && ejeX != tableroDeJuego.length - 1 && ejeY != tableroDeJuego[0].length - 1) {
                    for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                        for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                            if ((!(ejeXmenor == ejeX && ejeYmenor == ejeY)) && tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == 0 && ejeY == 0) {
                    for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                        for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeXmenor == 0 && ejeYmenor == 0)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == 0 && ejeY == tableroDeJuego.length - 1) {
                    for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                        for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeXmenor == 0 && ejeYmenor == tableroDeJuego.length - 1)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == 0) {
                    for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                        for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeYmenor == 0 && ejeXmenor == tableroDeJuego.length - 1)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == tableroDeJuego[0].length - 1 && ejeY == tableroDeJuego.length - 1) {
                    for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego[0].length; ejeXmenor++) {
                        for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeYmenor == tableroDeJuego.length - 1 && ejeXmenor == tableroDeJuego.length - 1)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == 0) {
                    for (int ejeXmenor = 0; ejeXmenor < 2; ejeXmenor++) {
                        for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeXmenor == 0 && ejeYmenor == ejeY)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeY == 0) {
                    for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                        for (int ejeYmenor = 0; ejeYmenor < 2; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeXmenor == ejeX && ejeYmenor == 0)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeX == tableroDeJuego[0].length - 1) {
                    for (int ejeXmenor = tableroDeJuego[0].length - 2; ejeXmenor < tableroDeJuego.length; ejeXmenor++) {
                        for (int ejeYmenor = ejeY - 1; ejeYmenor < ejeY + 2; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeXmenor == tableroDeJuego[0].length - 1 && ejeYmenor == ejeY)) {
                                minas++;
                            }
                        }
                    }
                } else if (ejeY == tableroDeJuego.length - 1) {
                    for (int ejeXmenor = ejeX - 1; ejeXmenor < ejeX + 2; ejeXmenor++) {
                        for (int ejeYmenor = tableroDeJuego.length - 2; ejeYmenor < tableroDeJuego.length; ejeYmenor++) {
                            if (tableroDeJuego[ejeXmenor][ejeYmenor].getValor() == Valores.MINA && !(ejeYmenor == tableroDeJuego.length - 1 && ejeXmenor == ejeX)) {
                                minas++;
                            }
                        }
                    }
                }
                tableroDeJuego[ejeX][ejeY].setNumMinasAlrededor(minas);
                minas = 0;
            }
        }
    }
}
