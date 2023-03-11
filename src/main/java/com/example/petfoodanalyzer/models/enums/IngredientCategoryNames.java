package com.example.petfoodanalyzer.models.enums;

public enum IngredientCategoryNames {
    AA("Amino Acids"),
    AP("Animal Products"),
    FO("Fats/Oils"),
    G("Gums"),
    GR("Grains"),
    HP("Hydrolyzed Protein"),
    PP("Plant Products"),
    CL("Cellulose"),
    L("Legumes"),
    RV("Root Vegetables"),
    M("Minerals"),
    NF("Natural Flavors"),
    P("Preservatives"),
    PR("Probiotics"),
    V("Vitamins");

    private final String value;

    IngredientCategoryNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }



}
