package cn.kuwo.player.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.kuwo.player.MyApplication;
import cn.kuwo.player.R;
import cn.kuwo.player.adapter.ShowRetailAdapter;
import cn.kuwo.player.base.BaseActivity;
import cn.kuwo.player.bean.RetailBean;
import cn.kuwo.player.bean.UserBean;
import cn.kuwo.player.custom.CommomDialog;
import cn.kuwo.player.custom.ScanUserFragment;
import cn.kuwo.player.custom.ShowReduceListFragment;
import cn.kuwo.player.event.CouponEvent;
import cn.kuwo.player.event.OrderDetail;
import cn.kuwo.player.fragment.PayFg;
import cn.kuwo.player.print.Bill;
import cn.kuwo.player.util.CONST;
import cn.kuwo.player.util.CameraProvider;
import cn.kuwo.player.util.MyUtils;
import cn.kuwo.player.util.ObjectUtil;
import cn.kuwo.player.util.ProductUtil;
import cn.kuwo.player.util.ToastUtil;

public class SettleActivity extends BaseActivity {
    @BindView(R.id.user_avatar)
    QMUIRadiusImageView userAvatar;
    @BindView(R.id.svip_avatar)
    ImageView svipAvatar;
    @BindView(R.id.user_type)
    TextView userType;
    @BindView(R.id.user_tel)
    TextView userTel;
    @BindView(R.id.user_whitebar)
    TextView userWhitebar;
    @BindView(R.id.user_stored)
    TextView userStored;
    @BindView(R.id.user_meatweight)
    TextView userMeatweight;
    @BindView(R.id.ll_show_member)
    LinearLayout llShowMember;
    @BindView(R.id.sign_user)
    Button signUser;
    @BindView(R.id.total_number)
    TextView totalNumber;
    @BindView(R.id.recycle_scan_good)
    RecyclerView recycleScanGood;
    @BindView(R.id.orgin_price)
    TextView orginPrice;
    @BindView(R.id.svip_all_reduce)
    TextView svipAllReduce;
    @BindView(R.id.ll_max_reduce)
    LinearLayout llMaxReduce;
    @BindView(R.id.min_pay_money)
    TextView minPayMoney;
    @BindView(R.id.my_svip_reduce_weight)
    TextView mySvipReduceWeight;
    @BindView(R.id.ll_my_reduce)
    LinearLayout llMyReduce;
    @BindView(R.id.my_svip_reduce_money)
    TextView mySvipReduceMoney;
    @BindView(R.id.tv_offline_content)
    TextView tvOfflineContent;
    @BindView(R.id.tv_offline_moeny)
    TextView tvOfflineMoeny;
    @BindView(R.id.ll_offline_moeny)
    LinearLayout llOfflineMoeny;
    @BindView(R.id.tv_online_content)
    TextView tvOnlineContent;
    @BindView(R.id.tv_online_moeny)
    TextView tvOnlineMoeny;
    @BindView(R.id.ll_online_moeny)
    LinearLayout llOnlineMoeny;
    @BindView(R.id.store_reduce_money)
    TextView storeReduceMoney;
    @BindView(R.id.pay_content)
    TextView payContent;
    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.cb_use_svip)
    CheckBox cbUseSvip;
    @BindView(R.id.btn_pay)
    Button btnPay;

    private RetailBean retailBean;
    private LinearLayoutManager linearLayoutManager;
    private ShowRetailAdapter showRetailAdapter;
    private AVObject tableAVObject;
    private String userId = "";
    private String useMeatId = "";
    private Double originTotalMoneny = 0.0;
    private Double actualTotalMoneny = 0.0;
    private Double offlineCouponMoney = 0.0;
    private Double onlineCouponMoney = 0.0;
    private Double meatReduceMoney = 0.0;
    private Double meatReduceWeight = 0.0;
    private Double myMeatReduceMoney = 0.0;
    private Double myMeatReduceWeight = 0.0;
    private Double realPayMoeny = 0.0;
    private Double activityReduceMoney = 0.0;
    private Double hasMeatWeight = 0.0;
    private CouponEvent onlineCouponEvent = null;
    private CouponEvent offlineCouponEvent = null;
    private Boolean isSvip = false;
    private List<Object> orders = new ArrayList<>();
    private List<Object> useExchangeList = new ArrayList<>();
    private AVObject avUser;
    private UserBean userBean;
    private int REQUEST_CODE_SCAN = 111;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_settle;
    }

    /**
     * 设置商品展示布局
     */
    @Override
    public void initData() {
        retailBean = (RetailBean) getIntent().getSerializableExtra("retailBean");
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleScanGood.setLayoutManager(linearLayoutManager);
        showRetailAdapter = new ShowRetailAdapter(MyApplication.getContextObject(), retailBean);
        recycleScanGood.setAdapter(showRetailAdapter);
        orders = ObjectUtil.toObject(retailBean);
        cbUseSvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshList();
            }
        });
        setData();
    }

    /**
     * 获取基本数据信息
     */
    public void setData() {
        if (userId.length() == 0) {
            llShowMember.setVisibility(View.INVISIBLE);
            signUser.setText("用户登录");
            refreshList();
        } else {
            getUserInfo();
        }
        originTotalMoneny = MyUtils.totalPrice(retailBean.getPrices());
        orginPrice.setText(originTotalMoneny + "元");
        totalMoney.setText(originTotalMoneny + "元");
        totalNumber.setText(retailBean.getCodes().size() + "");

    }

    private void getUserInfo() {
        showDialog();
        AVQuery<AVObject> userQuery = new AVQuery<>("_User");
        userQuery.getInBackground(userId, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    parameter.put("userID", avObject.getObjectId());
                    AVCloud.callFunctionInBackground("svip", parameter, new FunctionCallback<Map<String, Object>>() {
                        @Override
                        public void done(Map<String, Object> objectMap, AVException e) {
                            if (e == null) {
                                hideDialog();
                                avUser=avObject;
                                llShowMember.setVisibility(View.VISIBLE);
                                Double whiteBarBalance = MyUtils.formatDouble(MyUtils.formatDouble(avUser.getDouble("gold")) - MyUtils.formatDouble(avUser.getDouble("arrears")));
                                Double storedBalance = MyUtils.formatDouble(avUser.getDouble("stored"));
                                Glide.with(MyApplication.getContextObject()).load(avUser.getAVFile("avatar").getUrl()).into(userAvatar);
                                userTel.setText("用户手机号:" + avUser.getString("username"));
                                userStored.setText("消费金:" + storedBalance);
                                userWhitebar.setText("白条:" + whiteBarBalance);
                                userMeatweight.setText("牛肉额度:" + objectMap.get("meatWeight").toString() + "kg");
                                hasMeatWeight = Double.parseDouble(objectMap.get("meatWeight").toString());
                                useMeatId = objectMap.get("meatId").toString().length() > 0 ? objectMap.get("meatId").toString() : "";
                                if ((Boolean) objectMap.get("svip")) {
                                    svipAvatar.setVisibility(View.VISIBLE);
                                    userType.setText("超牛会员");
                                    isSvip = true;
                                } else {
                                    svipAvatar.setVisibility(View.GONE);
                                    userType.setText("普通会员");
                                    isSvip = false;
                                }
                                signUser.setText("退出登录");
                                refreshList();
                            } else {
                                hideDialog();
                                refreshList();
                                ToastUtil.showShort(MyApplication.getContextObject(), e.getMessage());
                            }
                        }
                    });

                } else {
                    hideDialog();
                    refreshList();
                }
            }
        });
    }

    private void refreshList() {
        originTotalMoneny = 0.0;
        meatReduceMoney = 0.0;
        offlineCouponMoney = 0.0;
        onlineCouponMoney = 0.0;
        activityReduceMoney = 0.0;
        myMeatReduceMoney = 0.0;
        myMeatReduceWeight = 0.0;
        totalNumber.setText(orders.size() + "");
        originTotalMoneny = MyUtils.totalPrice(retailBean.getPrices());
        orginPrice.setText(originTotalMoneny + "");
        if (userId.length()>0) {
            cbUseSvip.setVisibility(View.VISIBLE);
        } else {
            cbUseSvip.setVisibility(View.GONE);
        }
        meatReduceMoney = ProductUtil.calMeatduceMoney(orders,retailBean.getPrices());
        meatReduceWeight = ProductUtil.calMeatduceWeight(orders,retailBean.getWeight());
        svipAllReduce.setText(meatReduceWeight + "kg");
        if (userId.length()>0) {
            if (hasMeatWeight >= meatReduceWeight) {//用户牛肉大于等于可兑换的牛肉重量
                mySvipReduceWeight.setText(meatReduceWeight + "kg");
                mySvipReduceMoney.setText("-" + meatReduceMoney);
                myMeatReduceWeight = meatReduceWeight;
                myMeatReduceMoney = meatReduceMoney;
                List<Object> centerOrder = new ArrayList<>();
                try {
                    centerOrder = ObjectUtil.deepCopy(orders);
                } catch (Exception e) {
                    centerOrder = new ArrayList<>();
                    e.printStackTrace();
                }
                useExchangeList = ProductUtil.canExchangeMeatList(centerOrder, hasMeatWeight,retailBean.getWeight());
            } else {
                List<Object> centerOrder = new ArrayList<>();
                try {
                    centerOrder = ObjectUtil.deepCopy(orders);
                } catch (Exception e) {
                    centerOrder = new ArrayList<>();
                    e.printStackTrace();
                }
                useExchangeList = ProductUtil.canExchangeMeatList(centerOrder, hasMeatWeight,retailBean.getWeight());
                myMeatReduceWeight = ProductUtil.calculateTotalWeight(useExchangeList);
                myMeatReduceMoney = ProductUtil.calculateTotalMoney(useExchangeList);
                mySvipReduceWeight.setText(myMeatReduceWeight + "kg");
                mySvipReduceMoney.setText("-" + myMeatReduceMoney);
            }

        } else {
            mySvipReduceWeight.setText("0.0kg");
            mySvipReduceMoney.setText("-0.0");
        }
        if (onlineCouponEvent != null) {
            onlineCouponMoney = onlineCouponEvent.getMoney();
            tvOnlineMoeny.setText("-" + onlineCouponMoney);
            tvOnlineContent.setText(onlineCouponEvent.getContent());
        }
        if (offlineCouponEvent != null) {
            offlineCouponMoney = offlineCouponEvent.getMoney();
            tvOfflineMoeny.setText("-" + offlineCouponMoney);
            tvOfflineContent.setText(offlineCouponEvent.getContent());
        }
        if (cbUseSvip.isChecked()) {
            activityReduceMoney = MyUtils.formatDouble((originTotalMoneny - myMeatReduceMoney - offlineCouponMoney - onlineCouponMoney) * (1 - MyUtils.getDayRate()));
            if (activityReduceMoney < 0) activityReduceMoney = 0.0;
            actualTotalMoneny = originTotalMoneny - offlineCouponMoney - onlineCouponMoney - activityReduceMoney - myMeatReduceMoney;
            if (actualTotalMoneny < 0) actualTotalMoneny = 0.0;
            totalMoney.setText("￥" + actualTotalMoneny + "元");
            storeReduceMoney.setText("-" + activityReduceMoney);
        } else {
            activityReduceMoney = MyUtils.formatDouble((originTotalMoneny - offlineCouponMoney - onlineCouponMoney) * (1 - MyUtils.getDayRate()));
            if (activityReduceMoney < 0) activityReduceMoney = 0.0;
            actualTotalMoneny = originTotalMoneny - offlineCouponMoney - onlineCouponMoney - activityReduceMoney;
            if (actualTotalMoneny < 0) actualTotalMoneny = 0.0;
            totalMoney.setText("￥" + actualTotalMoneny + "元");
            storeReduceMoney.setText("-" + activityReduceMoney);
        }
        minPayMoney.setText("-" + meatReduceMoney);
    }

    @OnClick({R.id.sign_user, R.id.btn_pay, R.id.ll_max_reduce, R.id.ll_my_reduce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_user:
                if (userId.length() > 0) {
                    new CommomDialog(this, R.style.dialog, "确定要取消此订单的用户信息？", new cn.kuwo.player.custom.CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                                userId = "";
                                avUser=null;
                                ToastUtil.showShort(MyApplication.getContextObject(), "清空用户数据成功");
                                setData();
                            }

                        }
                    })
                            .setTitle("提示").setNegativeButton("取消").setPositiveButton("退出").show();
                } else {
                    chooseScanType();
                }


                break;
            case R.id.btn_pay:
                Intent intent = new Intent(SettleActivity.this, PayActivity.class);
                OrderDetail orderDetail = new OrderDetail(tableAVObject, hasMeatWeight, originTotalMoneny,
                        actualTotalMoneny, meatReduceWeight, meatReduceMoney, myMeatReduceWeight, myMeatReduceMoney, cbUseSvip.isChecked(),
                        onlineCouponEvent, offlineCouponEvent, activityReduceMoney, isSvip, useExchangeList, useMeatId, ProductUtil.calExchangeMeatList(orders),userBean,orders);
                intent.putExtra("table", orderDetail);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_max_reduce:
                ShowReduceListFragment showReduceListFragment = new ShowReduceListFragment(ProductUtil.calExchangeMeatList(orders), 0);
                showReduceListFragment.show(getSupportFragmentManager(), "showreducelist");
                break;
            case R.id.ll_my_reduce:
                showReduceListFragment = new ShowReduceListFragment(useExchangeList, 1);
                showReduceListFragment.show(getSupportFragmentManager(), "showreducelist");
                break;
        }
    }

    private void chooseScanType() {
        if (CameraProvider.hasCamera()) {
            if (MyUtils.getCameraPermission(getApplicationContext())) {
                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, MyUtils.caremaSetting());
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        } else {
            ScanUserFragment scanUserFragment = new ScanUserFragment(1);
            scanUserFragment.show(getSupportFragmentManager(), "scanuser");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserMessgae(UserBean bean) {
        if (bean.getCallbackCode() == CONST.UserCode.SCANCUSTOMER) {
            userBean=bean;
            llShowMember.setVisibility(View.VISIBLE);
            Glide.with(MyApplication.getContextObject()).load(userBean.getAvatar()).into(userAvatar);
            userTel.setText("用户手机号:" + userBean.getUsername());
            userStored.setText("消费金:" + userBean.getStored());
            userWhitebar.setText("白条:" + userBean.getBalance());
            userMeatweight.setText("牛肉额度:" + userBean.getMeatWeight() + "kg");
            hasMeatWeight=userBean.getMeatWeight();
            userId = userBean.getId();
            if (userBean.getSvip()) {
                svipAvatar.setVisibility(View.VISIBLE);
                userType.setText("超牛会员");
            } else {
                svipAvatar.setVisibility(View.GONE);
                userType.setText("普通会员");
            }
            signUser.setText("退出登录");
            setData();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CouponEvent event) {

        if (event.getType() == 1) {
            onlineCouponEvent = event;
            setData();
        } else if (event.getType() == 2) {
            offlineCouponEvent = event;
            setData();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1){
            Intent intent = getIntent();
            setResult(1,intent);
            finish();
        }
    }
}
