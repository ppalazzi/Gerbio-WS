package com.palazzisoft.gerbio.integrator.configuration;

import com.palazzisoft.gerbio.integrator.catalogo.WSMG;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MGConfiguration {

    @Value("${mg.user}")
    private String user;

    @Value("${mg.clientId}")
    private String clientId;

    @Value("${mg.password}")
    private String password;

    @Bean
    public WSMG msmg() {
        return new WSMG();
    }
}
