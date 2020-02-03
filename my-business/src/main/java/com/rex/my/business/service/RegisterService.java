package com.rex.my.business.service;

import com.rex.my.model.input.Register;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface RegisterService {

    Optional<String> verify(Register register, BindingResult bindingResult);

    void register(Register register);

}
