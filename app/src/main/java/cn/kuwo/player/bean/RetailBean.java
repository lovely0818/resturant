package cn.kuwo.player.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<ProductBean> commodityList;
    private ArrayList<String> codes;
    private ArrayList<Double> prices;
    private ArrayList<Double> weight;

    public RetailBean(ArrayList<ProductBean> commodityList, ArrayList<String> codes, ArrayList<Double> prices, ArrayList<Double> weight) {
        this.commodityList=commodityList;
        this.codes = codes;
        this.prices = prices;
        this.weight = weight;
    }

    public ArrayList<ProductBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(ArrayList<ProductBean> commodityList) {
        this.commodityList = commodityList;
    }

    public ArrayList<String> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
    }

    public ArrayList<Double> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Double> prices) {
        this.prices = prices;
    }

    public ArrayList<Double> getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<Double> weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RetailBean{" +
                "commodityList=" + commodityList +
                ", codes=" + codes +
                ", prices=" + prices +
                ", weight=" + weight +
                '}';
    }
}
