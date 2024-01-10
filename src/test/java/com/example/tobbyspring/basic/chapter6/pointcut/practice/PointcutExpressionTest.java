package com.example.tobbyspring.basic.chapter6.pointcut.practice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

class PointcutExpressionTest {

    @Test
    void methodSignaturePointcut() throws NoSuchMethodException, SecurityException{

        System.out.println(Target.class.getMethod("minus", int.class, int.class));

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int " +
                "com.example.tobbyspring.basic.chapter6.pointcut.practice.Target.minus(int,int) " +
                "throws java.lang.RuntimeException)");

        // Target.minus()
        Assertions.assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(
                        Target.class.getMethod("minus", int.class, int.class), null)).isTrue();

        // Target.plus()
        Assertions.assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(
                        Target.class.getMethod("plus", int.class, int.class), null)).isFalse();

        //Bean class
        Assertions.assertThat(pointcut.getClassFilter().matches(Bean.class)).isFalse();

    }

}
