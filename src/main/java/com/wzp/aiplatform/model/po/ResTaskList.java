package com.wzp.aiplatform.model.po;

import com.wzp.aiplatform.model.TaskList;
import lombok.Data;

import java.util.List;

/**
 * Created by wzp on 2019-04-13 14:42
 */
@Data
public class ResTaskList {

    private Integer size;
    private List<TaskList> taskLists;
}
