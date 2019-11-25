package com.tjh.livy_rest_task.service;

import com.alibaba.fastjson.JSONObject;

public interface LivyRestService {
    Object putLivyTask(String paramJson);
    Object getTaskInfo(String id);
}
