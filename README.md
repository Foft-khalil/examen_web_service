# TP — API REST Spring Boot consommant un service SOAP (CRUD Personne)

## Architecture globale

```
 Client HTTP (JSON)
       |
       v (port 8080)
 rest-client  [PersonneController -> PersonneSoapClient -> WebServiceTemplate]
       |
       | SOAP/XML
       v (port 8081)
 soap-service  [PersonneEndpoint -> PersonneRepository -> H2 in-memory]
```

---

## Structure des projets

```
examen_ws/
├── soap-service/
│   ├── pom.xml
│   └── src/main/
│       ├── resources/
│       │   ├── personnes.xsd              (schema XSD + contrat SOAP)
│       │   └── application.properties    (port=8081, H2, JPA)
│       └── java/com/example/soapservice/
│           ├── SoapServiceApplication.java
│           ├── config/WebServiceConfig.java   (Spring-WS, WSDL auto)
│           ├── model/PersonneEntity.java      (entite JPA)
│           ├── repository/PersonneRepository.java
│           └── endpoint/PersonneEndpoint.java  (@Endpoint 5 operations)
│
└── rest-client/
    ├── pom.xml
    └── src/main/
        ├── resources/
        │   ├── personnes.xsd              (copie du XSD pour JAXB)
        │   └── application.properties    (port=8080, url SOAP)
        └── java/com/example/restclient/
            ├── RestClientApplication.java
            ├── config/SoapClientConfig.java   (Jaxb2Marshaller + WebServiceTemplate)
            ├── dto/PersonneDto.java           (DTO JSON pour Jackson)
            ├── client/PersonneSoapClient.java  (client SOAP 5 methodes)
            └── controller/PersonneController.java (@RestController 5 endpoints)
```

---

## Pre-requis

- Java 17+
- Maven 3.6+

---

## Lancement

### 1. Service SOAP (port 8081)

```bash
cd soap-service
mvn clean install
mvn spring-boot:run
```

WSDL disponible sur : http://localhost:8081/ws/personnes.wsdl

### 2. Client REST (port 8080)

```bash
cd rest-client
mvn clean install
mvn spring-boot:run
```

API REST disponible sur : http://localhost:8080/api/personnes

---

## Test des endpoints REST

```bash
# Creer
curl -X POST http://localhost:8080/api/personnes \
  -H "Content-Type: application/json" \
  -d '{"nom":"Ndiaye","prenom":"Mohamed","age":30}'

# Lister tous
curl http://localhost:8080/api/personnes

# Lire par id
curl http://localhost:8080/api/personnes/1

# Modifier
curl -X PUT http://localhost:8080/api/personnes/1 \
  -H "Content-Type: application/json" \
  -d '{"nom":"Keita","prenom":"Awa","age":25}'

# Supprimer
curl -X DELETE http://localhost:8080/api/personnes/1
```

---

## Classes JAXB generees (target/generated-sources/jaxb)

| Classe | Role |
|---|---|
| Personne | Type de base |
| AddPersonneRequest / AddPersonneResponse | Creer |
| GetPersonneRequest / GetPersonneResponse | Lire par id |
| GetAllPersonnesRequest / GetAllPersonnesResponse | Lire tous |
| UpdatePersonneRequest / UpdatePersonneResponse | Modifier |
| DeletePersonneRequest / DeletePersonneResponse | Supprimer |

---

## Console H2

URL : http://localhost:8081/h2-console
- JDBC URL : jdbc:h2:mem:personnesdb
- User : sa  /  Password : (vide)
