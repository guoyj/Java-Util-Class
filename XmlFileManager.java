XML工具类
package com.company.cpc.offlinelog.dao;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
//需要引用castor.jar文件
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;
import com.zte.ecc.util.tracer.Debug;
/**
 *  类 名 称:XmlFileManager
 *  内容摘要：该类是XML工具类
 */
public class XmlFileManager {
 /**
  * 方法名称：objectListToXMLString
  * 内容摘要：把对象编组成XML
  * @param mappingXMLString 映射的XML串
  * @param containerClass   编组类 
  * @return String 返回XML
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static String objectListToXMLString(
  String mappingXMLString,
  Object containerClass)
  throws IOException, MappingException, MarshalException, ValidationException {
  if (containerClass == null) {
   Debug.println("containerClass  is NULL!!!!!");
   return "";
  }
  //准备Mapping
  Mapping mapping = new Mapping();
  Reader reader = new StringReader(mappingXMLString);
  InputSource is = new InputSource(reader);
  mapping.loadMapping(is);
  //准备Writer
  StringWriter writer = new StringWriter();
  Marshaller marshaller = new Marshaller(writer);
  //开始编组
  marshaller.setMapping(mapping);
  marshaller.setEncoding("gb2312");
  marshaller.marshal(containerClass);
  StringBuffer bf = writer.getBuffer();
  writer.close();
  return bf.toString();
 }
 /**
  * 
  * 方法名称：XmlToObjectList
  * 内容摘要：把XML解组成对象
  * @param mappingXMLString 映射的XML串
  * @param xmlString 描述数据的XML串
  * @return Object
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static Object XmlToObjectList(
  String mappingXMLString,
  String xmlString)
  throws IOException, MappingException, MarshalException, ValidationException {
  //准备Mapping
  StringReader mapingReader = new StringReader(mappingXMLString);
  InputSource is = new InputSource(mapingReader);
  Mapping mapping = new Mapping();
  mapping.loadMapping(is);
  //准备Reader
  Reader reader = new StringReader(xmlString);
  //开始解组 
  Unmarshaller unmarshaller = new Unmarshaller(mapping);
  Object containerClass = unmarshaller.unmarshal(reader);
  reader.close();
  return containerClass;
 }
 /**
  * 
  * 方法名称：saveToXMLFile
  * 内容摘要：把对象编组成XML文件
  * @param xmlFileName 文件名
  * @param mappingFileName  映射文件名
  * @param containerClass  
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static void saveToXMLFile(
  String xmlFileName,
  String mappingFileName,
  Object containerClass)
  throws IOException, MappingException, MarshalException, ValidationException {
  if (containerClass == null) {
   Systen.out.println("containerClass  is NULL!!!!!");
   return;
  }
  //准备Mapping 
  Mapping mapping = new Mapping();
  mapping.loadMapping(mappingFileName);
  //准备Writer
  File file = new File(xmlFileName);
  Writer writer = new FileWriter(file);
  Marshaller marshaller = new Marshaller(writer);
  //开始编组
  marshaller.setMapping(mapping);
  marshaller.setEncoding("gb2312");
  marshaller.marshal(containerClass);
  writer.close();
 }
 /**
  * 
  * 方法名称：loadFromXMLFile
  * 内容摘要：把XML文件解组成对象
  * @param xmlFileName
  * @param mappingFileName
  * @return
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static Object loadFromXMLFile(
  String xmlFileName,
  String mappingFileName)
  throws IOException, MappingException, MarshalException, ValidationException {
  //准备Mapping
  Mapping mapping = new Mapping();
  mapping.loadMapping(mappingFileName);
  //准备Reader
  Reader reader = new FileReader(xmlFileName);
  //开始解组 
  Unmarshaller unmarshaller = new Unmarshaller(mapping);
  Object containerClass = unmarshaller.unmarshal(reader);
  reader.close();
  return containerClass;
 }
 /**
  * 
  * 方法名称：readerToString
  * 内容摘要：把Reader流中的数据变为字符串
  * @param reader
  * @param bfferSize
  * @return
  */
 public static String readerToString(Reader reader, int bfferSize) {
  StringBuffer sb = new StringBuffer();
  char[] b = new char[bfferSize];
  int n = 0;
  try {
   while ((n = reader.read(b)) > 0) {
    System.out.println("read:" + n);
    sb.append(b, 0, n);
   }
  } catch (IOException e) {
   // TODO 自动生成 catch 块
   e.printStackTrace();
  }
  return sb.toString();
 }
 /**
  * 方法名称：objectListToXMLString
  * 内容摘要：把对象编组成XML
  * @param mappingFileName 映射文件名
  * @param containerClass   编组类 
  * @return String 返回XML
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static String objectListToXMLStr(
  String mappingFileName,
  Object containerClass)
  throws IOException, MappingException, MarshalException, ValidationException {
  if (containerClass == null) {
   Debug.println("containerClass  is NULL!!!!!");
   return "";
  }
  
  //准备Mapping
  Mapping mapping = new Mapping();
  mapping.loadMapping(mappingFileName);
  //准备Writer
  StringWriter writer = new StringWriter();
  Marshaller marshaller = new Marshaller(writer);
  
  //开始编组
  marshaller.setMapping(mapping);
  marshaller.setEncoding("gb2312");
  marshaller.marshal(containerClass);
  StringBuffer bf = writer.getBuffer();
  writer.close();
  
  return bf.toString();
 }
 /**
  * 
  * 方法名称：XmlToObjectList
  * 内容摘要：把XML解组成对象
  * @param mappingFileName 映射文件名
  * @param xmlString 描述数据的XML串
  * @return
  * @throws IOException
  * @throws MappingException
  * @throws MarshalException
  * @throws ValidationException
  */
 public static Object XmlStrToObjectList(
  String mappingFileName,
  String xmlString)
  throws IOException, MappingException, MarshalException, ValidationException {
  //准备Mapping
  Mapping mapping = new Mapping();
  mapping.loadMapping(mappingFileName);
  //准备Reader
  Reader reader = new StringReader(xmlString);
  //开始解组 
  Unmarshaller unmarshaller = new Unmarshaller(mapping);
  Object containerClass = unmarshaller.unmarshal(reader);
  reader.close();
  return containerClass;
 }
 /**
  * 方法名称：XmlToObjectList
  * 内容摘要：得到资源文件的绝对路径文件名
  * @param sResourceName 资源名称
  * @return String
  */
 public static String getResourceFilePath(String sResourceName) {
  if (!sResourceName.startsWith("/")) {
   sResourceName = "/" + sResourceName;
  }
  java.net.URL classUrl = XmlFileManager.class.getResource(sResourceName);
  if (classUrl == null) {
   System.out.println(
    "\nResource '"
     + sResourceName
     + "' not found in \n'"
     + System.getProperty("java.class.path")
     + "'");
   return null;
  } else {
   System.out.println(
    "\nResource '"
     + sResourceName
     + "' found in \n'"
     + classUrl.getFile()
     + "'");
   return classUrl.getFile();
  }
 }
}

