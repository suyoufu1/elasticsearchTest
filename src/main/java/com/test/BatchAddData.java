package com.test;

import com.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
public class BatchAddData {
    // 连接客户端
    private RestHighLevelClient client = EsClient.getClient ();
    // 定义索引
    private String index = "student_test";
    @Test
    public void batchCreatDoc() throws IOException {
        ObjectMapper mapper = new ObjectMapper ();
        Student student1 = new Student ("1", "syy" , 18L, new Date ());
        String json1 = mapper.writeValueAsString (student1);
        Student student2 = new Student ("2", "syp" , 19L, new Date ());
        String json2 = mapper.writeValueAsString (student2);
        BulkRequest bulkRequest = new BulkRequest ();
        bulkRequest.add (new IndexRequest (index).source (json1, XContentType.JSON)).
                add (new IndexRequest (index).source (json2, XContentType.JSON));
        BulkResponse responses = client.bulk (bulkRequest, RequestOptions.DEFAULT);
        System.out.println (responses.getItems ().toString ());
    }
    /**
     * 批量删除
     */
    @Test
    public void deleteBulkDoc() throws IOException {
        BulkRequest request = new BulkRequest ();
        request.add (new DeleteRequest (index,"MHCCR3YBR9t4wfWKIobv"));
        request.add (new DeleteRequest (index,"UHCCR3YBR9t4wfWKkobK"));
        BulkResponse responses = client.bulk (request, RequestOptions.DEFAULT);
        System.out.println (responses.getItems ().toString ());
    }
}
