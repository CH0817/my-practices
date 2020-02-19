package com.rex.practice.web.controller;

import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
