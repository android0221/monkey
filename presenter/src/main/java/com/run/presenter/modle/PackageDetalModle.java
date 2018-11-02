package com.run.presenter.modle;

import com.run.config.modle.BaseModle;

import java.util.List;

public class PackageDetalModle  extends BaseModle {

   private List<PackBean> list;

    public List<PackBean> getList() {
        return list;
    }

    public void setList(List<PackBean> list) {
        this.list = list;
    }

    public static class PackBean{
        private String create_time; //创建时间
        private String expire_time; //到期时间
        private double money;
        private int  packet_type;
        private String head_avatar;

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

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getPacket_type() {
            return packet_type;
        }

        public void setPacket_type(int packet_type) {
            this.packet_type = packet_type;
        }

        public String getHead_avatar() {
            return head_avatar;
        }

        public void setHead_avatar(String head_avatar) {
            this.head_avatar = head_avatar;
        }
    }

}
