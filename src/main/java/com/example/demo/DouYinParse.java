package com.example.demo;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// https://maven.aliyun.com/repository/public/com/alibaba/fastjson/1.2.76/fastjson-1.2.76.jar
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;

/**
 * 解析抖音无水印视频
 *
 * @author 张泽楠
 * @since 2021-06-15
 *
 */
public class DouYinParse {
    public static void main(String[] args) throws Exception{
        String url1 ="9.28 bnd:/ 复制打开抖音，看看【寻风少予的作品】做朵温柔的小玫瑰 # 比不完的剪刀手 # 请叫我甜... https://v.douyin.com/rUTfyWV/";
        String url2 ="1.20 Mws:/ 没错，我就是2G网  https://v.douyin.com/rPJx9Se/ 复制此链接，打开Dou音搜索，直接观看视频！";
        String[] strings=new String[]{
               // "2.02 HIi:/ 妹妹说紫色很有韵味# 氛围感 # 甜御 # 随拍 # 06年  https://v.douyin.com/rP8YwG4/ 复制此链接，打开Dou音搜索，直接观看视频！"

                //"2.02 rRX:/ 囚徒困境：你会背叛你的朋友吗？博弈论与纳什均衡（二）（3）  https://v.douyin.com/rPNVxvS/ 复制此链接，打开Dou音搜索，直接观看视频！"
"0.20 qRk:/ # 红色系  https://v.douyin.com/r5sogpA/ 复制此链接，打开Dou音搜索，直接观看视频！"

        };
        for (String url : strings) {
            down(url);
        }

    }
    public static void down(String url1) throws Exception {

        //过滤链接，获取http连接地址
        String finalUrl = DouYinQushuiyin.decodeHttpUrl(url1);
        System.out.println();
        String matchUrl = parseDouYinVideo(finalUrl);
        //5.将链接封装成流
        //注:由于抖音对请求头有限制,只能设置一个伪装手机浏览器请求头才可实现去水印下载
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("Host", "aweme.snssdk.com");
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1");

        //6.利用Joup获取视频对象,并作封装成一个输入流对象
        BufferedInputStream in = Jsoup.connect(matchUrl).headers(headers).timeout(10000).ignoreContentType(true).execute().bodyStream();

        Long timetmp = new Date().getTime();
        String fileAddress = "d:/抖音视频/douyin_"+timetmp+".mp4";

        //7.封装一个保存文件的路径对象
        File fileSavePath = new File(fileAddress);

        //注:如果保存文件夹不存在,那么则创建该文件夹
        File fileParent = fileSavePath.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }

        //8.新建一个输出流对象
        OutputStream out =
                new BufferedOutputStream(
                        new FileOutputStream(fileSavePath));

        //9.遍历输出文件
        int b ;
        while((b = in.read()) != -1) {
            out.write(b);
        }

        out.close();//关闭输出流
        in.close(); //关闭输入流

    }

    /**
     *
     * 解析抖音无水印视频
     *
     * @author 张泽楠
     * @param httpURL
     *            抖音视频分享链接
     * @return 抖音无水印视频链接
     */
    public static String parseDouYinVideo(String httpURL) {
        String result = null;
        // 获取重定向后的URL地址，并从中截取item_ids
        String itemIDs = getRedirectsURL(httpURL).split("/")[5];
        // 使用item_ids作为参数，请求携带有带水印的视频URL的JSON数据
        String jsonString = getJSON("https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + itemIDs);
        // 使用FastJSON解析为JSONObject对象
        JSONObject json = JSON.parseObject(jsonString);
        // 解析JSON数据，获取带水印的视频URL
        Object videoURL = json.getJSONArray("item_list").getJSONObject(0).getJSONObject("video")
                .getJSONObject("play_addr").getJSONArray("url_list").get(0);
        // 替换URL关键位置，得到无水印视频URL
        result = videoURL.toString().replace("/playwm/", "/play/");
        return result;
    }

    /**
     *
     * 获取重定向后的URL地址
     *
     * @author 张泽楠
     * @param httpURL
     *            原URL
     * @return 重定向后的URL
     */
    public static String getRedirectsURL(String httpURL) {
        String result = null;
        HttpURLConnection conn = null;
        try {
            // 配置请求头
            conn = (HttpURLConnection) new URL(httpURL).openConnection();
            // 禁止重定向
            conn.setInstanceFollowRedirects(false);
            // 获取重定向后的URL
            result = conn.getHeaderField("Location");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 清理资源
            conn.disconnect();
        }
        return result;
    }

    /**
     *
     * 以GET的方式请求JSON数据
     *
     * @author 张泽楠
     * @param httpURL
     *            请求的URL
     * @return 请求到的JSON数据
     */
    public static String getJSON(String httpURL) {
        HttpURLConnection connection = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpURL);
            // 配置请求头
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 发送HTTP连接请求
            connection.connect();
            // 如果请求连接成功则接收数据
            if (connection.getResponseCode() == 200) {
                // 读取并保存JSON数据
                is = connection.getInputStream();
                isr = new InputStreamReader(is, "UTF8");
                br = new BufferedReader(isr);
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                }
                result = sbf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (null != br) {
                    br.close();
                }
                if (null != isr) {
                    isr.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 清理资源
            connection.disconnect();
        }
        return result;
    }
}

