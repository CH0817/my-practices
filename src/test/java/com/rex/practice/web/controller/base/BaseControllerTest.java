package com.rex.practice.web.controller.base;

import com.rex.practice.config.BeansConfig;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.model.recapthcha.ReCaptchaProperty;
import com.rex.practice.security.CustomAuthenticationProvider;
import com.rex.practice.security.CustomAuthenticationSuccessHandler;
import com.rex.practice.service.*;
import com.rex.practice.service.impl.MenuServiceImpl;
import com.rex.practice.web.controller.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {AccountBookController.class, AccountController.class, ComboboxController.class,
        FunctionController.class, ItemController.class, LoginController.class, RegisterController.class,
        HelperController.class})
@AutoConfigureMockMvc
@MockBean(classes = {JavaMailSender.class, ReCaptchaProperty.class})
@SpyBean(classes = {CustomAuthenticationProvider.class, CustomAuthenticationSuccessHandler.class})
@Import({BeansConfig.class})
@Ignore
public abstract class BaseControllerTest {

    protected String userId = "a";
    @Autowired
    protected MockMvc mvc;
    @MockBean
    protected TradeService tradeService;
    @MockBean
    protected AccountService accountService;
    @MockBean
    protected ComboboxService comboboxService;
    @MockBean
    protected ItemService itemService;
    @MockBean
    protected MenuServiceImpl menuService;
    @MockBean
    protected RegisterService registerService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected TokenService tokenService;
    @MockBean
    protected EmailService emailService;

    protected ResultActions sendGetRequest(String url) throws Exception {
        return sendRequest(get(url));
    }

    protected ResultActions sendGetJsonRequest(String url) throws Exception {
        return sendRequest(get(url).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions sendPostRequest(String url) throws Exception {
        return sendPostRequest(url, null);
    }

    protected ResultActions sendPostRequest(String url, Map<String, String[]> params) throws Exception {
        MockHttpServletRequestBuilder request = post(url).with(csrf());
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(request::param);
        }
        return sendRequest(request);
    }

    protected ResultActions sendPostJsonRequest(String url) throws Exception {
        return sendRequest(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions sendPaginationPostJsonRequest(String url) throws Exception {
        return sendRequest(post(url).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("rows", "30"));
    }

    protected ResultActions sendDeleteRequest(String url) throws Exception {
        return sendDeleteRequest(url, null);
    }

    protected ResultActions sendDeleteRequest(String url, Map<String, String[]> params) throws Exception {
        MockHttpServletRequestBuilder request = delete(url).with(csrf());
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(request::param);
        }
        return sendRequest(request);
    }

    protected ResultActions sendRequest(MockHttpServletRequestBuilder request) throws Exception {
        return mvc.perform(request).andDo(print());
    }

    protected void verifyForwardMessage(ResultActions resultActions, Message expectMessage) throws Exception {
        resultActions.andExpect(request().attribute("message", allOf(
                hasProperty("message", is(expectMessage.getMessage())),
                hasProperty("redirectUrl", is(expectMessage.getRedirectUrl())),
                hasProperty("icon", is(expectMessage.getIcon()))
        )));
    }

}
