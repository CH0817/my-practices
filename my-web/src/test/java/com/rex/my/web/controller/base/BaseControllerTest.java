package com.rex.my.web.controller.base;

import com.rex.MyWebApplication;
import com.rex.my.business.service.LoginService;
import com.rex.my.model.dao.primary.User;
import com.rex.my.web.constant.SessionAttribute;
import com.rex.my.web.controller.AccountBookController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MyWebApplication.class})
@WebAppConfiguration
@Ignore
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected MockHttpSession session;

    @Before
    public void setUserSession() {
        User user = new User();
        user.setId("a");
        user.setEmail("test@email.com");
        session.setAttribute(SessionAttribute.USER, user);
    }

    protected ResultActions sendGetRequest(String url) throws Exception {
        return sendRequest(get(url));
    }

    protected ResultActions sendGetJsonRequest(String url) throws Exception {
        return sendRequest(get(url).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions sendGetJsonRequestWithUserSession(String url) throws Exception {
        return sendRequest(get(url).session(session).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions sendPostRequest(String url) throws Exception {
        return sendRequest(post(url));
    }

    protected ResultActions sendPostRequest(String url, Map<String, String> paramMap) throws Exception {
        MockHttpServletRequestBuilder postRequest = post(url);
        if (Objects.nonNull(paramMap)) {
            paramMap.forEach(postRequest::param);
        }
        return sendRequest(postRequest);
    }

    protected ResultActions sendPostJsonRequestWithUserSession(String url) throws Exception {
        return sendPostJsonRequestWithUserSession(url, null);
    }

    protected ResultActions sendPostJsonRequestWithUserSession(String url, Map<String, String> paramMap) throws Exception {
        MockHttpServletRequestBuilder postRequest = post(url).session(session).contentType(MediaType.APPLICATION_JSON);
        if (Objects.nonNull(paramMap)) {
            paramMap.forEach(postRequest::param);
        }
        return sendRequest(postRequest);
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
