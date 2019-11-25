package com.tjh.livy_rest_task.model.vo;

public class SparkTaskParamVo {
    private String sparkConf;
    private String[] args;
    private String[] jars;
    private String otherConfig;

    public String getSparkConf() {
        return sparkConf;
    }

    public void setSparkConf(String sparkConf) {
        this.sparkConf = sparkConf;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getJars() {
        return jars;
    }

    public void setJars(String[] jars) {
        this.jars = jars;
    }

    public String getOtherConfig() {
        return otherConfig;
    }

    public void setOtherConfig(String otherConfig) {
        this.otherConfig = otherConfig;
    }
}
