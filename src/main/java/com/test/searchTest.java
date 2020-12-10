package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/10
 * @Version
 */
public class searchTest {

    private RestHighLevelClient client = EsClient.getClient ();
    private String index = "student_test";
    private SearchRequest request = new SearchRequest ( index );
    private SearchSourceBuilder builder = new SearchSourceBuilder ();

    /**
     * 精确查询
     */
    @Test
    public void termSearch() throws IOException {
        builder.from (0);
        builder.size (5);
        builder.query (QueryBuilders.termsQuery ("name","syf","00"));
        request.source (builder);
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
    /**
     * match查询
     */
    @Test
    public void matchSearch() throws IOException {
        builder.size (10);
        builder.query (QueryBuilders.matchQuery ("name","syf"));
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
        System.out.println (response.getHits ().getHits ().length);
    }

    @Test
    public void booleanMatchSearch() throws IOException {
        builder.size (10);
        builder.query (QueryBuilders.matchQuery ("name","syf 00").operator (Operator.OR));
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }

    @Test
    public void idGetESData() throws IOException {
        GetRequest request = new GetRequest (index,"L3CCR3YBR9t4wfWKIobv");
        GetResponse response = client.get (request, RequestOptions.DEFAULT);
        System.out.println (response.getSourceAsMap ());
    }

    /**
     * fuzzy模糊查询
     */
    @Test
    public void findByFuzzy() throws IOException {
        builder.query (QueryBuilders.fuzzyQuery ("name","syf"));
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }

    /**
     * 前缀查询
     */
    @Test
    public void prefixSearch() throws IOException {
        builder.query (QueryBuilders.prefixQuery ("name","s"));
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
    /**
     * 通配查询 wildcard 类似mysql 的like
     */
    @Test
    public void wildcardSearch() throws IOException {
        builder.query (QueryBuilders.wildcardQuery ("name","*y?"));
        request.source (builder);
        SearchResponse response = client.search (request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }

    /**
     * 范围查询 range 针对数值型
     */
    @Test
    public void rangeSearch() throws IOException {
        builder.query (QueryBuilders.rangeQuery ( "age" ).gt ( 18 ).lte ( 20 ));
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
    /**
     * 正则查询
     */
    @Test
    public void regexSearch() throws IOException {
        builder.query (QueryBuilders.regexpQuery ( "name","s[a-z]f" ));
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
    /**
     * 深分页 scroll
     */
    @Test
    public void scrollSearch() throws IOException {
        request.scroll ( TimeValue.timeValueMillis ( 1L ));
        builder.size (2);
        builder.sort ( "age", SortOrder.DESC );
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        String scrollId = response.getScrollId ();

        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }

        while (true){
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest ( scrollId );
            searchScrollRequest.scroll ( TimeValue.timeValueMillis ( 1L )  );
            SearchResponse scroll = client.scroll ( searchScrollRequest, RequestOptions.DEFAULT );
            SearchHit[] hits = scroll.getHits ().getHits ();
            if(null != hits && hits.length>0){
                System.out.println ("================下一页数据================");
                for(SearchHit hit : hits){
                    System.out.println (hit.getSourceAsMap ());
                }
            }else {
                System.out.println ("========结束分页=========");
                break;
            }
        }
        // 创建clearScrollRequest
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest ();
        // 指定scrollId
        clearScrollRequest.addScrollId ( scrollId );
        // 删除scrollId
        ClearScrollResponse clearScrollResponse = client.clearScroll ( clearScrollRequest, RequestOptions.DEFAULT );
        System.out.println ("删除scroll:"+clearScrollResponse.isSucceeded ());
    }
    /**
     * 根据term,match等查询删除大量索引 delete-by-query
     */
    @Test
    public void deleteByQuery() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest ( index );
        request.setQuery ( QueryBuilders.rangeQuery ( "age" ).lte(18 ) );
        BulkByScrollResponse response = client.deleteByQuery ( request, RequestOptions.DEFAULT );
        System.out.println (response.toString ());
    }
    /**
     * 复合查询  must:类似and  should:类似or
     */
    @Test
    public void  boolSearch() throws IOException {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder ();
        boolQueryBuilder.should (QueryBuilders.termQuery ( "name","syf" ));
        boolQueryBuilder.should (QueryBuilders.termQuery ( "name","syy" ));
        boolQueryBuilder.mustNot (QueryBuilders.termQuery ( "age",18 ));
        boolQueryBuilder.must (QueryBuilders.matchQuery ( "birthday","2020-12-09" ));
        builder.query (boolQueryBuilder);
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }

    /**
     * boosting 查询
     *  影响查询后的分数
     *      positive：只有匹配上positive 查询的内容，才会被放到返回的结果集中
     */
    @Test
    public void boostSearch() throws IOException {
        BoostingQueryBuilder boost = QueryBuilders.boostingQuery (
                QueryBuilders.matchQuery ( "name", "syf" ),
                QueryBuilders.matchQuery ( "name", "01" )
        ).negativeBoost ( 0.5f );
        builder.query (boost);
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit :response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }

    /**
     * filter查询
     */
    @Test
    public void filterSearch() throws IOException {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery ();
        boolQueryBuilder.filter (QueryBuilders.termQuery ( "name","syp" ));
        boolQueryBuilder.filter (QueryBuilders.rangeQuery ( "age" ).gte ( 19 ));
        builder.query (boolQueryBuilder);
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
    /**
     * 高亮查询
     */
    @Test
    public void highLightSearch() throws IOException {
        builder.query (QueryBuilders.matchQuery ( "name","syp" ));
        HighlightBuilder highlightBuilder = new HighlightBuilder ();
        highlightBuilder.field ( "name",18 )
                .preTags ("<font colr='red'>")
                .postTags ("</font>");
        builder.highlighter (highlightBuilder);
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
    /**
     * 聚合查询
     */
    @Test
    public void aggCardinalityC() throws IOException {
        builder.aggregation ( AggregationBuilders.cardinality ("nameAgg").field ("name") );
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        Aggregation agg = response.getAggregations ().get ( "nameAgg" );
        System.out.println (agg.getName ());

    }
    /**
     * 范围统计
     */
    @Test
    public void aggRange() throws IOException {
        builder.aggregation ( AggregationBuilders.range ( "agg" ).field ("age")
        .addUnboundedTo ( 10 )
        .addRange ( 10,30 )
        .addUnboundedFrom ( 50 ));
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        Range agg = response.getAggregations ().get ( "agg" );
        for (Range.Bucket bucket : agg.getBuckets ()){
            String key = bucket.getKeyAsString ();
            Object from = bucket.getFrom ();
            Object to = bucket.getTo ();
            long docCount = bucket.getDocCount ();
            System.out.println (String.format ( "key: %s , from: %s ,to: %s ,docCount: %s",key,from,to,docCount ));
        }
    }
    /**
     * 统计聚合
     */
    @Test
    public void aggExtendedStats() throws IOException {
        builder.aggregation ( AggregationBuilders.extendedStats ( "agg" ).field ("age") );
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        ExtendedStats extendedStats = response.getAggregations ().get ( "agg" );
        System.out.println ("最大值:"+extendedStats.getMaxAsString ()+",最小值："+extendedStats.getMinAsString ());
    }
}
