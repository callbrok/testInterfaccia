package com.artistcorner.controller.guicontroller.mobile.getreccomandation;

import com.artistcorner.controller.applicationcontroller.GetReccomandation;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.Nodo;
import com.artistcorner.engclasses.exceptions.GetRaccomandationProblemException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileGetReccomandation implements Serializable {
    @FXML
    public AnchorPane anchorMain;
    @FXML
    public Label labelUsernameDisplay;
    @FXML
    public Label labelQuestion;
    @FXML
    public AnchorPane anchorResult;
    @FXML
    public Label labelResultSoggetto;
    @FXML
    public Label labelResultCaract;
    @FXML
    public Label labelResultStato;
    @FXML
    public Label labelResultColori;
    @FXML
    public Label labelResultStile;
    @FXML
    public Button buttonReset;

    public static final String OBJECTNODO_PATH = "ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt";

    private double x=0;
    private double y=0;
    private Stage stage;

    GetReccomandation lc = new GetReccomandation();
    List<Nodo> arraylist = lc.initializeTreeTxt(); // Inizializza albero
    int idLivello; // Variabile che tiene conto del livello corrente dell'albero
    ArtistBean art;
    Nodo n;

    /**
     * Deserializza lo stato dell'algoritmo di decisione, nel caso in cui il file contenente l'ultima istanza
     * serializzata ("object.txt") non fosse presente, inizializza l'algoritmo da zero.
     */
    public void inizializeIdLivello() throws IOException, ClassNotFoundException {
        // Controlla prima se c'è un file su cui fare al deserializzazione
        File f = new File(OBJECTNODO_PATH);

        if(f.exists() && !f.isDirectory()) { // Controlla l'esistenza del file object.txt
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(OBJECTNODO_PATH));

            String rispostaSerial = (String)in.readObject();
            Nodo c2 = (Nodo)in.readObject();
            in.close();

            lc.setSerialSolution(rispostaSerial); // Prende l'ultima istanza della soluzione
            labelQuestion.setText(c2.getDomanda()); // Prende la domanda dal nodo serializzato
            idLivello = c2.getIdProprio(); // Prende l'id del nodo serializzato
        } else {
            idLivello = 1; // Inizializzazione dell'algoritmo al primo nodo
        }

    }
    
    public void initialize() throws IOException, ClassNotFoundException {
        makeDraggable();

        labelQuestion.setAlignment(Pos.CENTER);
        labelQuestion.setMaxWidth(362);
        labelQuestion.setWrapText(true);  // Per far andare a capo la linea.

        anchorResult.setVisible(false);
        inizializeIdLivello();

    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    /**
     * Serializza il nodo passato, come oggetto nel file "object.txt".
     */
    public void makeSerializable(Nodo n) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OBJECTNODO_PATH));
        out.writeObject(lc.getSerialSolution()); // Serializza l'ultima istanza di soluzione creata
        out.writeObject(n);  // Serializza l'ultimo nodo
        out.close(); // Also flushes the output
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta negativa e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerNo() throws IOException, GetRaccomandationProblemException {
        // In caso di risposta negativa

        n = lc.decisionTree("N", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta positiva e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerYes() throws IOException, GetRaccomandationProblemException {
        n = lc.decisionTree("Y", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta randomica e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerRand() throws IOException, GetRaccomandationProblemException {
        n = lc.decisionTree("YN", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}            // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());                // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                         // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                  // Serializza il nodo corrente
    }

    public void showSolution(){
        String[] soluzione = lc.getSolution();

        // Visualizza risposte.
        anchorResult.setVisible(true);
        labelResultSoggetto.setText(soluzione[0]);
        labelResultCaract.setText(soluzione[1]);
        labelResultStato.setText(soluzione[2]);
        labelResultColori.setText(soluzione[3]);
        labelResultStile.setText(soluzione[4]);
    }


    /**
     * Resetta l'algoritmo.
     */
    public void resetAlgo() throws IOException {
        Files.delete(Path.of(OBJECTNODO_PATH));    // Cerca il file contenente l'oggetto serializzato e lo elimina

        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {                // Ricarica la scena
            SceneControllerMobile sc = new SceneControllerMobile();
            try {
                sc.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void exitWindow() {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}
