需要一些jar，自己去down吧

/*
* created by intellij idea.
* user: administrator
* date: mar 26, 2002
* time: 1:24:56 pm
* to change template for new class use
* code style | class templates options (tools | ide options).
*/
/*****  readxml.java  **********************
*this is a javabean.
*this bean read a xml file from a url,and return a xmldom
*[工具类] 读取、打印输出、保存xml
***** created by xiao yusong  2001-11-30 ****
*/
package com.chinacountry.util;

import java.util.*;
import java.net.url;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import org.apache.xml.serialize.outputformat;
import org.apache.xml.serialize.serializer;
import org.apache.xml.serialize.serializerfactory;
import org.apache.xml.serialize.xmlserializer;
import org.xml.sax.inputsource;

public class xmlutil implements java.io.serializable {

    public xmlutil()
    {
    }
    public static documentbuilder getbuilder() throws parserconfigurationexception
    {
        documentbuilder builder=documentbuilderfactory.newinstance().newdocumentbuilder();
        return builder;
    }
    //get a document from given file
    public static document getdocument(string path) throws exception
    {
        //bufferedreader filein=new bufferedreader(new filereader(path));
        file f = new file(path);
        documentbuilder builder=getbuilder();
        document doc = builder.parse(f);
        return doc;
    }
    //get a document from inputstream
    public static document getdocument(inputstream in) throws exception
    {
        documentbuilder builder=getbuilder();
        document doc = builder.parse(in);
        return doc;
    }

    //create a empty document
    public static document getnewdoc() throws exception
    {
        documentbuilder builder=getbuilder();
        document doc = builder.newdocument();
        return doc;
    }
    //create a document from given string
    public static document getnewdoc(string xmlstr)
    {
        document doc = null;
        try
        {
            stringreader sr = new stringreader(xmlstr);
            inputsource isrc = new inputsource(sr);
            documentbuilder builder=getbuilder();
            doc = builder.parse(isrc);
        }
        catch (exception ex)
        {
            ex.printstacktrace();
        }
        return doc;
    }

    //save a document as a file at the given file path
    public static void save(document doc, string filepath)
    {
        try
        {
            outputformat format = new outputformat(doc);   //serialize dom
            format.setencoding("gb2312");
            stringwriter stringout = new stringwriter();        //writer will be a string
            xmlserializer serial = new xmlserializer(stringout, format);
            serial.asdomserializer();                     // as a dom serializer
            serial.serialize(doc.getdocumentelement());
            string strxml = stringout.tostring(); //spit out dom as a string
            string path = filepath;
            writexml(strxml, path);

        }
        catch (exception e)
        {
            e.printstacktrace();
        }
    }

    //save a string(xml) in the given file path
    public static void writexml(string strxml, string path)
    {
        try
        {
            file f = new file(path);
            printwriter out = new printwriter(new filewriter(f));
            out.print(strxml + "\n");
            out.close();
        }
        catch (ioexception e)
        {
            e.printstacktrace();
        }
    }
    //format a document to string
    public static string tostring(document doc)
    {
        string strxml = null;
        try
        {
            outputformat format = new outputformat(doc);   //serialize dom
            format.setencoding("gb2312");
            stringwriter stringout = new stringwriter();        //writer will be a string
            xmlserializer serial = new xmlserializer(stringout, format);
            serial.asdomserializer();                     // as a dom serializer
            serial.serialize(doc.getdocumentelement());
            strxml = stringout.tostring(); //spit out dom as a string
        }
        catch (exception e)
        {
            e.printstacktrace();
        }
        return strxml;
    }
    //format a node to string
    public static string tostring(node node, document doc)
    {
        string strxml = null;
        try
        {
            outputformat format = new outputformat(doc);   //serialize dom
            format.setencoding("gb2312");
            stringwriter stringout = new stringwriter();        //writer will be a string
            xmlserializer serial = new xmlserializer(stringout, format);
            serial.asdomserializer();                     // as a dom serializer
            serial.serialize((element) node);
            strxml = stringout.tostring(); //spit out dom as a string
        }
        catch (exception e)
        {
            e.printstacktrace();
        }
        return strxml;
    }

    public static void main(string[] args) throws exception
    {
        string pathroot = "netrees.xml";
        document doc,doc1;
        try
        {
            doc = xmlutil.getdocument(pathroot);
            doc1 = xmlutil.getdocument(pathroot);
            if(doc == doc1)
            {
                system.out.println("they are  same objects!");
            }
            else
            {
                system.out.println("they are different!");
                outputformat format = new outputformat(doc);   //serialize dom
                format.setencoding("gb2312");
                stringwriter stringout = new stringwriter();        //writer will be a string
                xmlserializer serial = new xmlserializer(stringout, format);
                serial.asdomserializer();                 // as a dom serializer
                serial.serialize(doc.getdocumentelement());
                string strxml = stringout.tostring(); //spit out dom as a string
                system.out.println(strxml);
            }
        }
        catch (exception ex)
        {
            system.out.print("reading file\"" + pathroot + "\" error!");
            ex.printstacktrace();
        }
    }
} 