package cn.kuwo.player.event;
import java.util.List;

import cn.kuwo.player.bean.ProductBean;
import cn.kuwo.player.bean.entity.SideDishEntity;

public class ComboEvent {
    private ProductBean productBean;
    private List<String> comboList;
    private int cookSerial;
    private String content;
    private Double CommodityNumber;
    private Boolean isEdit;
    private int orderIndex;
    private String barcode;
    private String cookStyle;
    private int OriginNumber;
    private SideDishEntity sideDish;
    private Double sideDishPrice;
    private int sideDishIndex;

    public ComboEvent(ProductBean productBean, List<String> comboList,int cookSerial,String content,Double CommodityNumber,Boolean isEdit,int orderIndex,String barcode,String cookStyle,int OriginNumber,SideDishEntity sideDish,Double sideDishPrice,int sideDishIndex) {
        this.productBean = productBean;
        this.comboList = comboList;
        this.cookSerial = cookSerial;
        this.content = content;
        this.CommodityNumber = CommodityNumber;
        this.isEdit=isEdit;
        this.orderIndex=orderIndex;
        this.barcode=barcode;
        this.cookStyle=cookStyle;
        this.OriginNumber=OriginNumber;
        this.sideDish=sideDish;
        this.sideDishPrice=sideDishPrice;
        this.sideDishIndex=sideDishIndex;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }

    public List<String> getComboList() {
        return comboList;
    }

    public void setComboList(List<String> comboList) {
        this.comboList = comboList;
    }

    public int getCookSerial() {
        return cookSerial;
    }

    public void setCookSerial(int cookSerial) {
        this.cookSerial = cookSerial;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getCommodityNumber() {
        return CommodityNumber;
    }

    public void setCommodityNumber(Double commodityNumber) {
        CommodityNumber = commodityNumber;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCookStyle() {
        return cookStyle;
    }

    public void setCookStyle(String cookStyle) {
        this.cookStyle = cookStyle;
    }

    public int getOriginNumber() {
        return OriginNumber;
    }

    public void setOriginNumber(int originNumber) {
        OriginNumber = originNumber;
    }

    public SideDishEntity getSideDish() {
        return sideDish;
    }

    public void setSideDish(SideDishEntity sideDish) {
        this.sideDish = sideDish;
    }

    public Double getSideDishPrice() {
        return sideDishPrice;
    }

    public void setSideDishPrice(Double sideDishPrice) {
        this.sideDishPrice = sideDishPrice;
    }

    public int getSideDishIndex() {
        return sideDishIndex;
    }

    public void setSideDishIndex(int sideDishIndex) {
        this.sideDishIndex = sideDishIndex;
    }

    @Override
    public String toString() {
        return "ComboEvent{" +
                "productBean=" + productBean +
                ", comboList=" + comboList +
                ", cookSerial=" + cookSerial +
                ", content='" + content + '\'' +
                ", CommodityNumber=" + CommodityNumber +
                ", isEdit=" + isEdit +
                ", orderIndex=" + orderIndex +
                ", barcode='" + barcode + '\'' +
                ", cookStyle='" + cookStyle + '\'' +
                ", OriginNumber=" + OriginNumber +
                ", sideDish=" + sideDish +
                ", sideDishPrice=" + sideDishPrice +
                ", sideDishIndex=" + sideDishIndex +
                '}';
    }
}
