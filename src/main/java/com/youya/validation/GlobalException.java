package com.youya.validation;

import com.youya.validation.adapter.ResultAdapter;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tengxq
 */
@RestControllerAdvice
public class GlobalException {

    @Resource
    private ResultAdapter resultAdapter;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        final StringBuilder sb = new StringBuilder();
        for (ObjectError error : allErrors) {
            sb.append(error.getDefaultMessage());
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(resultAdapter.getCode(), resultAdapter.getCodeValue());
        result.put(resultAdapter.getMessage(), sb.toString());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
