package com.example.demo.main;

import javax.swing.*;
import java.awt.*;

public class JFrameUtil {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("按钮");
        //设置按钮尺寸
        Dimension preferredSize = new Dimension(400,100);
        button.setPreferredSize(preferredSize);
        frame.add(button);
        frame.setBounds(0,0, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
