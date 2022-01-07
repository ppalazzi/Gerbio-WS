package com.palazzisoft.gerbio.integrator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class CSVCategoryReader {

    InputStreamReader componentesCSV;
    InputStreamReader impresorasCSV;
    InputStreamReader monitoresCSV;
    InputStreamReader notebooksCSV;
    InputStreamReader servidoresCSV;
    InputStreamReader variosCSV;

    public CSVCategoryReader() throws IOException {
        initializeIS();
    }

    private void initializeIS() throws IOException{
        componentesCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-componentespc.csv")).openStream()));
        impresorasCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-impresoras.csv")).openStream()));
        monitoresCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-monitores.csv")).openStream()));
        notebooksCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-notebooks.csv")).openStream()));
        servidoresCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-servidores.csv")).openStream()));
        variosCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-varios.csv")).openStream()));
    }

    public List<String> readVariosCSV() throws IOException{
        List<String> variosCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(variosCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                variosCsv.addAll((values));
            }
        }
        return variosCsv;
    }

    public List<String> readComponentesCSV() throws IOException{
        List<String> componentesCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(componentesCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                componentesCsv.addAll((values));
            }
        }
        return componentesCsv;
    }

    public List<String> readImpresorasCSV() throws IOException{
        List<String> impresorasCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(impresorasCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                impresorasCsv.addAll((values));
            }
        }
        return impresorasCsv;
    }

    public List<String> readMonitoresCSV() throws IOException{
        List<String> monitoresCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(monitoresCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                monitoresCsv.addAll((values));
            }
        }
        return monitoresCsv;
    }

    public List<String> readNotebooksCSV() throws IOException{
        List<String> notebooksCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(notebooksCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                notebooksCsv.addAll((values));
            }
        }
        return notebooksCsv;
    }

    public List<String> readServidoresCSV() throws IOException{
        List<String> servidoresCsv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(servidoresCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                servidoresCsv.addAll((values));
            }
        }
        return servidoresCsv;
    }


}
