package com.rjsoft.servicezuul.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/9.
 * 获取客户端信息的工具类
 */
public final class NetworkUtil {

   private final static Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

    /**
     * 获取请求主机IP地址，如果通过代理进来，则透过防火墙获取真实IP地址
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0;index < ip.length();index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return  ip;
    }

    public static String getMacAddress(String ip) {
        String str = "";
        String macAddress = "";
        final String LOOPBACK_ADDRESS = "127.0.0.1";
        if (LOOPBACK_ADDRESS.equals(ip)) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
                StringBuffer sb = new StringBuffer();
                for (int i = 0;i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
                macAddress = sb.toString().trim().toUpperCase();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return macAddress;
        } else {
            Process process = null;
            InputStreamReader isr = null;
            LineNumberReader input = null;
            try {
                process = Runtime.getRuntime().exec("nbtstat -A " + ip);
                isr = new InputStreamReader(process.getInputStream(),"gbk");
                input = new LineNumberReader(isr);
                for (;true;){
                    str = input.readLine();
                    if (!StringUtils.isEmpty(str)) {
                        if (str.indexOf("MAC") > 1) {
                            macAddress = str.substring(str.indexOf("MAC") + 9, str.length()).trim();
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return macAddress;
        }
    }

    public static String getOS(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        StringBuffer os = new StringBuffer();
        if (Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*").matcher(userAgent).find()) {
            os.append("Windows XP");
        }else if (Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*").matcher(userAgent).find()) {
            os.append("Windows 2000");
        }else if (Pattern.compile(".*(Windows NT 5\\.2).*").matcher(userAgent).find()) {
            os.append("Windows 2003");
        }else if (Pattern.compile(".*(Windows NT 6\\.1).*").matcher(userAgent).find()) {
            os.append("Windows 7");
        }else if (Pattern.compile(".*(Windows NT 6\\.3).*").matcher(userAgent).find()) {
            os.append("Windows 2012");
        }else if (Pattern.compile(".*(Windows NT 10\\.0).*").matcher(userAgent).find()) {
            os.append("Windows 10");
        }else if (Pattern.compile(".*(Win NT|Windows NT).*").matcher(userAgent).find()) {
            os.append("Windows NT");
        }else if (Pattern.compile(".*(Mac|apple|MacOS8).*").matcher(userAgent).find()) {
            os.append("Mac");
        }else if (Pattern.compile(".*(Linux).*").matcher(userAgent).find()) {
            os.append("Linux");
        }else if (Pattern.compile(".*(x11).*").matcher(userAgent).find()) {
            os.append("Unix");
        }else if (Pattern.compile(".*(android).*").matcher(userAgent).find()) {
            os.append("Android");
        }else if (Pattern.compile(".*(iphone).*").matcher(userAgent).find()) {
            os.append("Iphone");
        }else {
            os.append("Unknown,More-Info:").append(userAgent);
        }
        return os.toString();
    }

    public static String getBrowser(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String userAgentLower = userAgent.toLowerCase();
        StringBuffer browser = new StringBuffer();
        if (userAgentLower.contains("edge")) {
            browser.append((userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("msie")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[1]);
            browser.append((userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("MSIE", "IE"))
                   .append("-").append(IEVersion.substring(0, IEVersion.length() - 1));
        }else if (userAgentLower.contains("safari") && userAgentLower.contains("version")) {
            browser.append((userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0])
                   .append("-").append((userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1]);
        }else if (userAgentLower.contains("opr") || userAgentLower.contains("opera")) {
            if (userAgentLower.contains("opr")) {
                browser.append((userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0])
                       .append("-").append((userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1]);
            }else if (userAgentLower.contains("opera")) {
                browser.append((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-").replace("OPR", "Opera"));
            }
        }else if (userAgentLower.contains("chrome")) {
            browser.append((userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("firefox")) {
            browser.append((userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser.append("IE").append(IEVersion.substring(0, IEVersion.length() - 1));
        }else {
            browser.append("Unknown,More-Info:").append(userAgent);
        }

        return browser.toString();
    }

}
