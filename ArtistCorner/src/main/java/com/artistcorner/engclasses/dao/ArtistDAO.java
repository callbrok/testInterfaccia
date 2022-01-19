package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.ArtGalleryNotFoundException;
import com.artistcorner.engclasses.exceptions.ArtistNotFoundException;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.ProposalsManagementProblemException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArtistDAO {

    private ArtistDAO(){
        // Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract utility classes,
        // which can be extended, should not have public constructors.
        //
        // Java adds an implicit public constructor to every class which does not define at least one explicitly. Hence,
        // at least one non-public constructor should be defined.
        throw new IllegalStateException("Utility class");
    }

    public static Artist retrieveArtist(UserBean usr){
        Artist ar = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectArtist(stmt, usr.getUsername());

            if (!rs.first()){ // rs empty
                throw new ArtistNotFoundException("Artista non trovato");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String cognomeArtista = rs.getString("cognome");
                String nomeArtista = rs.getString("nome");
                int idArtista = rs.getInt("idArtista");

                ar = new Artist(idArtista, nomeArtista, cognomeArtista);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return ar;
    }


    public static List<ArtWork> retrieveSellArtWorks(int idUsr){
        ArrayList<ArtWork> listOfArtWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectSellArtWork(stmt, idUsr);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo = rs.getString("titolo");
                int venduto = rs.getInt("flagVendibile");
                double prezzo = rs.getDouble("prezzo");
                int idOpera = rs.getInt("idOpera");
                int artistaId = rs.getInt("artista");

                ArtWork at = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId);
                listOfArtWork.add(at);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return listOfArtWork;
    }


    public static void saveArtWork(ArtWorkBean upArt, int idArtist, String filePath) throws SQLException {
        Statement stmt = null;
        PreparedStatement prStmt = null;
        Connection conn = null;

        File file = new File(filePath); // Apre il file relativo al path passato.

        try(FileInputStream fis = new FileInputStream(new File(filePath))) {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // Check Duplicate ArtWork
                ResultSet rs = QueryArtist.selectArtWorkTitle(stmt);
                while (rs.next()) {
                    // lettura delle colonne "by name"
                    String artworkTitle = rs.getString("titolo");

                    if (artworkTitle.equals(upArt.getTitolo())){
                        throw new DuplicateArtWorkException("Opera attualmente già caricata.");
                    }
                }

                rs.close();
                stmt.close();

            prStmt = conn.prepareStatement(QueryArtist.insertArtWork());   // Prende la query SQL.

            // Setta i prepared statement.
            prStmt.setString(1, upArt.getTitolo());
            prStmt.setDouble(2, upArt.getPrezzo());
            prStmt.setInt(3, upArt.getFlagVendibile());
            prStmt.setBinaryStream(4, fis, (int) file.length());
            prStmt.setInt(5, idArtist);

            prStmt.executeUpdate();
            prStmt.close();

        } catch (DuplicateArtWorkException | SQLException | ClassNotFoundException | IOException e2) {
            e2.printStackTrace();
        } finally {
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
            if (stmt != null)
                stmt.close();
        }
    }



    public static List<Blob> retrieveAllArtWorksImage(int idUsr, String lastAction){
        ArrayList<Blob> listOfArtWorksImage = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllArtWorksImage(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                Blob immagine = rs.getBlob("immagine");
                listOfArtWorksImage.add(immagine);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return listOfArtWorksImage;
    }


    public static List<Proposal> retrieveArtGalleryProposals(int idUsr, String lastAction){
        ArrayList<Proposal> listOfProposal = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllGalleryProposals(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idOfferta = rs.getInt("idOfferta");
                int galleria = rs.getInt("galleria");
                int flagAccettazione = rs.getInt("flagAccettazione");

                Proposal pr = new Proposal(idOfferta, idUsr, galleria, flagAccettazione);
                listOfProposal.add(pr);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e4) {
            e4.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return listOfProposal;
    }


    public static void updateProposal(int idOfferta, int newFlag) throws SQLException {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryArtist.updateProposal(stmt, idOfferta, newFlag);

            if(result == -1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}

        } catch (Exception e5){
          e5.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }


    public static ArtGallery retrieveArtGallery(int idGallery){
        ArtGallery artG = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectArtGallery(stmt, idGallery);

            if (!rs.first()){ // rs empty
                throw new ArtGalleryNotFoundException("Nessuna galleria trovata");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idGalleria = rs.getInt("idGalleria");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                String indirizzo = rs.getString("indirizzo");
                String username = rs.getString("username");

                artG = new ArtGallery(idGalleria, nome, descrizione, indirizzo, username);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e6) {
            e6.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return artG;
    }





}
