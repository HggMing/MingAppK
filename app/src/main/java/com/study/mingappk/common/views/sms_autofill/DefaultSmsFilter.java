/*
 * 文 件 名:  DefaultSmsFilter.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/6/2
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.study.mingappk.common.views.sms_autofill;

/**
 * 默认的短信过滤器
 */
public class DefaultSmsFilter implements SmsFilter {

    @Override
    public String filter(String address, String smsContent) {
        return smsContent;
    }
}
