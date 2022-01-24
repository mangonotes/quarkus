package org.mangonotes.services.todo;

import org.mangonotes.exception.ValidationException;
import org.mangonotes.model.dto.req.TodoReqDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ValidationServices {
    @Inject
    Validator validator;

    public void validate(TodoReqDTO reqDTO) {
        Set<ConstraintViolation<TodoReqDTO>> violations = validator.validate(reqDTO);
        if (violations.isEmpty()) {
            return;
        }
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        throw new ValidationException(message);
    }

}
