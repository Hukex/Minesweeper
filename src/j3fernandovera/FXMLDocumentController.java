/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3fernandovera;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Fernando
 */
public class FXMLDocumentController implements Initializable {

    private Tablero tablero = new Tablero();
    private int tamañoXTablero;
    static Stage stage;
    @FXML
    private TextField tamaño;
    @FXML
    private Button b;
    @FXML
    private AnchorPane principal;
    GridPane gridpane = new GridPane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void clickJugar(ActionEvent event) {
        principal.getChildren().remove(tamaño);
        principal.getChildren().remove(b);
        if (gridpane != null) {
            principal.getChildren().remove(gridpane);
            gridpane = new GridPane();
        }
        if (tamaño.getText().isEmpty()) {
            tamaño.setText("2");
        }
        tamañoXTablero = Integer.valueOf(tamaño.getText());
        if (tamañoXTablero < 2) {
            tamañoXTablero = 2;
        }
        tablero.setX(tamañoXTablero);
        tablero.crearTablero();
        principal.getChildren().add(gridpane);
        for (int i = 0; i < tamañoXTablero; i++) {
            for (int j = 0; j < tamañoXTablero; j++) {
                MyButton boton = new MyButton();
                boton.x = j;
                boton.y = i;
                boton.setStyle("-fx-background-color:  linear-gradient(to bottom, #99ff33 0%, #33cc33 100%);"
                        + "-fx-border-color: #145A32;" + "-fx-border-radius: 10px;" + "-fx-background-radius:10px");

                boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int seguir = tablero.posicionElegida(boton.x, boton.y);
                        if (seguir == 0) {
                            recorrerGridPaneGanarOPerder(seguir);
                            putBombImage(boton);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("💣💣💣💣💣💣💣💣");
                            alert.setTitle("PUUUUUM!");
                            alert.setContentText("GAME OVER!");
                            alert.showAndWait();
                            principal.getChildren().remove(gridpane);
                            principal.getChildren().add(tamaño);
                            principal.getChildren().add(b);

                        } else if (seguir == 2) {
                            recorrerGridPaneGanarOPerder(seguir);
                            boton.setText(String.valueOf(tablero.getMinasAlrededor(boton.x, boton.y)));
                            boton.setStyle("-fx-background-color: rgb(247,229,52);"
                                    + "-fx-border-color: #145A32;" + "-fx-border-radius: 10px;" + "-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #1ad1ff, #32cd32);" + "-fx-background-radius:10px");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("☺☺☺");
                            alert.setTitle("WIN");
                            alert.setContentText("WIN!");
                            alert.showAndWait();
                            principal.getChildren().remove(gridpane);
                            principal.getChildren().add(tamaño);
                            principal.getChildren().add(b);
                        } else {
                            boton.setStyle("-fx-background-color: #D0D3D4; ");
                            recorrerGridPaneAbrir();
                        }
                    }

                });
                boton.setMinWidth(35);
                boton.setMinHeight(35);
                gridpane.add(boton, i, j);

            }
        }

    }

    private void recorrerGridPaneGanarOPerder(int num) {
        for (int i = 0; i < tamañoXTablero; i++) {
            for (int j = 0; j < tamañoXTablero; j++) {
                MyButton boton = (MyButton) gridpane.getChildren().get(i * tamañoXTablero + j);
                if (num != 2 && boton.isDisable() != true) {
                    boton.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #dc143c, #32cd32);"
                            + "-fx-border-color:#641E16; " + "-fx-border-radius: 10px;" + "-fx-background-radius:10px");

                }
                if (tablero.hasMina(boton.x, boton.y) == 1) {
                    putBombImage(boton);
                }
                boton.setDisable(true);

            }

        }
    }

    private void recorrerGridPaneAbrir() {
        for (int i = 0; i < tamañoXTablero; i++) {
            for (int j = 0; j < tamañoXTablero; j++) {
                MyButton boton = (MyButton) gridpane.getChildren().get(i * tamañoXTablero + j);
                boolean visible = tablero.isVisible(boton.x, boton.y);
                if (visible) {
                    boton.setDisable(true);
                    boton.setStyle("-fx-background-color: rgb(247,229,52);"
                            + "-fx-border-color: #145A32;" + "-fx-border-radius: 10px;" + "-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #1ad1ff, #32cd32);" + "-fx-background-radius:10px");
                    boton.setText(String.valueOf(tablero.getMinasAlrededor(boton.x, boton.y)));

                }
            }

        }
    }

    private void putBombImage(MyButton boton) {
        Image image = new Image(getClass().getResourceAsStream("bomb.png"));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(20);
        iv.setFitWidth(17);
        boton.setGraphic(iv);
        boton.setStyle("-fx-border-color: #641E16; " + "-fx-border-radius:10px;" + "-fx-background-radius:10px;-fx-background-color:#641E16;");
    }

}
