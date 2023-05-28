package com.mty.mockito.test;

import org.junit.jupiter.api.Test;

/**
 * @author mty
 * @since 2023/05/27 23:56
 **/


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {
    @Test
    public void test() {
        // 创建mock对象
        HttpService mockHttpService = mock(HttpService.class);
        // 使用 mockito 对 queryStatus 方法打桩
        when(mockHttpService.queryStatus()).thenReturn(1);
        // 调用 mock 对象的 queryStatus 方法，结果永远是 1
        assertEquals(1, mockHttpService.queryStatus());

        ExampleService exampleService = new ExampleService();
        exampleService.setHttpService(mockHttpService);
        assertEquals("Hello", exampleService.hello());
    }
}
