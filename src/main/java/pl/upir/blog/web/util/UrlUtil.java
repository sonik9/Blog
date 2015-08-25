/**
 * Created on Dec 12, 2011
 */
package pl.upir.blog.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 * @author Clarence
 *
 */
public class UrlUtil {

	public static String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

    public static String sendHttpRequest(String methodName, String url, String[] names, String[] values) throws HttpException, IOException {
        if (names.length != values.length) return null;
        if (!methodName.equalsIgnoreCase("GET") && !methodName.equalsIgnoreCase("POST")) return null;
        HttpRequestBase method;
        if (methodName.equalsIgnoreCase("GET")) {
            String[] parameters = new String[names.length];
            for (int i = 0; i < names.length; i++)
                parameters[i] = names[i] + "=" + values[i];
            //method = new GetMethod(url + "?" + StringUtils.join(parameters, "&"));
            method = new HttpGet(URI.create(url + "?" + StringUtils.join(parameters, "&")));
        } else {
            HttpPost post = new HttpPost(URI.create(url));
            method = new HttpPost(URI.create(url));
            List<NameValuePair> formData = new ArrayList<NameValuePair>();
            for (int i = 0; i < names.length; i++)
                formData.add(new BasicNameValuePair(names[i], values[i]));
            ((HttpPost)method).setEntity(new UrlEncodedFormEntity(formData));
            method.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse=httpClient.execute(method);

        HttpEntity responseEntity=httpResponse.getEntity();
        String r=EntityUtils.toString(responseEntity);
        System.out.println(r);
        /*BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());*/
        return r;
    }

    public static List<URI> sendHttpRequestRedirectUrl(String methodName, String url, String[] names, String[] values) throws HttpException, IOException {
        if (names.length != values.length) return null;
        if (!methodName.equalsIgnoreCase("GET") && !methodName.equalsIgnoreCase("POST")) return null;
        HttpRequestBase method;
        if (methodName.equalsIgnoreCase("GET")) {
            String[] parameters = new String[names.length];
            for (int i = 0; i < names.length; i++)
                parameters[i] = names[i] + "=" + values[i];
            //method = new GetMethod(url + "?" + StringUtils.join(parameters, "&"));
            method = new HttpGet(URI.create(url + "?" + StringUtils.join(parameters, "&")));
        } else {
            HttpPost post = new HttpPost(URI.create(url));
            method = new HttpPost(URI.create(url));
            List<NameValuePair> formData = new ArrayList<NameValuePair>();
            for (int i = 0; i < names.length; i++)
                formData.add(new BasicNameValuePair(names[i], values[i]));
            ((HttpPost)method).setEntity(new UrlEncodedFormEntity(formData));
            method.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse=httpClient.execute(method, context);



        return context.getRedirectLocations();
    }



    public static Map<String, String> parseURLQuery(String query) {
        Map<String, String> result = new HashMap<String,String>();
        String params[] = query.split("&");
        for (String param : params) {
            String temp[] = param.split("=");
            try {
                result.put(temp[0], URLDecoder.decode(temp[1], "UTF-8"));
            } catch (UnsupportedEncodingException exception) {
                exception.printStackTrace();
            }
        }
        return result;
    }

    public static String sourcePathFile(HttpServletRequest request, String filePath){

        return request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath()+filePath;
    }
	
}
