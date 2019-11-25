package com.tjh.livy_rest_task.common.response;

public enum  ResultCode {
    UNKNOWN_ERROR( 999, "系统异常" ),
    SUCCESS( 1000, "成功" ),
    FAILED( 1001, "失败" );
    private  int code;
    private  String message;
    ResultCode(int code, String message )
    {
       this.code=code;
       this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
