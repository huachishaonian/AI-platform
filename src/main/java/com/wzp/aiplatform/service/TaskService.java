package com.wzp.aiplatform.service;

import com.wzp.aiplatform.model.po.ResTaskList;
import com.wzp.aiplatform.model.po.ShowTask;
import com.wzp.aiplatform.utils.ApiResult;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by wzp on 2019-04-12 16:26
 */

public interface TaskService {

    /**
     * 创建任务
     * @param taskName
     * @param taskShort
     * @param taskType
     * @param taskDetail
     * @param file
     * @return
     */
    Mono<ApiResult<Object>> uploadTask(String taskName, String taskShort, Integer taskType, String taskDetail, MultipartFile file);

    /**
     * 展示任务
     * @return
     */
    Mono<ApiResult<? extends List<ShowTask>>> showTask();

    Mono<ApiResult<String>> showDetail(Integer taskId);

    /**
     * 展示详细任务
     * @param taskId
     * @param currentPage
     * @return
     */
    Mono<ApiResult<ResTaskList>> showTaskList(Integer taskId, Integer currentPage);

    /**
     * 标注任务
     * @param taskListId
     * @param content
     * @param taskId
     * @return
     */
    Mono<ApiResult<Object>> labelTask(Integer taskListId, String content, Integer taskId);
}
