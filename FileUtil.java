/*此类包含利用JAVA进行文件的压缩，解压，删除，拷贝操作。部分代码总结了网上的代码，并修正了很多BUG,例如压缩中文问题，压缩文件中多余空文件问题。

    注意：此类中用到的压缩类ZipEntry等都来自于org.apache.tools包而非java.util。此包在ant.jar中有。

  /* 
     * Version information

     *

     * Date:2008-6-26

     *

     * Copyright (C) 2008 Chris.Tu
     */

 

    package test;


    import java.io.File;

    import java.io.FileInputStream;

    import java.io.FileOutputStream;

    import java.io.IOException;

    import java.io.InputStream;

    import java.util.Enumeration;

 

    import org.apache.tools.zip.ZipEntry;

    import org.apache.tools.zip.ZipFile;

    import org.apache.tools.zip.ZipOutputStream;

    import org.slf4j.Logger;

    import org.slf4j.LoggerFactory;

 

    /**

     * java文件操作工具类

     * @author Chris

     * @version 2008-6-26

     */

    public class FileUtil {

 

        protected static Logger log = LoggerFactory.getLogger(FileUtil.class);

 

        /**

         * 压缩文件

         * @param inputFileName 要压缩的文件或文件夹路径，例如：c:\\a.txt,c:\\a\

         * @param outputFileName 输出zip文件的路径，例如：c:\\a.zip

         */

        public static void zip(String inputFileName, String outputFileName) throws Exception {

 

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFileName));

            zip(out, new File(inputFileName), "");

            log.debug("压缩完成！");

            out.closeEntry();

            out.close();

        }

/**

         * 压缩文件

         * @param out org.apache.tools.zip.ZipOutputStream

         * @param file 待压缩的文件

         * @param base 压缩的根目录

         */

        private static void zip(ZipOutputStream out, File file, String base) throws Exception {

 

            if (file.isDirectory()) {

                File[] fl = file.listFiles();

                base = base.length() == 0 ? "" : base + File.separator;

                for (int i = 0; i < fl.length; i++) {

                    zip(out, fl[i], base + fl[i].getName());

                }

            } else {

                out.putNextEntry(new ZipEntry(base));

                log.debug("添加压缩文件：" + base);

                FileInputStream in = new FileInputStream(file);

                int b;

                while ((b = in.read()) != -1) {

                    out.write(b);

                }

                in.close();

            }

        }

 

        /**

         * 解压zip文件

         * @param zipFileName 待解压的zip文件路径，例如：c:\\a.zip

         * @param outputDirectory 解压目标文件夹,例如：c:\\a\

         */

        public static void unZip(String zipFileName, String outputDirectory) throws Exception {

 

            ZipFile zipFile = new ZipFile(zipFileName);

            try {

                Enumeration<?> e = zipFile.getEntries();

                ZipEntry zipEntry = null;

                createDirectory(outputDirectory, "");

                while (e.hasMoreElements()) {

                    zipEntry = (ZipEntry) e.nextElement();

                    log.debug("解压：" + zipEntry.getName());

                    if (zipEntry.isDirectory()) {

                        String name = zipEntry.getName();

                        name = name.substring(0, name.length() - 1);

                        File f = new File(outputDirectory + File.separator + name);

                        f.mkdir();

                        log.debug("创建目录：" + outputDirectory + File.separator + name);

                    } else {

                        String fileName = zipEntry.getName();

                        fileName = fileName.replace('\\', '/');

                        if (fileName.indexOf("/") != -1) {

                            createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));

                            fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());

                        }

 

                        File f = new File(outputDirectory + File.separator + zipEntry.getName());

 

                        f.createNewFile();

                        InputStream in = zipFile.getInputStream(zipEntry);

                        FileOutputStream out = new FileOutputStream(f);

 

                        byte[] by = new byte[1024];

                        int c;

                        while ((c = in.read(by)) != -1) {

                            out.write(by, 0, c);

                        }

                        in.close();

                        out.close();

                    }

                }

            } catch (Exception ex) {

                System.out.println(ex.getMessage());

            } finally {

                zipFile.close();

                log.debug("解压完成！");

            }

 

        }

 private static void createDirectory(String directory, String subDirectory) {

 

            String dir[];

            File fl = new File(directory);

            try {

                if (subDirectory == "" && fl.exists() != true) {

                    fl.mkdir();

                } else if (subDirectory != "") {

                    dir = subDirectory.replace('\\', '/').split("/");

                    for (int i = 0; i < dir.length; i++) {

                        File subFile = new File(directory + File.separator + dir[i]);

                        if (subFile.exists() == false)

                            subFile.mkdir();

                        directory += File.separator + dir[i];

                    }

                }

            } catch (Exception ex) {

                System.out.println(ex.getMessage());

            }

        }

 

        /**

         * 拷贝文件夹中的所有文件到另外一个文件夹

         * @param srcDirector 源文件夹

         * @param desDirector 目标文件夹

         */

        public static void copyFileWithDirector(String srcDirector, String desDirector) throws IOException {

 

            (new File(desDirector)).mkdirs();

            File[] file = (new File(srcDirector)).listFiles();

            for (int i = 0; i < file.length; i++) {

                if (file[i].isFile()) {

                    log.debug("拷贝：" + file[i].getAbsolutePath() + "-->" + desDirector + "/" + file[i].getName());

                    FileInputStream input = new FileInputStream(file[i]);

                    FileOutputStream output = new FileOutputStream(desDirector + "/" + file[i].getName());

                    byte[] b = new byte[1024 * 5];

                    int len;

                    while ((len = input.read(b)) != -1) {

                        output.write(b, 0, len);

                    }

                    output.flush();

                    output.close();

                    input.close();

                }

                if (file[i].isDirectory()) {

                    log.debug("拷贝：" + file[i].getAbsolutePath() + "-->" + desDirector + "/" + file[i].getName());

                    copyFileWithDirector(srcDirector + "/" + file[i].getName(), desDirector + "/" + file[i].getName());

                }

            }

        }

 

        /**

         * 删除文件夹

         * @param folderPath folderPath 文件夹完整绝对路径

         */

        public static void delFolder(String folderPath) throws Exception {

 

            //删除完里面所有内容

            delAllFile(folderPath);

            String filePath = folderPath;

            filePath = filePath.toString();

            File myFilePath = new File(filePath);

            //删除空文件夹

            myFilePath.delete();

        }

 

        /**

         * 删除指定文件夹下所有文件

         * @param path 文件夹完整绝对路径

         */

        public static boolean delAllFile(String path) throws Exception {

 

            boolean flag = false;

            File file = new File(path);

            if (!file.exists()) {

                return flag;

            }

  if (!file.isDirectory()) {

                return flag;

            }

            String[] tempList = file.list();

            File temp = null;

            for (int i = 0; i < tempList.length; i++) {

                if (path.endsWith(File.separator)) {

                    temp = new File(path + tempList[i]);

                } else {

                    temp = new File(path + File.separator + tempList[i]);

                }

                if (temp.isFile()) {

                    temp.delete();

                }

                if (temp.isDirectory()) {

                    //先删除文件夹里面的文件

                    delAllFile(path + "/" + tempList[i]);

                    //再删除空文件夹

                    delFolder(path + "/" + tempList[i]);

                    flag = true;

                }

            }

            return flag;

        }

 

    }


