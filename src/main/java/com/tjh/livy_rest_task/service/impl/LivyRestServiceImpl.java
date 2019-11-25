package com.tjh.livy_rest_task.service.impl;

import com.tjh.livy_rest_task.service.LivyRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class LivyRestServiceImpl implements LivyRestService {
    @Resource(name = "httpRestTemplateObj")
    private RestTemplate restTemplate;

    @Value("${request.livybaseurl}")
    private String livyBaseUrl;

    @Override
    public Object putLivyTask(String paramJson) {
        String resultStr = "";
        try {
            String putLivyTaskUrl = this.livyBaseUrl + "/batches";
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(paramJson, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(putLivyTaskUrl, entity, String.class);
            if (result.getStatusCode() == HttpStatus.CREATED || result.getStatusCode() == HttpStatus.OK) {
                resultStr = result.getBody();

            }
        } catch (Exception ex) {

        }
        return resultStr;
    }

    @Override
    public Object getTaskInfo(String id) {
        String taskInfoUrl = this.livyBaseUrl + "/batches/" + id;
        ResponseEntity<String> result = restTemplate.getForEntity(taskInfoUrl, String.class);
        return result.getBody();
    }
}
