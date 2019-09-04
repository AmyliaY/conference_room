package main.com.em.utils;

/**
 * HttpClientConfig http请求配置
 */
public class HttpClientConfig {

    /**
     * 连接时间 ms
     */
    protected int CONNECT_TIMING_OUT = 30000;
    /**
     * 请求响应时间 ms
     */
    protected int RESPONSE_TIMING_OUT = 30000;

    /**
     * 发起请求时间 ms
     */
    protected int REQUEST_TIMING_OUT = 30000;

    public HttpClientConfig(int CONNECT_TIMING_OUT, int RESPONSE_TIMING_OUT, int REQUEST_TIMING_OUT) {
        this.CONNECT_TIMING_OUT = CONNECT_TIMING_OUT;
        this.RESPONSE_TIMING_OUT = RESPONSE_TIMING_OUT;
        this.REQUEST_TIMING_OUT = REQUEST_TIMING_OUT;
    }

    public HttpClientConfig(){

    }

    public static HttpClientConfig defaultConfig(){
        return new HttpClientConfig();
    }

    public int getCONNECT_TIMING_OUT() {
        return CONNECT_TIMING_OUT;
    }

    public void setCONNECT_TIMING_OUT(int CONNECT_TIMING_OUT) {
        this.CONNECT_TIMING_OUT = CONNECT_TIMING_OUT;
    }

    public int getRESPONSE_TIMING_OUT() {
        return RESPONSE_TIMING_OUT;
    }

    public void setRESPONSE_TIMING_OUT(int RESPONSE_TIMING_OUT) {
        this.RESPONSE_TIMING_OUT = RESPONSE_TIMING_OUT;
    }

    public int getREQUEST_TIMING_OUT() {
        return REQUEST_TIMING_OUT;
    }

    public void setREQUEST_TIMING_OUT(int REQUEST_TIMING_OUT) {
        this.REQUEST_TIMING_OUT = REQUEST_TIMING_OUT;
    }
}
