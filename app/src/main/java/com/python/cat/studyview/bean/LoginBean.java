package com.python.cat.studyview.bean;

import java.util.List;

/**
 * Created by cat on 2018/6/16.
 */

public class LoginBean {


    /**
     * data : {"collectIds":[2864,3016],"email":"","icon":"","id":6649,"password":"wdwaz123","type":0,"username":"pythoncat"}
     * errorCode : 0
     * errorMsg :
     */

    public DataBean data;
    public int errorCode;
    public String errorMsg;

    public static class DataBean {
        /**
         * collectIds : [2864,3016]
         * email :
         * icon :
         * id : 6649
         * password : wdwaz123
         * type : 0
         * username : pythoncat
         */

        public String email;
        public String icon;
        public int id;
        public String password;
        public int type;
        public String username;
        public List<Integer> collectIds;

        @Override
        public String toString() {
            return "DataBean{" +
                    "email='" + email + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", password='" + password + '\'' +
                    ", type=" + type +
                    ", username='" + username + '\'' +
                    ", collectIds=" + collectIds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
