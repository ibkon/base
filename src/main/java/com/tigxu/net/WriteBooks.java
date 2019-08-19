package com.tigxu.net;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/***
 * 写入小说
 */
public class WriteBooks {
    //缓冲区
    private byte[]          buffer;

    //以写入缓冲
    private int             bufferCount;

    //缓冲大小
    private int             bufferSize;

    //当前写入页数
    private int             writePage;

    //文件输出
    private OutputStream    out;

    /**
     *
     * @param outName   输出文件名，包括路径
     * @param page      小说页面总数，用以排序
     */
    public WriteBooks(String outName,int page){
        this.bufferSize = 0x4000;
        this.buffer     = new byte[this.bufferSize];
        this.bufferCount= 0;
        this.writePage = 0;
        try {
            this.out        = new FileOutputStream(new File(outName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写入缓存
     * @param page  章节数
     * @param date  数据
     */
    public synchronized void write(int page,byte[]date){
    }

    /**
     * 刷入缓冲区
     */
    public void flush(){

    }
    /**
     * 设置缓冲区大小，默认0x10000
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize) {
        if(bufferSize>this.bufferSize){
            //仅当重设缓冲区大小大于默认值才重新分配缓冲区
            this.bufferSize     = bufferSize;
            byte[] newBuffer    = new byte[bufferSize];
            if(this.bufferCount == 0){
                //缓冲区无数据直接替换
                this.buffer     = newBuffer;
            }
            else {
                //拷贝数据并替换
                System.arraycopy(newBuffer,0,this.buffer,0,this.bufferCount);
                this.buffer     = newBuffer;
            }
        }
    }
}
