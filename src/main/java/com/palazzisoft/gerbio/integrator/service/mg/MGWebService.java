package com.palazzisoft.gerbio.integrator.service.mg;

import com.palazzisoft.gerbio.integrator.catalogo.ContentRequest;
import com.palazzisoft.gerbio.integrator.catalogo.ProductsRequest;
import com.palazzisoft.gerbio.integrator.catalogo.WSMG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MGWebService {

    private WSMG wsmg;
    private String user;
    private String clientId;
    private String password;

    @Autowired
    public  MGWebService(final WSMG wsmg,
                         @Value("${mg.user}") String user,
                         @Value("${mg.clientId}") String clientId,
                         @Value("${mg.password}") String password) {
        this.wsmg = wsmg;
        this.user = user;
        this.clientId = clientId;
        this.password = password;
    }

    public ProductsRequest getCatalog() {
        return wsmg.getWSMGSoap().getCatalog(clientId, user, password);
    }

    public ContentRequest getContenido() {
        return wsmg.getWSMGSoap().getXMLContenido(clientId, user, password);
    }
}
