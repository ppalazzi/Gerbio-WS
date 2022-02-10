package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.catalogo.ProductsRequest;
import com.palazzisoft.gerbio.integrator.exception.GerbioException;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/data")
@Slf4j
public class DataController {

    private MGWebService mgWebService;

    public DataController(final MGWebService mgWebService) {
        this.mgWebService = mgWebService;
    }

    @GetMapping(value = "/mgProducts")
    public ResponseEntity<List<Product>> mgProducts() throws GerbioException {
        ProductsRequest productRequest = mgWebService.getCatalog();
        if (productRequest.getResult() != 0) {
            throw new GerbioException(productRequest.getMessage());
        }

        List<Product> productos = productRequest.getListProducts().getProduct();

        return ResponseEntity.ok(productos);
    }
}
