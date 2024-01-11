package com.example.tobbyspring.basic.configuration;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chapter6.pointcut.NameMatchClassMethodPointcut;
import com.example.tobbyspring.basic.chapter6.transactionproxy.TransactionAdvice;
import com.example.tobbyspring.basic.chpter5.MockMailSender;
import com.example.tobbyspring.basic.chpter5.step1.JdbcUserDao;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BeanConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem://localhost/~/tobby-spring;DB_CLOSE_ON_EXIT=FALSE")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserDao userDao() {
        return new JdbcUserDao(jdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        return new MockMailSender();
    }

//    @Bean
//    public Chapter6Service chapter6Service() {
//        return new Chapter6ServiceTxImpl(chapter6ServiceImpl(), platformTransactionManager());
//    }

    @Bean
    public Chapter6Service chapter6ServiceImpl() {
        Chapter6ServiceImpl chapter6Service = new Chapter6ServiceImpl(userDao());
        chapter6Service.setMailSender(mailSender());
        return chapter6Service;
    }

    @Bean(name = "exceptionService")
    public Chapter6Service exceptionTestService() {
        return new Chapter6ServiceImpl.ExceptionTestServiceImpl(userDao());
    }

//    @Bean(name = "userService2")
//    public TxProxyFactoryBean txProxyFactoryBean() {
//        return new TxProxyFactoryBean(chapter6ServiceImpl(), platformTransactionManager(), "upgradeLevels", Chapter6Service.class);
//    }

    @Bean
    public TransactionAdvice transactionAdvice() {

        return new TransactionAdvice(platformTransactionManager());
    }

    @Bean
    public NameMatchMethodPointcut nameMatchMethodPointcut() {
        //기존 포인트컷 주석처리
//        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
//        nameMatchMethodPointcut.setMappedName("upgrade*");

        NameMatchClassMethodPointcut newPointCut = new NameMatchClassMethodPointcut();
        newPointCut.setMappedClassName("*ServiceImpl");
        newPointCut.setMappedName("upgrade*");

        return newPointCut;
    }

    /**
     * execution을 활용한 포인트컷 추가
     * @return
     */
    @Bean
    public AspectJExpressionPointcut aspectJExpressionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example..*ServiceImpl.*(..))");

        return pointcut;
    }

    //이전 transactionAdvice() - methodInterceptor를 활용한 advice등록
//    @Bean
//    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
//        return new DefaultPointcutAdvisor(aspectJExpressionPointcut(), transactionAdvice());
//    }


    //TransactionInterCeptor를 통한 등록
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut(), newTransactionAdvice());
    }

//    @Bean(name = "userServiceWithAdvisor")
//    public ProxyFactoryBean proxyFactoryBean() {
//        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        proxyFactoryBean.setTarget(chapter6ServiceImpl());
//        proxyFactoryBean.setInterceptorNames("defaultPointcutAdvisor");
//
//        return proxyFactoryBean;
//    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public TransactionInterceptor newTransactionAdvice() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();

        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("getAll", "readOnly");
        transactionAttributes.setProperty("update", "");

        transactionInterceptor.setTransactionManager(platformTransactionManager());
        transactionInterceptor.setTransactionAttributes(transactionAttributes);

        return transactionInterceptor;
    }
}
