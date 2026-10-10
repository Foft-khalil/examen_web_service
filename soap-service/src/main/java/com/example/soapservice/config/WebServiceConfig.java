package com.example.soapservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Configuration Spring Web Services :
 * - Enregistre le MessageDispatcherServlet sur /ws/*
 * - Génère automatiquement le WSDL à partir du XSD
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * Enregistre le servlet Spring-WS sur /ws/*.
     * Ce chemin sera donc : http://localhost:8081/ws/...
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Définition WSDL générée automatiquement.
     * Accessible à : http://localhost:8081/ws/personnes.wsdl
     *
     * Le nom du bean ("personnes") détermine le chemin du WSDL.
     */
    @Bean(name = "personnes")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema personnesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PersonnesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/personnes");
        wsdl11Definition.setSchema(personnesSchema);
        return wsdl11Definition;
    }

    /**
     * Charge le schéma XSD depuis le classpath.
     */
    @Bean
    public XsdSchema personnesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("personnes.xsd"));
    }
}
