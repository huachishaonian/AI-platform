package com.wzp.aiplatform.controller;

import com.wzp.aiplatform.service.TaskService;
import com.wzp.aiplatform.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * Created by wzp on 2019-04-12 16:29
 */

@RestController
@CrossOrigin(allowCredentials="true", origins = "http://localhost:8080")
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

     @PostMapping("/uploadtask")
     public Mono<ApiResult<Object>> uploadTask(@RequestParam String taskName,
                                               @RequestParam String taskShort,
                                               @RequestParam Integer taskType,
                                               @RequestParam String taskDetail,
                                               @RequestParam MultipartFile file) {
         log.info("uploadTask ~ file = {}", file);
         return taskService.uploadTask(taskName, taskShort, taskType, taskDetail, file);
     }
}
