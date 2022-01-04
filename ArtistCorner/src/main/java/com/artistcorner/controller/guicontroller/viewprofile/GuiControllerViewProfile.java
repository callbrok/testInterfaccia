package com.artistcorner.controller.guicontroller.viewprofile;

import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.singleton.UserHolder;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerViewProfile {

    @FXML
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;

    public AnchorPane anchorParent;
    public AnchorPane anchorPaneFocus;
    public ImageView imageThumb;
    public ImageView imageFocused;
    public TilePane tilePaneBlob;
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


    public void initialize() throws SQLException {
        anchorPaneFocus.setVisible(false);

        getArtist();
        initializeTilePane();
        makeDraggable();
        setTooltipMenu();

    }

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }


    private void initializeTilePane() throws SQLException {
        ArrayList<Blob> listOfArtWorksImage = ArtistDAO.retrieveAllArtWorksImage(art.getIdArtista());  // Prendi tutte le opere caricate dall'artista.

        tilePaneBlob.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
        tilePaneBlob.setVgap(10);    // Setta i bordi verticali tra un tile e l'altro.

        EventHandler<MouseEvent> mouseHandler = new EventHandler<>() {    // Crea un EventHandler sull'imageView all'interno del tilePane.
            @Override
            public void handle(MouseEvent t) {
                ImageView imageView = (ImageView) t.getSource();  // Prende l'imageView collegata all'evento.

                imageFocused.setImage(imageView.getImage());   // Setta l'immagine e la rende focused.
                centerImage(imageFocused);                     // Centra l'immagine.
                anchorPaneFocus.setVisible(true);
            }
        };

        anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            anchorPaneFocus.setVisible(false);
        });

        for (Blob b : listOfArtWorksImage){    // Scorre tutti i blob relativi all'artista.

            InputStream inputStream = b.getBinaryStream();

            /*1)preserveRatio:
            Indicates whether to preserve the aspect ratio of the source image when scaling to
            fit the image within the fitting bounding box.

            2)smooth:
            Indicates whether to use a better quality filtering algorithm or a faster one when transforming
            or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. */
            Image image = new Image(inputStream, 100, 100, true, false);

            ImageView imageThumb = new ImageView();
            imageThumb.setImage(image);

            imageThumb.setOnMouseClicked(mouseHandler);   // Setta un mouseHandler su ogni immagine.
            tilePaneBlob.getChildren().add(imageThumb);   // Popola la tilePane.
        }

    }

    private void getArtist() {
        UserHolder uh = UserHolder.getInstance(); // Interroga il singleton per le credenziali dell'utente.
        User u = uh.getUser();      // Prende le credenziali dell'utente.
        art = ArtistDAO.retrieveArtist(u);   // Prende le informazioni dell'artista collegate alle credenziali di accesso.
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void switchToMainArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(actionEvent);
    }
}
