package org.acme.entity.validation;

import am.ik.yavi.core.Validator;

import java.util.List;

public interface Validation {

    default List<ConstraintViolation> validation(){
        return ConstraintViolation.from(validator().validate(this));
    }

    <T> Validator<T> validator();
}
