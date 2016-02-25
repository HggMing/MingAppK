package com.study.mingappk.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.mingappk.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
* ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果；
* 既支持自动轮播页面也支持手势滑动切换页面
*
*
*/

public class SlideShowView extends FrameLayout {


    static ArrayList<String> strId;
    static ArrayList<String> text;


    //自动轮播启用开关
    private static boolean isAutoPlay = true;

    //自定义轮播图的资源
    private String[] imageUrls;
    //放轮播图片的ImageView 的list
    private static List<ImageView> imageViewsList;
    private static List<TextView> textViews;
    //放圆点的View的list
    private static List<View> dotViewsList;

    private static ViewPager viewPager;
    //当前轮播页
    private static int currentItem  = 0;
    //定时任务
    private static ScheduledExecutorService scheduledExecutorService;

    private Context context;
    static Boolean stopthear=false;

    //Handler
    private static Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Log.i("currentItem","currentItem"+currentItem);
            viewPager.setCurrentItem(currentItem);
        }
    };

    public SlideShowView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
       /* initImageLoader(context);*/

        initData();
        if(isAutoPlay){
            startPlay();
        }
    }



    /**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);//换图时间间隔10s
    }
    /**
     * 停止轮播图切换
     */
    private static void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 初始化相关Data
     */
    private void initData(){
        System.gc();
        textViews=new ArrayList<TextView>();
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        // 异步任务获取图片
        Asyn async=new Asyn();

        text=new ArrayList<String>();
        String result=null;
        try {
            //获取数据
            result=async.execute("").get();
            //本应该根据网络请求获得的图片数量写。这就不做网络请求了，直接写3张图片和百度上的图片url
            imageUrls=new String[3];
            imageUrls[0]="http://i2.topit.me/2/83/e1/1131356235bfce1832o.jpg";
            imageUrls[1]="http://imgsrc.baidu.com/forum/pic/item/3ac79f3df8dcd10036d1faba728b4710b8122fdf.jpg";
            imageUrls[2]="http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1206/18/c0/12043463_1339987117006.jpg";
            text.add("第一张图片");
            text.add("第二张图片：假如图片文字很长，假如图片文字很长，假如图片文字很长，假如图片文字很长。");
            text.add("第三张图片");
            if (imageUrls.length>0&&imageUrls[0].toString().length()>0){
                initUI(context);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
        if(imageUrls == null || imageUrls.length == 0)
            return;

        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view =  new ImageView(context);
            DownloadPic downloadpic=new DownloadPic();
            downloadpic.execute(imageUrls[i]);
            try {
               view.setImageBitmap(downloadpic.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);

            //放广告词放入textview，在将textview放入集合
            TextView textView=new TextView(context);
            textView.setText(text.get(i));
            textViews.add(textView);


            //热点个数添加
            ImageView dotView =  new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager_dot);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 填充ViewPager的页面适配器
     *
     */
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub

        }

        @Override
        public Object instantiateItem(View container, int position) {

            if (((ViewPager) container).getChildCount() == imageViewsList.size()){
                RelativeLayout l2= (RelativeLayout) imageViewsList.get(position % imageViewsList.size()).getParent();
                l2.removeAllViewsInLayout();
                ((ViewPager) container).removeView(l2);
            }
            ImageView imageView = imageViewsList.get(position % imageViewsList.size());
            TextView textView=textViews.get(position % imageViewsList.size());

            RelativeLayout l1=new RelativeLayout(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            //广告语的位置控制
            int i= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15 , getResources().getDisplayMetrics());
            int a= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5 , getResources().getDisplayMetrics());
           // params.setMargins(i,0,0,a);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textView.setLayoutParams(params);
            textView.setTextColor(Color.WHITE);//文字颜色
            textView.setTextSize(16);//文字大小
            textView.setBackgroundColor(getResources().getColor(R.color.transparent));//文字背景颜色
            textView.setPadding(i, a, i*4, a);//文字内间距
            textView.setSingleLine(true);//设置单行
            textView.setEllipsize(TextUtils.TruncateAt.END);//单行显示不全，末尾添加省略号
            imageView.setLayoutParams(params1);
            l1.addView(imageView);
            l1.addView(textView);
            ((ViewPager) container).addView(l1, 0);
            return l1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            //如果是一张图片的时候就不让他最大化
            if (imageViewsList.size() == 1) {
                return imageViewsList.size();
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }
        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕

                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub

            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos && pos<dotViewsList.size()){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.sign_circle_white);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.sign_circle_alpha);
                }
                if (pos>=dotViewsList.size()){
                    if (i==(pos%dotViewsList.size())){
                        ((View)dotViewsList.get(pos%dotViewsList.size())).setBackgroundResource(R.drawable.sign_circle_white);
                    }else {
                        ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.sign_circle_alpha);
                    }
                    }
                }

        }

    }

    /**
     *执行轮播图切换任务
     *
     */
    static Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (!stopthear){
                synchronized (viewPager) {
                    currentItem = (currentItem+1)/*%imageViewsList.size()*/;
                    handler.obtainMessage().sendToTarget();

                }}
        }
    };
    public static void stop(boolean bln){
        stopthear=bln;
    }
    /**
     * 销毁ImageView资源，回收内存
     *
     */
    public static void destoryBitmaps() {
        /*stopPlay();

        stopthear=true;*/
        scheduledExecutorService.shutdown();
        handler.removeCallbacks(runnable);
        currentItem=0;
        /*stopPlay();*/
        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
            System.gc();

        }
        text.clear();
        textViews.clear();
        viewPager.removeAllViews();
        System.gc();
        System.runFinalization();
    }

    /**
     * 异步任务,获取数据
     *
     */

    class Asyn extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                //这里进行网络请求 返回图片参数。
                String result=null;

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result!=null) {


                }
            }


    }

    class DownloadPic extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpClient httpClient=new DefaultHttpClient();
            if (params[0]!=null) {

                HttpGet httpGet = new HttpGet(params[0]);
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        InputStream inputStream = httpResponse.getEntity().getContent();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();

                        return bitmap;
                    }else {
                        return null;
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {



            super.onPostExecute(result);
       }
    }


}
