package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.model.security.Security;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.parser.xml.SecurityXmlParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SecurityFilterTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpSession httpSession;

    @Before
    public void setUp() {
        String fileName = ".\\src\\main\\resources\\security.xml";
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(fileName);
        Security security = securityXmlParser.getSecurity();
        Mockito.when(servletContext.getAttribute(ServletContextConstant.SECURITY)).thenReturn(security);
    }

    @Test
    public void shouldCrossToUrlPage() throws ServletException, IOException {
        Filter filter = new SecurityFilter();

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/index");
        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
        filter.init(filterConfig);
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void shouldCheckUrlAndCrossToMainPage() throws IOException, ServletException {
        Filter filter = new SecurityFilter();

        Mockito.when(httpServletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/admin");
        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
        filter.init(filterConfig);
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletResponse).sendRedirect(Link.INDEX);
    }

    @Test
    public void shouldSuccessfullyCrossToPage() throws IOException, ServletException {
        Filter filter = new SecurityFilter();
        Role role = new Role(0, "Administrator");
        User user = new User(1L, "one", "one", "one", "one@gmail.com", "1234567", true, "img", role);

        Mockito.when(httpSession.getAttribute(UserConstant.USER)).thenReturn(user);
        Mockito.when(httpServletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/admin");
        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
        filter.init(filterConfig);
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

}