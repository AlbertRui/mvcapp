# Javaweb MVC开发案例

## 一、Java EE开发流程
![image](/images/009.png)

## 二、MVC设计模式

### 什么是MVC？

<span style="font-size: 18px;">　　MVC是Model-View-Controller的简称，即模型-视图-控制器。</span>

<span style="font-size: 18px;">　　MVC是一种设计模式，它把应用程序分成三个核心模块：模型、视图、控制器，它们各自处理自己的任务。</span>

### 模型(model)

<span style="font-size: 18px;">　　模型是应用程序的主体部分，模型表示业务数据和业务逻辑。主要编写DAO（Data Access Object），访问数据库，以及处理各种业务逻辑。</span>

<span style="font-size: 18px;">　　一个模型能为多个视图提供数据。</span>

<span style="font-size: 18px;">　　由于应用于模型的代码只需写一次就可以被多个视图重用，所以提高了代码的可重用性。</span>

### 视图（view）

<span style="font-size: 18px;">　　视图是用户看到并与之交互的界面，Javaweb中一般为JSP页面等，视图向用户显示相关的数据。接受用户的输入。不进行任何实际的业务处理。</span>

### 控制器（controller）

<span style="font-size: 18px;">　　控制器接受用户的输入并调用模型和视图去完成用户的需求。</span>

<span style="font-size: 18px;">　　控制器接收请求并决定调用哪个模型组件去处理请求，然后决定调用哪个视图来显示模型处理返回的数据。</span>
### MVC处理过程
![iamge](/images/010.png)

## 三、MVC案例
### 案例简介
* 没有业务层，直接由 Servlet 调用 DAO，所以也没有事务操作。所以可以在 DAO 中直接获取 Connection 对象
* 采取 MVC 设计模式
* 使用到的技术：
    * MVC 设计模式：JSP、Servlet，POJO
    * 数据库使用 MySQL
    * 连接数据库需要使用 C3P0 数据库连接池
    * JDBC 工具采用 DBUtils
    * 页面上的提示操作使用 jQuery
* 技术难点：
    * 多个请求如何使用一个 Servlet ？
    * 如何模糊查询 ？
    * 如何在创建或修改的情况下，验证用户名是否已经被使用，并给出提示
![image](/images/001.PNG)
### MVC理论分析
* View：JSP
    * 呈现数据：从 reuqes 中获取 Servlet 放入的属性。
    * 接收用户的输入
    * 编写JavaScript代码给出对应的提示
* Controller：Servlet
    * 获取请求信息：获取请求参数
    * 验证请求参数的合法性：验证失败，需要返回页面，并给出提示信息
    * 把请求参数封装为一个 JavaBean
    * 调用 DAO 的方法获取返回的结果
    * 把返回的结果放入到 request 中
    * 响应页面：转发、重定向
* Model：DAO
    * 获取数据库连接
    * 执行 CRUD 操作
    * 返回结果
    * 其中MySQL用于存储数据
* 注意：
    * 不能跨层访问
    * 只能自上向下依赖，而不能自下向上依赖
### 案例实现过程
* 建数据表

![image](/images/002.png)

```sql
CREATE TABLE customers (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(30) NOT NULL UNIQUE,
  address VARCHAR(30),
  phone   VARCHAR(30)
);
```
* 为 name 字段添加唯一约束：
```sql
ALTER TABLE customers
  ADD CONSTRAINT name_uk UNIQUE (name);
```
* 加入 C3P0 数据源
    * C3p0配置文件：[c3p0-config.xml](/src/c3p0-config.xml)
    * 数据库驱动的 jar 包
* 编写 [DAO](/src/me/mvcapp/dao)、[JdbcUtils](/src/me/mvcapp/db/JdbcUtils.java)工具类 和 [CustomerDAO](/src/me/mvcapp/dao/CustomerDAO.java) 接口
* 提供 CustomerDAO 接口的实现类：[CustomerDAOJDBCImpl](/src/me/mvcapp/dao/impl/CustomerDAOJdbcImpl.java)
* 多个请求使用同一个servlet
![image](/images/003.PNG)
![image](/images/004.PNG)
![image](/images/005.PNG)
#### 查询操作：
* Servlet
    * 调用 CustomerDAO 的 getAll() 得到 Customer 的集合
    ```java
    List<Customer> customers = customerDAO.getAll();
    ```
    * 把 Customer 的集合放入 request 中
    ```java
    request.setAttribute("customers", customers);
    ```
    * 转发页面到 index.jsp(不能使用重定向)
    ```java
    request.getRequestDispatcher("/index.jsp").forward(request, response);
    ```
* JSP
    * 获取 request 中的 customers 属性
    * 遍历显示
#### 模糊查询
* 根据传入的 name, address, phone 进行模糊查询
* 例子: name: a、address: b、phone: 3 则 SQL 语句的样子为: SELECT id, name, address, phone FROM customers WHERE name LIKE ‘%a%’ AND address LIKE ‘%b%’ AND phone LIKE ‘%3%’
* 需要在 CustomerDAO 接口中定义一个 getForListWithCriteriaCustomer(CriteriaCustomer cc)。 其中 CriteriaCustomer 用于封装查询条件：name, address, phone。因为查询条件很多时候和 domain 类并不相同，所以要做成一个单独的类
* 拼 SQL：
    ```sql
    SELECT id, name, address, phone FROM customers WHERE name LIKE ? AND address LIKE ? ANDphone LIKE ?";
    ```
    * 为了正确的填充占位符时，重写了 CriteriaCustomer 的 getter：
* 修改 Servlet：获取请求参数；把请求参数封装为CriteriaCustomer 对象，再调用 getForListWithCriteriaCustomer(CriteriaCustomer cc) 方法
#### 删除操作
* 超链接：delete.do?id=<%=customer.getId()%>
* Servlet 的 delete 方法
    * 获取 id
    * 调用 DAO 执行删除
    * 重定向到 query.do（若目标页面不需要读取当前请求的 request 属性，就可以使用重定向），将显示删除后的 Customer 的 List
* JSP 上的 jQuery 提示：确定要删除 xx 的信息吗？
```javascript
<script type="text/javascript">
    $(function () {
        $(".delete").click(function () {
            var content = $(this).parent().parent().find("td:eq(1)").text();
            return confirm("确定要删除" + content + "的信息吗？");
        });
    });
</script>
```
#### 添加的流程
* Add New Customer 超链接连接到 newcustomer.jsp
* 新建 newcustomer.jsp：
![image](/images/006.png)
* 在 CustomerServlet 的 addCustomer 方法中：参见注释
![image](/images/007.PNG)
* 上图一共有 2 个请求
    * 加载页面的请求：发出请求到页面加载完成，request 结束
    * 点击提交按钮，到 Serlvet，发出了一个 request，Serlvet 转发到 newcustomer.jsp 直到页面加载完成，整个过程一个 request
```java
doPost(request, response) --- request.getRequestDispatcher(path).forward(request, response);
```
#### 修改：
* 先显示（SELECT 操作）修改的页面，再进行修改（update）
* 显示修改页面
    * Update 的超链接：`<a href="edit.do?id=<%= customer.getId() %>">UPDATE</a>`
    * edit 方法： 参考注释
    * JSP 页面：
        * 获取请求域中的 Customer 对象，调用对应的字段的 get 方法来显示值。
        * 使用隐藏域来保存要修改的 Customer 对象的 id：<input type="hidden" name="id" value=“<%= customer.getId() %>"/>
        * 使用隐藏域来保存 oldName：<input type="hidden" name=“oldName" value=“<%= customer.getName() %>"/>
        * 关于隐藏域：和其他的表单域一样可以被提交到服务器，只不过在额页面上不显示
        * 提交到 update.do 
* 修改操作
    * Update 方法：参见注释
    * Updatecustomer.jsp：
        * 隐藏域的问题
        * 回显的问题
    * Newcustomer.jsp 和 updateCustomer.jsp 能汇总到一个页面吗 ?
#### 关于面向接口编程
* 深入理解面向接口编程：在类中调用接口的方法，而不必关心具体的实现。这将有利于代码的解耦。使程序有更好的可移植性和可扩展性
![image](/images/008.PNG)
#### 其它
* 动态修改 Customer 的存储方式：通过修改类路径下的 switch.properties 文件的方式来实现
    * type=xml
    * type=jdbc
* CustomerServlet 中不能在通过 private CustomerDAO customerDAO = new CustomerDAOXMLImpl(); 的方式来写死实现类
* 需要通过一个类的一个方法来获取具体的实现类的对象
```jsp
<servlet>
    <servlet-name>InitServlet</servlet-name>
    <servlet-class>me.mvcapp.servlet.InitServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```
* 当前 WEB 应用才启动的时候，InitServlet 被创建，并由 Servlet 容器调用其 init() 方法：
    * 读取类路径下的 switch.properties 文件
    * 获取 switch.properties 的 type 属性值
    * 赋给了 CustomerDAOFactory 的 type 属性值
```java
private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();
```
* 创建 CustomerServlet 时，为 customerDAO 属性赋值是通过 CustomerDAOFactory  的 getCustomerDAO() 方法完成的 。此时的 type 已经在 InitServlet 中被赋值了。
```java
private CustomerDAOFactory() {
    daos.put("jdbc", new CustomerDAOJdbcImpl());
    daos.put("xml", new CustomerDAOXMLImpl());
}

public CustomerDAO getCustomerDAO() {
    return daos.get(type);
}
```