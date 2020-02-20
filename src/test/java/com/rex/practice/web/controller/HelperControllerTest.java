package com.rex.practice.web.controller;

import com.rex.practice.model.message.InfoMessage;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HelperControllerTest extends BaseControllerTest {

    @Test
    public void toConfirmResendRegisterMailPage() throws Exception {
        sendRequest(get("/helper/register/verify/resend").requestAttr("userId", userId))
                .andExpect(status().isOk())//
                .andExpect(model().attribute("confirmParameter", allOf(
                        hasProperty("title", is("重發Email認證信")),
                        hasProperty("message", is("是否重發Email認證信？")),
                        hasProperty("confirmForm", allOf(
                                hasProperty("action", is("/register/verify/resend")),
                                hasProperty("method", is("post")),
                                hasProperty("parameter", hasEntry(is("userId"), is(userId)))
                        )),
                        hasProperty("denyForm", allOf(
                                hasProperty("action", is("/login")),
                                hasProperty("method", is("get")),
                                hasProperty("parameter", nullValue())
                        ))
                )))
                .andExpect(view().name("help/confirm"));
    }

    @Test
    public void toConfirmResendRegisterMailPageNotGiveUserId() throws Exception {
        sendRequest(get("/helper/register/verify/resend"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())//
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void toShowMessagePage() throws Exception {
        Message message = new InfoMessage("test", "/login");
        ResultActions resultActions = sendRequest(post("/helper/show/info")
                .with(csrf())
                .requestAttr("message", message))
                .andExpect(status().isOk())
                .andExpect(view().name("help/message"));
        verifyForwardMessage(resultActions, message);
    }

    @Test
    public void toShowMessagePageNoMessage() throws Exception {
        sendPostRequest("/helper/show/info")
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

}
