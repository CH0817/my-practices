package com.rex.my.web.controller.base;

import com.rex.my.business.service.AccountService;
import com.rex.my.business.service.ComboboxService;
import com.rex.my.business.service.ItemService;
import com.rex.my.business.service.TradeService;
import com.rex.my.business.service.impl.MenuServiceImpl;
import com.rex.my.web.advice.ControllerDataBindAdvice;
import com.rex.my.web.config.WebConfig;
import com.rex.my.web.controller.*;
import com.rex.my.web.controller.security.MockSecuredUser;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@EnableWebMvc
@AutoConfigureMockMvc
@MockSecuredUser
@SpringBootTest(classes = {AccountBookController.class, AccountController.class, ComboboxController.class,
        FunctionController.class, ItemController.class})
@Import({WebConfig.class, ControllerDataBindAdvice.class})
@Ignore
public abstract class BaseControllerTest {

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

    protected ResultActions sendGetRequest(String url) throws Exception {
        return sendRequest(get(url));
    }

    protected ResultActions sendGetJsonRequest(String url) throws Exception {
        return sendRequest(get(url).contentType(MediaType.APPLICATION_JSON));
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

    private ResultActions sendRequest(MockHttpServletRequestBuilder request) throws Exception {
        ResultActions result = mvc.perform(request);
        doPrint(result);
        return result;
    }

    private void doPrint(ResultActions resultActions) throws Exception {
        resultActions.andDo(print());
    }

}
