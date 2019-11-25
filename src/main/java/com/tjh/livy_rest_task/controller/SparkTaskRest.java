package com.tjh.livy_rest_task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tjh.livy_rest_task.common.response.LivyResponse;
import com.tjh.livy_rest_task.model.vo.SparkTaskParamVo;
import com.tjh.livy_rest_task.service.LivyRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/livy/spark")
public class SparkTaskRest {
    @Autowired
    private LivyRestService livyRestService;

    @GetMapping(path = "/get_task_info")
    public LivyResponse<Object> getHbase(@RequestParam String id) {
        Object result = livyRestService.getTaskInfo(id);
        return LivyResponse.ok(result);
    }

    @PostMapping(path = "/put_batch_spark_task")
    public LivyResponse<Object> putBatchSparkTask(@RequestBody String sparkJsonConfig) {
        SparkTaskParamVo sparkTaskParamVo = JSON.parseObject(sparkJsonConfig, SparkTaskParamVo.class);
        JSONObject sparkConfObj = JSON.parseObject(sparkTaskParamVo.getSparkConf());
        Map otherConfMap = JSON.parseObject(sparkTaskParamVo.getOtherConfig());
        JSONObject paramObj = new JSONObject();
        paramObj.put("conf", sparkConfObj);
        if (sparkTaskParamVo.getArgs() != null) {
            paramObj.put("args", sparkTaskParamVo.getArgs());
        }

        if (sparkTaskParamVo.getJars() != null) {
            paramObj.put("jars", sparkTaskParamVo.getJars());
        }

        if (!CollectionUtils.isEmpty(otherConfMap)) {
            for (Object entry : otherConfMap.keySet()) {
                paramObj.put(entry.toString(), otherConfMap.get(entry.toString()));
            }
        }

        String paramJson = paramObj.toJSONString();
        Object result = livyRestService.putLivyTask(paramJson);
        return LivyResponse.ok(result);
    }
}
