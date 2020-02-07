package com.tigxu.tool;

import java.util.HashMap;
import java.util.Map;

/***
 * 概率工具
 */
public class Probability {
    public Probability(Map<String,Double> args){
        for(Object o:args.keySet()){
            System.out.println(o);
        }
    }

    /**
     * 传入一个对象，更具概率生成一个值对比其是否出现
     * @param obj
     * @return
     */
    public boolean isIn(Object obj){
        return false;
    }
}
