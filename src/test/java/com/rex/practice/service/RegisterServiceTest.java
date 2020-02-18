package com.rex.practice.service;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import com.rex.practice.model.verify.RegisterVerifyError;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegisterServiceTest extends BaseServiceTest {

    private Register register;
    private BindingResult bindingResult;
    @Autowired
    private RegisterService service;
    @SpyBean
    private UserService userService;
    @SpyBean
    private EmailService emailService;
    @SpyBean
    private TokenService tokenService;

    @Before
    public void setUp() {
        register = new Register();
        register.setEmail(registerEmail);
        register.setPassword("11111111");
        register.setConfirmPassword("11111111");

        bindingResult = new DataBinder(register).getBindingResult();
    }

    @Test
    public void hasFieldErrors() {
        String errorMessage = "Email不能為空";
        bindingResult.addError(new FieldError("", "email", errorMessage));

        Optional<RegisterError> optional = service.verify(register, bindingResult);

        assertRegisterVerify(optional, errorMessage, "redirect:/register");
    }

    @Test
    public void passwordDifferentError() {
        register.setConfirmPassword("22222222");

        Optional<RegisterError> optional = service.verify(register, bindingResult);

        assertRegisterVerify(optional, "兩次密碼不相同", "redirect:/register");
    }

    @Test
    public void emailRegistered() {
        doReturn(true).when(userService).isEmailExists(register.getEmail());
        doReturn(true).when(userService).isEmailVerified(register.getEmail());

        Optional<RegisterError> optional = service.verify(register, bindingResult);

        assertRegisterVerify(optional, "Email已被註冊", "redirect:/register");

        verify(userService, times(1)).isEmailExists(register.getEmail());
        verify(userService, times(1)).isEmailVerified(register.getEmail());
    }

    @Test
    public void emailVerifying() {
        doReturn(true).when(userService).isEmailExists(register.getEmail());
        doReturn(false).when(userService).isEmailVerified(register.getEmail());

        Optional<RegisterError> optional = service.verify(register, bindingResult);

        assertRegisterVerify(optional, "Email驗證中", "redirect:/login");

        verify(userService, times(1)).isEmailExists(register.getEmail());
        verify(userService, times(1)).isEmailVerified(register.getEmail());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void assertRegisterVerify(Optional<RegisterError> optional, String expectMessage, String expectViewName) {
        assertTrue(optional.isPresent());
        assertEquals(expectMessage, optional.get().getErrorMessage());
        assertEquals(expectViewName, optional.get().getViewName());
    }

    @Test
    public void register() {
        String token = UUID.randomUUID().toString().replace("-", "");

        User user = new User();
        user.setId(userId);
        user.setEmail(register.getEmail());

        doReturn(true).when(userService).addUser(any(Register.class));
        doReturn(Optional.of(user)).when(userService).findByEmail(register.getEmail());
        doReturn(token).when(tokenService).createRegisterToken(user.getId());
        doNothing().when(emailService).sendConfirmRegisterEmail(userId, register.getEmail(), token);

        assertTrue(service.register(register));

        verify(userService, times(1)).addUser(any(Register.class));
        verify(userService, times(1)).findByEmail(register.getEmail());
        verify(tokenService, times(1)).createRegisterToken(user.getId());
        verify(emailService, times(1)).sendConfirmRegisterEmail(userId, register.getEmail(), token);
    }

    @Test
    public void accountVerifyButNotRegisterData() throws Exception {
        doReturn(Optional.empty()).when(userService).findById(userId);

        Optional<RegisterVerifyError> optionalError = service.accountVerify(userId, UUID.randomUUID().toString());

        assertTrue(optionalError.isPresent());
        assertTrue(optionalError.get().isAccountError());

        verify(userService, times(1)).findById(userId);
    }

    @Test
    public void accountVerifyButTokenExpired() throws Exception {
        doReturn(Optional.of(new User())).when(userService).findById(userId);
        doReturn(true).when(tokenService).isTokenExpired(userId);

        Optional<RegisterVerifyError> optionalError = service.accountVerify(userId, UUID.randomUUID().toString());

        assertTrue(optionalError.isPresent());
        assertTrue(optionalError.get().isTokenError());

        verify(userService, times(1)).findById(userId);
        verify(tokenService, times(1)).isTokenExpired(userId);
    }

    @Test
    public void accountVerifyButTokenNotSame() throws Exception {
        doReturn(Optional.of(new User())).when(userService).findById(userId);
        doReturn(false).when(tokenService).isTokenExpired(userId);
        doReturn(UUID.randomUUID().toString()).when(tokenService).getRegisterToken(userId);

        Optional<RegisterVerifyError> optionalError = service.accountVerify(userId, UUID.randomUUID().toString());

        assertTrue(optionalError.isPresent());
        assertTrue(optionalError.get().isTokenError());

        verify(userService, times(1)).findById(userId);
        verify(tokenService, times(1)).isTokenExpired(userId);
        verify(tokenService, times(1)).getRegisterToken(userId);
    }

    @Test
    public void accountVerify() throws Exception {
        String token = UUID.randomUUID().toString();

        doReturn(Optional.of(new User())).when(userService).findById(userId);
        doReturn(false).when(tokenService).isTokenExpired(userId);
        doReturn(token).when(tokenService).getRegisterToken(userId);

        assertFalse(service.accountVerify(userId, token).isPresent());

        verify(userService, times(1)).findById(userId);
        verify(tokenService, times(1)).isTokenExpired(userId);
        verify(tokenService, times(1)).getRegisterToken(userId);
    }

    @Test
    public void updateAccountToVerified() {
        doReturn(true).when(userService).updateEmailVerifyStatus(userId);

        assertTrue(service.updateAccountToVerified(userId));

        verify(userService, times(1)).updateEmailVerifyStatus(userId);
    }

    @Test
    @Ignore
    public void updateAccountToVerifiedFailure() {
        // TODO 拋出自訂例外
    }

}
