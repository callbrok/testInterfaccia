package com.artistcorner.controller.guicontroller.uploadartwork;

import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerUploadArtwork {
    @FXML
    private Button button1Up;
    @FXML
    private Button button2Up;
    @FXML
    private Button button3Up;
    @FXML
    private Button button4Up;
    @FXML
    private Button button5Up;
    @FXML
    private AnchorPane anchorParentUpload;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private Label labelFilePath;
    @FXML
    private AnchorPane anchorPaneDragAndDrop;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelLogOutUpload;
    @FXML
    private RadioButton radioBtmSellUpload;

    private double xUpload=0;
    private double yUpload=0;
    private Stage stage;

    private String filePath="";
    private ArtistBean art;


    private void makeDraggable(){
        anchorParentUpload.setOnMousePressed((eventUpload -> {
            xUpload=eventUpload.getSceneX();
            yUpload= eventUpload.getSceneY();
        }));

        anchorParentUpload.setOnMouseDragged((eventUpload -> {
            stage = (Stage) ((Node)eventUpload.getSource()).getScene().getWindow();
            stage.setX(eventUpload.getScreenX() - xUpload);
            stage.setY(eventUpload.getScreenY() - yUpload);
        }));
    }
    
    public void exitWindowUpload() {
        stage = (Stage) anchorParentUpload.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentUpload.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOutUpload.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void initialize(){
        initDragAndDrop();
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void setTooltipMenu(){
        button1Up.setTooltip(new Tooltip("Home"));
        button2Up.setTooltip(new Tooltip("Profilo"));
        button3Up.setTooltip(new Tooltip("Carica Opera"));
        button4Up.setTooltip(new Tooltip("Offerte Mostre"));
        button5Up.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    public void selectFile(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Impone la selezione di soli file di tipo immagine.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File Immagine", "*.jpg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(stage);

        filePath = selectedFile.toString();

        labelFilePath.setText(selectedFile.toString());   // Mostra il percorso del file selezionato.
    }

    public void initDragAndDrop(){
        //Implement Drag and Drop Feature.

    }

    public void uploadFile(){
        UploadArtWork upawDesk = new UploadArtWork();
        int flagVendibile;
        double prezzo;

        // Stati di flagVendibile
        //  0 : opera non acquistabile
        //  1 : opera acquistabile
        if (radioBtmSellUpload.isSelected()) {
            flagVendibile=1;
            prezzo = Double.parseDouble(textFieldPrice.getText());
        } else {
            flagVendibile = 0;
            prezzo = 0;
        }

        try {
            ArtWorkBean upArtWork = new ArtWorkBean(textFieldTitle.getText(), prezzo, flagVendibile,art.getIdArtista());
            upawDesk.uploadImage(upArtWork, art.getIdArtista(), filePath);
        } catch (EmptyFieldException e){
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.EMPTYFIELD);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }catch (EmptyPathException e) {
            // Eccezione: File non selezionato.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.EMPTYPATH);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }catch (DuplicateArtWorkException e){
            // Eccezione: Opera già caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.DUPLICATEARTWORK);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

        resetForm();
    }

    public void resetForm(){
        labelFilePath.setText("");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(event, art);
    }


}
