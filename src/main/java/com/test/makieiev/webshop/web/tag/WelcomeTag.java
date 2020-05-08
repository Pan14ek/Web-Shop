package com.test.makieiev.webshop.web.tag;

import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Objects;

public class WelcomeTag extends SimpleTagSupport {

    private static final String A_CLASS_NAV_LINK_DISABLED_HREF_TABINDEX_1_ARIA_DISABLED_TRUE = "<a class=\"nav-link disabled\" href=\"#\" tabindex=\"-1\" aria-disabled=\"true\">";
    private static final String A_LINK = "</a>";

    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession httpSession = request.getSession();
        JspWriter jspWriter = getJspContext().getOut();
        if (Objects.nonNull(httpSession.getAttribute(UserConstant.USER))) {
            User user = (User) httpSession.getAttribute(UserConstant.USER);
            jspWriter.print(A_CLASS_NAV_LINK_DISABLED_HREF_TABINDEX_1_ARIA_DISABLED_TRUE + "Hello, " + user.getFirstName() + A_LINK);
        }
    }

}