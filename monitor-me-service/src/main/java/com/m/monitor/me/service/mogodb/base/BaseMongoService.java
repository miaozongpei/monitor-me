package com.m.monitor.me.service.mogodb.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class BaseMongoService<T>{

    @Autowired
    private MongoTemplate mongoTemplate;

    public void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
    }
    public String createIndex(String collectionName, String filedName,boolean isUnique) {
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(filedName),  new IndexOptions().unique(isUnique));
    }
    public List<String> getAllIndexes(String collectionName) {
        ListIndexesIterable<Document> list = mongoTemplate.getCollection(collectionName).listIndexes();
        List<String> indexes = new ArrayList<>();
        for (Document document : list) {
            document.entrySet().forEach((key) -> {
                //提取出索引的名称
                if (key.getKey().equals("name")) {
                    indexes.add(key.getValue().toString());
                }
            });
        }
        return indexes;
    }

    public void insert(String collectionName,T t) {
        mongoTemplate.insert(t, collectionName);
    }

    public void update( String collectionName, String id,T info) {
        Update update = new Update();
        JSONObject jQuery = JSON.parseObject(JSON.toJSONString(info));
        jQuery.forEach((key, value) -> {
            if (!key.equals("id")) {
                update.set(key, value);
            }
        });
        mongoTemplate.updateMulti(createQuery(id), update, info.getClass(), collectionName);
    }

    public void delete(String id,String collectionName,Class<T> clazz) {
        mongoTemplate.remove(createQuery(id), clazz, collectionName);
    }
    public T find(String id, Class<T> clazz, String collectionName) {
        return mongoTemplate.findById(id, clazz, collectionName);
    }
    public List<T> findAll(String collectName, Class<T> clazz) {
        return findPage(collectName ,null,null, null,clazz);
    }

    public List<T> findPage(String collectName,Query query, Integer currentPage, Integer size,Class<T> clazz) {
        if (!ObjectUtils.isEmpty(currentPage) && ObjectUtils.isEmpty(size)) {
            query.limit(size).skip(size * (currentPage - 1));
        }
        return mongoTemplate.find(query, clazz, collectName);
    }
    public List<T> find(String collectName,Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz, collectName);
    }
    private Query createQuery(String id){
        return new Query(Criteria.where("id").is(id));
    }
}