package com.tigxu.tool;

import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/***
 * 根据文件夹a删除文件夹b中包含文件a的文件及空目录
 */
public class SynDel {
    public static final int MOD_AUTO       = 0x0000;   //默认
    public static final int MOD_DELNAME    = 0x0001;   //根据文件名删除所有文件
    public static final int MOD_DELHASH    = 0x0002;   //更具文件内容删除，即保留已被修改的文件
    public static final int MOD_DELEMPTY   = 0x0004;   //不删除空目录

    private Map<String,String>  mapFile;
    private Map<String,String>  mapDir;
    private String              inPath;
    private boolean             thread;

    public SynDel(){this(false);}
    public SynDel(boolean thread){
        this.thread = thread;
    }

    private static Map<String ,String>  map;
    public void syndel(String inPath,String delPath,int mod){
        File    inFile  = new File(inPath);
        File    outFile = new File(delPath);
        this.mapFile    = new HashMap<>();
        this.mapDir     = new HashMap<>();
        this.inPath     = inPath;
        if(inFile.exists()&&outFile.exists()&&inFile.isDirectory()&&outFile.isDirectory()){
            getDir(inFile);
        }
        System.out.println(mapFile.size());
        System.out.println(mapFile);
        System.out.println(mapDir.size());
        System.out.println(mapDir);
    }
    private void getDir(File file){
        if(file.isFile()){
            this.mapFile.put(Hash.md5(file), file.getAbsolutePath().substring(inPath.length()));
        }
        else if(file.listFiles().length==0){
            this.mapDir.put(Hash.md5(file.getPath().substring(inPath.length())), file.getPath().substring(inPath.length()));
        }
        else{
            for(File f:file.listFiles()){
                getDir(f);
            }
        }
    }

}
