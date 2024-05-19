# Activiti6.0

* Activiti官网[https://www.activiti.org/](https://www.activiti.org/)
* Activiti6下载[https://www.activiti.org/get-started](https://www.activiti.org/get-started)
* Activiti Github[https://github.com/Activiti/Activiti](https://github.com/Activiti/Activiti)
* BPMN流程图[https://bpmn.io/](https://bpmn.io/)

当BPMN2.0规范在2011年被发布时，各个工作流引擎的供应商均向其靠拢，包括jBPM5和Activiti。
2010年12月发布了Activiti的第一个版本Activiti5.0；Activiti6.0于2017年5月发布。
Activiti6.0是Activiti5.0的升级版本，在Activiti5.0的基础上，对流程引擎进行了升级。

Activiti平台全面兼容BPMN 2.0规范，因此，无论是采用何种遵循BPMN 2.0标准的流程模型编辑器所构建的设计成果，原则上都能够无缝接入并应用于Activiti流程引擎之中。

## 下载Activiti6.0

> 参考文章：https://zhuanlan.zhihu.com/p/691432915

可以直接访问[Activiti官网](https://www.activiti.org/get-started)
导航页面下载[Activiti6的全套资源包](https://github.com/Activiti/Activiti/releases/download/activiti-6.0.0/activiti-6.0.0.zip)
，其已精心整合了配套的开发指南和技术参考文献，其中涵盖了深度解析的JavaDoc内容。尤为称道的是，官方提供的这套文档凝练而不失周全，强烈建议具备阅读条件的用户，花时间细致研读这份文档，它将有助于您系统地领略和掌握Activiti6的所有特性及其运用之道。

activiti-6.0.0.zip压缩包解压后，目录结构如下：

```text
activiti-6.0.0.zip
├── database             # 数据库表相关脚本
│ └── create             # 存放用于初始化数据库表结构的脚本
│ └── drop               # 包含移除数据库表的脚本
│ └── upgrade            # 提供了从旧版本升级到当前6.0.0版本所需的相关数据库更新脚本
├── libs                 # 包含了众多与Activiti 6.0.0版本相关的Java库文件（JAR包）
├── wars                 # 包含了三个可部署至应用服务器的WAR包
│ └── activiti-admin.war # 用于管理界面
│ └── activiti-app.war   # 主要应用功能模块
│ └── activiti-rest.war  # 提供restful API服务
├── activiti_readme      # 包含一张名为"activiti_readme.png"的说明图片
├── license.txt          # 详细列出了Activiti项目的授权许可条款
├── notice.txt           # 记载了关于软件使用的版权信息和其他重要声明。 
└── readme.html          # 提供了一个HTML格式的详尽文档，便于用户快速了解Activiti 6.0.0的安装步骤、配置方法以及使用教程等内容。
```

我们主要关注3个war包，将三个war文件放到tomcat的webapps目录下就可以完成部署。在初次进行部署时，初始化数据库的过程可能会耗时较长，系统默认采用了嵌入式的H2内存数据库作为存储方案。后续我将逐步指导如何将数据库配置替换为自有数据库系统，以满足不同场景下的存储需求。

#### activiti-app

该应用程序集成了构建流程模型和用户权限管理等功能模块，在实际操作中，用户需在此平台上设计并构建相应的业务流程模型，并能够将其导出为标准的BPMN文件格式。这样，这些设计好的流程模型即可被无缝地整合进我们的代码中，实现对业务流程的有效驱动和控制。

完成部署之后，访问本地主机上的指定地址即 [http://localhost:8080/activiti-app](http://localhost:8080/activiti-app)
，系统会自动加载预设的默认账户凭据，用户名为`admin`，密码为`test`。登录后，用户将看到已经预先配置好的三个独立的应用程序实例。

* 启动应用套件（Kickstart app）涵盖了四大核心功能板块，分别涉及流程蓝图的构思与绘制、表单结构的设计制作、决策表格的配置规划以及整个应用程序的综合管理。
* 任务处理应用（Task App）中，用户可以体验简洁明了的待办事项审批界面，同时也具备发起新流程的基本入口。
* 身份与权限管理(Identity management)，则是一个专门用于统筹和调整用户账户以及用户组权限分配的工具。
* 数据库配置文件在activiti-app/WEB-INF/classes/META-INF/activiti-app/activiti-app.properties

#### activity-admin

Activity-admin应用程序扮演着流程运行时管控中心的角色，它允许用户实时洞察并管理所有正在执行的流程实例及其关联的任务，并且还支持流程定义文件的上传操作。默认的登录凭证是admin/admin。
* 修改restful API接口地址配置 activiti-admin.properties

#### activity-rest

Activity-rest组件提供了基于restful API的方式来与Activiti流程引擎交互的能力，但其API的调用必须遵循HTTP基础认证机制以确保安全访问。默认的登录凭证是kermit/kermit。
* 数据库配置文件activiti-rest/WEB-INF/classes/db.properties


## 数据库的切换

> 参考文章：https://zhuanlan.zhihu.com/p/691508094

Activiti默认配置使用轻量级的H2内存数据库，对于初步的本地测试足以满足需求，但在面对更为复杂和高要求的测试环境、开发环境乃至生产环境时，这种内存数据库显然无法满足长期稳定和大规模数据处理的需求。因此，为了适应不同的应用场景，有必要切换到更加强大、灵活且适合生产的数据库解决方案。

在解压得到的Activiti资源包中database下的create目录中包含了用于在各种主流关系型数据库上搭建架构的数据库创建脚本。

#### MySQL

选择MySQL作为作为Activiti的数据存储解决方案，Activiti 6提供了两种途径来初始化数据库结构：

* 使用SQL脚本，这种方式适用于不具备生产环境直接操作的开发人员；

脚本部署执行以下三个脚本(mysql的版本需要高于5.6.4):

```text
activiti.mysql.create.engine.sql  
activiti.mysql.create.history.sql  
activiti.mysql.create.identity.sql 
```

如果mysql的版本低于5.6.4，那么需要执行(低版本的mysql不支持timestamps类型)：

```text
activiti.mysql55.create.engine.sql  
activiti.mysql55.create.history.sql  
activiti.mysql.create.identity.sql  
```

* 另一种更加便捷的方式是利用Activiti自身提供的工具类，即在libs/activiti-engine-6.0.0.jar内部封装了一个名为DbSchemaCreate的类，开发人员若拥有生产环境数据库的访问凭证（用户名和密码），可以直接运行此类来自动完成数据库表结构的创建。

> 注意无论使用那种方式，都需要先手动创建一个空白的MySQL数据库。

创建一个ActivitiCreate类，用于执行DbSchemaCreate类的main方法
```java
public class ActivitiCreate {
    public static void main(String[] args) {
        DbSchemaCreate.main(args);
    }
}
```

在src/resources文件夹下创建activiti.cfg.xml文件
```text
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="processEngineConfiguration" class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
        <property name="databaseType" value="mysql"/>
        <property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/activiti"/>
        <property name="jdbcDriver" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUsername" value="root"/>
        <property name="jdbcPassword" value="123456"/>
    </bean>
</beans>
```

运行该类会有28张表被创建，除了ACT_EVT_LOG表外，其他的是业务表。
```text
ACT_EVT_LOG  存储事件处理日志

# General（通用）模块，用于承载那些跨越多个模块、在整个Activiti框架中通用的数据实体
ACT_GE_BYTEARRAY  
ACT_GE_PROPERTY 属性数据表

# History（历史记录）模块  
ACT_HI_ACTINST  
ACT_HI_ATTACHMENT  
ACT_HI_COMMENT  
ACT_HI_DETAIL  
ACT_HI_IDENTITYLINK  
ACT_HI_PROCINST  历史流程实例信息
ACT_HI_TASKINST  历史任务流程实例信息
ACT_HI_VARINST  历史变量信息

# Identity（身份识别）模块，负责存储组织机构内的用户、角色、组等认证和权限相关的信息
ACT_ID_GROUP  用户组信息表
ACT_ID_INFO   用户扩展信息表
ACT_ID_MEMBERSHIP 用户和用户组关联表
ACT_ID_USER  用户信息表

ACT_PROCDEF_INFO 流程定义扩展表

# Repository（资源库）模块，主要用于存放流程相关的静态资源，如流程模型定义、流程图元信息等持久化数据
ACT_RE_DEPLOYMENT  部署信息表
ACT_RE_MODEL  流程设计模型表，创建流程的设计模型时，保存在该表中
ACT_RE_PROCDEF  流程定义解析表

# Runtime（运行时）模块；记录流程运行期间产生的各种数据；流程结束后会被定时清理掉
ACT_RU_DEADLETTER_JOB  
ACT_RU_EVENT_SUBSCR  运行时事件表
ACT_RU_EXECUTION  运行时流程执行实例，待办任务查询表
ACT_RU_IDENTITYLINK  存储当前节点参与者的信息，任务参与者数据表
ACT_RU_JOB  运行中的任务，运行时定时任务数据表
ACT_RU_SUSPENDED_JOB  
ACT_RU_TASK  
ACT_RU_TIMER_JOB  
ACT_RU_VARIABLE
```
在Activiti中，各个数据库表名皆以前缀"ACT_"开始，并紧随两个字母以标识它们所属的不同模块范畴：
* 表前缀ACT_RE_指向Repository（资源库）模块，主要用于存放流程相关的静态资源，如流程模型定义、流程图元信息等持久化数据。
* ACT_RU_开头的表代表Runtime（运行时）模块，这部分数据库记录了在流程运行期间生成的各种动态数据，包括流程实例详情、用户任务状态、定时任务安排等临时信息。值得注意的是，一旦流程结束，这些运行时数据会被适时清理以维持数据库的精简，从而确保系统性能的高效。
* 使用ACT_ID_作为前缀的表属于Identity（身份识别）模块，负责存储组织机构内的用户、角色、组等认证和权限相关的信息。
* ACT_HI_打头的表服务于History（历史记录）模块，保留了流程实例的历史数据，例如已完成流程实例的状态记录、流程变量的历史值等，为后期审计和追溯提供依据。
最后，以ACT_GE_起始的表集合代表General（通用）模块，用于承载那些跨越多个模块、在整个Activiti框架中通用的数据实体。

## 使用示例
下面的示例都是基于spring boot2来的(因为官方提供的整理包不支持spring boot3)，添加如下maven依赖
```text
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
<!--         activiti -->
        <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter-basic</artifactId>
            <version>6.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
```
启动类如下：
```java
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiRestfulApplication.class);
    }
}
```

### 部署流程
当执行部署指令时，Activiti后台系统执行了一系列操作，包括在"ACT_RE_DEPLOYMENT"表中新增一行记录，用于追踪部署信息；同时，将流程文件的内容作为字节数组存入"ACT_GE_BYTEARRAY"表中的"BYPES_"字段，以实现资源在数据库中的持久化存储。

#### 部署方式一
在resources文件夹下创建processes文件夹，并将流程定义文件放入其中。在启动时会自动加载该文件夹下的所有流程定义文件。这种方式尽在学习时做demo示例时使用，实际使用中不推荐使用这种方式。在实际使用中，应使用Activiti的API来部署流程定义文件，这样避免每次改动流程定义文件都需要重新部署服务。

#### 部署方式二
> 文件名必须为"bpmn20.xml"或以"bpmn"作为扩展名结尾

```java
@Autowired
private RepositoryService repositoryService;

public String deploy(String name, InputStream fin) {  
        String deploymentId = repositoryService.createDeployment()  
                .addInputStream(name, fin)  
                .name(name)  
                .key(name)  
                .deploy()  
                .getId();  
        return deploymentId;  
}  
```

### activiti API介绍

Activiti API体系主要分为两大类别：Service与Query。Service接口专注于执行操作，负责处理数据的增删改等动作；而Query接口则专司查询职责，负责检索和筛选数据。在Spring Boot应用中，Service接口可以通过依赖注入轻松获取，而Query接口则可通过相应的Service进一步获取。值得注意的是，所有Service接口都能通过ProcessEngine核心对象间接获取。

#### service接口
Activiti有9个service来管理Activiti:

* RepositoryService：用于管理流程定义的部署、撤销部署、查询和删除等操作，以及与流程资源相关的其他操作。
* RuntimeService：处理运行时流程实例的管理，包括启动新流程实例、查询和操作运行中的流程实例、变量管理以及信号发送等。
* TaskService：管理任务相关的操作，如创建、查询、完成、委派和更新任务，以及与任务相关的变量和附件等。
* IdentityService：处理用户、组和成员身份相关的管理，包括用户和组的创建、查询和删除等。
* HistoryService：提供对流程历史数据的查询，包括历史流程实例、历史任务、历史变量和审计信息等。
* FormService：用于处理与表单相关的操作，如获取表单定义、绑定表单到任务等。
* ManagementService：提供了一些管理层面的操作，如作业管理、数据库schema的创建和更新、事务管理和命令执行等。
* BatchService：处理批处理任务，例如批量更新流程实例、历史数据等。
* DynamicBpmnService:用于动态修改已部署流程模型的服务接口，允许在运行时修改流程定义的结构，比如添加、删除或更新任务、网关等流程元素。

#### query接口
Activiti的Query接口主要提供了查询流程实例、任务、历史数据等操作。这些接口都继承自Query接口，并实现了不同的查询方法。通过这些方法，可以获取到符合特定条件的数据，并返回一个结果集。

Query接口都是实现了org.activiti.engine.query.Query接口，其中T表示查询对象类型，U表示查询结果类型。
```java
public interface Query<T extends Query<?, ?>, U extends Object> {}
```

> ProcessEngine 中包含了所有的service，可以通过该对象来获取service

## 核心概念
> https://zhuanlan.zhihu.com/p/691702464

* 部署（Deployment）: 指流程定义文件（如BPMN 2.0 XML文件）被上传至Activiti引擎并注册为可执行流程模型的过程。部署后，流程模型可在运行时被实例化和执行。
* 实例（Instance）: 指流程模型在运行时被激活并开始执行的一个具体流程执行过程。每个流程实例都对应一个独特的流程执行历程，包含了流程运行过程中的所有活动和数据。
* 执行（Execution）: 在Activiti中，执行是对流程实例内部状态的抽象表现，它代表了流程执行路径上的一个点，包含了流程实例执行上下文，如当前活动、变量、任务等信息。执行可以分解成多个子执行，体现了流程的并发和分支结构。
* 任务（Task）: 表示流程实例中等待人工参与的一个工作单元，通常与流程中的用户任务节点相对应。任务通常由流程参与者接收、处理并完成，通过TaskService进行管理，如领取、完成、查询等操作。任务是流程实例执行过程中人机交互的重要环节。

### 部署（Deployment）
通过RepositoryService进行部署。当同一名称的流程模型文件进行重新部署时，系统会自动为其创建一个新的版本，原有流程实例不受影响，仍在原版本下继续运行直至结束。因此，每一次部署操作（deployment）实质上是对一组资源的发布行为，而这组资源不仅可以是BPMN流程模型文件，还可以是任何类型的文件。

每完成一次部署，系统会在ACT_RE_DEPLOYMENT表中记录这次部署的元数据信息，与此同时，部署所涉及的资源内容将以二进制格式存储在ACT_GE_BYTEARRAY表中。这两张表之间通过DEPLOYMENT_ID字段建立起关联关系，确保了资源与其部署记录之间的对应性。

### 实例（Instance）
在Activiti流程管理中，每当一个流程被启动一次，系统便会生成一个对应的流程实例，而流程实例的数量对于流程的统计至关重要。例如，若某一流程产生了10000个实例，就意味着该流程已被启动执行了10000次。值得注意的是，流程实例在Activiti中并没有单独的实体表进行存储，而是通过流程实例ID（PROC_INST_ID_）贯穿整个流程生命周期，该ID在流程发起时由系统分配，并始终保持不变。

当通过API启动流程时，应用程序应妥善保管并记录下分配给该流程实例的PROC_INST_ID_，因为它在后续流程的管理和追踪过程中不可或缺。例如，若要查询某个特定流程实例当前的审批人信息，应用程序应调用相关的API，传入对应的PROC_INST_ID_参数进行查询操作。

```mysql
SELECT table_name,column_name FROM information_schema.COLUMNS where COLUMN_NAME='PROC_INST_ID_';

+-----------------------+---------------+  
| TABLE_NAME            | COLUMN_NAME   |  
+-----------------------+---------------+  
| ACT_EVT_LOG           | PROC_INST_ID_ |  
| ACT_FO_SUBMITTED_FORM | PROC_INST_ID_ |  
| ACT_HI_ACTINST        | PROC_INST_ID_ |  
| ACT_HI_ATTACHMENT     | PROC_INST_ID_ |  
| ACT_HI_COMMENT        | PROC_INST_ID_ |  
| ACT_HI_DETAIL         | PROC_INST_ID_ |  
| ACT_HI_IDENTITYLINK   | PROC_INST_ID_ |  
| ACT_HI_PROCINST       | PROC_INST_ID_ |  
| ACT_HI_TASKINST       | PROC_INST_ID_ |  
| ACT_HI_VARINST        | PROC_INST_ID_ |  
| ACT_RU_EVENT_SUBSCR   | PROC_INST_ID_ |  
| ACT_RU_EXECUTION      | PROC_INST_ID_ |  
| ACT_RU_IDENTITYLINK   | PROC_INST_ID_ |  
| ACT_RU_TASK           | PROC_INST_ID_ |  
| ACT_RU_VARIABLE       | PROC_INST_ID_ |  
+-----------------------+---------------+ 
```

### 执行（Execution）

在Activiti工作流引擎中，Execution对象代表了流程实例中的一条执行路径，这一概念初看起来较为抽象。设想一个简单的流程，从起点（start event）到终点（end event）构成一条线性的执行路径，这时，整个流程仅对应一个Execution实体。然而，当流程出现分支（如并行审批环节），在到达并行网关时，流程会分化为多条执行路径，此时每一条路径都将对应一个新的Execution对象。

Execution之间的父子关系通过PARENT_ID_字段建立联系，这种设计有助于区分不同执行路径上的数据，避免数据混乱。例如，在流程的一个分支A上定义了一个变量，若没有Execution的隔离，当流程在另一个分支B上重新定义相同变量时，可能会造成数据覆盖的问题。

值得注意的是，流程实例（ProcessInstance）自身也被视为一个Execution，其在Execution表中的唯一标识即为流程实例ID（PROC_INST_ID_），这一点可以从源代码层面得到证实。通过这样的设计，流程实例与执行路径形成了紧密的内在关联，使得流程实例能够反映出整个流程执行的完整脉络。

### 任务（Task）

在Activiti工作流框架中，task指UserTask，UserTask特指那些分配给单个用户进行处理的任务单元，它对应着每位参与者具体的待办事项。通过调用Activiti API，开发人员能够检索特定用户当前所面临的待办任务列表。当流程实例流转至UserTask阶段时，该流程实例会自动挂起并进入待办状态，静候用户完成相应的审批或其他操作。

换言之，在流程执行过程中，一旦触及到需要人工介入的UserTask节点，流程便会暂停前行，直到相关用户完成了他们的任务。这些由用户待办的任务详情会被记录在Activiti数据库中的ACT_RU_TASK表内，以便系统跟踪任务状态及后续流程推进。

### 变量（Variable）
> https://zhuanlan.zhihu.com/p/691733323

* 执行变量（Execution Variable）：与Execution关联，作用范围涵盖整个流程实例执行路径。
* 本地变量（Local Variable）：与Task紧密相关，作用域限定在特定的任务节点上。
* 临时变量（Transient Variable）：这类变量不存储在数据库中，仅存在于内存中，当流程进入等待状态（如UserTask节点）时，临时变量会自动清除。

RuntimeService和TaskService是操作流程变量的核心服务接口。变量的表结构表：ACT_RU_VARIABLE是用来存储变量的

在Activiti中，变量的名称由NAME_字段标识，而其数据类型由TYPE_字段确定。根据不同类型，系统会将变量的值存储在相应的数据库字段内。例如，若TYPE_为字符串类型，其值将被存入TEXT_或TEXT_2字段；若是二进制数据，则该数据会被存储到ACT_GE_BYTEARRAY表中，并在当前变量记录中通过BYTEARRAY_ID_字段引用存储在该表中的ID。

至于变量的作用域控制，EXECUTION_ID_、PROC_INST_ID_和TASK_ID_字段起到了关键作用。若TASK_ID_字段为空，则该变量属于执行变量，作用于整个流程实例的执行路径；反之，若TASK_ID_字段具有非空值，则该变量被定义为本地变量，其作用域限于与该任务ID关联的任务节点之内。

#### TaskService的变量
TaskService接口内设有多项用于操作任务变量的方法，其中两个尤为关键：
```java
/**
 * 用于设定全局变量，即流程实例级别的变量。一旦设置了全局变量，它在整个流程实例的执行过程中对所有相关部分可见
 */
void setVariable(String taskId, String variableName, Object value);

/**
 * 用于设置本地变量，其作用范围限定在当前任务节点内，这意味着设置的本地变量仅对该任务可见，超出该任务范围则不可见。
 */
void setVariableLocal(String taskId, String variableName, Object value);  
```

获取变量：
```java
// 获取全局变量
taskService.getVariable(taskId, variableName);
// 获取本地变量
taskService.getVariableLocal(taskId, variableName);
```
> 底层查询SQL：taskService.getVariable
> 1.先通过任务ID查询 SELECT * FROM ACT_RU_VARIABLE WHERE TASK_ID_ = #{taskId} AND NAME_ = #{variableName}
> 2.没有查询到则查询执行变量，若还是没有查询到则继续查询父级的变量：SELECT * FROM ACT_RU_VARIABLE WHERE EXECUTION_ID_ = #{executionId} AND NAME_ = #{variableName}


#### RuntimeService的变量
当流程启动时，通过参数传递并保存在流程实例内的变量在整个流程运转期间均可被访问。
```java
public String start() {  
    Map<String, Object> params = new HashMap<>();  
    params.put("startVariable", "this is startVariable");  
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("myProcess1", params);  
    return instance.getId();  
} 
```

#### Execution变量
```java
runtimeService.setVariable(task.getExecutionId(), "runtimeVariable", "this is runtimeVariable");  
runtimeService.setVariableLocal(task.getExecutionId(), "runtimeLocalVariable", "this is Local runtimeLocalVariable");  
System.out.println("getVariable(variable) ==>"+runtimeService.getVariable(task.getExecutionId(),"runtimeVariable"));  
System.out.println("getVariable(localVariable) ==>"+runtimeService.getVariable(task.getExecutionId(),"runtimeLocalVariable"));  
System.out.println("getVariableLocal(variable) ==>"+runtimeService.getVariableLocal(task.getExecutionId(),"runtimeVariable"));  
System.out.println("getVariableLocal(localVariable) ==>"+runtimeService.getVariableLocal(task.getExecutionId(),"runtimeLocalVariable")); 
```
结果为：
```java
getVariable(variable) ==>this is runtimeVariable  
getVariable(localVariable) ==>this is Local runtimeLocalVariable  
getVariableLocal(variable) ==>null  
getVariableLocal(localVariable) ==>this is Local runtimeLocalVariable  
```

### 临时的变量
临时变量不具备持久化特性，意味着它们不会被存储在数据库中。
```java
void setTransientVariable(String variableName, Object variableValue);  
void setTransientVariableLocal(String variableName, Object variableValue);  
void setTransientVariables(Map<String, Object> transientVariables);  
void setTransientVariablesLocal(Map<String, Object> transientVariables);  
Object getTransientVariable(String variableName);  
Object getTransientVariableLocal(String variableName);  
Map<String, Object> getTransientVariables();  
Map<String, Object> getTransientVariablesLocal();  
```
在实际应用中，设置临时变量的API调用类似于设置其他类型变量的API，只是此类变量的生命期较短，仅在流程执行的某一特定时段内有效。获取临时变量时，同样可以通过getVariable方法进行读取，事实上，该方法在检索变量时会优先从临时变量中查找，若未能找到临时变量才会查询数据库中的执行变量或本地变量。因此，如果临时变量、执行变量或本地变量存在同名的情况，实际返回的将是临时变量的值。
