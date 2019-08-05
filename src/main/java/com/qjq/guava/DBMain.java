package com.qjq.guava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBMain {

    public static void main(String[] args) {
        String[] text = readToString("D:/会员用户.csv");
        System.out.println(text);
        System.out.println(text.length);
        System.out.println(text[0]);
        List<User> userList=new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            String str = text[i];
            String[] data = str.split(",");
            User user=new User();
            user.setId(data[0]);
            user.setMobile(data[1]);
            user.setDate(data[2]);
            user.setType(data[3]);
            user.setFun(data[4]);
            userList.add(user);
        }
        System.out.println(userList.size());
        System.out.println(userList.toString());
    }

    public static List<String> readTxtFileIntoStringArrList(String filePath) {
        List<String> list = new ArrayList<String>();
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return list;
    }


    public static String[] readToString(String filePath) {
        File file = new File(filePath);
        Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();

            String[] fileContentArr = new String(filecontent, "GBK").split("\r\n");
            return fileContentArr;// 返回文件内容,默认编码
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static class User {

        private String id;
        private String mobile;
        private String date;
        private String type;
        private String fun;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFun() {
            return fun;
        }

        public void setFun(String fun) {
            this.fun = fun;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", date='" + date + '\'' +
                    ", type='" + type + '\'' +
                    ", fun='" + fun + '\'' +
                    '}';
        }
    }
}
