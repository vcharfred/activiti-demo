package top.vchar.activiti.adapter.api;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.vchar.activiti.application.dto.TaskRepresentation;
import top.vchar.activiti.application.service.ActAppService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>  activiti api 接口 </p>
 *
 * @author vchar fred
 * @create_date 2024/5/16
 */
@RestController
@RequestMapping("/activiti")
public class ActController {

    @Autowired
    private ActAppService actAppService;

    @GetMapping("/start")
    public String start() {
        return actAppService.start();
    }

    @GetMapping("/task")
    public List<TaskRepresentation> getTask(@RequestParam(value = "uid") String uid) {
        List<Task> tasks = actAppService.getTask(uid);
        List<TaskRepresentation> list = new ArrayList<>();
        for (Task task : tasks) {
            list.add(TaskRepresentation.builder().id(task.getId()).name(task.getName()).build());
        }
        return list;
    }

    @PostMapping(value = "/deployBpmn")
    public String deployBpmn(@RequestParam("file") MultipartFile file) {
        try {
            String name = file.getOriginalFilename();
            if (!name.endsWith(".bpmn20.xm") && !name.endsWith(".bpmn")) {
                name = name + ".bpmn";
            }
            return actAppService.deploy(name, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("upload failed");
        }
    }
}