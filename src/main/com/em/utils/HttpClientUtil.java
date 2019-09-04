package main.com.em.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HttpClientUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String DEFAULT_CHARSET_UTF8 = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE_JSON = "application/json";

    /**
     * @param url    请求路径
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, Object> params, HttpClientConfig... configList) throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = createClient(url, getHttpConfig(configList));
            if (params != null) {
                StringBuilder sb = new StringBuilder();
                for (Entry<String, Object> entry : params.entrySet()) {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
                if (sb.length() > 0) {
                    if (url.indexOf("?") > -1) {
                        url = url + sb.toString();
                    } else {
                        sb.delete(0, 1);
                        url = url + "?" + sb.toString();
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * @param url    请求路径
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> params, HttpClientConfig... configList) throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = createClient(url, getHttpConfig(configList));
            HttpPost httpPost = new HttpPost(url);
            if (params != null && params.size() > 0) {
                List<NameValuePair> pairs = new ArrayList<>();
                for (Entry<String, Object> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                    pairs.add(pair);
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
                httpPost.setEntity(httpEntity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() < 400) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, "UTF-8");
            } else {
                throw new Exception("http请求错误：" + httpResponse.getStatusLine().getStatusCode() + "," + httpResponse.getEntity().toString());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 参数JSON格式 Post
     *
     * @param url 请求路径
     * @return
     */
    public static String jsonPost(String url, Object paramVO, HttpClientConfig... configList) throws Exception {
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            httpClient = createClient(url, getHttpConfig(configList));
            HttpPost httpPost = new HttpPost(url);

            StringEntity requestEntity = new StringEntity(JSON.toJSONString(paramVO), "UTF-8");
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
            EntityUtils.consume(httpEntity);

            if (httpResponse.getStatusLine().getStatusCode() < 400) {
                return result;
            } else {
                throw new Exception("http请求错误：" + httpResponse.getStatusLine().getStatusCode() + "," + result);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * @param url    请求路径
     * @param params 请求参数
     * @return
     */
    public static String put(String url, Map<String, Object> params, HttpClientConfig... configList) throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = createClient(url, getHttpConfig(configList));
            HttpPut httpPut = new HttpPut(url);
            if (params != null && params.size() > 0) {
                List<NameValuePair> pairs = new ArrayList<>();
                for (Entry<String, Object> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                    pairs.add(pair);
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
                httpPut.setEntity(httpEntity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPut);
            if (httpResponse.getStatusLine().getStatusCode() < 400) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, "UTF-8");
            } else {
                throw new Exception("http请求错误：" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * 发送带head的请求
     *
     * @param url
     * @param headerParam
     * @param bodyParam
     * @param contentType
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> headerParam, Map<Object, Object> bodyParam, String contentType, String charSet, HttpClientConfig... configList) throws Exception {
        String content_type = contentType;
        if (content_type == null || "".equals(content_type)) content_type = DEFAULT_CONTENT_TYPE_JSON;

        String char_set = charSet;
        if (char_set == null || "".equals(char_set)) char_set = DEFAULT_CHARSET_UTF8;

        HttpPost httpPost = new HttpPost(url);

        //header参数
        if (headerParam != null && headerParam.size() > 0) {
            LOGGER.info("Post请求Header：" + JSON.toJSONString(headerParam));
            for (String key : headerParam.keySet()) {
                httpPost.addHeader(key, headerParam.get(key));
            }
        }

        //entity数据
        if (bodyParam != null) {
            //x-www-form-urlencoded类型
            if (ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equals(contentType)) {
                if (bodyParam instanceof Map) {

                    @SuppressWarnings("unchecked")
                    Map<Object, Object> params = bodyParam;
                    if (!CollectionUtils.isEmpty(params)) {
                        List<NameValuePair> pairs = new ArrayList<>();
                        for (Entry<Object, Object> entry : params.entrySet()) {
                            NameValuePair pair = new BasicNameValuePair(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                            pairs.add(pair);
                        }
                        HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
                        httpPost.setEntity(httpEntity);
                        LOGGER.info("post请求body：" + httpEntity.toString());
                    }
                }
            } else {//json或其他类型
                LOGGER.info("Post请求Body：" + JSON.toJSONString(bodyParam));
                StringEntity entity = new StringEntity(JSON.toJSONString(bodyParam), char_set);
                entity.setContentEncoding(char_set);
                entity.setContentType(content_type);

                httpPost.setEntity(entity);
            }
        }

        String resultStr = "";
        CloseableHttpResponse response = null;
        try {
            response = createClient(url, getHttpConfig(configList)).execute(httpPost);
            resultStr = EntityUtils.toString(response.getEntity(), char_set);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpPost.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Post请求返回：" + resultStr);
        return resultStr;
    }

    /**
     * 发送Gzip压缩请求
     *
     * @param sendurl
     * @param bytes
     * @param headMap
     * @return
     * @throws Exception
     */
    public static byte[] postGzip(String sendurl, byte[] bytes, Map<String, String> headMap, HttpClientConfig... configList) throws Exception {
        HttpClientConfig httpClientConfig = getHttpConfig(configList);
        HttpURLConnection con = null;
        ByteArrayOutputStream baos = null;
        GZIPInputStream reader = null;
        GZIPOutputStream zip = null;
        try {
            con = getConnection(sendurl);
            if (headMap != null) {
                for (Entry<String, String> en : headMap.entrySet()) {
                    con.setRequestProperty(en.getKey(), en.getValue());
                }
            }
            con.setConnectTimeout(httpClientConfig.CONNECT_TIMING_OUT);
            con.setReadTimeout(httpClientConfig.RESPONSE_TIMING_OUT);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/gzip");
            zip = new GZIPOutputStream(con.getOutputStream());
            zip.write(bytes);
            zip.flush();
            zip.close();
            reader = new GZIPInputStream(con.getInputStream());
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = reader.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            con.disconnect();
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            LOGGER.error("GzipPost:{}, 出现异常，error: {}", sendurl, e.getMessage());
            throw new RuntimeException("请求异常：" + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (zip != null) {
                zip.close();
            }
            if (baos != null) {
                baos.close();
            }
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static CloseableHttpClient createHttpsClient(HttpClientConfig config) throws Exception {
        X509TrustManager xtm = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(null, new TrustManager[]{xtm}, null);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.CONNECT_TIMING_OUT)
                .setConnectionRequestTimeout(config.REQUEST_TIMING_OUT)
                .setSocketTimeout(config.RESPONSE_TIMING_OUT).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslsf).build();
        return httpClient;
    }

    private static CloseableHttpClient createHttpClient(HttpClientConfig config) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.CONNECT_TIMING_OUT)
                .setConnectionRequestTimeout(config.REQUEST_TIMING_OUT)
                .setSocketTimeout(config.RESPONSE_TIMING_OUT).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    private static CloseableHttpClient createClient(String url, HttpClientConfig config) throws Exception {
        if (url.startsWith("https")) {    //https请求
            return createHttpsClient(config);
        } else {    //http请求
            return createHttpClient(config);
        }
    }

    private static HttpURLConnection getConnection(String reqUrl) throws Exception {
        if (reqUrl.startsWith("https")) {
            return getHttpsConnection(reqUrl);
        } else {
            return getHttpConnection(reqUrl);
        }
    }

    private static HttpURLConnection getHttpsConnection(String reqUrl) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        URL url = new URL(reqUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        return conn;
    }

    private static HttpURLConnection getHttpConnection(String reqUrl) throws IOException {
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static HttpClientConfig getHttpConfig(HttpClientConfig[] configList) {
        if (ObjectUtils.isEmpty(configList)) {
            return HttpClientConfig.defaultConfig();
        }
        return configList[0];
    }
}