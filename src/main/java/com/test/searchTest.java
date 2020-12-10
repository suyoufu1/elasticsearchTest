package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/10
 * @Version
 */
public class searchTest {
    ObjectMapper mapper = new ObjectMapper ();
    RestHighLevelClient client = EsClient.getClient ();
    String index = "student_test";

    /**
     * 精确查询
     */
    @Test
    public void termSearch() throws IOException {
        SearchRequest request = new SearchRequest (index);
        // 创建要查询的条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder ();
        sourceBuilder.from (0);
        sourceBuilder.size (5);
        sourceBuilder.query (QueryBuilders.termsQuery ("name","syf","00"));
        request.source (sourceBuilder);
        // 执行查询
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit);
            Map<String, Object> sourceAsMap = hit.getSourceAsMap ();
            System.out.println (sourceAsMap);
        }
    }
    /**
     * 查询所有
     */
    @Test
    public void termsSearch() throws IOException {
        SearchRequest request = new SearchRequest (index);
        SearchSourceBuilder builder = new SearchSourceBuilder ();
        builder.query (QueryBuilders.matchAllQuery ());
        builder.size (10);
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            Map<String, Object> asMap = hit.getSourceAsMap ();
            System.out.println (asMap);
        }
        System.out.println (response.getHits ().getHits ().length);
    }
}
