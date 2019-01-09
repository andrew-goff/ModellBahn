package com.linepro.modellbahn.model.util;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.linepro.modellbahn.model.IItem;

class AdressValidator<A extends Annotation, T extends IItem> implements ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }

}
