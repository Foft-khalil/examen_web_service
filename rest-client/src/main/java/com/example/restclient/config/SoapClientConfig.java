package com.example.restclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Configuration du client SOAP :
 * - Jaxb2Marshaller : sérialise/désérialise les objets JAXB générés
 * - WebServiceTemplate : envoie les messages SOAP au service
 */
@Configuration
public class SoapClientConfig {

    @Value("${soap.service.url}")
    private String soapServiceUrl;

    /**
     * Marshaller JAXB configuré avec le package des classes générées
     * (com.example.personnes, issu du XSD via le plugin JAXB2).
     */
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Package des classes générées par JAXB à partir du XSD
        marshaller.setContextPath("com.example.personnes");
        return marshaller;
    }

    /**
     * Template Spring-WS préconfigurée avec l'URL du service SOAP.
     */
    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri(soapServiceUrl);
        return template;
    }
}
