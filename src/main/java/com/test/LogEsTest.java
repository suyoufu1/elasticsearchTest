package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
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
public class LogEsTest {
    RestHighLevelClient client = EsClient.getClient ();

    ObjectMapper objectMapper = new ObjectMapper ();
    String index = "sms-logs";
    @Test
    public void createIndex() throws IOException {
        Settings.Builder settings = Settings.builder ().
                put ("numbers_of_shards", 5).
                put ("numbers_of_replicas", 1);
        JsonXContent.contentBuilder ().startObject ().
                startObject ("properties").
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                         field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                    startObject ("name").
                        field ("type","keyword")
                    .endObject ().
                endObject ().

                endObject ();
    }
}
