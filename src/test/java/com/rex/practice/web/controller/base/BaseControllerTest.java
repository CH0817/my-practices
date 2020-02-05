package com.rex.practice.web.controller.base;

import com.rex.practice.PracticeApplication;
import com.rex.practice.service.AccountService;
import com.rex.practice.service.ComboboxService;
import com.rex.practice.service.ItemService;
import com.rex.practice.service.TradeService;
import com.rex.practice.service.impl.MenuServiceImpl;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PracticeApplication.class})
@AutoConfigureMockMvc
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

    private ResultActions sendRequest(MockHttpServletRequestBuilder request) throws Exception {
        return mvc.perform(request).andDo(print());
    }

}
