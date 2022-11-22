package com.example.demo;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class Cmder {
    /**
     * 得到系统U盘根目录
     */
    public String findURootPath(){
        FileSystemView sys = FileSystemView.getFileSystemView();
        //循环盘符
        File[] files = File.listRoots();
        for(File file:files){
            //得到系统中存在的C:\,D:\,E:\,F:\,H:\
            System.out.println("系统中存在的"+file.getPath());
        }
        File file = null;
        String path = null;
        for(int i = 0; i < files.length; i++) {
            //得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
            System.out.println("得到文字命名形式的盘符"+sys.getSystemDisplayName(files[i]));
            if(sys.getSystemDisplayName(files[i]).contains("测试U盘")){
                file = files[i];
                break;
            }
        }
        if(file!=null){
            path = file.getPath();
        }
        return path;
    }

    public static void main(String[] args) {
       new  Cmder().findURootPath();
        // String result = executeBatFile("G:\\备份\\投资\\电脑清理.bat", true);
         String result = executeBatFile("G:\\tool\\DeepFaceLab_build_02_12_2018\\DeepFaceLabTorrent\\2.bat", true);
        System.out.println(result);
    }

    /**
     * 执行bat文件，
     *
     * @param file          bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */
    public static String executeBatFile(String file, boolean isCloseWindow) {
        String cmdCommand = null;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c start " + file;
        } else {
            cmdCommand = "cmd.exe /k start " + file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行一个cmd命令
     *
     * @param cmdCommand cmd命令
     * @return 命令执行结果字符串，如出现异常返回null
     */
    public static String executeCmdCommand(String cmdCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行bat文件,新开窗口
     *
     * @param file          bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */
    public static String executeBatFileWithNewWindow(String file, boolean isCloseWindow) {
        String cmdCommand;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c start" + file;
        } else {
            cmdCommand = "cmd.exe /k start" + file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行bat脚本
     *
     * @param batScript 脚本内容
     * @param location  脚本存储路径
     * @return 结果
     */
    public static String executeBatScript(String batScript, String location) {
        StringBuilder stringBuilder = new StringBuilder();
        FileWriter fw = null;
        try {
            //生成bat文件
            fw = new FileWriter(location);
            fw.write(batScript);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Process process;
        try {
            process = Runtime.getRuntime().exec(location);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行脚本,不停止,并输出执行结果
     *
     * @param batScript 脚本内容
     * @param location  bat文件生成地址
     */
    public void executeBatScriptAlways(String batScript, String location) {
        FileWriter fw = null;
        try {
            //生成bat文件
            fw = new FileWriter(location);
            fw.write(batScript);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        //运行bat文件
        Process process;
        try {
            process = Runtime.getRuntime().exec(location);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}