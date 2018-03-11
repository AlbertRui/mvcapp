# Javaweb MVC设计模式

## 一、Java EE开发流程

<span style="font-size: 18px;">![](http://images2017.cnblogs.com/blog/1268854/201802/1268854-20180208200730498-1891313301.png)</span>

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

<span style="font-size: 18px;">![](http://images2017.cnblogs.com/blog/1268854/201802/1268854-20180208200812982-1174732478.png)</span>
