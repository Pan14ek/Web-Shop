package com.test.makieiev.webshop.web.listener;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.dao.CategoryDao;
import com.test.makieiev.webshop.dao.OrderDao;
import com.test.makieiev.webshop.dao.ProducerDao;
import com.test.makieiev.webshop.dao.ProductDao;
import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.dao.impl.CategoryDaoImpl;
import com.test.makieiev.webshop.dao.impl.MemoryUserDaoImpl;
import com.test.makieiev.webshop.dao.impl.OrderDaoImpl;
import com.test.makieiev.webshop.dao.impl.ProducerDaoImpl;
import com.test.makieiev.webshop.dao.impl.ProductDaoImpl;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.dao.impl.UserDaoImpl;
import com.test.makieiev.webshop.model.dto.DtoConverter;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.security.Security;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.CaptchaService;
import com.test.makieiev.webshop.service.CategoryService;
import com.test.makieiev.webshop.service.OrderService;
import com.test.makieiev.webshop.service.ProducerService;
import com.test.makieiev.webshop.service.ProductService;
import com.test.makieiev.webshop.service.UploadService;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.service.impl.CaptchaServiceImpl;
import com.test.makieiev.webshop.service.impl.CategoryServiceImpl;
import com.test.makieiev.webshop.service.impl.OrderServiceImpl;
import com.test.makieiev.webshop.service.impl.ProducerServiceImpl;
import com.test.makieiev.webshop.service.impl.ProductServiceImpl;
import com.test.makieiev.webshop.service.impl.UploadServiceImpl;
import com.test.makieiev.webshop.service.impl.UserServiceImpl;
import com.test.makieiev.webshop.storage.Storage;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.content.ContentInstallation;
import com.test.makieiev.webshop.util.content.impl.JpgContentInstallation;
import com.test.makieiev.webshop.util.content.impl.PngContentInstallation;
import com.test.makieiev.webshop.util.filter.Filter;
import com.test.makieiev.webshop.util.filter.impl.AmountItemFilter;
import com.test.makieiev.webshop.util.filter.impl.CategoryFilter;
import com.test.makieiev.webshop.util.filter.impl.PageFilter;
import com.test.makieiev.webshop.util.filter.impl.PriceFilter;
import com.test.makieiev.webshop.util.filter.impl.ProducerFilter;
import com.test.makieiev.webshop.util.filter.impl.SearchFilter;
import com.test.makieiev.webshop.util.filter.impl.SortFilter;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;
import com.test.makieiev.webshop.util.generation.CleanerWorker;
import com.test.makieiev.webshop.util.generation.impl.AttributeContextCaptchaStorage;
import com.test.makieiev.webshop.util.generation.impl.CookieContextCaptchaStorage;
import com.test.makieiev.webshop.util.generation.impl.SessionCaptchaStorage;
import com.test.makieiev.webshop.util.localestorage.LocaleStorage;
import com.test.makieiev.webshop.util.localestorage.impl.CookieLocaleStorage;
import com.test.makieiev.webshop.util.localestorage.impl.SessionLocaleStorage;
import com.test.makieiev.webshop.util.parser.xml.SecurityXmlParser;
import com.test.makieiev.webshop.util.validation.AddressValidator;
import com.test.makieiev.webshop.util.validation.ProductFilterValidator;
import com.test.makieiev.webshop.util.validation.SignInValidator;
import com.test.makieiev.webshop.util.validation.SignUpValidator;
import com.test.makieiev.webshop.util.wrapper.WrapperServletFactory;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    private static final String COOKIE = "cookie";
    private static final String ATTRIBUTE = "attribute";
    private static final String SESSION = "session";
    private static final String REGEXP_COMMA = ",";
    private static final String LOCALES = "locales";
    private static final String FILE_NAME = "fileName";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            init(servletContextEvent);
        } catch (NamingException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void init(ServletContextEvent servletContextEvent) throws NamingException {
        ServletContext servletContext = servletContextEvent.getServletContext();
        initService(servletContext);
        initUtil(servletContext);
        initFilters(servletContext);
        initFilterWithPagination(servletContext);
    }

    private void initUtil(ServletContext servletContext) {
        ProductFilterValidator productFilterValidator = new ProductFilterValidator();
        int limitMinutes = Integer.parseInt(servletContext.getInitParameter(ServletContextConstant.TIMEOUT_REGISTRATION));
        String captchaStorageAnswer = servletContext.getInitParameter(ServletContextConstant.CAPTCHA_STORAGE);
        Map<Long, Captcha> captchaValues = Collections.synchronizedMap(new HashMap<>());
        AddressValidator addressValidator = new AddressValidator();
        CleanerWorker cleanerWorker = new CleanerWorker(captchaValues, limitMinutes);
        Thread worker = new Thread(cleanerWorker);
        worker.setDaemon(true);
        servletContext.setAttribute(ServletContextConstant.CLEANER, worker);
        DtoConverter dtoConverter = new DtoConverter();
        SignUpValidator signUpValidator = new SignUpValidator();
        SignInValidator signInValidator = new SignInValidator();
        DtoCreator dtoCreator = new DtoCreator();
        Map<String, ContentInstallation> installationMap = getInstallations();
        Map<String, LocaleStorage> localeStorageMap = initLocaleStorage();
        List<String> locales = getLocales(servletContext);
        WrapperServletFactory wrapperServletFactory = new WrapperServletFactory();
        Security security = getSecurity(servletContext);
        servletContext.setAttribute(ServletContextConstant.SECURITY, security);
        servletContext.setAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY, wrapperServletFactory);
        servletContext.setAttribute(ServletContextConstant.ADDRESS_VALIDATOR, addressValidator);
        servletContext.setAttribute(ServletContextConstant.INSTALLATION_CONTENT, installationMap);
        servletContext.setAttribute(ServletContextConstant.SIGN_IN_USER_VALIDATOR, signInValidator);
        servletContext.setAttribute(ServletContextConstant.DTO_CREATOR, dtoCreator);
        servletContext.setAttribute(ServletContextConstant.DTO_CONVERTER, dtoConverter);
        servletContext.setAttribute(ServletContextConstant.SIGN_UP_USER_VALIDATOR, signUpValidator);
        servletContext.setAttribute(ServletContextConstant.CAPTCHA_VALUES, captchaValues);
        CaptchaStorage captchaStorage = getCaptchaStorage(captchaStorageAnswer, captchaValues);
        servletContext.setAttribute(ServletContextConstant.CAPTCHA_STORAGE, captchaStorage);
        servletContext.setAttribute(ServletContextConstant.PRODUCT_FILTER_VALIDATOR, productFilterValidator);
        servletContext.setAttribute(ServletContextConstant.LOCALE_STORAGE, localeStorageMap);
        servletContext.setAttribute(ServletContextConstant.LOCALE_LIST, locales);
    }

    private void initService(ServletContext servletContext) throws NamingException {
        TransactionManager transactionManager = new TransactionManager(getDataSource());
        Storage storage = new Storage(initUsers());
        UserDao memoryUserDao = new MemoryUserDaoImpl(storage);
        UserDao mySqlUserDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        CategoryDao categoryDao = new CategoryDaoImpl();
        ProducerDao producerDao = new ProducerDaoImpl();
        UploadService uploadService = new UploadServiceImpl();
        UserService memoryUserService = new UserServiceImpl(memoryUserDao, transactionManager);
        CaptchaService captchaService = new CaptchaServiceImpl();
        OrderService orderService = new OrderServiceImpl(transactionManager, orderDao);
        UserService userService = new UserServiceImpl(mySqlUserDao, transactionManager);
        ProductService productService = new ProductServiceImpl(productDao, transactionManager);
        ProducerService producerService = new ProducerServiceImpl(producerDao, transactionManager);
        CategoryService categoryService = new CategoryServiceImpl(categoryDao, transactionManager);
        servletContext.setAttribute(ServletContextConstant.UPLOAD_SERVICE, uploadService);
        servletContext.setAttribute(ServletContextConstant.CAPTCHA_SERVICE, captchaService);
        servletContext.setAttribute(ServletContextConstant.MEMORY_USER_SERVICE, memoryUserService);
        servletContext.setAttribute(ServletContextConstant.USER_SERVICE, userService);
        servletContext.setAttribute(ServletContextConstant.PRODUCT_SERVICE, productService);
        servletContext.setAttribute(ServletContextConstant.CATEGORY_SERVICE, categoryService);
        servletContext.setAttribute(ServletContextConstant.PRODUCER_SERVICE, producerService);
        servletContext.setAttribute(ServletContextConstant.ORDER_SERVICE, orderService);
    }

    private CaptchaStorage getCaptchaStorage(String answer, Map<Long, Captcha> captchaValues) {
        Map<String, CaptchaStorage> captchaStorageMap = new HashMap<>();
        captchaStorageMap.put(COOKIE, new CookieContextCaptchaStorage(captchaValues));
        captchaStorageMap.put(ATTRIBUTE, new AttributeContextCaptchaStorage(captchaValues));
        captchaStorageMap.put(SESSION, new SessionCaptchaStorage(captchaValues));
        return captchaStorageMap.get(answer);
    }

    private DataSource getDataSource() throws NamingException {
        Context initialContext = new InitialContext();
        Context context = (Context) initialContext.lookup("java:comp/env");
        return (DataSource) context.lookup("jdbc/WebShop");
    }

    private Map<String, ContentInstallation> getInstallations() {
        Map<String, ContentInstallation> installations = new HashMap<>();
        installations.put("png", new PngContentInstallation());
        installations.put("jpeg", new JpgContentInstallation());
        return installations;
    }

    private void initFilters(ServletContext servletContext) {
        Filter searchFilter = new SearchFilter();
        Filter categoryFilter = new CategoryFilter();
        Filter producerFilter = new ProducerFilter();
        Filter priceFilter = new PriceFilter();
        Filter sortFilter = new SortFilter();
        searchFilter.setNext(categoryFilter);
        categoryFilter.setNext(producerFilter);
        producerFilter.setNext(priceFilter);
        priceFilter.setNext(sortFilter);
        servletContext.setAttribute(ServletContextConstant.ITEM_FILTER_WITHOUT_PAGINATION, searchFilter);
    }

    private void initFilterWithPagination(ServletContext servletContext) {
        Filter searchFilter = new SearchFilter();
        Filter categoryFilter = new CategoryFilter();
        Filter producerFilter = new ProducerFilter();
        Filter priceFilter = new PriceFilter();
        Filter sortFilter = new SortFilter();
        Filter amountItemFilter = new AmountItemFilter();
        Filter pageFilter = new PageFilter();
        searchFilter.setNext(categoryFilter);
        categoryFilter.setNext(producerFilter);
        producerFilter.setNext(priceFilter);
        priceFilter.setNext(sortFilter);
        sortFilter.setNext(amountItemFilter);
        amountItemFilter.setNext(pageFilter);
        servletContext.setAttribute(ServletContextConstant.ITEM_FILTER, searchFilter);
    }

    private Map<Long, User> initUsers() {
        Map<Long, User> users = new HashMap<>();
        Role role = new Role(1, "RegisteredUser");
        users.put(1L, new User(1L, "one", "one", "one", "one@gmail.com", "1234567", true, "img", role));
        users.put(2L, new User(2L, "two", "two", "two", "two@gmail.com", "1234567", false, "img", role));
        users.put(3L, new User(3L, "three", "three", "three", "three@gmail.com", "1234567", true, "img", role));
        users.put(4L, new User(4L, "four", "four", "four", "four@gmail.com", "1234567", true, "img", role));
        users.put(5L, new User(5L, "five", "five", "five", "five@gmail.com", "1234567", true, "img", role));
        users.put(6L, new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", role));
        return users;
    }

    private Map<String, LocaleStorage> initLocaleStorage() {
        Map<String, LocaleStorage> localeStorageMap = new HashMap<>();
        localeStorageMap.put(COOKIE, new CookieLocaleStorage());
        localeStorageMap.put(SESSION, new SessionLocaleStorage());
        return localeStorageMap;
    }

    private List<String> getLocales(ServletContext servletContext) {
        String[] locales = servletContext.getInitParameter(LOCALES).split(REGEXP_COMMA);
        return Arrays.asList(locales);
    }

    private Security getSecurity(ServletContext servletContext) {
        String fileName = servletContext.getInitParameter(FILE_NAME);
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(fileName);
        return securityXmlParser.getSecurity();
    }

}