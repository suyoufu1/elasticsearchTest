package com.test;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/10
 * @Version
 */
public class GeoPolygon {
    /**
     * 地图经纬度搜索
     */
    private RestHighLevelClient client = EsClient.getClient ();
    private String index = "map";
    private SearchRequest request = new SearchRequest ( index );
    private SearchSourceBuilder builder = new SearchSourceBuilder ();

    @Test
    public void GeoPSearch() throws IOException {
        List<GeoPoint> geoPoints = new ArrayList<GeoPoint> ();
        geoPoints.add ( new GeoPoint (40.07,116.22) );
        geoPoints.add ( new GeoPoint (40.04,116.33) );
        geoPoints.add ( new GeoPoint (39.987,116.23) );
        builder.query ( QueryBuilders.geoPolygonQuery ( "location",geoPoints ) );
        request.source (builder);
        SearchResponse response = client.search ( request, RequestOptions.DEFAULT );
        for (SearchHit hit : response.getHits ().getHits ()){
            System.out.println (hit.getSourceAsMap ());
        }
    }
}
