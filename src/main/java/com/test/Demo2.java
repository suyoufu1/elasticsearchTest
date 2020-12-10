package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.Student;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.mapper.Mapper;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
public class Demo2 {
    /**
     * java 操作文档
     */

    RestHighLevelClient client = EsClient.getClient ();

    ObjectMapper objectMapper = new ObjectMapper ();
    String index = "student";
    String type = "man" ;


    /**
     * 创建文档
     */
    @Test
    public void createDocTest() throws IOException {
        Student student = new Student ("1","李四",18L,new Date () );
        IndexRequest request = new IndexRequest (index);
        String json = objectMapper.writeValueAsString (student);
        request.source (json, XContentType.JSON);
        IndexResponse response = client.index (request, RequestOptions.DEFAULT);
        System.out.println (response.getResult ().toString ());
    }
    /**
     * 修改文档
     */
    @Test
    public void updateDocTest() throws IOException {
        // 1.修改的内容
        Map<String,Object> map = new HashMap<String, Object> ();
        map.put ("name","syf");
        // 2.创建request,将doc封装
        UpdateRequest request = new UpdateRequest (index, "FHBuR3YBR9t4wfWK3IZq");
        request.doc (map);
        UpdateResponse response = client.update (request, RequestOptions.DEFAULT);
        System.out.println (response.getResult ());
    }
    /**
     * 删除文档
     */
    @Test
    public void deleteDocTest() throws IOException {
        DeleteRequest request = new DeleteRequest (index,"FHBuR3YBR9t4wfWK3IZq");
        DeleteResponse response = client.delete (request, RequestOptions.DEFAULT);
        System.out.println (response.getResult ().toString ());
    }
}

