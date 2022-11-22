package com.example.demo;

import java.awt.EventQueue;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.example.demo.main.MainWindow;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


public class MainVideoPlayler {
    //声明全局变量MainWindow
    static MainWindow frame;
    public static void main(String[] args) {
        //实例化NativeDiscovery类
        //new NativeDiscovery().discover();
        //通过判断选择系统，Windows，Mac OS，Liunx。以下都是各个系统的VLC默认安装路径
        if (RuntimeUtil.isWindows()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files (x86)\\VideoLAN\\VLC");
        }else if (RuntimeUtil.isMac()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "/Applications/VLC.app/Contents/MacOS/lib");
        }else if (RuntimeUtil.isNix()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "/home/linux/vlc/install/lib");
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new MainWindow();
                    frame.setVisible(true);
                    //通过--subsdec-encoding= 可以指定字幕文件编码格式
                    String options[] = {"--subsdec-encoding=GB18030"};
                    //让窗体获得视频资源
                    frame.getMediaPlayer().prepareMedia(
                            "D:\\我的文件\\06、Java语言\\7、界面设计\\10、Java视频播放器的制作\\1、工程的准备.mp4",options);
                    //prepareMedia（）；是准备播放视频。而PlayMedia（）；是直接播放视频
                    //frame.getMediaPlayer().playMedia(
                    // "D:\\我的文件\\06、Java语言\\7、界面设计\\10、Java视频播放器的制作\\1、工程的准备.mp4",options);
                    new SwingWorker<String, Integer>() {
                        //调节视频音量
                        protected String doInBackground() throws Exception {
                            while (true) {
                                //获得当前视频总时间长度
                                long total = frame.getMediaPlayer().getLength();
                                //获得当期播放时间
                                long curr = frame.getMediaPlayer().getTime();
                                //获取播放视频的百分比
                                float percent = ((float)curr/total);
                                publish((int)(percent*100));
                                Thread.sleep(100);
                            }
                        }
                        protected void process(java.util.List<Integer> chunks) {
                            for (int v:chunks) {
                                frame.getProgressBar().setValue(v);
                            }
                        };
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //开始播放
    public static void play() {
        frame.getMediaPlayer().play();
    }
    //暂停播放
    public static void pause() {
        frame.getMediaPlayer().pause();
    }
    //停止播放
    public static void stop() {
        frame.getMediaPlayer().stop();
    }
    //通过进度条调整播放时间
    public static void jumpTo(float to) {
        //传入进度条的值的百分比，乘以视频总长度就是当前视频需要播放的值
        frame.getMediaPlayer().setTime((long)( to*frame.getMediaPlayer().getLength()));
    }
    //实现菜单打开视频文件
    public static void openVideo() {
        JFileChooser chooser = new JFileChooser();
        int v = chooser.showOpenDialog(null);
        if (v == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            frame.getMediaPlayer().playMedia(file.getAbsolutePath());
        }
    }
    //实现菜单打开字幕文件
    public static void openSubtitle() {
        JFileChooser chooser = new JFileChooser();
        int v = chooser.showOpenDialog(null);
        if (v == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            frame.getMediaPlayer().playMedia(file.getAbsolutePath());
        }
        File file = chooser.getSelectedFile();
        frame.getMediaPlayer().setSubTitleFile(file);
    }
    //实现软件退出
    public static void exit() {
        frame.getMediaPlayer().release();
        System.exit(0);
    }
    //调节音量
    public static void volume(int v) {
        frame.getMediaPlayer().setVolume(v);
    }
}