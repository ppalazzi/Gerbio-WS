package com.palazzisoft.gerbio.integrator.mapping;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyImage;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProductCharacteristic;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnySku;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.model.mg.TechnicalSpec;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ItemToAnyProductMapper {

    public AnyProduct mapItemToAnyProduct (AnyProduct anyProduct, Item item) {
        List<AnyImage> anyImages = new ArrayList<>();
        List<AnyProductCharacteristic> anyProductCharacteristics = new ArrayList<>();
        List<AnySku> anySkus = new ArrayList<>();
        AnySku anySku;

        anyProduct.setTitle(item.getDescription().getShort_());
        anyProduct.setDefinitionPriceScope("SKU");
        anyProduct.setCalculatedPrice(false);

        anySku = AnySku.builder()
                .price(anyProduct.getPriceFactor())
                .amount(1)
                .title("SKU-" + item.getPartNumber())
                .partnerId(item.getPartNumber())
                .build();

        anySkus.add(anySku);
        anyProduct.setSkus(anySkus);

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
            for (TechnicalSpec technicalSpec : item.getTechnicalSpecList()){
                AnyProductCharacteristic anyProductCharacteristic = AnyProductCharacteristic.builder()
                        .name(technicalSpec.getNombre())
                        .value(technicalSpec.getDescripcion())
                        .build();

                anyProductCharacteristics.add(anyProductCharacteristic);
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

            Optional<TechnicalSpec> widthOptional = item.getTechnicalSpecList().stream()
                    .filter(anchoPredicate.or(anchuraPredicate)).findFirst();
            widthOptional.ifPresent(technicalSpec -> anyProduct.setHeight(convertMeasure(technicalSpec.getDescripcion())));

            Optional<TechnicalSpec> lengthOptional = item.getTechnicalSpecList().stream()
                    .filter(largoPredicate.or(longitudPredicate)).findFirst();
            lengthOptional.ifPresent(technicalSpec -> anyProduct.setHeight(convertMeasure(technicalSpec.getDescripcion())));

            Optional<TechnicalSpec> warrantyOptional = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Garantia")).findFirst();
            warrantyOptional.ifPresent(technicalSpec -> anyProduct.setWarrantyText(technicalSpec.getDescripcion()));

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
