package com.run.presenter.modle;

import com.run.config.modle.BaseModle;

import java.util.List;

public class PacketModle extends BaseModle {


    /**
     * pack_list : []
     * z_money : null
     * activity_type : 1
     */
    private double z_money;
    private int activity_type;
    private List<WaterModel> pack_list;

    public double getZ_money() {
        return z_money;
    }

    public void setZ_money(double z_money) {
        this.z_money = z_money;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public List<WaterModel> getPack_list() {
        return pack_list;
    }

    public void setPack_list(List<WaterModel> pack_list) {
        this.pack_list = pack_list;
    }
}
