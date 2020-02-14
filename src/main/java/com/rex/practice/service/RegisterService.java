package com.rex.practice.service;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface RegisterService {

    Optional<RegisterError> verify(Register register, BindingResult bindingResult);

    boolean register(Register register);

}
