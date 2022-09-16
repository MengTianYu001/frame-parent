package com.mty.property.common.utils.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author mty
 * @date 2022/09/15 17:02
 **/
public final class HostNameUtils {
    private static String ip;
    private static String hostName;

    static {
        try {
            resolveHoost();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void resolveHoost() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        hostName = addr.getHostName();
        if (addr.isLoopbackAddress()) {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface in = interfaces.nextElement();
                Enumeration<InetAddress> addrs = in.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress address = addrs.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        ip = address.getHostAddress();
                    }
                }

            }
        }

    }

    public static String getIp() {
        return ip;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getConfigString() {
        return "{\n"
                + "\t\"machine\" : \"" + hostName + "\",\n"
                + "\t\"ip\" : \"" + ip + "\"\n"
                + "}";
    }

    private HostNameUtils() {}

}
