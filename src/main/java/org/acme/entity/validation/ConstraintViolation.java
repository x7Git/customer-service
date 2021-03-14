package org.acme.entity.validation;

import java.util.ArrayList;
import java.util.List;

public class ConstraintViolation {
    private String property;
    private String message;

    public static List<ConstraintViolation> from(am.ik.yavi.core.ConstraintViolations cvs){
        List<ConstraintViolation> violations = new ArrayList<>();
        cvs.forEach(cv -> {
            ConstraintViolation violation = new ConstraintViolation();
            violation.property = cv.name();
            violation.message = cv.defaultMessageFormat();
            violations.add(violation);
        });

        return violations;
    }

    public String getProperty() {
        return property;
    }

    public String getMessage() {
        return message;
    }

}
