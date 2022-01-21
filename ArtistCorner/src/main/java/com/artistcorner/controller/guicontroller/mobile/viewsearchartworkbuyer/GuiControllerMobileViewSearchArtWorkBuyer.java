package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewSearchArtWorkBuyer {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public Button button1;
    public Button button2;
    public ListView<HBoxCellMobile> listView;
    public Button buttonSearch;
    public Button buttonCanc;
    private double x=0, y=0;
    @FXML
    private Pane paneExceptionLoad;
    private Stage stage;
    public TextField textField;
    BuyerBean buy;

    public void initialize(){
        makeDraggable();
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void getBuyer(BuyerBean loggedBuyer){
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
    }

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            populateListView(input);
        }
    }
    public void buttonSearchOnClick() throws SQLException, IOException {
        String input= textField.getText();
        populateListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    public void switchToSummaryBuyer(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

    public static class HBoxCellMobile extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonAcquista = new Button();
        public Button buttonPreferiti = new Button();
        Label prezzo = new Label();
        Label prezzo1 = new Label();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCellMobile (String labelText, String labelText1, Image img, int idOpera, double price, String labelPreferiti, int idBuyer, int idArtista, List<Integer> arrayListArtWorkId, String input)throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            labelArtWorkName.setText(labelText);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelText1);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(75, 25);
            labelArtistName.setPrefSize(75, 25);
            prezzo.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzo.setMaxWidth(Double.MAX_VALUE);
            prezzo.setText("€ " + Double.toString(price));prezzo.setPrefSize(50, 50);prezzo.setAlignment(Pos.CENTER);
            prezzo1.setText("Prezzo Opera: ");prezzo1.setPrefSize(75, 50);prezzo1.setAlignment(Pos.CENTER);prezzo1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");labelArtWorkDefault.setAlignment(Pos.CENTER);labelArtWorkDefault.setPrefSize(75, 25);
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(75, 25);
            labelArtistNameDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonAcquista.setText("Acquista");
            buttonAcquista.setPrefSize(90, 25);
            buttonAcquista.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            buttonPreferiti.setText(labelPreferiti);
            buttonPreferiti.setPrefSize(90, 25);
            buttonPreferiti.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            VBox vBox = new VBox(buttonAcquista, buttonPreferiti);
            vBox.setSpacing(2.5);
            vBox.setAlignment(Pos.CENTER);
            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
            buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferiti.setText("Paga con Carte");
                    buttonAcquista.setText("Paga con PayPal");
                    buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);buttonAcquista.setPrefSize(90,35);buttonPreferiti.setPrefSize(90,10);
                        }
                    });
                    buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);buttonAcquista.setPrefSize(90,35);buttonPreferiti.setPrefSize(90,10);
                        }
                    });
                }

            });

            if (arrayListArtWorkId.contains(idOpera)){
                buttonPreferiti.setText("Rimuovi dai Preferiti");
            }
            buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = sa.manageButtonClick(buttonPreferiti,idOpera,idBuyer);
                    buttonPreferiti.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox2, vBox1, prezzo1, prezzo, vBox);
        }

    } public void populateListView(String input) throws SQLException, IOException {
        if (listView.getItems().size()!=0){
            listView =new ListView<>();
        }
        ViewSearchArtWorkBuyer vsb = new ViewSearchArtWorkBuyer();
        List<Integer> arrayOfArtWorkId=null;
        ArtistBean artist=null;
        Blob artWorkBlob =null;

        try{
            List<ArtWorkBean> arrayOfArtWork = vsb.retrieveSearchArtWorkByName(input);
            arrayOfArtWorkId = vsb.retrieveSearchArtWorkId(buy);
            for (ArtWorkBean artWork: arrayOfArtWork) {
                artWorkBlob = vsb.retrieveSearchArtWorkBlob(artWork.getIdOpera());
                artist = vsb.retrieveSearchArtistName(artWork);
                InputStream inputStream = null;
                try {
                    inputStream = artWorkBlob.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(inputStream, 100, 100, true, false);
                listView.getItems().add(new HBoxCellMobile(artWork.getTitolo(), artist.getNome()+" "+artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Aggiungi ai Preferiti", buy.getIdBuyer(), artist.getIdArtista(),arrayOfArtWorkId,input));

            }


        } catch ( ArtWorkNotFoundException throwables) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

}


