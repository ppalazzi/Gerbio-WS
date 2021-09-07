package com.palazzisoft.gerbio.integrator.mapping;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyImage;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProductCharacteristic;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnySku;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.model.mg.TechnicalSpec;

import java.util.ArrayList;
import java.util.List;

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
                .partnerId(anyProduct.getId().toString())
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

            String model = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Modelo"))
                    .findAny().get().getDescripcion();
            anyProduct.setModel(model);

            String weight = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Peso"))
                    .findAny().get().getDescripcion();
            anyProduct.setWeight(Double.parseDouble(weight));

            String height = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Altura"))
                    .findAny().get().getDescripcion();
            anyProduct.setHeight(Double.parseDouble(height));

            String width = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Ancho"))
                    .findAny().get().getDescripcion();
            anyProduct.setWidth(Double.parseDouble(width));

            String length = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Largo"))
                    .findAny().get().getDescripcion();
            anyProduct.setLength(Double.parseDouble(length));

            String warranty = item.getTechnicalSpecList().stream()
                    .filter(technicalSpec -> technicalSpec.getNombre().equals("Garantia"))
                    .findAny().get().getDescripcion();
            anyProduct.setWarrantyText(warranty);
        }

        return anyProduct;
    }
}
