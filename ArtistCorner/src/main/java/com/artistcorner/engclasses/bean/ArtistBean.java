package com.artistcorner.engclasses.bean;

public class ArtistBean {
    private int idArtista;
    private String nome;
    private String cognome;

    public ArtistBean(int newId, String newNome, String newCognome){
        this.nome = newNome;
        this.cognome = newCognome;
        this.idArtista = newId;
    }

    public ArtistBean(String newNome, String newCognome){
        this.nome = newNome;
        this.cognome = newCognome;
        this.idArtista = 0;
    }

    public String getNome() {return nome;}
    public int getIdArtista() {return idArtista;}
    public String getCognome() {return cognome;}
}
