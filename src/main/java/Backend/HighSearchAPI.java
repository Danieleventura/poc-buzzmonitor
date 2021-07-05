package Backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import joptsimple.internal.Strings;

public class HighSearchAPI {

	
	
	public static SearchResponse buildQuery(JSONObject body) throws IOException{
	    
		String [] fields = null;
		TermsAggregationBuilder groupBy = null;
        QueryBuilder query = null;

        // se a request for null, retorna todos os documentos
        if (body == null) {
        	System.out.println("Request null");
            query = QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (body.has("service")) {
            	 boolQueryBuilder.must(
                         QueryBuilders.matchQuery("service", body.getString("service")).fuzziness(Fuzziness.AUTO));
            }
            if (body.has("sentiment") ) {
            	 boolQueryBuilder.must(
                         QueryBuilders.matchQuery("sentiment", body.getString("sentiment")).fuzziness(Fuzziness.AUTO));
            
            }
            if (body.has("title") ) {
              	 boolQueryBuilder.must(
                           QueryBuilders.matchQuery("title", body.getString("title")).fuzziness(Fuzziness.AUTO));
              
              }
            if (body.has("author.name") ) {
             	 boolQueryBuilder.must(
                          QueryBuilders.matchQuery("author.name", body.getString("author.name")).fuzziness(Fuzziness.AUTO));
             
             }
            if (body.has("collected_time") ) {
            	 boolQueryBuilder.must(
                         QueryBuilders.matchQuery("collected_time", body.getString("collected_time")).fuzziness(Fuzziness.AUTO));
            
            }
            if (body.has("_source")) {
            	JSONArray campos = body.getJSONArray("_source");
            	fields = new String[campos.length()];
            	for (int i=0; i< campos.length(); i++) {
            		fields[i] = campos.get(i).toString();
            		System.out.println("Fields: " + fields[i] );
            	}
              }
            if(body.has("filter_by")) {
           	 boolQueryBuilder.filter(
                        QueryBuilders.rangeQuery(body.getString("filter_by")));	
            }
            
            if(body.has("group_by")) {
            	groupBy = AggregationBuilders.terms(body.getString("group_by")).field(body.getString("group_by") + ".keyword");

            }

            query = boolQueryBuilder;
            System.out.println(query);
            System.out.println(groupBy);
        }

        SearchResponse response = search(query, fields,  groupBy);

        return response;
    }
	
	public static SearchResponse search(QueryBuilder query2, String [] fields, TermsAggregationBuilder groupBy) throws IOException {
		System.out.println("entrou no search");
		ESHighClient.getESClient();
    	RestHighLevelClient client = ESHighClient.client;

    	SearchResponse response = null;
    	
    	if (groupBy == null) {
	       response = client.search(new SearchRequest("buzzmonitor")
	                .source(new SearchSourceBuilder()
	                        .query(query2)	
	                        .fetchSource(fields, null)));
    	}
    	else {
        response = client.search(new SearchRequest("buzzmonitor")
                .source(new SearchSourceBuilder()
                        .aggregation(groupBy)), RequestOptions.DEFAULT);}

        return response;
    }
	

}
