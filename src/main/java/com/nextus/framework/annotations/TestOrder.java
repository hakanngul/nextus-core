package com.nextus.framework.annotations;

import org.junit.jupiter.api.Order;
import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Order(0)
public @interface TestOrder {
    int value();
}