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

    public static final int CARD_BLUE = 0x1;//蓝色
    public static final int CARD_PURPLE = 0x2;//紫色
    public static final int CARD_PINK = 0x3;//樱花色
    public static final int CARD_GREEN = 0x4;//绿色
    public static final int CARD_GREEN_LIGHT = 0x5;//浅绿
    public static final int CARD_YELLOW = 0x6;//黄色
    public static final int CARD_ORANGE = 0x7;//橙色
    public static final int CARD_RED = 0x8;//红色

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .commit();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_BLUE);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == CARD_BLUE;
    }

    public static String getName(int currentTheme) {
        switch (currentTheme) {
            case CARD_BLUE:
                return "blue";
            case CARD_PURPLE:
                return "purple";
            case CARD_PINK:
                return "pink";
            case CARD_GREEN:
                return "green";
            case CARD_GREEN_LIGHT:
                return "green_light";
            case CARD_YELLOW:
                return "yellow";
            case CARD_ORANGE:
                return "orange";
            case CARD_RED:
                return "red";
        }
        return "blue";
    }
}
