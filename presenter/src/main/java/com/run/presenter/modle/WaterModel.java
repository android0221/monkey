package com.run.presenter.modle;


public class WaterModel {

    private String content;

    public WaterModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    private int r_id;//红包ID
    private String create_time;//创建时间
    private String expire_time;//到期时间

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
}
