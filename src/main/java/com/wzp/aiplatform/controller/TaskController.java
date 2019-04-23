package com.wzp.aiplatform.controller;

import com.wzp.aiplatform.model.po.ResTaskList;
import com.wzp.aiplatform.model.po.ShowTask;
import com.wzp.aiplatform.service.TaskService;
import com.wzp.aiplatform.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by wzp on 2019-04-12 16:29
 */

@RestController
@CrossOrigin(allowCredentials="true", origins = "http://localhost:8080", allowedHeaders = "*")
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 创建任务
     * @param taskName
     * @param taskShort
     * @param taskType
     * @param taskDetail
     * @param file
     * @return
     */
     @PostMapping("/uploadtask")
     public Mono<ApiResult<Object>> uploadTask(@RequestParam String taskName,
                                               @RequestParam String taskShort,
                                               @RequestParam Integer taskType,
                                               @RequestParam String taskDetail,
                                               @RequestParam MultipartFile file) {
         log.info("uploadTask ~ file = {}", file);
         return taskService.uploadTask(taskName, taskShort, taskType, taskDetail, file);
     }

    /**
     * 展示任务
     * @return
     */
    @GetMapping("/showtask")
     public Mono<ApiResult<? extends List<ShowTask>>> showTask() {
         log.info("showTask ~ ");
         return taskService.showTask();
     }

    /**
     * 展示详细任务
     * @param taskId
     * @param currentPage
     * @return
     */
     @PostMapping("/showtasklist")
     public Mono<ApiResult<ResTaskList>> showTaskList(@RequestParam Integer taskId,
                                                      @RequestParam Integer currentPage) {
         log.info("showTaskList ~ taskId = {}", taskId);
         return taskService.showTaskList(taskId, currentPage);
     }

    /**
     * 标注任务
     * @param taskListId
     * @param content
     * @param taskId
     * @return
     */
     @PostMapping("/labeltask")
    public Mono<ApiResult<Object>> labelTask(@RequestParam Integer taskListId, @RequestParam String content, @RequestParam Integer taskId) {
         log.info("labelTask ~ taskListId = {}, content = {}, taskId = {}", taskListId, content, taskId);
         return taskService.labelTask(taskListId, content, taskId);
    }
}
