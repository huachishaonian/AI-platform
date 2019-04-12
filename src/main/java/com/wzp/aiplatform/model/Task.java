package com.wzp.aiplatform.model;

import lombok.Data;

@Data
public class Task {

    private Integer taskid;
    private String taskname;
    private String taskshort;
    private Integer tasktype;
    private String taskdetail;

}