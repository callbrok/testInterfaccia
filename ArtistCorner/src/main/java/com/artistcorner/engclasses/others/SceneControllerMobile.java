package com.artistcorner.engclasses.others;

import com.artistcorner.controller.guicontroller.mobile.getreccomandation.GuiControllerMobileGetReccomandation;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileArtistSummary;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileBuyerSummary;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileGallerySummary;
import com.artistcorner.controller.guicontroller.mobile.uploadartwork.GuiControllerMobileUploadArtwork;
import com.artistcorner.controller.guicontroller.mobile.manageproposals.GuiControllerMobileManageProposals;
import com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer.GuiControllerMobileViewFavouritesBuyer;
import com.artistcorner.controller.guicontroller.mobile.manageartworks.GuiControllerMobileManageArtworks;
import com.artistcorner.controller.guicontroller.mobile.viewprofilegallery.GuiControllerMobileViewProfileGallery;
import com.artistcorner.controller.guicontroller.mobile.viewsentartgalleryproposal.GuiControllerMobileViewSentArtGalleryProposal;
import com.artistcorner.controller.guicontroller.mobile.viewsoldartworks.GuiControllerMobileViewSoldArtworks;
import com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer.GuiControllerMobileViewSearchArtWorkBuyer;
import com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery.GuiControllerMobileViewSearchArtWorkGallery;
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
import java.sql.SQLException;

public class SceneControllerMobile {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static  final String CSS_PATH = "/css/" + "mobile/" + "main.css";


    public void switchToSceneMainArtista(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/login/ArtistSummaryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event, ArtistBean art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ManageArtworksMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileManageArtworks gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event, ArtistBean art) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/GetReccomandationMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileGetReccomandation gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ViewSoldArtworksMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewSoldArtworks gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ManageProposalsMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileManageProposals gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneUploadOpera(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/UploadArtWorkMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/mobile/login/LoginMobileView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneSearchArtWorkBuyer(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/BuyerSearchMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewSearchArtWorkBuyer gcas = loader.getController();
        gcas.getBuyer(buy);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneBuyerSummary(ActionEvent event, BuyerBean buy) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/login/BuyerSummaryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileBuyerSummary gcas = loader.getController();
        gcas.getBuyer(buy);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToSceneFavouritesBuyer(ActionEvent event, BuyerBean buy) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/BuyerFavouritesMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewFavouritesBuyer gcas = loader.getController();
        gcas.getBuyer(buy);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneSentArtGalleryProposal(ActionEvent event, ArtGalleryBean gal) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/SentArtGalleryProposalMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewSentArtGalleryProposal gcas = loader.getController();
        gcas.getGallery(gal);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneGallerySummary(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/login/GallerySummaryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileGallerySummary gcas = loader.getController();
        gcas.getGallery(gal);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneSearchArtWorkGallery(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/GallerySearchMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewSearchArtWorkGallery gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneProfiloGallery(ActionEvent event,ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ProfileGalleryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewProfileGallery gcas = loader.getController();
        gcas.getGallery(gal);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
