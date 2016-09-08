/*
 * Copyright (C) 2016 Bilibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.study.mingappk.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class ThemeHelper {
    private static final String CURRENT_THEME = "theme_current";

    public static final int CARD_01 = 0x1;//蓝色
    public static final int CARD_02 = 0x2;//紫色
    public static final int CARD_03 = 0x3;//樱花色
    public static final int CARD_04 = 0x4;//绿色
    public static final int CARD_05 = 0x5;//浅绿
    public static final int CARD_06 = 0x6;//黄色
    public static final int CARD_07 = 0x7;//橙色
    public static final int CARD_08 = 0x8;//红色

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .commit();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_01);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == CARD_01;
    }

    public static String getName(int currentTheme) {
        switch (currentTheme) {
            case CARD_01:
                return "color01";
            case CARD_02:
                return "color02";
            case CARD_03:
                return "color03";
            case CARD_04:
                return "color04";
            case CARD_05:
                return "color05";
            case CARD_06:
                return "color06";
            case CARD_07:
                return "color07";
            case CARD_08:
                return "color08";
        }
        return "color01";
    }
}
