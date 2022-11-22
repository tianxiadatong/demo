package com.example.demo.main;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.awt.Frame;

import java.awt.Graphics;

import java.awt.Image;

import java.awt.MediaTracker;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

public class ImageViewer extends Frame{
    private Image image;

    /**

     * 显示一个图像

     * @param viewMe

     */

    public ImageViewer(Image viewMe) {

        image = viewMe;

        MediaTracker mediaTracker = new MediaTracker(this);

        mediaTracker.addImage(image, 0);

        try {

            mediaTracker.waitForID(0);

        } catch (InterruptedException ie) {

            ie.printStackTrace();

            System.exit(1);

        }

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }

        });

//窗口适应图像大小

        setSize(image.getWidth(null), image.getHeight(null));

//窗口标题

        setTitle("Viewing Image from Clipboard");

        setVisible(true);

    }

    public void paint(Graphics graphics) {

        graphics.drawImage(image, 0, 0, null);

    }

    /**

     * 用于读取图像文件并生成Image对象

     */

    public static Image getImageFromFile(String fileName) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image image = toolkit.getImage(fileName);

        return image;
    }

  //  1. 从指定的剪切板中获取文本内容

    protected static String getClipboardText(Clipboard clip){

// 获取剪切板中的内容

        Transferable clipT = clip.getContents(null);

        if (clipT != null) {

// 检查内容是否是文本类型

            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    return (String)clipT.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;

    }

//2. 往剪切板写文本数据

    protected static void setClipboardText(Clipboard clip, String writeMe) {

        Transferable tText = new StringSelection(writeMe);

        clip.setContents(tText, null);

    }

//3. 从剪切板读取图像

    public static Image getImageFromClipboard() throws Exception{

        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable cc = sysc.getContents(null);

        if (cc == null)

            return null;

        else if(cc.isDataFlavorSupported(DataFlavor.imageFlavor))

            return (Image)cc.getTransferData(DataFlavor.imageFlavor);

        return null;

    }

//4. 写图像到剪切板

    protected static void setClipboardImage2(final Image image) {

        Transferable trans = new Transferable(){

            public DataFlavor[] getTransferDataFlavors() {

                return new DataFlavor[] { DataFlavor.imageFlavor };

            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {

                return DataFlavor.imageFlavor.equals(flavor);

            }

            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

                if(isDataFlavorSupported(flavor))

                    return image;

                throw new UnsupportedFlavorException(flavor);

            }

        };

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);

    }

    public static void main(String[] args) {
       // "4.33 xFH:/ “这世上从来不缺让人心动的新鲜感，长久的陪伴属实难得”# 你动心了吗 # 你好2022  https://v.douyin.com/r5ctruT/ 复制此链接，打开Dou音搜索，直接观看视频！"
        //本地剪切板使用 来构造；
        Clipboard cp = new Clipboard("clip1");
        //系统剪切板通过
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
          String url1=  getClipboardText(cp);
          String url2=  getClipboardText(sysc);
            System.out.println("本地剪切:"+url1);
            System.out.println("系统剪切:"+url2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
