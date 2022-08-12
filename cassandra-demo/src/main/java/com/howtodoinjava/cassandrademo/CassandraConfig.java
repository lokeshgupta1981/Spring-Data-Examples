package com.howtodoinjava.cassandrademo;

import com.datastax.astra.boot.autoconfigure.AstraClientProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Primary
@Configuration
@EnableCassandraRepositories(basePackages = {"com.howtodoinjava.cassandrademo"})
public class CassandraConfig extends AstraClientProperties {

    @Override
    public String getClientId() {
        return "QUIllHLpRUzTZENLUHfiqskr";
    }

    @Override
    public String getApplicationToken() {
        return "AstraCS:QUIllHLpRUzTZENLUHfiqskr:b2c77ecc13063a0e84fc0c3902c6e078a9944b641cc70e2ee991fb24022164b7";
    }

    @Override
    public String getClientSecret() {
        return "WOxGafNZJymadT4-yEjZQTK2C_uiMR9pUD6Fwewcg.7i0uONW2ECFv6_HGLbXwcW3F+vCaEwlB7ouWDXTw1g2nSAA.l-S,iK6gpU0iPftGKgrgUJ5qzyh3EwdjD2oLHE";
    }

    @Override
    public String getCloudRegion() {
        return "us-east1";
    }

    @Override
    public String getDatabaseId() {
        return "8e152e11-38a6-40bd-86fa-155b3d2bfd67";
    }

    @Override
    public String getKeyspace() {
        return "spring_cassandra";
    }
}
