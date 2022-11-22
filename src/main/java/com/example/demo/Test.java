package com.example.demo;

import java.io.File;
import java.util.Optional;

public class Test {
    public static void main(String[] args) {
       /* // 传入正常值，正常返回一个 Optional 对象
        Optional<String> optional1 = Optional.of("hhhh");
        System.out.println(optional1);

        // 传入参数为 null，抛出 NullPointerException.
        Optional optional2 = Optional.of(null);
        System.out.println(optional2);*/

        // 传入正常值，正常返回一个 Optional 对象
      /*  Optional<String> optional1 = Optional.ofNullable("hhhh");
        System.out.println(optional1);
        // 传入 null 参数，正常返回 Optional 对象
        Optional optional2 = Optional.ofNullable(null);
        System.out.println(optional2);*/
        // 传入正常值，正常返回一个 Optional 对象，并使用 isPresent 方法
      /*  Optional optional1 = Optional.ofNullable("hhh");
        System.out.println("传入正常值返回：" + optional1.isPresent());

        // 传入参数为 null 生成一个 Optional 对象，并使用 isPresent 方法
        Optional optional2 = Optional.ofNullable(null);
        System.out.println("传入 null 值返回：" + optional2.isPresent());*/

        // 传入正常值，正常返回一个 Optional 对象，并使用 get 方法获取值
 /*       Optional optional1 = Optional.ofNullable("hhh");
        System.out.println(optional1.get());

        // 传入参数为 null 生成一个 Optional 对象，并使用 get 方法获取值
        Optional optional2 = Optional.ofNullable(null);
        System.out.println(optional2.get());*/

        // 传入正常参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
      /*  Optional optional1 = Optional.ofNullable("hhh");
        Object object1 = optional1.orElse("111");
        System.out.println("如果值不为空："+object1);

        // 传入 null 参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
        Optional optional2 = Optional.ofNullable(null);
        Object object2 = optional2.orElse("111");
        System.out.println("如果值为空："+object2);*/

      File file=new File("C:\\Users\\39805\\Desktop\\公众号\\金阳儿");
        File[] files = file.listFiles();
        for (File file1 : files) {
            if(file1.getName().contains(".webp")){
                System.out.println(file1.getName());
               String newName=file1.getName().replace(".webp",".jpg");
              File newDir=new File("C:\\Users\\39805\\Desktop\\公众号\\webp"+"\\"+newName);
                file1.renameTo(newDir);
            }
        }
    }
}
