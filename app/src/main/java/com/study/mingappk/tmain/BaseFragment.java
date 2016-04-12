package com.study.mingappk.tmain;

import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * Created by Ming on 2016/3/28.
 */
public class BaseFragment extends Fragment {

    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes,BaseActivity.PermissionCallback runnable,@NonNull String... permissions){
        if(getActivity()!=null && getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).performCodeWithPermission(permissionDes,runnable,permissions);
        }
    }


}
