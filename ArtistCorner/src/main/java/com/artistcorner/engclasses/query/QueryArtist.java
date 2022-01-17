package com.artistcorner.engclasses.query;

import com.artistcorner.model.Proposal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryArtist {

    public static ResultSet selectArtist(Statement stmt, String user) throws SQLException {
        String sql = "SELECT * FROM artista WHERE username ='" + user + "';";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectSellArtWork(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM opera JOIN compra ON opera.idOpera = compra.opera WHERE artista =" + idArtista + ";";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtWorksImage(Statement stmt, int idArtista, String lastAction) throws SQLException {
        String sql = "SELECT immagine FROM opera WHERE artista ='" + idArtista + "';";

        if(lastAction.equals("LAST")){sql = "SELECT immagine FROM opera WHERE artista ='" + idArtista + "' ORDER BY idOpera DESC LIMIT 2;";}

        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllGalleryProposals(Statement stmt, int idArtista, String lastAction) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE artista ='" + idArtista + "';";

        if(lastAction.equals("LAST")){sql = "SELECT * FROM offerta WHERE artista ='" + idArtista + "' ORDER BY idOfferta DESC LIMIT 4;";}

        return stmt.executeQuery(sql);
    }

    public static int updateProposal(Statement stmt, int idOfferta, int newFlag) throws SQLException  {
        String updateStatement = String.format("UPDATE offerta SET flagAccettazione='%s' WHERE idOfferta = %s", newFlag, idOfferta);
        return stmt.executeUpdate(updateStatement);
    }

    public static String insertArtWork() throws SQLException {
        return "INSERT INTO opera(titolo, prezzo, flagVendibile, immagine, artista) VALUES (?, ?, ?, ?, ?)";
    }

    public static ResultSet selectArtGallery(Statement stmt, int artGallery) throws SQLException {
        String sql = "SELECT * FROM galleria WHERE idGalleria ='" + artGallery + "';";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectArtWorkTitle(Statement stmt) throws SQLException  {
        String sql = "SELECT DISTINCT titolo FROM opera ;";
        return stmt.executeQuery(sql);
    }


}
