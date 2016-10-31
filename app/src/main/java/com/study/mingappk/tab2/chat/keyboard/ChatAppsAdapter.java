package com.study.mingappk.tab2.chat.keyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyGallerFinal;
import com.study.mingappk.common.widgets.gallerfinal.FunctionConfig;
import com.study.mingappk.common.widgets.gallerfinal.GalleryFinal;
import com.study.mingappk.common.widgets.gallerfinal.model.PhotoInfo;
import com.study.mingappk.model.bean.ChatAppBean;
import com.study.mingappk.model.event.SendImageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ChatAppsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<ChatAppBean> mDdata = new ArrayList<>();

    public ChatAppsAdapter(Context context, ArrayList<ChatAppBean> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        if (data != null) {
            this.mDdata = data;
        }
    }

    @Override
    public int getCount() {
        return mDdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mDdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_keyboard_app, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ChatAppBean appBean = mDdata.get(position);
        if (appBean != null) {
            viewHolder.iv_icon.setBackgroundResource(appBean.getIcon());
            viewHolder.tv_name.setText(appBean.getFuncName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext,appBean.getFuncName(), Toast.LENGTH_SHORT).show();
                    switch (position) {
                        case 0://图片
                            loadPicture();
                            break;
                        case 1://拍照
                            loadPhoto();
                            break;
                    }
                }
            });
        }
        return convertView;
    }

    private void loadPicture() {
        MyGallerFinal aFinal = new MyGallerFinal();
        GalleryFinal.init(aFinal.getCoreConfig(mContext));
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(false)//开启编辑功能
                .setEnableCrop(false)//开启裁剪功能
                .setEnableCamera(false)
                .build();
        GalleryFinal.openGallerySingle(1001, functionConfig, mOnHanlderResultCallback);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                PhotoInfo photoInfo = resultList.get(0);
//                Bitmap bitmap = BitmapFactory.decodeFile(photoInfo.getPhotoPath());//图片文件转为Bitmap对象
                EventBus.getDefault().post(new SendImageEvent(photoInfo.getPhotoPath()));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };

    private void loadPhoto() {
        MyGallerFinal aFinal = new MyGallerFinal();
        GalleryFinal.init(aFinal.getCoreConfig(mContext));
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(false)//开启编辑功能
                .setEnableCrop(false)//开启裁剪功能
                .setEnableRotate(false)//开启旋转功能
                .setEnableCamera(false)//开启相机功能
                .setEnablePreview(true)//开启预览功能
//                .setSelected(imageList)//添加已选列表,只是在列表中默认呗选中不会过滤图片
                .build();
        GalleryFinal.openCamera(1003, functionConfig, mOnHanlderResultCallback2);
    }

    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback2 = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                PhotoInfo photoInfo = resultList.get(0);
//                Bitmap bitmap = BitmapFactory.decodeFile(photoInfo.getPhotoPath());//图片文件转为Bitmap对象
                EventBus.getDefault().post(new SendImageEvent(photoInfo.getPhotoPath()));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }
}