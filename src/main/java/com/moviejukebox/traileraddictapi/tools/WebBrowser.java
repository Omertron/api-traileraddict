/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.traileraddictapi.tools;

import com.moviejukebox.traileraddictapi.TrailerAddictException;
import com.moviejukebox.traileraddictapi.TrailerAddictException.TrailerAddictExceptionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * Web browser with simple cookies support
 */
public final class WebBrowser {

    private static final Logger LOGGER = Logger.getLogger(WebBrowser.class);
    private static Map<String, String> browserProperties = new HashMap<String, String>();
    private static Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();
    private static String proxyHost = null;
    private static String proxyPort = null;
    private static String proxyUsername = null;
    private static String proxyPassword = null;
    private static String proxyEncodedPassword = null;
    private static int webTimeoutConnect = 25000;   // 25 second timeout
    private static int webTimeoutRead = 90000;      // 90 second timeout

    // Hide the constructor
    protected WebBrowser() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Populate the browser properties
     */
    private static void populateBrowserProperties() {
        if (browserProperties.isEmpty()) {
            browserProperties.put("User-Agent", "Mozilla/5.25 Netscape/5.0 (Windows; I; Win95)");
        }
    }

    public static String request(String url) throws TrailerAddictException {
        try {
            return request(new URL(url));
        } catch (MalformedURLException ex) {
            throw new TrailerAddictException(TrailerAddictExceptionType.INVALID_URL, null, ex);
        }
    }

    public static URLConnection openProxiedConnection(URL url) throws TrailerAddictException {
        try {
            if (proxyHost != null) {
                System.getProperties().put("proxySet", "true");
                System.getProperties().put("proxyHost", proxyHost);
                System.getProperties().put("proxyPort", proxyPort);
            }

            URLConnection cnx = url.openConnection();

            if (proxyUsername != null) {
                cnx.setRequestProperty("Proxy-Authorization", proxyEncodedPassword);
            }

            return cnx;
        } catch (IOException ex) {
            throw new TrailerAddictException(TrailerAddictException.TrailerAddictExceptionType.INVALID_URL, null, ex);
        }
    }

    public static String request(URL url) throws TrailerAddictException {
        StringWriter content = null;

        try {
            content = new StringWriter();

            BufferedReader in = null;
            URLConnection cnx = null;
            try {
                cnx = openProxiedConnection(url);

                sendHeader(cnx);
                readHeader(cnx);

                in = new BufferedReader(new InputStreamReader(cnx.getInputStream(), getCharset(cnx)));
                String line;
                while ((line = in.readLine()) != null) {
                    content.write(line);
                }
            } finally {
                if (in != null) {
                    in.close();
                }

                if (cnx instanceof HttpURLConnection) {
                    ((HttpURLConnection) cnx).disconnect();
                }
            }
            return content.toString();
        } catch (IOException ex) {
            throw new TrailerAddictException(TrailerAddictException.TrailerAddictExceptionType.CONNECTION_ERROR, null, ex);
        } finally {
            if (content != null) {
                try {
                    content.close();
                } catch (IOException ex) {
                    LOGGER.debug("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    private static void sendHeader(URLConnection cnx) {
        populateBrowserProperties();

        // send browser properties
        for (Map.Entry<String, String> browserProperty : browserProperties.entrySet()) {
            cnx.setRequestProperty(browserProperty.getKey(), browserProperty.getValue());
        }
        // send cookies
        String cookieHeader = createCookieHeader(cnx);
        if (!cookieHeader.isEmpty()) {
            cnx.setRequestProperty("Cookie", cookieHeader);
        }
    }

    private static String createCookieHeader(URLConnection cnx) {
        String host = cnx.getURL().getHost();
        StringBuilder cookiesHeader = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> domainCookies : cookies.entrySet()) {
            if (host.endsWith(domainCookies.getKey())) {
                for (Map.Entry<String, String> cookie : domainCookies.getValue().entrySet()) {
                    cookiesHeader.append(cookie.getKey());
                    cookiesHeader.append("=");
                    cookiesHeader.append(cookie.getValue());
                    cookiesHeader.append(";");
                }
            }
        }
        if (cookiesHeader.length() > 0) {
            // remove last ; char
            cookiesHeader.deleteCharAt(cookiesHeader.length() - 1);
        }
        return cookiesHeader.toString();
    }

    private static void readHeader(URLConnection cnx) {
        // read new cookies and update our cookies
        for (Map.Entry<String, List<String>> header : cnx.getHeaderFields().entrySet()) {
            if ("Set-Cookie".equals(header.getKey())) {
                for (String cookieHeader : header.getValue()) {
                    String[] cookieElements = cookieHeader.split(" *; *");
                    if (cookieElements.length >= 1) {
                        String[] firstElem = cookieElements[0].split(" *= *");
                        String cookieName = firstElem[0];
                        String cookieValue = firstElem.length > 1 ? firstElem[1] : null;
                        String cookieDomain = null;
                        // find cookie domain
                        for (int i = 1; i < cookieElements.length; i++) {
                            String[] cookieElement = cookieElements[i].split(" *= *");
                            if ("domain".equals(cookieElement[0])) {
                                cookieDomain = cookieElement.length > 1 ? cookieElement[1] : null;
                                break;
                            }
                        }
                        if (cookieDomain == null) {
                            // if domain isn't set take current host
                            cookieDomain = cnx.getURL().getHost();
                        }
                        Map<String, String> domainCookies = cookies.get(cookieDomain);
                        if (domainCookies == null) {
                            domainCookies = new HashMap<String, String>();
                            cookies.put(cookieDomain, domainCookies);
                        }
                        // add or replace cookie
                        domainCookies.put(cookieName, cookieValue);
                    }
                }
            }
        }
    }

    private static Charset getCharset(URLConnection cnx) {
        Charset charset = null;
        // content type will be string like "text/html; charset=UTF-8" or "text/html"
        String contentType = cnx.getContentType();
        if (contentType != null) {
            // changed 'charset' to 'harset' in regexp because some sites send 'Charset'
            Matcher m = Pattern.compile("harset *=[ '\"]*([^ ;'\"]+)[ ;'\"]*").matcher(contentType);
            if (m.find()) {
                String encoding = m.group(1);
                try {
                    charset = Charset.forName(encoding);
                } catch (UnsupportedCharsetException e) {
                    // there will be used default charset
                }
            }
        }
        if (charset == null) {
            charset = Charset.defaultCharset();
        }

        return charset;
    }

    public static String getProxyHost() {
        return proxyHost;
    }

    public static void setProxyHost(String myProxyHost) {
        WebBrowser.proxyHost = myProxyHost;
    }

    public static String getProxyPort() {
        return proxyPort;
    }

    public static void setProxyPort(String myProxyPort) {
        WebBrowser.proxyPort = myProxyPort;
    }

    public static String getProxyUsername() {
        return proxyUsername;
    }

    public static void setProxyUsername(String myProxyUsername) {
        WebBrowser.proxyUsername = myProxyUsername;
    }

    public static String getProxyPassword() {
        return proxyPassword;
    }

    public static void setProxyPassword(String myProxyPassword) {
        WebBrowser.proxyPassword = myProxyPassword;

        if (proxyUsername != null) {
            proxyEncodedPassword = proxyUsername + ":" + proxyPassword;
            proxyEncodedPassword = "Basic " + new String(Base64.encodeBase64((proxyUsername + ":" + proxyPassword).getBytes()));
        }
    }

    public static int getWebTimeoutConnect() {
        return webTimeoutConnect;
    }

    public static int getWebTimeoutRead() {
        return webTimeoutRead;
    }

    public static void setWebTimeoutConnect(int webTimeoutConnect) {
        WebBrowser.webTimeoutConnect = webTimeoutConnect;
    }

    public static void setWebTimeoutRead(int webTimeoutRead) {
        WebBrowser.webTimeoutRead = webTimeoutRead;
    }
}
