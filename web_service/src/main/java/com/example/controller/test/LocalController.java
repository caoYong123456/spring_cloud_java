package com.example.controller.test;

import com.alibaba.fastjson.JSON;
import com.example.entity.test.TestEntity;
import com.example.service.test.TestService;
import com.example.utils.AjaxResult;
import com.example.utils.SendRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dev")
//@CrossOrigin
public class LocalController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestService testService ;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public TestEntity test(@RequestParam Integer id){
        logger.info("-----id:-----"+id);
        return testService.getById(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "limit",defaultValue = "10") int limit,
                           TestEntity testEntity){
        AjaxResult ajaxResult = new AjaxResult();
        HashMap map = new HashMap();
        PageHelper.offsetPage((page-1) * limit,limit);
        List<TestEntity> list = testService.getList(testEntity);
        logger.info("list:"+ JSON.toJSONString(list));
        PageInfo<TestEntity> pageInfo = new PageInfo<>(list);
        //计算总页数
        int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)limit);
        if(page > pageNumTotal){
            PageInfo<TestEntity> entityPageInfo = new PageInfo<>();
            entityPageInfo.setList(new ArrayList<>());
            entityPageInfo.setTotal(pageInfo.getTotal());
            entityPageInfo.setPageNum(page);
            entityPageInfo.setPageSize(limit);
            map.put("pageInfo",entityPageInfo);
        }else {
            map.put("pageInfo",pageInfo);
        }
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        ajaxResult.setData(map);
        logger.info("responseMsg:"+ JSON.toJSONString(ajaxResult));
        return ajaxResult;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public AjaxResult save(@RequestBody TestEntity testEntity){
        AjaxResult ajaxResult = new AjaxResult();
        if(testEntity.getId() != null){
            testService.update(testEntity);
        }else{
            testService.save(testEntity);
        }
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public AjaxResult delete(@RequestParam(required=false, value = "id") Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        testService.delete(id);
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }

    @RequestMapping(value = "/sendRequest",method = RequestMethod.GET)
    public String sendRequest(){
        return SendRequest.post();
    }
}