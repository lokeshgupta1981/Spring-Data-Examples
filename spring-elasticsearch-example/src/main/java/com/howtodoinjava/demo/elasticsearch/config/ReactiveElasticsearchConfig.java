package com.howtodoinjava.demo.elasticsearch.config;

import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

//@Configuration
//@EnableReactiveElasticsearchRepositories
public class ReactiveElasticsearchConfig extends ReactiveElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}
