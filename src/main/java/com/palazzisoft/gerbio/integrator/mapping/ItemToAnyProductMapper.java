package com.palazzisoft.gerbio.integrator.mapping;

import com.palazzisoft.gerbio.integrator.model.anymarket.*;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.model.mg.TechnicalSpec;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

public class ItemToAnyProductMapper {

    public AnyProduct mapItemToAnyProduct (AnyProduct anyProduct, Item item) {
        List<AnyImage> anyImages = new ArrayList<>();
        List<AnyProductCharacteristic> anyProductCharacteristics = new ArrayList<>();

        final String title = item.getDescription().getShort_().length() < 120 ? item.getDescription().getShort_()
                : item.getDescription().getShort_().substring(0, 119);

        final String description = anyProduct.getDescription().length() < 120 ? anyProduct.getDescription()
                : anyProduct.getDescription().substring(0, 119);

        anyProduct.setTitle(title.replaceAll("/", "-"));
        anyProduct.setDescription(description.replaceAll("/", "-"));
        anyProduct.setDefinitionPriceScope("SKU");
        anyProduct.setCalculatedPrice(false);

        AnySku anySku = anyProduct.getSkus().get(0);
        anySku.setPrice(anyProduct.getPriceFactor());
        anySku.setSellPrice(anyProduct.getPriceFactor());
        anyProduct.setPriceFactor(1d);
        anySku.setTitle(anyProduct.getTitle());
        anySku.setAmount(1d);

        anyProduct.setOrigin(AnyOrigin.builder().id(1L).build());

        if(item.getPicturesUrls() != null && !item.getPicturesUrls().isEmpty()){
            int index = 0;
            for (String pictureUrl : item.getPicturesUrls()){
                AnyImage image = AnyImage.builder()
                        .index(index++)
                        .originalImage(pictureUrl)
                        .url(pictureUrl)
                        .build();
                if (image.getIndex() == 0){
                    image.setMain(true);
                }
                anyImages.add(image);
            }
            anyProduct.setImages(anyImages);
        }

        if (item.getTechnicalSpecList() != null && !item.getTechnicalSpecList().isEmpty()){
            int i = 0;
            for (TechnicalSpec technicalSpec : item.getTechnicalSpecList()){
                if (nonNull(technicalSpec.getNombre()) && nonNull(technicalSpec.getDescripcion())) {
                    final String name = technicalSpec.getNombre().length() < 149 ? technicalSpec.getNombre() :
                            technicalSpec.getNombre().substring(0, 149);

                    final String value = technicalSpec.getDescripcion().length() < 149 ? technicalSpec.getDescripcion() :
                            technicalSpec.getDescripcion().substring(0, 149);
                    AnyProductCharacteristic anyProductCharacteristic = AnyProductCharacteristic.builder()
                            .name(name.replaceAll("/", "-"))
                            .value(value.replaceAll("/", "-"))
                            .index(i)
                            .build();

                    anyProductCharacteristics.add(anyProductCharacteristic);
                    i++;
                }
            }
            anyProduct.setCharacteristics(anyProductCharacteristics);

            Predicate<TechnicalSpec> altoPredicate = technicalSpec -> technicalSpec.getNombre().equals("Alto");
            Predicate<TechnicalSpec> alturaPredicate = technicalSpec -> technicalSpec.getNombre().equals("Altura");

            Predicate<TechnicalSpec> anchoPredicate = technicalSpec -> technicalSpec.getNombre().equals("Ancho");
            Predicate<TechnicalSpec> anchuraPredicate = technicalSpec -> technicalSpec.getNombre().equals("Anchura");

            Predicate<TechnicalSpec> largoPredicate = technicalSpec -> technicalSpec.getNombre().equals("Largo");
            Predicate<TechnicalSpec> longitudPredicate = technicalSpec -> technicalSpec.getNombre().equals("Longitud");

            Optional<TechnicalSpec> modelOptional = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Modelo")).findFirst();
            modelOptional.ifPresent(technicalSpec -> anyProduct.setModel(technicalSpec.getDescripcion()));

            String weight;
            Optional<TechnicalSpec> weightOptional = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Peso")).findFirst();
            if (weightOptional.isPresent()){
                weight = weightOptional.get().getDescripcion();
                if (Character.isDigit(weight.charAt(0)) && !StringUtils.containsAny(weight,"lb","-","oz")){
                    if (weight.contains("kg")) {
                        weight = normalizeString(weight);
                        anyProduct.setWeight(Double.parseDouble(StringUtils.substringBefore(weight, "kg")));
                    }
                    else if (weight.contains("Kg")){
                        weight = normalizeString(weight);
                        anyProduct.setWeight(Double.parseDouble(StringUtils.substringBefore(weight, "Kg")));
                    }
                    else if (weight.contains("g")) {
                        weight = normalizeString(weight);
                        anyProduct.setWeight(Double.parseDouble(StringUtils.substringBefore(weight, "g")) / 1000);
                    }
                    else if (weight.contains("G")) {
                        weight = normalizeString(weight);
                        anyProduct.setWeight(Double.parseDouble(StringUtils.substringBefore(weight, "G")) / 1000);
                    }
                }

            }

            Optional<TechnicalSpec> heightOptional = item.getTechnicalSpecList().stream()
                    .filter(altoPredicate.or(alturaPredicate)).findFirst();
            heightOptional.ifPresent(technicalSpec -> anyProduct.setHeight(convertMeasure(technicalSpec.getDescripcion())));
            if (heightOptional.isEmpty()) {
                anyProduct.setHeight(1d);
            }

            Optional<TechnicalSpec> widthOptional = item.getTechnicalSpecList().stream()
                    .filter(anchoPredicate.or(anchuraPredicate)).findFirst();
            widthOptional.ifPresent(technicalSpec -> anyProduct.setHeight(convertMeasure(technicalSpec.getDescripcion())));
            if (widthOptional.isEmpty()) {
                anyProduct.setWidth(1d);
            }

            Optional<TechnicalSpec> lengthOptional = item.getTechnicalSpecList().stream()
                    .filter(largoPredicate.or(longitudPredicate)).findFirst();
            lengthOptional.ifPresent(technicalSpec -> anyProduct.setHeight(convertMeasure(technicalSpec.getDescripcion())));
            if (lengthOptional.isEmpty()) {
                anyProduct.setLength(1d);
            }

            Optional<TechnicalSpec> warrantyOptional = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Garantia")).findFirst();
            warrantyOptional.ifPresent(technicalSpec -> anyProduct.setWarrantyText(technicalSpec.getDescripcion()));
            if (warrantyOptional.isEmpty()) {
                anyProduct.setWarrantyTime(1);
            }
        }

        // fix non zero values
        if (anyProduct.getLength() <= 0d) {
            anyProduct.setLength(1d);
        }

        if (anyProduct.getWeight() <= 0d) {
            anyProduct.setWeight(1d);
        }

        if (anyProduct.getWidth() <= 0d) {
            anyProduct.setWidth(1d);
        }
        if (anyProduct.getHeight() <= 0d) {
            anyProduct.setHeight(1d);
        }

        return anyProduct;
    }

    private Double convertMeasure(String valueFromXML){
        Double valueConverted = 0.0;
        if (!(valueFromXML.indexOf(".", valueFromXML.indexOf(".") + 1) > -1)) {
            if (valueFromXML.contains("cm")) {
                valueFromXML = normalizeString(valueFromXML);
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "cm"));
            }
            else if (valueFromXML.contains("Cm")){
                valueFromXML = normalizeString(valueFromXML);
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "Cm"));
            }
            else if (valueFromXML.contains("mm")) {
                valueFromXML = normalizeString(valueFromXML);
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "mm")) / 10;
            }
            else if (valueFromXML.contains("Mm")) {
                valueFromXML = normalizeString(valueFromXML);
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "Mm")) / 10;
            }
            else if (valueFromXML.contains("m")) {
                valueFromXML = normalizeString(valueFromXML);
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "m")) * 100;
            }
            else if (valueFromXML.contains("M")){
                valueFromXML = normalizeString(valueFromXML);;
                valueConverted =  Double.parseDouble(StringUtils.substringBefore(valueFromXML, "M")) * 100;
            }
        }

        return valueConverted;
    }

    private String normalizeString (String input){
        String returnString = input;
        returnString = returnString.replaceAll(",",".");
        returnString = StringUtils.deleteWhitespace(returnString);
        //para whitespace especial de HTML que hace fallar el deleteWhitespace
        returnString = returnString.replaceAll("\u00A0", "");
        return returnString;
    }

}
