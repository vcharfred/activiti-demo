package top.vchar.activiti.application.dto;

import lombok.*;

/**
 * <p> 任务 </p>
 *
 * @author vchar fred
 * @create_date 2024/5/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TaskRepresentation {

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;
}
