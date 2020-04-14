package com.fzxt.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {


    @Value("${fzxt.elasticsearch.hostlist}")
    private  String hostlist;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        //解析hostlist信息
        String[] split = hostlist.split(",");
        //创建httpHost数组 将es主机和端口文件
        HttpHost[] httpHosts = new HttpHost[split.length];
        int i = 0;
        for (String s : split) {

            String[] split1 = s.split(":");
            httpHosts[i] = new HttpHost(split1[0],Integer.parseInt(split1[1]));
            ++i;
        }

        //创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }

    @Bean
    public RestClient restClient(){
        //解析hostlist信息
        String[] split = hostlist.split(",");
        //创建httpHost数组 将es主机和端口文件
        HttpHost[] httpHosts = new HttpHost[split.length];
        int i = 0;
        for (String s : split) {

            String[] split1 = s.split(":");
            httpHosts[i] = new HttpHost(split1[0],Integer.parseInt(split1[1]));
            ++i;
        }

        return  RestClient.builder(httpHosts).build();
    }

}
