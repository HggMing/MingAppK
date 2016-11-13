package com.study.mingappk.model.event;

/**
 * Created by Ming on 2016/11/10.
 */

import android.util.SparseArray;

import com.study.mingappk.model.bean.ProductList;

import java.math.BigDecimal;

/**
 * 传递特产购买的购物清单和总价格
 */
public class ProductBuyEvent {

    private BigDecimal priceAll;//商品总价
    SparseArray<ProductList.DataBean.ListBean> buyList = new SparseArray<>();//已买商品列表

    public ProductBuyEvent(BigDecimal priceAll, SparseArray<ProductList.DataBean.ListBean> buyList) {
        this.priceAll = priceAll;
        this.buyList = buyList;
    }

    public BigDecimal getPriceAll() {
        return priceAll;
    }

    public SparseArray<ProductList.DataBean.ListBean> getBuyList() {
        return buyList;
    }
}
