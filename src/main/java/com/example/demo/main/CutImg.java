package com.example.demo.main;

        import net.coobird.thumbnailator.Thumbnails;
        import net.coobird.thumbnailator.geometry.Positions;
        import org.springframework.stereotype.Service;
        import org.springframework.util.ObjectUtils;

        import javax.imageio.IIOException;
        import javax.imageio.ImageIO;
        import java.awt.AlphaComposite;
        import java.awt.Color;
        import java.awt.Font;
        import java.awt.Graphics;
        import java.awt.Graphics2D;
        import java.awt.Image;
        import java.awt.Toolkit;
        import java.awt.geom.AffineTransform;
        import java.awt.image.AffineTransformOp;
        import java.awt.image.BufferedImage;
        import java.awt.image.CropImageFilter;
        import java.awt.image.FilteredImageSource;
        import java.awt.image.ImageFilter;
        import java.io.File;
        import java.io.IOException;

/**
 * @author ：lixinyu
 * @version ：M1.0
 * @program ：vevor-research
 * @date ：Created in 2020/9/11 9:59
 * @description ：java图片处理类，包括java图片处理,java图片水印,java图片切割,java图片缩放,java图片格式转换,java图片等比例缩小
 *                Image是一个抽象类，BufferedImage是其实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
 *               （BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便地操作这个图片），
 *                提供获得绘图对象、图像缩放、选择图像平滑度等功能，通常用来做图片大小变换、图片变灰、设置透明不透明等。
 */

public class CutImg {

    /**
     * 缩放图像（按指定比例缩放）
     * @param localImagePath 源图像文件地址
     * @param scaleImagePath 缩放后的图像地址
     * @param scaling        缩放比例
     * @param flag           缩放选择:true 放大; false 缩小;
     * @throws IOException   IOException
     */
    public void scaling(String localImagePath, String scaleImagePath, int scaling, boolean flag) throws IOException {
        // 读入文件
        BufferedImage localImage = ImageIO.read(new File(localImagePath));
        if (ObjectUtils.isEmpty(localImage)){
            throw new IllegalArgumentException("未找到图片");
        }
        // 得到源图宽
        int width = localImage.getWidth();
        // 得到源图长
        int height = localImage.getHeight();
        if (flag) {
            // 放大
            width = width * scaling;
            height = height * scaling;
        } else {
            // 缩小
            width = width / scaling;
            height = height / scaling;
        }
        // 创建图像的缩放版本
        Image image = localImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        // 构造一个预定义图像类型的BufferedImage
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形2d
        Graphics graphics = tag.getGraphics();
        // 绘制缩小后的图
        graphics.drawImage(image, 0, 0, null);
        // 释放图形资源
        graphics.dispose();
        // 输出到文件流
        boolean success = ImageIO.write(tag, "JPEG", new File(scaleImagePath));
        if (!success){
            throw new IIOException("缩放图片失败");
        }
    }

    /**
     * 缩放图像（按指定宽高缩放）
     * @param localImagePath 源图像文件地址
     * @param scaleImagePath 缩放后的图像地址
     * @param width          缩放后的宽度
     * @param height         缩放后的高度
     * @throws IOException   IOException
     */
    public void scale1WithCustom(String localImagePath, String scaleImagePath, int width, int height) throws IOException {
        // 读入文件
        BufferedImage localImage = ImageIO.read(new File(localImagePath));
        if (ObjectUtils.isEmpty(localImage)){
            throw new IllegalArgumentException("未找到图片");
        }
        // 创建图像的缩放版本
        Image image = localImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        // 构造一个预定义图像类型的BufferedImage
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形2d
        Graphics graphics = tag.getGraphics();
        // 绘制缩小后的图
        graphics.drawImage(image, 0, 0, null);
        // 释放图形资源
        graphics.dispose();
        // 输出到文件流
        boolean success = ImageIO.write(tag, "JPEG", new File(scaleImagePath));
        if (!success){
            throw new IIOException("缩放图片失败");
        }
    }

    /**
     * 缩放图像（按高度或者宽度比值缩放）
     * @param localImagePath 源图像文件地址
     * @param scaleImagePath 缩放后的图像地址
     * @param height         缩放后的高度
     * @param width          缩放后的宽度
     * @param repair         比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException   IOException
     */
    public void scale1WithRatio(String localImagePath, String scaleImagePath, int width, int height, boolean repair) throws IOException {
        // 缩放比例
        double ratio;
        BufferedImage localImage = ImageIO.read(new File(localImagePath));
        if (ObjectUtils.isEmpty(localImage)){
            throw new IllegalArgumentException("未找到图片");
        }
        // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的优先级比速度高 生成的图片质量比较好 但速度慢
        Image image = localImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((localImage.getHeight() > height) || (localImage.getWidth() > width)) {
            if (localImage.getHeight() > localImage.getWidth()) {
                // 源图高比宽长，缩放比例为：缩放高度/源图高度
                ratio = (new Integer(height)).doubleValue() / localImage.getHeight();
            } else {
                // 源图宽比高长，缩放比例为：缩放宽度/源图宽度
                ratio = (new Integer(width)).doubleValue() / localImage.getWidth();
            }
            // AffineTransform是J2SE中非常重要的专门处理2D图像仿射变换的类,表示缩放变换的变换。ratio为width,height倍数
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);

            // 转换源BufferedImage并将结果存储到目标BufferedImage。如果两个图像的颜色模型不匹配，则执行到目标颜色模型的颜色转换。
            // 如果目标图像为空，使用源代码创建缓冲区映像,返回过滤后的缓冲区图像
            image = op.filter(localImage, null);
        }
        if (repair) {
            // 补白
            // BufferedImage.TYPE_INT_RGB表示压缩为8位RGB颜色组件的图像
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 基于图片对象打开绘图
            Graphics2D graphics = bufferedImage.createGraphics();
            // 添加背景色
            graphics.setColor(Color.white);
            // 填充指定的图形
            graphics.fillRect(0, 0, width, height);

            // 根据宽度缩放规则绘图
            if (width == image.getWidth(null)) {
                graphics.drawImage(image, 0, (height - image.getHeight(null)) / 2,
                        image.getWidth(null), image.getHeight(null),
                        Color.white, null);
            } else {
                graphics.drawImage(image, (width - image.getWidth(null)) / 2, 0,
                        image.getWidth(null), image.getHeight(null),
                        Color.white, null);
            }

            // 释放图形资源
            graphics.dispose();
            image = bufferedImage;
        }
        boolean success = ImageIO.write((BufferedImage) image, "JPEG", new File(scaleImagePath));
        if (!success){
            throw new IIOException("缩放图片失败");
        }
    }

    public static void main(String[] args) {
        try {
      /* String url="C:\\Users\\39805\\Desktop\\公众号\\20221102\\Videoframe_20211128_013534_com.huawei.himovie.jpg";
       String url1="C:\\Users\\39805\\Desktop\\公众号\\20221102\\1.jpg";
            //cutImage(url,url1,0,120,590,1090);
            Thumbnails.of(new File(url)).sourceRegion(0,120,590,1090).scale(1.0)
                    .outputQuality(1)
                    .outputFormat("jpg")
                    .toFile(new File(url1));

       */
        File file=new File("C:\\Users\\39805\\Desktop\\1");
        File[] files = file.listFiles();
        for (File file1 : files) {
            Thumbnails.of(file1).sourceRegion(0,120,590,1090).scale(1.0)
                    .outputQuality(1)
                    .outputFormat("jpg")
                    .toFile(new File("C:\\Users\\39805\\Desktop\\2\\"+file1.getName()));
        }
           /* if(file1.getName().contains(".webp")){
                System.out.println(file1.getName());
                String newName=file1.getName().replace(".webp",".jpg");
                File newDir=new File("C:\\Users\\39805\\Desktop\\公众号\\webp"+"\\"+newName);
                file1.renameTo(newDir);
            }*/ } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param localImagePath 源图像地址
     * @param scaleImagePath 切片后的图像地址
     * @param x              目标切片起点坐标X
     * @param y              目标切片起点坐标Y
     * @param width          目标切片宽度
     * @param height         目标切片高度
     * @throws IOException   IOException
     */
    public static void cutImage(String localImagePath, String scaleImagePath, int x, int y, int width, int height) throws IOException {
        // 读取源图像
        BufferedImage localImage = ImageIO.read(new File(localImagePath));
       /* if (ObjectUtils.isEmpty(localImage)){
            throw new IllegalArgumentException("未找到图片");
        }
        // 源图宽度和高度
        int localWidth = localImage.getWidth(null);
        int localHeight = localImage.getHeight(null);

        if (localWidth > 0 && localHeight > 0) {
            // 创建图像的缩放版本
            Image image = localImage.getScaledInstance(localWidth, localHeight,Image.SCALE_DEFAULT);
            // 四个参数分别为图像起点坐标和宽高
            // 即: CropImageFilter(int x,int y,int width,int height)
            // 提取绝对矩形
            ImageFilter filter = new CropImageFilter(x, y, width, height);

            // 使用指定的图像生成器创建图像
            Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics graphics = tag.getGraphics();
            // 绘制切割后的图
            graphics.drawImage(img, 0, 0, width, height, null);
            graphics.dispose();*/
            // 输出为文件
          // boolean success = ImageIO.write(tag, "JPEG", new File(scaleImagePath));
            Thumbnails.of(localImage)
                    .size(width, height)
                    .outputFormat("jpg")
                    .toFile(new File(scaleImagePath));
           /* if (!success) {
                throw new IIOException("切割图片失败");
            }*/
       // }
    }

    /**
     * 图像类型转换：如GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param localImagePath   源图像地址
     * @param convertImageFile 目标图像地址
     * @throws IOException     IOException
     */
    public void convert(String localImagePath, String convertImageFile) throws IOException {
        File file = new File(localImagePath);
        if (ObjectUtils.isEmpty(file)){
            throw new IllegalArgumentException("未找到图片");
        }
        // 判断文件是否有读取权限
        if (file.canRead() && file.canWrite()){
            BufferedImage image = ImageIO.read(file);
            // formatName：包含格式非正式名称的 String：如JPG、JPEG、GIF等
            boolean success = ImageIO.write(image, "jpg", new File(convertImageFile));
            if (!success) {
                throw new IIOException("切割图片失败");
            }
        } else {
            throw new SecurityException("无权访问文件");
        }
    }

    /**
     * 给图片添加文字水印
     * @param pressText      水印文字
     * @param localImageFile 源图像地址
     * @param markImageFile  目标图像地址
     * @param fontName       水印的字体名称
     * @param fontStyle      水印的字体样式(Font.BOLD / 36)
     * @param color          水印的字体颜色
     * @param fontSize       水印的字体大小
     * @param x              修正值
     * @param y              修正值
     * @param alpha          透明度,必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字,值越小透明度越高
     * @throws IOException   IOException
     */
    public void textWatermark(String pressText,
                              String localImageFile,
                              String markImageFile,
                              String fontName,
                              int fontStyle,
                              Color color,
                              int fontSize,
                              int x,
                              int y,
                              float alpha) throws IOException {
        File localFile = new File(localImageFile);
        if (ObjectUtils.isEmpty(localFile)){
            throw new IllegalArgumentException("未找到图片");
        }
        Image src = ImageIO.read(localFile);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(src, 0, 0, width, height, null);
        graphics.setColor(color);
        graphics.setFont(new Font(fontName, fontStyle, fontSize));
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 在指定坐标绘制水印文字
        graphics.drawString(pressText, (width - (getLength(pressText) * fontSize))
                / 2 + x, (height - fontSize) / 2 + y);
        graphics.dispose();
        boolean success = ImageIO.write(image, "jpg", new File(markImageFile));
        if (!success) {
            throw new IIOException("添加文字水印失败");
        }
    }

    /**
     * 给图片添加图片水印
     * @param pressImg       水印图片
     * @param localImagePath 源图像地址
     * @param pressImageFile 目标图像地址
     * @param x              修正值。 默认在中间
     * @param y              修正值。 默认在中间
     * @param alpha          透明度,必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字,值越小透明度越高
     * @throws IOException   IOException
     */
    public void imageWatermark(String pressImg,
                               String localImagePath,
                               String pressImageFile,
                               int x,
                               int y,
                               float alpha) throws IOException {
        File localFile = new File(localImagePath);
        if (ObjectUtils.isEmpty(localFile)){
            throw new IllegalArgumentException("未找到图片");
        }
        Image localImage = ImageIO.read(localFile);
        int width = localImage.getWidth(null);
        int height = localImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(localImage, 0, 0, width, height, null);
        // 水印文件
        Image markImage = ImageIO.read(new File(pressImg));
        if (ObjectUtils.isEmpty(markImage)){
            throw new IllegalArgumentException("未找到水印图片");
        }
        int markImageWidth = markImage.getWidth(null);
        int markImageHeight = markImage.getHeight(null);
        // 将新像素与图形处理器上的现有像素组合
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 矩形图像原点为左上角,x,y=0,图片水印在源图中间
        // x为原点在水平距离向右的修正值，x为原点在垂直距离向下的修正值
        graphics.drawImage(markImage, x, y, markImageWidth, markImageHeight, null);
        // 释放图形处理资源
        graphics.dispose();
        ImageIO.write(image,  "jpg", new File(pressImageFile));
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text 字符
     * @return 字符长度
     */
    private int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}
