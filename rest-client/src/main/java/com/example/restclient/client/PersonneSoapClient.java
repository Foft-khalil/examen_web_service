package com.example.restclient.client;

import com.example.personnes.*;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

/**
 * Client SOAP utilisant WebServiceTemplate.
 * Chaque méthode correspond à une opération du service SOAP.
 *
 * Les classes (AddPersonneRequest, GetPersonneResponse, etc.)
 * sont générées par JAXB à partir de personnes.xsd.
 */
@Component
public class PersonneSoapClient {

    private final WebServiceTemplate webServiceTemplate;

    public PersonneSoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    // ─────────────────────────────────────────────────────────────
    // addPersonne
    // ─────────────────────────────────────────────────────────────
    public Personne addPersonne(String nom, String prenom, int age) {
        AddPersonneRequest request = new AddPersonneRequest();
        request.setNom(nom);
        request.setPrenom(prenom);
        request.setAge(age);

        AddPersonneResponse response = (AddPersonneResponse)
                webServiceTemplate.marshalSendAndReceive(request);
        return response.getPersonne();
    }

    // ─────────────────────────────────────────────────────────────
    // getPersonne
    // ─────────────────────────────────────────────────────────────
    public Personne getPersonne(Long id) {
        GetPersonneRequest request = new GetPersonneRequest();
        request.setId(id);

        GetPersonneResponse response = (GetPersonneResponse)
                webServiceTemplate.marshalSendAndReceive(request);
        return response.getPersonne();
    }

    // ─────────────────────────────────────────────────────────────
    // getAllPersonnes
    // ─────────────────────────────────────────────────────────────
    public List<Personne> getAllPersonnes() {
        GetAllPersonnesRequest request = new GetAllPersonnesRequest();

        GetAllPersonnesResponse response = (GetAllPersonnesResponse)
                webServiceTemplate.marshalSendAndReceive(request);
        return response.getPersonnes();
    }

    // ─────────────────────────────────────────────────────────────
    // updatePersonne
    // ─────────────────────────────────────────────────────────────
    public Personne updatePersonne(Long id, String nom, String prenom, int age) {
        UpdatePersonneRequest request = new UpdatePersonneRequest();
        request.setId(id);
        request.setNom(nom);
        request.setPrenom(prenom);
        request.setAge(age);

        UpdatePersonneResponse response = (UpdatePersonneResponse)
                webServiceTemplate.marshalSendAndReceive(request);
        return response.getPersonne();
    }

    // ─────────────────────────────────────────────────────────────
    // deletePersonne
    // ─────────────────────────────────────────────────────────────
    public String deletePersonne(Long id) {
        DeletePersonneRequest request = new DeletePersonneRequest();
        request.setId(id);

        DeletePersonneResponse response = (DeletePersonneResponse)
                webServiceTemplate.marshalSendAndReceive(request);
        return response.getMessage();
    }
}
