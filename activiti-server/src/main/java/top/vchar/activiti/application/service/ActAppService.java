package top.vchar.activiti.application.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>  activiti 服务 </p>
 *
 * @author vchar fred
 * @create_date 2024/5/16
 */
@Service
public class ActAppService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 开始流程
     *
     * @return instance id
     */
    public String start() {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("demo1");
        return instance.getId();
    }

    /**
     * 查询用户任务列表
     *
     * @param uid 用户id
     * @return user task list
     */
    public List<Task> getTask(String uid) {
        return taskService.createTaskQuery().taskAssignee(uid).list();
    }

    public String deploy(String name, InputStream fin) {
        String deploymentId = repositoryService.createDeployment()
                .addInputStream(name, fin)
                .name(name)
                .key(name)
                .deploy()
                .getId();
        return deploymentId;
    }
}
