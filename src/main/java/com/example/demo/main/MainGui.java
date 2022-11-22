package com.example.demo.main;

import com.example.demo.DouYinParse;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGui extends JFrame {
    private static final long serialVersionUID = -8161981948004677531L;
    int DEFAULT_WIDTH = 300;
    int DEFAULT_HEIGHT = 200;
    private JLabel label;
    private JComboBox<String> faceCombo;
    private static TextField tf;
    private static JButton filePathButton;
    private static JLabel endShowDate;
    private static JLabel startShowDate;
    private static Button button;
    private static String type;
    private static String url;
    private static String startTime ;
    private static String endTime;
    private static String filePath;

    public MainGui() {
        this.setTitle("抖音视频下载");
       //this.setSize(this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT);
        //this.setLayout(new GridLayout(6, 2,3,3));
        this.setBounds(0,0, 260, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
       /* faceCombo = new JComboBox<>();
        faceCombo.setEditable(false);
        faceCombo.setEnabled(true);
        // faceCombo.addItem("用户昵称");
        // faceCombo.addItem("用户名");
        faceCombo.addItem("用户ID");
        //this.add(label);
        this.add(faceCombo);*/
        //label.setHorizontalAlignment(SwingConstants.RIGHT);
        // 第二行内容
      /*  JLabel label2 = new JLabel("url");
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        tf = new TextField(20);
        this.add(label2);
        this.add(tf);*/
       /*JButton button1 = new JButton("按钮");
        //设置按钮尺寸
        Dimension preferredSize = new Dimension(400,100);
        button1.setPreferredSize(preferredSize);
        this.add(button1);*/

        button = new Button("Start");
        Font f=new Font("华文行楷",Font.BOLD,20);//根据指定字体名称、样式和磅值大小，创建一个新 Font。
        button.setFont(f);
        button.setPreferredSize(new Dimension(40,10));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //type = (String)faceCombo.getSelectedItem();
                //系统剪切板通过
                Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();

                url=  ImageViewer.getClipboardText(sysc);

                //url = tf.getText();
                System.out.println("url is " + url);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            DouYinParse.down(url);

                        } catch (Exception err) {

                            err.printStackTrace();
                        } finally {
                            button.setEnabled(true);
                        }
                    }
                });
                th.start();
            }
        });

        //this.add(filePathButton);
        this.add(button);

    }
    public static void main(String[] args){
        MainGui mainGui = new MainGui();
        mainGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGui.setLocationRelativeTo(null);
        mainGui.setVisible(true);
    }
}
