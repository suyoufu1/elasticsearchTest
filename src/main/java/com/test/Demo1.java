package com.test;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
public class Demo1 {
    // 连接客户端
    RestHighLevelClient client = EsClient.getClient ();
    // 定义索引
    String index = "student_test";
    //  定义类型
    String type = "man" ;

    /**
     * 创建索引
     */
    @Test
    public void  creatIndex() throws Exception{
        // 1.准备关于索引的settings
        Settings.Builder settings = Settings.builder ().
                // 设置分片
                put ("number_of_shards",5).
                // 设置备份
                put ("number_of_replicas",1);
        // 2.准备关于索引的创建
        XContentBuilder mappings = JsonXContent.contentBuilder ().
                startObject ().
                    startObject ("properties").
                        startObject ("name").
                            field ("type", "text").
                        endObject ().
                        startObject ("age").
                            field ("type", "long").
                        endObject ().
                        startObject ("birthday").
                            field ("type", "date").
                            field ("format", "yyyy-MM-dd").
                        endObject ().
                    endObject ().
                endObject ();
        // 3.讲settings和mappings封装到request中
        CreateIndexRequest request = new CreateIndexRequest (index).
                settings (settings).mapping (mappings);
        // 4.使用client连接es
        CreateIndexResponse response = client.indices ().create (request, RequestOptions.DEFAULT);
        System.out.println ("response:"+response.toString ());
    }
    /**
     * 检查是否存在
     */
    @Test
    public void existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest (index);
        boolean exists = client.indices ().exists (request, RequestOptions.DEFAULT);
        System.out.println (exists);
    }
    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest (index);
        AcknowledgedResponse delete = client.indices ().delete (request, RequestOptions.DEFAULT);
        System.out.println (delete.isAcknowledged ());
    }
}
