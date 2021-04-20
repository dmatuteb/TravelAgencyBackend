package personal.danielmatute.TravelAgency.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import personal.danielmatute.TravelAgency.exception.CategoryNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CategoryAdvise {

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List categoryNotFoundHandler(CategoryNotFoundException categoryNotFoundException) {
        return Collections.singletonList(categoryNotFoundException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List categoryNotValidHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

}
