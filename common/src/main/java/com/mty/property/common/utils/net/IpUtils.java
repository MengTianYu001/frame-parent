package com.mty.property.common.utils.net;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author mty
 * @since 2022/09/15 17:12
 **/
public class IpUtils {
    private volatile static String localAddr;

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0];
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @SneakyThrows
    public static String getLocalAddr() {
        if (localAddr == null) {
            localAddr = InetAddress.getLocalHost().getHostAddress();
        }
        return localAddr;
    }
}
