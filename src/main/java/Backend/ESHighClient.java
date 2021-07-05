package Backend;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESHighClient {
	
	public static RestHighLevelClient client;
    public static void getESClient(){
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ));
        System.out.println("Cliente conectado. ");
    }

    public static void clientClose(){
        if (client!=null){
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
