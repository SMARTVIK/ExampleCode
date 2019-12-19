package com.spf.panditji.model;

public class SignInResponse {


    /**
     * user_id : WWxjNWFreHRlSEJaVnpGdVVVZDBhMk4zUFQwPQ==
     * name : DHEERAJ SINGH
     * token : 1576520032
     * msg : user log in
     * success : 1
     */

    private String user_id;
    private String name;
    private int token;
    private String msg;
    private int success;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
