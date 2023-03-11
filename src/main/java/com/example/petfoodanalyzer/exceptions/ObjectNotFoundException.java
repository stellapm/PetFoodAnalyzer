package com.example.petfoodanalyzer.exceptions;

import static com.example.petfoodanalyzer.constants.Exceptions.OBJECT_NOT_FOUND;

public class ObjectNotFoundException extends RuntimeException {
    private final String criteria;
    private final String objectType;

    public ObjectNotFoundException(String identifier, String criteria, String objectType) {

        super(String.format(OBJECT_NOT_FOUND, identifier, criteria, objectType));

        this.criteria = criteria;
        this.objectType = objectType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getObjectType() {
        return objectType;
    }


}

