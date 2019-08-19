package com.tigxu.tool;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class TheadHash<T> extends Hash{
    private List<T>                 obj;
    private Map<Object,String>     objHash;
    private int     count;
    private int     thNumber;

    public Map<Object,String> start(List<T> obj){
        int         cpu         = Runtime.getRuntime().availableProcessors();
        this.thNumber   = cpu;
        this.objHash    = new Hashtable<>();
        this.obj        = obj;
        this.count      = 0;

        Runnable    runnable    = new Runnable() {
            @Override
            public void run() {
                T       data    = null;
                String  hash    = null;
                Object  ID      = null;
                while((data=getElement())!=null){
                    hash    = runHash(data);
                    ID      = getObjID(data);
                    if(hash==null)
                        continue;
                    objHash.put(ID,hash);
                }
                setThNumber();
            }
        };
        Thread[]    ths = new Thread[cpu];
        for(Thread t:ths){
            t   = new Thread(runnable);
            t.start();
        }
        while(thNumber>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.objHash;
    }
    public abstract String runHash(T obj);
    public abstract Object getObjID(T obj);
    private synchronized T getElement(){
        if(count<obj.size()){
            return obj.get(count++);
        }
        return null;
    }
    private synchronized void setThNumber(){
        thNumber--;
    }
}
