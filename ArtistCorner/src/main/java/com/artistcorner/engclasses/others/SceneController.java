package com.artistcorner.engclasses.others;


import com.artistcorner.controller.guicontroller.getreccomandation.GuiControllerGetReccomandation;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerArtistSummary;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerBuyerSummary;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerGallerySummary;
import com.artistcorner.controller.guicontroller.manageproposals.GuiControllerManageProposals;
import com.artistcorner.controller.guicontroller.uploadartwork.GuiControllerUploadArtwork;
import com.artistcorner.controller.guicontroller.viewfavouritesbuyer.GuiControllerViewFavouritesBuyer;
import com.artistcorner.controller.guicontroller.manageartworks.GuiControllerManageArtworks;
import com.artistcorner.controller.guicontroller.viewprofilogallery.GuiControllerViewProfiloGallery;
import com.artistcorner.controller.guicontroller.viewsentartgalleryproposal.GuiControllerViewSentArtGalleryProposal;
import com.artistcorner.controller.guicontroller.viewsoldartworks.GuiControllerViewSoldArtworks;
import com.artistcorner.controller.guicontroller.viewsearchartworkbuyer.GuiControllerViewSearchArtWorkBuyer;
import com.artistcorner.controller.guicontroller.viewsearchartworkgallery.GuiControllerViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public static final String CSS_PATH = "/css/" + "desktop/" + "main.css";

    public void switchToSceneMainArtista(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/ArtistSummaryView.fxml"));
        root = loader.load();

        GuiControllerArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event, ArtistBean art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManageArtworksView.fxml"));
        root = loader.load();

        GuiControllerManageArtworks gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event, ArtistBean art) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GetReccomandationView.fxml"));
        root = loader.load();

        GuiControllerGetReccomandation gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewSoldArtworksView.fxml"));
        root = loader.load();

        GuiControllerViewSoldArtworks gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManageProposalsView.fxml"));
        root = loader.load();

        GuiControllerManageProposals gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneUploadOpera(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UploadArtWork.fxml"));
        root = loader.load();

        GuiControllerUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/login/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void deleteSerialNodo(int idArtista) throws IOException {
        String objetNodoPath = "ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo_" + idArtista + ".txt";

        if(Files.exists(Path.of(objetNodoPath)) && !Files.isDirectory(Path.of(objetNodoPath))) {Files.delete(Path.of(objetNodoPath));}
    }

    public void switchToAnalytics(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/ViewLogAnalytics.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAnalyticsSA(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/ViewSALogAnalytics.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneBuyerSummary(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/BuyerSummaryView.fxml"));
        root = loader.load();

        GuiControllerBuyerSummary gcas = loader.getController();
        gcas.getBuyer(buy);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneSearchArtWorkBuyer(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BuyerSearchView.fxml"));
        root = loader.load();

        GuiControllerViewSearchArtWorkBuyer gcas = loader.getController();
        gcas.getBuyer(buy);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneFavouritesBuyer(ActionEvent event, BuyerBean buy) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BuyerFavouriteView.fxml"));
        root = loader.load();

        GuiControllerViewFavouritesBuyer gcas = loader.getController();
        gcas.getBuyer(buy);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public void switchToSceneSearchArtWorkGallery(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ArtGallerySearchView.fxml"));
        root = loader.load();

        GuiControllerViewSearchArtWorkGallery gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneGallerySummary(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/ArtGallerySummaryView.fxml"));
        root = loader.load();

        GuiControllerGallerySummary gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloGallery(ActionEvent event, ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ArtGalleryProfiloView.fxml"));
        root = loader.load();

        GuiControllerViewProfiloGallery gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneSentArtGalleryProposal(ActionEvent event,ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SentArtGalleryProposalView.fxml"));
        root = loader.load();
        GuiControllerViewSentArtGalleryProposal gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
