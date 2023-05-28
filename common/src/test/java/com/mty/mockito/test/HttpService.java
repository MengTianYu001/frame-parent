package com.mty.mockito.test;

import java.util.Random;

/**
 * @author mty
 * @since 2023/05/27 23:54
 **/
public class HttpService {
    public int queryStatus() {
        // 发起网络请求，提取返回结果
        // 这里用随机数模拟结果
        return new Random().nextInt(2);
    }
}
