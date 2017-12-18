package com.secy.planttagger.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import com.secy.planttagger.helper.ImageFile;


@Component
public class ImageFileValidator implements Validator {

    public static final String PNG_MIME_TYPE="image/png";
    public static final long TEN_MB_IN_BYTES = 10485760;

    @Override
    public boolean supports(Class clazz) {
       return ImageFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ImageFile file = (ImageFile)target;
        
        if(file.getImg().isEmpty()){
            errors.rejectValue("img", "upload.file.required");
        }
        else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getImg().getContentType())){
            errors.rejectValue("img", "upload.invalid.file.type");
        }        
        else if(file.getImg().getSize() > TEN_MB_IN_BYTES){
            errors.rejectValue("img", "upload.exceeded.file.size");
        }
    }
 }