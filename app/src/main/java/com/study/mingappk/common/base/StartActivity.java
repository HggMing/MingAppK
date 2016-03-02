package com.study.mingappk.common.base;

import android.content.Intent;

/**
 * 启动Activity接口
 */
public interface StartActivity {
    void startActivityForResult(Intent intent, int requestCode);
}
