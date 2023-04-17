package com.example.service.test;

import com.example.entity.test.TestEntity;

import java.util.List;

public interface TestService {

    TestEntity getById(Integer id);

    List<TestEntity> getList();

    void save(TestEntity testEntity);

    void update(TestEntity testEntity);

    void delete(Integer id);
}
