package com.kslima.bluefood.infrastructure;

import com.kslima.bluefood.util.FileType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile> {

    private List<FileType> acceptedFileTypes;

    @Override
    public void initialize(UploadConstraint constraintAnnotation) {
        acceptedFileTypes = Arrays.asList(constraintAnnotation.acceptedTypes());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedFileTypes.stream()
                .anyMatch(f -> f.sameOf(value.getContentType()));
    }
}
