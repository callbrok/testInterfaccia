package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.Artist;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ViewProfile {

    public List<Blob> retrieveAllArtWorksImage(ArtistBean artBean) throws ArtWorkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        List<Blob> listOfArtWorksImage = ArtistDAO.retrieveAllArtWorksImage(art.getIdArtista(), "");  // Prendi tutte le opere caricate dall'artista.

        if(listOfArtWorksImage.isEmpty()){
            throw new ArtWorkNotFoundException("Nessun opera caricata");
        }

        return listOfArtWorksImage;

    }

}
