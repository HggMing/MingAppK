/*
 * 文 件 名:  CerificationCode.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/6/2
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.study.mingappk.common.widgets.sms_autofill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信验证码过滤器
 */
public class VerificationCodeSmsFilter implements SmsFilter {
    /**
     * 需要过滤的发短信的人
     */
    private String filterAddress;

    public VerificationCodeSmsFilter(String filterAddress) {
        this.filterAddress = filterAddress;
    }

    @Override
    public String filter(String address, String smsContent) {
        if (address.startsWith(filterAddress)) {
            Pattern pattern = Pattern.compile("(\\d{4,8})");//匹配4-8位的数字
            Matcher matcher = pattern.matcher(smsContent);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return null;
    }
}
