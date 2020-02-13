package com.rex.practice.service;

import com.rex.practice.model.input.Register;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Deprecated
public interface RegisterService {

    Optional<String> verify(Register register, BindingResult bindingResult);

    void register(Register register);

}
