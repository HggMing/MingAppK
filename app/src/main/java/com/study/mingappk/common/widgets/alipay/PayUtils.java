package com.study.mingappk.common.widgets.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.study.mingappk.shop.shoptab2.InsuranceOrderActivity;
import com.study.mingappk.shop.shoptab2.PhoneRechargeOrderActivity;
import com.study.mingappk.shop.shoptab2.SalesOrderActivity;
import com.study.mingappk.shop.shoptab2.TravelOrderActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PayUtils {
    private Activity mContext;

    // 商户PID
    private static final String PARTNER = "2088121967690689";
    // 商户收款账号
    private static final String SELLER = "yyzx@isall.com.cn";
    // 商户私钥，pkcs8格式
    private static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANOs0p36avbp" +
            "UFJ34DgK+GSFxi4V3cqearsuJqAl2laShQWV89BttZaluvbh73+lVENw2mX2iVEH8z93YDBpvcmYyevsOVssrD4IL7eYH" +
            "3RNk3AQ17Nky7sDUedAXqJztfKair8l9cE2E+lR7R5Wf3NqigyWiHkaWUxejQe1av0tAgMBAAECgYEAhBTs/4LIx/ViF3" +
            "ORx6agxCjtNsiSMuKS+uWbWIcGLMsUkh21m7M5gh08At2MknvZjpOW09T4bkWltU6KCOuSblvqBOqPNgyXUGHKozybmJ" +
            "8azsKyZ2cyZxZmecGyOnzkTKbpWuARVR+HoP9lkT2nkHpndZqxURVR3eHYpk3IIUkCQQD5B7YYhh1AaAoCluoJjak7LL" +
            "Gl+ky2NAuHEgwv2mdx5AO8lYGYvKQT8WG1XE35FfWDJqDf7xuLmWT1rMy75ygfAkEA2Zl25ANeADcNCiXZSwSYdgC+KF" +
            "mzZUaNkCv5Av6tOL26dmdQJdFTnaERXVkIVb6kA/W017jBQ6kCcUk/p44hMwJABfktYQE2imj0dINyMbB4Mrcru7N5S5" +
            "WMAT1plWxDtvOlM0zSwvsjZGcR1OvV4ven3/F5QmXV309aoJn4fzlYOQJBALo6znbcruGuO9m4hNzgZP9xKU3tX0zI8jU2" +
            "fRpkDqpmLqRRTVXgbhiVFgB3R1vjGgpNimWpuLYIeo+sKe9kA+MCQQC3eYKYy0u/YbBK9pWN1EcoS7+QPKV3csxDLkofqq" +
            "fjwprRirv/tp7m8PtU+1keR7dnGFy21F3HiLqRMMtThZP6";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private int fromActivity;//1、话费流量2、汽车保险3、村实惠4、旅游5、特产订单 -1、其他

    public PayUtils(Activity mContext,int fromActivity) {
        this.mContext = mContext;
        this.fromActivity=fromActivity;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();

                        if(fromActivity==-1){
                            mContext.finish();
                            break;
                        }
                        Intent intent=new Intent();
                        switch (fromActivity){
                            case 1://话费流量
                                intent.setClass(mContext, PhoneRechargeOrderActivity.class);
                                break;
                            case 2://汽车保险
                                intent.setClass(mContext,InsuranceOrderActivity.class);
                                break;
                            case 3://村实惠
                                intent.setClass(mContext, SalesOrderActivity.class);
                                break;
                            case 4://旅游
                                intent.setClass(mContext, TravelOrderActivity.class);
                                break;
                            case 5://特产
                                //Todo  跳转到特产订单界面
                                break;
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     * @param goods_name  商品名称
     * @param goods_detail 商品详情
     * @param goods_price 商品价格
     * @param order_no  商品订单号
     * @param notify_url 服务器异步通知地址
     */
    public void pay(String goods_name,String goods_detail,String goods_price,String order_no,String notify_url) {

        String orderInfo = getOrderInfo(goods_name, goods_detail, goods_price,order_no,notify_url);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price,String order_no,String url) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + order_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + url + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
