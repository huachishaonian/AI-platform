package com.wzp.aiplatform.service.impl;

import com.wzp.aiplatform.mapper.TaskListMapper;
import com.wzp.aiplatform.mapper.TaskMapper;
import com.wzp.aiplatform.mapper.ZipMapper;
import com.wzp.aiplatform.model.Task;
import com.wzp.aiplatform.model.TaskList;
import com.wzp.aiplatform.model.Zip;
import com.wzp.aiplatform.service.TaskService;
import com.wzp.aiplatform.utils.ApiResult;
import com.wzp.aiplatform.utils.StaticConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * Created by wzp on 2019-04-12 16:28
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private StaticConfig staticConfig;
    @Autowired
    private ZipMapper zipMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskListMapper taskListMapper;

    @Override
    public Mono uploadTask(String taskName, String taskShort, Integer taskType,
                                                           String taskDetail, MultipartFile file) {
        return Mono.fromSupplier(() -> {

            Task task = taskMapper.selectByName(taskName);
            if (task != null && !StringUtils.isEmpty(task.getTaskname())) {
                return ApiResult.getApiResult(-1, "the taskname already exists");
            }
            String filePath = staticConfig.getUploadPath();
            String fileName = null;
            if(!file.isEmpty()){
                fileName = file.getOriginalFilename();
                String zipfilename = zipMapper.selectByPrimaryKey(fileName);
                if (!StringUtils.isEmpty(zipfilename)) {
                    return ApiResult.getApiResult(-1, "the zipfile exists");
                }
                try {
                    task = new Task();
                    task.setTaskname(taskName);
                    task.setTaskshort(taskShort);
                    task.setTasktype(taskType);
                    task.setTaskdetail(taskDetail);
                    Zip zip = new Zip();
                    zip.setFilename(fileName);
                    if (taskMapper.insert(task) > 0 && zipMapper.insert(zip) > 0) {
                        file.transferTo(new File(filePath + File.separator + fileName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File zipFile = new File(staticConfig.getUploadPath() + File.separator + fileName);
            unZip(zipFile, staticConfig.getDecompressionPath() + File.separator + fileName);
            Integer taskId = taskMapper.selectByName(taskName).getTaskid();
            getFile(staticConfig.getDecompressionPath() + File.separator + fileName, taskId);
            return ApiResult.getApiResult("create the task successfully", taskId +"," + fileName);
        }).publishOn(Schedulers.elastic()).doOnError(t ->
                log.error("uploadTask error!~~,taskName == {}", taskName,t))
                .onErrorReturn(ApiResult.getApiResult(-1, "create the task failly"));
    }
     /**
       * zip解压
       * @param srcFile        zip源文件
       * @param destDirPath     解压后的目标文件夹
       * @throws RuntimeException 解压失败会抛出运行时异常
       */
    public void unZip(File srcFile, String destDirPath) throws RuntimeException {

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                File targetFile = new File(destDirPath + File.separator + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[BUFFER_SIZE];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getFile(String path, Integer taskId) {
        File file = new File(path);
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                TaskList taskList = new TaskList();
                taskList.setTaskid(taskId);
                taskList.setTasklistname(array[i].getName());
                taskListMapper.insert(taskList);
            }
        }
    }

}
