package com.example.bonchainguback.blockchain.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.wavesplatform.wavesj.exceptions.NodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

@Component
@Slf4j
public class BlockchainClient {


    public static String getData(String key) throws IOException, NodeException {
        final ClientConfig clientConfig = new DefaultClientConfig();
        final Client client = Client.create(clientConfig);
        final WebResource webResource = client.resource(UriBuilder.fromUri("https://nodes-testnet.wavesnodes.com/addresses/data/3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv/" + key).build());
        return (webResource.accept(String.valueOf(MediaType.APPLICATION_JSON)).get(String.class));

    }

    public static String getDataTest(String key) throws IOException, NodeException {
        final ClientConfig clientConfig = new DefaultClientConfig();
        final Client client = Client.create(clientConfig);
        final WebResource webResource = client.resource(UriBuilder.fromUri("https://nodes-testnet.wavesnodes.com/addresses/data/3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv/" + key).build());
        return (webResource.accept(String.valueOf(MediaType.APPLICATION_JSON)).get(String.class));

    }
}
