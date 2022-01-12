package com.artistcorner.controller.guicontroller.viewsaleshistory;

import com.artistcorner.engclasses.bean.Nodo;
import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerViewSalesHistory {
    public AnchorPane anchorParent;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public ListView listViewSale;
    public Label labelArtWorkTitle;
    public Label labelArtWorkPrice;
    public LineChart lineChartSell;
    public Label labelLogOut;
    private double x=0, y=0;
    private Stage stage;

    Artist art;

    private void makeDraggable(){
        anchorParent.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParent.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void initialize() throws IOException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
       // initializeLineChart();
    }

    public void getArtist(Artist loggedArtist) {
        art = loggedArtist;
        populateListView(loggedArtist);
    }

    public void populateListView(Artist art){
        ArrayList<ArtWork> arrayOfArtwork = ArtistDAO.retrieveSellArtWorks(art.getIdArtista());

        for (ArtWork n : arrayOfArtwork) {
            listViewSale.getItems().add(n.getTitolo());  // Popola la listView.
                }

        listViewSale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int index = listViewSale.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                ArtWork currentArt = arrayOfArtwork.get(index);   // Prende l'i-esima (index) opera d'arte dal result set.

                labelArtWorkTitle.setText(currentArt.getTitolo());
                labelArtWorkPrice.setText(String.valueOf(currentArt.getPrezzo()));
            }
        });

    }

    public void initializeLineChart(){

    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}