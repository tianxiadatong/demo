package com.example.demo.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.example.demo.MainVideoPlayler;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.ImageIcon;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    //创建播放器界面组件
    EmbeddedMediaPlayerComponent playerComponent =
            new EmbeddedMediaPlayerComponent();
    private final JPanel panel = new JPanel();
    private JProgressBar progress;

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 712, 512);
        //菜单条
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //菜单中的文件
        JMenu menu = new JMenu("文件");
        menuBar.add(menu);
        //一级菜单中的打开文件选项
        JMenuItem menuItem = new JMenuItem("打开文件");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            //点击后会文件选择器
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.openVideo();
            }
        });
        //一级菜单中的打开字符选项
        JMenuItem menuItem_1 = new JMenuItem("打开字幕");
        menu.add(menuItem_1);
        menuItem_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.openSubtitle();
            }
        });
        //退出
        JMenuItem menuItem_2 = new JMenuItem("退出");
        menu.add(menuItem_2);
        menuItem_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.exit();
            }
        });
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel videopanel = new JPanel();
        //因为playerComponent布局为边界布局，所以Jpanl布局也必须边界布局，不然只能听到声音，看不到画面
        contentPane.add(videopanel, BorderLayout.CENTER);
        videopanel.setLayout(new BorderLayout(0, 0));
        //将播放器界面添加到videopanel中，用来播放视频，并设置布局为居中
        playerComponent = new EmbeddedMediaPlayerComponent();
        videopanel.add(playerComponent, BorderLayout.CENTER);
        videopanel.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel controlPanel = new JPanel();
        panel.add(controlPanel);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton button_1 = new JButton("\u9000\u51FA");
        controlPanel.add(button_1);

        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(MainWindow.class.getResource(
                "/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
        controlPanel.add(btnNewButton);

        JButton button = new JButton("");
        button.setIcon(new ImageIcon(MainWindow.class.getResource(
                "/com/sun/javafx/webkit/prism/resources/mediaPause.png")));
        controlPanel.add(button);

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(MainWindow.class.getResource(
                "/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
        controlPanel.add(label);

        JSlider slider = new JSlider();
        slider.setValue(100);//设置默认音量100
        slider.setMaximum(120);//设置最大音量120
        controlPanel.add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                //将音量条的值传递给播放器的音量控件
                MainVideoPlayler.volume(slider.getValue());
            }
        });
        //视频播放进度条
        progress = new JProgressBar();
        progress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获得鼠标点击进度条时的横坐标x值
                int x = e.getX();
                //x除以进度条总长度为当前百分比
                MainVideoPlayler.jumpTo(((float)x/progress.getWidth()));
            }
        });
        progress.setStringPainted(true);
        panel.add(progress, BorderLayout.NORTH);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainVideoPlayler.pause();
            }
        });
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                MainVideoPlayler.play();
            }
        });
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainVideoPlayler.stop();
            }
        });
    }
    //定义一个方法，返回视频播放器
    public EmbeddedMediaPlayer getMediaPlayer() {
        return playerComponent.getMediaPlayer();
    }
    //定义一个方法，获得经度条的进度
    public JProgressBar getProgressBar() {
        return progress;
    }
}

