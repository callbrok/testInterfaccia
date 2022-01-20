package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GuiControllerSignUp {
    @FXML
    private AnchorPane anchorDuplicateUser;
    @FXML
    private Label labelExcepDuplicate;
    @FXML
    private TextField textFieldUserArtist;
    @FXML
    private TextField textFieldPassArtist;
    @FXML
    private TextField textFieldCognomeArtist;
    @FXML
    private TextField textFieldNomeArtist;


    private SignUp signUp = new SignUp();

    public void registerArtist() {
        UserBean userReg = new UserBean(textFieldUserArtist.getText(), textFieldPassArtist.getText(), "artista");
        ArtistBean artistReg = new ArtistBean(textFieldNomeArtist.getText(), textFieldCognomeArtist.getText());

        try {
            signUp.registerArtist(userReg, artistReg);
        } catch (DuplicateUserException e) {
            labelExcepDuplicate.setText(e.getMessage());
            anchorDuplicateUser.setVisible(true);
        }

        textFieldUserArtist.clear();
        textFieldPassArtist.clear();
        textFieldNomeArtist.clear();
        textFieldCognomeArtist.clear();
    }


    public void initialize(){
       labelExcepDuplicate.setMaxWidth(299);
       labelExcepDuplicate.setWrapText(true);
       anchorDuplicateUser.setVisible(false);
    }

}
