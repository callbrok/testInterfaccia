package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.others.Nodo;
import com.artistcorner.engclasses.exceptions.GetRaccomandationProblemException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetRecommendation {
    // Inizializza la risposta dell'algoritmo.
    public static final String NO_ANSWER = "Nessuna Risposta";
    public static final String OBJECTNODO_PATH = "ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo_";
    private Random random = new Random();

    private String[] risposta = {NO_ANSWER,NO_ANSWER,NO_ANSWER,NO_ANSWER,NO_ANSWER};

    /**
     * Inizializza l'albero con i nodi, presi dal file "treeStructure.txt".
     */
    public List<Nodo> initializeTreeTxt() {
        ArrayList<Nodo> arraylist = new ArrayList<>();

        String line = "";
        String splitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader("ArtistCorner/src/main/resources/auxiliaryfacilities/treeStructure.txt"))) {

            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                if (line.charAt(0) == '/'){continue;}  // salta le righe di commento nel file di testo

                String[] nodo = line.split(splitBy);   // tokenizza la linea tramite il delimitatore ','

                Nodo node = new Nodo();
                
                node.setIdAppartenenza(Integer.parseInt(nodo[0]));
                node.setIdProprio(Integer.parseInt(nodo[1]));
                node.setDecisione(nodo[2]);
                node.setDomanda(nodo[3]);
                node.setSolutionPart(nodo[4]);
                node.setKeyObj(nodo[5]);

                arraylist.add(node);   // inizializza un singolo nodo dell'albero
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return arraylist;
    }

    /**
     * Ritorna il nodo da visualizzare a video.
     */
    public Nodo decisionTree(String str, List<Nodo> arraylist, int idLivello) throws GetRaccomandationProblemException {
        Nodo endNode = new Nodo();

        endNode.setIdProprio(0);

        Nodo currentNode = getCurrentNode(idLivello, arraylist); // Prende il nodo corrente.

        String [] arrRandAns = {"Y", "N"};
        int select = random.nextInt(arrRandAns.length);
        String randomChoice = arrRandAns[select];  // Inizializza la scelta randomica.

        if (str.equals("YN")){str=randomChoice;}

        // Passa l'identificativo della soluzione e la parte della soluzione del nodo corrente.
        if (str.equals("Y")) {
            createSolution(currentNode.getSolutionPart(), currentNode.getKeyObj());
        }

        for (Nodo n : arraylist) {
            // Se il nodo di risposta affermativa/negativa ha come padre il nodo corrente identificato dall'idLivello.
            if (n.getDecisione().contains(str) && n.getIdAppartenenza() == idLivello) {
                return n;
            }
        }

        return endNode; // Mostra nodo di fine albero.
    }

    /**
     * Ritorna il nodo corrente.
     */
    public Nodo getCurrentNode(int idLivello, List<Nodo> arraylist){
        // Scorre tutti i nodi e ne ritorna quello con idProprio + idLivello, ergo il
        // nodo corrente.
        for (Nodo n : arraylist) {
            if (n.getIdProprio() == idLivello) {
                return n;
            }
        }
        return null;
    }

    /**
     * Ritorna la soluzione.
     */
    public String[] getSolution(){ return risposta;}

    // Ritorna la soluzione per la serializzazione.
    public String getSerialSolution(){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<risposta.length;i++){
            stringBuilder.append(risposta[i]);
            stringBuilder.append("-");
        }

        return stringBuilder.toString();
    }

    /**
     * Imposta la risposta.
     */
    public void setSerialSolution( String serialRisposta){
        risposta = serialRisposta.split("-", risposta.length);
        risposta[4] = risposta[4].substring(0, risposta[4].length()-1);
    }

    /**
     * Crea la soluzione.
     */
    public void createSolution(String sol, String key) throws GetRaccomandationProblemException {
        // A seconda della key associata alla domanda inserisco una parte della
        // soluzione nella stringa apposita.
        switch (key){
            case "[A]":
                risposta[0] = sol;
                break;
            case "[W]":
                risposta[1] = sol;
                break;
            case "[S]":
                risposta[2] = sol;
                break;
            case "[D]":
                risposta[3] = sol;
                break;
            case "[R]":
                risposta[4] = sol;
                break;
            default:
                throw new GetRaccomandationProblemException("Problema nella compilazione della risposta");
        }

    }

    /**
     * Deserializza il nodo.
     */
    public Nodo deserializaStartNode(ArtistBean art) {
        // Controlla prima se c'è un file su cui fare al deserializzazione
        File f = new File(OBJECTNODO_PATH + art.getIdArtista() + ".txt");
        Nodo nodeToReturn = new Nodo();

        if (f.exists() && !f.isDirectory()) { // Controlla l'esistenza del file object.txt
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
                nodeToReturn = (Nodo) in.readObject();
                setSerialSolution(nodeToReturn.getSolutionS()); // Prende l'ultima istanza della soluzione e la reimposta
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return nodeToReturn;
    }


    /**
     * Serializza il nodo passato, come oggetto nel file "object_*id.txt".
     */
    public void makeSerializable(ArtistBean art, Nodo n) throws FileNotFoundException {
        n.setSolutionS(getSerialSolution());

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OBJECTNODO_PATH + art.getIdArtista() + ".txt"))) {
            out.writeObject(n);  // Serializza l'ultimo nodo con la relativa ultima istanza di soluzione creata
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
