/*[java]一个压缩工具类 zip2008年05月28日 星期三 17:48/*
* Zip.java
*
* Created on 2008年4月2日, 下午2:20
*
* To change this template, choose Tools | Template Manager
* and open the template in the editor.
*/

package com.founder.mnp.utl;

/**
*
* @author GuoJiale
*/
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipUtils {
    static final int BUFFER = 2048;
    
    public static boolean zip( String[] filename ,String outname){
        
        boolean test = true;
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(outname);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];
            // File f= new File("d:\\111\\");
            //   File files[] = f.listFiles();
            
            for (int i = 0; i < filename.length; i++) {
                File file = new File(filename[i]);
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(file.getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch (Exception e) {
            test = false;
            e.printStackTrace();
        }
        return test;
    }
    
    
    
    public static void main(String argv[]) {
        // File f= new File("d:\\111\\");
        String[] filenames = new String[]{"E:/mnpdata/ad/temp/1209014565259/郁香.jpg"};
        zip(filenames,"c:/myfui.zip");
    }
}
 
