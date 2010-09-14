/**﻿使用Java程序来实现HTTP文件的队列下载  
 
发布时间：2007.07.27 06:30     来源：赛迪网    作者：dxaw 

序言 

许多用户可能会遇到这样的情况：在网站上发现一个很好的资源，但是这个资源是分成了很多个文件存放的，如果想把它保存到本地，只有靠用户点击另存来完成保存，如果资源分了几百甚至上千上万，那简直是个灾难。 

在Internet上很多的资源分成多个文件存放时，它的文件命名是有一定的规则的；正因如此，我们就可以用程序来完成这个资源的完全下载。 

1. 基础知识 

在Internet上，我们要下载网站上的某个资源，我们会获得一个URL（Uniform Resource Locator），它是一个服务器资源定位的描述，下载的过程总是如下步骤: 

步骤1:客户端发起连接请求一个URL 

步骤2:服务器解析URL，并将指定的资源返回一个输入流给客户 

步骤3:客户端接收输入流，将流中的内容存到文件 

2. 网络连接的建立 

Java提供了对URL访问和大量的流操作的的API，我们可以很容易的完成对网络上资源的存取,下面的代码段就完成了对一个网站的资源进行访问: 


......
destUrl="http://www.ebook.com/java/网络编程001.zip";
url = new URL(destUrl);
httpUrl = (HttpURLConnection) url.openConnection();
//连接指定的网络资源
httpUrl.connect();
//获取网络输入流
bis = new BufferedInputStream(httpUrl.getInputStream());
......
 

3. 代理的访问 

Java 中通过代理服务器访问外网的方法已经是世人皆知的秘密了。这里就不再多描述了，访问的JAVA代码如下: 


//设置代理服务器
System.getProperties().put("proxySet", "true");
System.getProperties().put("proxyHost", "10.154.134.110");
System.getProperties().put("proxyPort", "8080");
 

4. 网络资源的保存 

在上节中，我们已经获取了指定网络资源的输入流，接下来我们要完成的就是读取输入流中的所以内容，并将其保存在文件中。示例代码: 


......
fos = new FileOutputStream(fileName);
if (this.DEBUG) 
System.out.println
("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" + fileName +"]");

//保存文件
while ( (size = bis.read(buf)) != -1)
fos.write(buf, 0, size);
......
 

上面的示例代码就将网络资源的内容保存到了本地指定的文件中。 

5. 代码清单 
**/


import java.io.*;
import java.net.*;
import java.util.*;

/**
* ＜p＞Title: 个人开发的API＜/p＞
* ＜p＞Description: 将指定的HTTP网络资源在本地以文件形式存放＜/p＞
* ＜p＞Copyright: Copyright (c) 2004＜/p＞
* ＜p＞Company: NewSky＜/p＞
* @author MagicLiao
* @version 1.0
*/
public class HttpGet {

　 public final static boolean DEBUG = true;//调试用
　 private static int BUFFER_SIZE = 8096;//缓冲区大小
　 private Vector vDownLoad = new Vector();//URL列表
　 private Vector vFileList = new Vector();//下载后的保存文件名列表

　 /**
　 * 构造方法
　 */
　 public HttpGet() {}

　 /**
　 * 清除下载列表
　 */
　 public void resetList() {
　　 vDownLoad.clear();
　　 vFileList.clear();
　 }

　 /**
　 * 增加下载列表项
　 *
　 * @param url String
　 * @param filename String
　 */

public void addItem(String url, String filename) {
　 vDownLoad.add(url);
　 vFileList.add(filename);
}

　 /**
　 * 根据列表下载资源
　 */
public void downLoadByList() {
　 String url = null;
　 String filename = null;

　 //按列表顺序保存资源
　 for (int i = 0; i ＜ vDownLoad.size(); i++) {
　　 url = (String) vDownLoad.get(i);
　　 filename = (String) vFileList.get(i);

　　 try {
　　　 saveToFile(url, filename);
　　 }
　　 catch (IOException err) {
　　　 if (DEBUG) {
　　　　 System.out.println("资源[" + url + "]下载失败!!!");
　　　 }
　　 }
　 }

　 if (DEBUG) {
　　 System.out.println("下载完成!!!");
　 }
}

/**
* 将HTTP资源另存为文件
*
* @param destUrl String
* @param fileName String
* @throws Exception
*/
public void saveToFile(String destUrl, String fileName)throws IOException{
　 FileOutputStream fos = null;
　 BufferedInputStream bis = null;
　 HttpURLConnection httpUrl = null;
　 URL url = null;
　 byte[] buf = new byte[BUFFER_SIZE];
　 int size = 0;

　 //建立链接
　 url = new URL(destUrl);
　 httpUrl = (HttpURLConnection) url.openConnection();
　 //连接指定的资源
　 httpUrl.connect();
　 //获取网络输入流
　 bis = new BufferedInputStream(httpUrl.getInputStream());
　 //建立文件
　 fos = new FileOutputStream(fileName);

　 if (this.DEBUG) 
　System.out.println
("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" + fileName + "]");

　 //保存文件
　 while ( (size = bis.read(buf)) != -1) 
　　 fos.write(buf, 0, size);

　 fos.close();
　 bis.close();
　 httpUrl.disconnect();
}

/**
* 设置代理服务器
*
* @param proxy String
* @param proxyPort String
*/
public void setProxyServer(String proxy, String proxyPort) {
　 //设置代理服务器 
　 System.getProperties().put("proxySet", "true");
　 System.getProperties().put("proxyHost", proxy);
　 System.getProperties().put("proxyPort", proxyPort);
}

/**
* 设置认证用户名与密码
*
* @param uid String
* @param pwd String
*/
public void setAuthenticator(String uid, String pwd) {
Authenticator.setDefault(new MyAuthenticator(uid, pwd));
}

/**
* 主方法(用于测试)
*
* @param argv String[]
*/
public static void main(String argv[]) {
　 HttpGet oInstance = new HttpGet();
　 try {
　 //增加下载列表（此处用户可以写入自己代码来增加下载列表）
　 oInstance.addItem("http://www.ebook.com/java/网络编程001.zip","./网络编程1.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程002.zip","./网络编程2.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程003.zip","./网络编程3.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程004.zip","./网络编程4.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程005.zip","./网络编程5.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程006.zip","./网络编程6.zip");
　 oInstance.addItem("http://www.ebook.com/java/网络编程007.zip","./网络编程7.zip");
　 //开始下载
　 oInstance.downLoadByList();
　 }
　 catch (Exception err) {
　　 System.out.println(err.getMessage());
　 }
}
}
 
 
