package com.example.soapservice.endpoint;

import com.example.personnes.*;
import com.example.soapservice.model.PersonneEntity;
import com.example.soapservice.repository.PersonneRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * Endpoint SOAP exposant les 5 opérations CRUD sur Personne.
 * Le namespace doit correspondre à celui déclaré dans personnes.xsd.
 */
@Endpoint
public class PersonneEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/personnes";

    private final PersonneRepository repository;

    public PersonneEndpoint(PersonneRepository repository) {
        this.repository = repository;
    }

    // ─────────────────────────────────────────────────────────────
    // addPersonne
    // ─────────────────────────────────────────────────────────────
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonneRequest")
    @ResponsePayload
    public AddPersonneResponse addPersonne(@RequestPayload AddPersonneRequest request) {
        PersonneEntity entity = new PersonneEntity(
                request.getNom(),
                request.getPrenom(),
                request.getAge()
        );
        entity = repository.save(entity);

        AddPersonneResponse response = new AddPersonneResponse();
        response.setPersonne(toSoap(entity));
        return response;
    }

    // ─────────────────────────────────────────────────────────────
    // getPersonne
    // ─────────────────────────────────────────────────────────────
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonneRequest")
    @ResponsePayload
    public GetPersonneResponse getPersonne(@RequestPayload GetPersonneRequest request) {
        PersonneEntity entity = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Personne non trouvée : id=" + request.getId()));

        GetPersonneResponse response = new GetPersonneResponse();
        response.setPersonne(toSoap(entity));
        return response;
    }

    // ─────────────────────────────────────────────────────────────
    // getAllPersonnes
    // ─────────────────────────────────────────────────────────────
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPersonnesRequest")
    @ResponsePayload
    public GetAllPersonnesResponse getAllPersonnes(
            @RequestPayload GetAllPersonnesRequest request) {

        List<PersonneEntity> entities = repository.findAll();
        GetAllPersonnesResponse response = new GetAllPersonnesResponse();
        entities.forEach(e -> response.getPersonnes().add(toSoap(e)));
        return response;
    }

    // ─────────────────────────────────────────────────────────────
    // updatePersonne
    // ─────────────────────────────────────────────────────────────
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePersonneRequest")
    @ResponsePayload
    public UpdatePersonneResponse updatePersonne(
            @RequestPayload UpdatePersonneRequest request) {

        PersonneEntity entity = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Personne non trouvée : id=" + request.getId()));

        entity.setNom(request.getNom());
        entity.setPrenom(request.getPrenom());
        entity.setAge(request.getAge());
        entity = repository.save(entity);

        UpdatePersonneResponse response = new UpdatePersonneResponse();
        response.setPersonne(toSoap(entity));
        return response;
    }

    // ─────────────────────────────────────────────────────────────
    // deletePersonne
    // ─────────────────────────────────────────────────────────────
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonneRequest")
    @ResponsePayload
    public DeletePersonneResponse deletePersonne(
            @RequestPayload DeletePersonneRequest request) {

        if (!repository.existsById(request.getId())) {
            throw new RuntimeException("Personne non trouvée : id=" + request.getId());
        }
        repository.deleteById(request.getId());

        DeletePersonneResponse response = new DeletePersonneResponse();
        response.setMessage("Personne id=" + request.getId() + " supprimée avec succès.");
        return response;
    }

    // ─────────────────────────────────────────────────────────────
    // Helper : entité JPA → objet SOAP (JAXB)
    // ─────────────────────────────────────────────────────────────
    private Personne toSoap(PersonneEntity entity) {
        Personne p = new Personne();
        p.setId(entity.getId());
        p.setNom(entity.getNom());
        p.setPrenom(entity.getPrenom());
        p.setAge(entity.getAge());
        return p;
    }
}
