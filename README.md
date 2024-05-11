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

#### activity-admin

Activity-admin应用程序扮演着流程运行时管控中心的角色，它允许用户实时洞察并管理所有正在执行的流程实例及其关联的任务，并且还支持流程定义文件的上传操作。默认的登录凭证是admin/admin。

#### activity-rest

Activity-rest组件提供了基于restful API的方式来与Activiti流程引擎交互的能力，但其API的调用必须遵循HTTP基础认证机制以确保安全访问。默认的登录凭证是kermit/kermit。

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
ACT_EVT_LOG  

ACT_GE_BYTEARRAY  
ACT_GE_PROPERTY
  
ACT_HI_ACTINST  
ACT_HI_ATTACHMENT  
ACT_HI_COMMENT  
ACT_HI_DETAIL  
ACT_HI_IDENTITYLINK  
ACT_HI_PROCINST  
ACT_HI_TASKINST  
ACT_HI_VARINST  

ACT_ID_GROUP  
ACT_ID_INFO  
ACT_ID_MEMBERSHIP  
ACT_ID_USER  

ACT_PROCDEF_INFO
  
ACT_RE_DEPLOYMENT  
ACT_RE_MODEL  
ACT_RE_PROCDEF  

ACT_RU_DEADLETTER_JOB  
ACT_RU_EVENT_SUBSCR  
ACT_RU_EXECUTION  
ACT_RU_IDENTITYLINK  
ACT_RU_JOB  
ACT_RU_SUSPENDED_JOB  
ACT_RU_TASK  
ACT_RU_TIMER_JOB  
ACT_RU_VARIABLE
```
在Activiti中，各个数据库表名皆以前缀"ACT_"开始，并紧随两个字母以标识它们所属的不同模块范畴：
* 表前缀ACT_RE_指向Repository（资源库）模块，主要用于存放流程相关的静态资源，如流程模型定义、流程图元信息等持久化数据。
* ACT_RU_开头的表代表Runtime（运行时）模块，这部分数据库记录了在流程运行期间生成的各种动态数据，包括流程实例详情、用户任务状态、定时任务安排等临时信息。值得注意的是，一旦流程结束，这些运行时数据会被适时清理以维持数据库的精简，从而确保系统性能的高效。
使用ACT_ID_作为前缀的表属于Identity（身份识别）模块，负责存储组织机构内的用户、角色、组等认证和权限相关的信息。
以ACT_HI_打头的表服务于History（历史记录）模块，保留了流程实例的历史数据，例如已完成流程实例的状态记录、流程变量的历史值等，为后期审计和追溯提供依据。
最后，以ACT_GE_起始的表集合代表General（通用）模块，用于承载那些跨越多个模块、在整个Activiti框架中通用的数据实体。
