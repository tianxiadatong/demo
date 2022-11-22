package com.example.demo.main;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.util.ObjectUtils;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author ：lixinyu
 * @version ：M1.0
 * @program ：vevor-research
 * @date ：Created in 2020/9/11 9:59
 * @description ：java图片处理类，包括java图片处理,java图片水印,java图片切割,java图片缩放,java图片格式转换,java图片等比例缩小
 * Image是一个抽象类，BufferedImage是其实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
 * （BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便地操作这个图片），
 * 提供获得绘图对象、图像缩放、选择图像平滑度等功能，通常用来做图片大小变换、图片变灰、设置透明不透明等。
 */

public class compressImg {


    public static void main(String[] args) {
        try {
           // File file = new File("C:\\Users\\39805\\Desktop\\公众号\\金阳儿");
            Thumbnails.of(new File("C:\\Users\\39805\\Desktop\\公众号\\20221105").listFiles()).scale(1D).outputQuality(0.5).outputFormat("jpg")
                    .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
           /* File[] files = file.listFiles();
            for (File file1 : files) {
                Thumbnails.of(file1)
                        //.sourceRegion(0, 120, 590, 1090)
                        //.scale(1.0)
                        .outputQuality(0.5)
                        .outputFormat("jpg")
                        .toFile(new File("C:\\Users\\39805\\Desktop\\公众号\\1\\" + file1.getName()));
            }*/
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
