package com.palazzisoft.gerbio.integrator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JaxbConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.palazzisoft.gerbio.integrator.catalogo");

        var props = new HashMap<String, Object>();
        props.put("jaxb.formatted.output", false);
        jaxb2Marshaller.setMarshallerProperties(props);
        return jaxb2Marshaller;
    }
}
