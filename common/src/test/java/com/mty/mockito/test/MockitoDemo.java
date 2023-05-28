package com.mty.mockito.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author mty
 * @since 2023/05/28 00:23
 **/
@ExtendWith(MockitoExtension.class)
public class MockitoDemo {
    @Mock
    private Random random;

    /*@BeforeEach
    public  void before() {
        // 让注解生效
        MockitoAnnotations.openMocks(this);
    }*/
    @Test
    public void test() {
        when(random.nextInt()).thenReturn(100);

        assertEquals(100, random.nextInt());
    }
}
