package com.example.dao.test;

import com.example.entity.test.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TestDaoMapper {

    TestEntity getById(Integer id);

    List<TestEntity> getList();

    void save(TestEntity testEntity);

    void update(TestEntity testEntity);

    void delete(Integer id);

}