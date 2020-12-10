package com.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @ClassName Demo
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
public class EsClient {

    public static RestHighLevelClient getClient(){
        // 创建httpHost,连接es服务
        HttpHost httpHost = new HttpHost ("172.20.52.208", 9200);
        RestClientBuilder builder = RestClient.builder (httpHost);
        // 创建restHighLevelClient
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient (builder);
        return restHighLevelClient;
    }

}
