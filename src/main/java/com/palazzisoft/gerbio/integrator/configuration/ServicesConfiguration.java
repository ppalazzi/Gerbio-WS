package com.palazzisoft.gerbio.integrator.configuration;

import com.palazzisoft.gerbio.integrator.client.CatalogClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ServicesConfiguration {

    @Bean
    public CatalogClient catalogClient(Jaxb2Marshaller marshaller) {
        CatalogClient catalogClient = new CatalogClient();
        catalogClient.setDefaultUri("https://ecommerce.microglobal.com.ar/WSMG/WSMG.asmx?WSDL");
        catalogClient.setMarshaller(marshaller);
        catalogClient.setUnmarshaller(marshaller);

        // https://ecommerce.microglobal.com.ar/WSMG/WSMG.asmx?WSDL

        return catalogClient;
    }
}
