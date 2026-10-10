package com.example.restclient.controller;

import com.example.personnes.Personne;
import com.example.restclient.client.PersonneSoapClient;
import com.example.restclient.dto.PersonneDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST exposant les 5 endpoints CRUD sur /api/personnes.
 * Toutes les données transitent par le service SOAP — aucun stockage local.
 */
@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private final PersonneSoapClient soapClient;

    public PersonneController(PersonneSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    // ─────────────────────────────────────────────────────────────
    // GET /api/personnes  →  liste toutes les personnes
    // ─────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<PersonneDto>> getAllPersonnes() {
        List<Personne> personnes = soapClient.getAllPersonnes();
        List<PersonneDto> dtos = personnes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ─────────────────────────────────────────────────────────────
    // GET /api/personnes/{id}  →  récupère une personne
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<PersonneDto> getPersonne(@PathVariable Long id) {
        Personne p = soapClient.getPersonne(id);
        return ResponseEntity.ok(toDto(p));
    }

    // ─────────────────────────────────────────────────────────────
    // POST /api/personnes  →  crée une personne
    // ─────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<PersonneDto> createPersonne(@RequestBody PersonneDto dto) {
        Personne created = soapClient.addPersonne(
                dto.getNom(),
                dto.getPrenom(),
                dto.getAge()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
    }

    // ─────────────────────────────────────────────────────────────
    // PUT /api/personnes/{id}  →  modifie une personne
    // ─────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<PersonneDto> updatePersonne(
            @PathVariable Long id,
            @RequestBody PersonneDto dto) {

        Personne updated = soapClient.updatePersonne(
                id,
                dto.getNom(),
                dto.getPrenom(),
                dto.getAge()
        );
        return ResponseEntity.ok(toDto(updated));
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE /api/personnes/{id}  →  supprime une personne
    // ─────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonne(@PathVariable Long id) {
        String message = soapClient.deletePersonne(id);
        return ResponseEntity.ok(message);
    }

    // ─────────────────────────────────────────────────────────────
    // Helper : objet SOAP (JAXB) → DTO JSON
    // ─────────────────────────────────────────────────────────────
    private PersonneDto toDto(Personne p) {
        return new PersonneDto(p.getId(), p.getNom(), p.getPrenom(), p.getAge());
    }
}
