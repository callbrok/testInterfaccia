package com.artistcorner.engclasses.others;

import java.net.URL;

public enum ExceptionsTypeMenager {

    EMPTYFIELD(0, "/view/exceptions/EmptyFieldException.fxml"),
    EMPTYPATH(1, "/view/exceptions/EmptyPathException.fxml"),
    DUPLICATEARTWORK(2, "/view/exceptions/DuplicateArtWorkException.fxml"),  //Opera già presente
    PROPOSALNOTFOUND(3, "/view/exceptions/ProposalNotFoundException.fxml"), // Nessuna proposta attuale
    SELLARTNOTFOUND(4, "/view/exceptions/SellNotFoundException.fxml"), // Nessun opera venduta
    ARTWORKNOTFOUND(5, "/view/exceptions/ArtWorkNotFoundException.fxml"); // Nessun opera caricata
    // Aggiungere errore per il recupero della serializzazione?

    private final int type;
    private final String path;

    private ExceptionsTypeMenager(int t, String p){
        this.type = t;
        this.path = p;
    }

    public int getType() {
        return type;
    }

    public static URL getUrl(ExceptionsTypeMenager subview){
        return ExceptionsTypeMenager.class.getResource(subview.path);
    }

}
