package io.github.dmatuteb.TravelAgency.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.github.dmatuteb.TravelAgency.exception.AmusementParkNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AmusementParkAdvise {

    @ResponseBody
    @ExceptionHandler(AmusementParkNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List amusementParkNotFoundHandler(AmusementParkNotFoundException amusementParkNotFoundException) {
        return Collections.singletonList(amusementParkNotFoundException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List amusementParkNotValidHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

}
