package com.tigxu.ui;

import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/***
 * UI主界面
 */
public class MainInterface extends JFrame{
    public MainInterface(){
        //初始化界面
        setTitle("Base UI");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Test
    public void test() throws InterruptedException{
        JFrame  jFrame  = new JFrame("Base UI");
        JLabel  label   = new JLabel("Label");
        jFrame.add(label);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300,200);
        jFrame.setVisible(true);
        TimeUnit.SECONDS.sleep(2);
        //label.setText("abcd");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                label.setText("1=9^0");
            }
        });
        //10s后测试线程退出关闭窗口
        Thread.sleep(10000);
    }
}
