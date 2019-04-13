package com.wzp.aiplatform.service;

import com.wzp.aiplatform.model.TaskList;
import com.wzp.aiplatform.model.po.ResTaskList;
import com.wzp.aiplatform.utils.ApiResult;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by wzp on 2019-04-12 16:26
 */

public interface TaskService {

    Mono<ApiResult<Object>> uploadTask(String taskName, String taskShort, Integer taskType, String taskDetail, MultipartFile file);

    Mono<ApiResult<ResTaskList>> showTaskList(Integer taskId, Integer currentPage);
}
