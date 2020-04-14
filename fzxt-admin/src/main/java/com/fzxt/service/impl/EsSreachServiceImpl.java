package com.fzxt.service.impl;

import com.fzxt.model.Course;
import com.fzxt.response.QueryResult;
import com.fzxt.service.EsSreachService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Service
public class EsSreachServiceImpl implements EsSreachService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Value("${fzxt.course.index}")
    private String es_index;
    @Value("${fzxt.course.type}")
    private String es_type;
    @Value("${fzxt.course.source_field}")
    private String source_field;

    @Override
    public QueryResult<Course> searchCourseList(Course course) {

        //设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        //source源字段过滤
        String[] sourceFiles = source_field.split(",");
        searchSourceBuilder.fetchSource(sourceFiles,new String[]{});


        //过滤 按类型和机构查询
        if(StringUtils.isNotEmpty(course.getTypeId())){
            searchSourceBuilder.query(QueryBuilders.termQuery("typeId",course.getTypeId()));
        }
        if(StringUtils.isNotEmpty(course.getOrgnId())){
            searchSourceBuilder.query(QueryBuilders.termQuery("orgnId",course.getOrgnId()));
        }
        if(StringUtils.isNotEmpty(course.getName())) {
            searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name", course.getName()));
        }
        if(StringUtils.isNotEmpty(course.getDescription())){
            searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("description",course.getDescription()));
        }

        int page = course.getRow();
        int size = course.getSize();
        //分页处理
        if(page <= 0){
            page = 1;
        }

        if(size <= 0){
            size = 10;
        }

        int start = (page -1) * page;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);


        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("description"));

        searchSourceBuilder.highlighter(highlightBuilder);
        //请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;

        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("fzxt course search..{}",e.getMessage());
            return new QueryResult<Course>(new ArrayList<>(),0);
        }

        //结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //记录总数
        long totalHits = hits.getTotalHits();
        //数据列表
        ArrayList<Course> courses = new ArrayList<>();

        for (SearchHit hit : searchHits) {
            Course cou = new Course();
            String name = "";
            String description = "";
            //取出source
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields.get("name") != null){
                Text[] names = highlightFields.get("name").getFragments();
                for (Text t : names) {
                    name += t.string();
                }
            }
            if(highlightFields.get("description") != null){
                Text[] names = highlightFields.get("description").getFragments();
                for (Text t : names) {
                    description += t.string();
                }
            }
            String id = (String) sourceAsMap.get("id");
            String typeId = (String)sourceAsMap.get("typeId");
            String orgnId = (String)sourceAsMap.get("orgnId");
            String createTime = (String)sourceAsMap.get("createTime");
            String updateTime = (String)sourceAsMap.get("updateTime");

            cou.setId(id);
            cou.setName(name);
            cou.setCreateTime(createTime);
            cou.setDescription(description);
            cou.setOrgnId(orgnId);
            cou.setTypeId(typeId);
            cou.setUpdateTime(updateTime);
            courses.add(cou);
        }

        QueryResult<Course> courseQueryResult = new QueryResult<>(courses,totalHits);
        return courseQueryResult;
    }
}
