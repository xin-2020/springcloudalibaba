package com.tuilngxueyuan.domain;

/**
 * @Author
 * @date 2021年10月09日11:55
 */
public class result<T> {
    private Integer code;
    private String msg;
    private T data;//作为统一返回对象。会有不同的数据返回，所以统一声明为泛型

    //通过error快速构建一个result对象出来
    public static result error(Integer code, String msg){
        return new result(code,msg);
    }


    public result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public result(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
