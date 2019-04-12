package com.wzp.aiplatform.model;

import lombok.Data;

@Data
public class TaskList {

    private Integer tasklistid;
    private Integer taskid;
    private String tasklistname;
    private Boolean finished;

}