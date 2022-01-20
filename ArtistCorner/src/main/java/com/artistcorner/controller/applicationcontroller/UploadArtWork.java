package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;

import java.io.FileNotFoundException;
import java.sql.SQLException;


public class UploadArtWork {

    public void uploadImage(ArtWorkBean upArt, int idArtista, String filePath) throws DuplicateArtWorkException, EmptyPathException, EmptyFieldException {
        // Dato che uploadImage non cattura l'eccezzione EmptyFieldExceptio
        // lo metto in throws.


        if(upArt.getTitolo().equals("")) {
            throw new EmptyFieldException("Lasciati campi per l'upload vuoti.");
        }

        if(filePath.equals("")){
            throw new EmptyPathException("Nessun file selezionato.");
        }


        ArtistDAO.saveArtWork(upArt, idArtista, filePath);


    }



}
