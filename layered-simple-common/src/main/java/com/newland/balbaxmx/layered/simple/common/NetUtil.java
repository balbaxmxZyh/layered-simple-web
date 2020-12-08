package com.newland.balbaxmx.layered.simple.common;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: zhangyh
 * @ClassName: NetUtil
 * @Date: 2020/5/12 17:10
 * @Operation:
 * @Description: 网络工具类
 */
public class NetUtil {

    /**
     * 筛选出网卡中IP是配置的IP一致的
     * @param ips
     * @return
     */
    public static String getLocalIp(List<String> ips) {
        String localIp = "";
        Enumeration<?> netInterfaces;
        List<NetworkInterface> netlist=new ArrayList<>();
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                if (ni.isLoopback()) {
                    continue;
                }
                netlist.add(0, ni);
                for (NetworkInterface list : netlist) {
                    Enumeration<?> cardipaddress = list.getInetAddresses();
                    while (cardipaddress.hasMoreElements()) {
                        InetAddress ip = (InetAddress) cardipaddress.nextElement();
                        if (!ip.isLoopbackAddress()) {
                            if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                                continue;
                            }
                            if (ip instanceof Inet6Address) {
                                continue;
                            }
                            if (ip instanceof Inet4Address) {
                                localIp = ip.getHostAddress();
                                for (int i = 0; i < ips.size(); i++) {
                                    String ipSingle = ips.get(i);
                                    if (ipSingle.equals(localIp)) {
                                        return localIp;
                                        }
                                    }
                                    return ip.getHostAddress();
                                }
                            }
                        }
                    }
                }
            } catch(SocketException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
            return localIp;
        }
}
