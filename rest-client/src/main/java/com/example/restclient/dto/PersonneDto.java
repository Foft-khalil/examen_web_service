package com.example.restclient.dto;

/**
 * DTO (Data Transfer Object) JSON utilisé par l'API REST.
 * Ce POJO est sérialisé/désérialisé par Jackson.
 */
public class PersonneDto {

    private Long id;
    private String nom;
    private String prenom;
    private int age;

    // ---- Constructeurs ----

    public PersonneDto() {}

    public PersonneDto(Long id, String nom, String prenom, int age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    // ---- Getters / Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
