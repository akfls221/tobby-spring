package com.example.tobbyspring.basic.chapter6.proxyTest;

import com.example.tobbyspring.basic.chapter6.Hello;
import com.example.tobbyspring.basic.chapter6.dynamicproxy.UppercaseHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProxyTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("Hello owen"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("Hi owen"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("ThankYou owen")
        );
    }

    @Test
    void proxyHello() {
        Hello hello = new HelloUppercase(new HelloTarget());

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("HI OWEN"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("THANKYOU OWEN")
        );
    }

    @Test
    void dynamicProxyHello() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );

        assertAll(
                () -> assertThat(proxyHello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                () -> assertThat(proxyHello.sayHi("owen")).isEqualTo("HI OWEN"),
                () -> assertThat(proxyHello.sayThankYou("owen")).isEqualTo("THANKYOU OWEN")
        );
    }

    @Test
    void proxyFactoryBeanTest() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());

        Hello hello = (Hello) proxyFactoryBean.getObject();

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("HI OWEN"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("THANKYOU OWEN")
        );
    }

    @Test
    void pointcutAdvisorTest() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(nameMatchMethodPointcut, new UpperCaseAdvice()));

        Hello hello = (Hello) proxyFactoryBean.getObject();

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("HI OWEN"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("ThankYou owen")
        );
    }

    @Test
    void pointCutTest() {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            public ClassFilter getClassFilter() {
                return clazz -> clazz.getSimpleName().startsWith("HelloS");
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        class HelloSuccess extends HelloTarget{}
        checkAdviced(new HelloSuccess(), classMethodPointcut, true);

        class HelloFail extends HelloTarget{}
        checkAdviced(new HelloFail(), classMethodPointcut, false);
    }

    private void checkAdviced(Object target, NameMatchMethodPointcut classMethodPointcut, boolean adviced) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(classMethodPointcut, new UpperCaseAdvice()));
        Hello proxyHello = (Hello) proxyFactoryBean.getObject();

        if (adviced) {
            assertAll(
                    () -> assertThat(proxyHello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                    () -> assertThat(proxyHello.sayHi("owen")).isEqualTo("HI OWEN"),
                    () -> assertThat(proxyHello.sayThankYou("owen")).isEqualTo("ThankYou owen")
            );
        } else {
            assertAll(
                    () -> assertThat(proxyHello.sayHello("owen")).isEqualTo("Hello owen"),
                    () -> assertThat(proxyHello.sayHi("owen")).isEqualTo("Hi owen"),
                    () -> assertThat(proxyHello.sayThankYou("owen")).isEqualTo("ThankYou owen")
            );
        }
    }

    static class UpperCaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }
}
