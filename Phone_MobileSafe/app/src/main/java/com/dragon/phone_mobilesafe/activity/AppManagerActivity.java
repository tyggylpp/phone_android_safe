package com.dragon.phone_mobilesafe.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.bean.AppInfo;
import com.dragon.phone_mobilesafe.engine.AppInfos;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnScroll;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AppManagerActivity extends Activity {

    private List<AppInfo> systemAppInfos;
    private List<AppInfo> userAppInfos;
    private List<AppInfo> appInfos;
    //ViewUtil 方便加载控件
    @ViewInject(R.id.app_list_view)
    private ListView list_view;
    @ViewInject(R.id.tv_rom)
    private TextView tv_rom;
    @ViewInject(R.id.tv_sd)
    private TextView tv_sd;
    @ViewInject(R.id.tv_app)
    private TextView tv_app;
    private PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initData();
    }
    private  class  AppManagerAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return appInfos.size();
        }

        @Override
        public Object getItem(int i) {
            if(i==0 || i==userAppInfos.size()+1)return null;
            AppInfo info=null;
            if(i<userAppInfos.size()+1)
            {
                info=userAppInfos.get(i-1);
            }else if(i>userAppInfos.size()+1)
            {
                info=systemAppInfos.get(i-(userAppInfos.size()+1+1));
            }
            return info;
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View viewcontent, ViewGroup viewGroup) {

              if(i==0)
              {
                  TextView textView=new TextView(AppManagerActivity.this);
                  textView.setTextColor(Color.WHITE);
                  textView.setBackgroundColor(Color.GRAY);
                  textView.setText("用户程序("+userAppInfos.size()+")");
                  return textView;
              }else if(i==(userAppInfos.size()+1))
              {
                  TextView textView=new TextView(AppManagerActivity.this);
                  textView.setTextColor(Color.WHITE);
                  textView.setBackgroundColor(Color.GRAY);
                  textView.setText("系统程序("+systemAppInfos.size()+")");
                  return textView;
              }

            AppInfo info=null;
            if(i<userAppInfos.size()+1)
            {
                info=userAppInfos.get(i-1);
            }else if(i>userAppInfos.size()+1)
            {
                info=systemAppInfos.get(i-(userAppInfos.size()+1+1));
            }

        View view;ViewHolder viewHolder;
            //如果viewcontent 不加上 instanceof LinearLayout 限制 会导致 两个特殊条目复用取不到 ITEM内的IMAGEVIEW TextView
            if(viewcontent!=null && viewcontent instanceof LinearLayout)
            {
            view =viewcontent;
            viewHolder= (ViewHolder) view.getTag();
            }else
            {
                view =View.inflate(AppManagerActivity.this,R.layout.item_app_manager,null);
                viewHolder=new ViewHolder();
                viewHolder.icon= (ImageView) view.findViewById(R.id.iv_icon);
                viewHolder.name= (TextView) view.findViewById(R.id.tv_name);
                viewHolder.size= (TextView) view.findViewById(R.id.tv_apkSize);
                viewHolder.location= (TextView) view.findViewById(R.id.tv_location);
                view.setTag(viewHolder);

            }

            viewHolder.icon.setImageDrawable(info.getIcon());
            viewHolder.name.setText(info.getApkName());
            viewHolder.size.setText(Formatter.formatFileSize(AppManagerActivity.this,info.getApkSize()));
            viewHolder.location.setText(info.isRom()?"手机存储":"SD卡");

            return view;
        }

    }
    static class ViewHolder
    {
        ImageView icon;
        TextView name;
        TextView size;
        TextView location;

    }

    private android.os.Handler handler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            AppManagerAdapter appAdapter=new AppManagerAdapter();
            list_view.setAdapter(appAdapter);
        }
    };

    private void initData() {
        new Thread()
        {
            @Override
            public void run() {
               appInfos = AppInfos.getAppInfos(AppManagerActivity.this);
                userAppInfos = new ArrayList<AppInfo>();
                systemAppInfos = new ArrayList<AppInfo>();
                for (int i = 0; i < appInfos.size(); i++) {
                    AppInfo info=appInfos.get(i);
                    if(info.isUserApp())userAppInfos.add(info);
                    else systemAppInfos.add(info);

                }
                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initUi() {
        setContentView(R.layout.activity_app_manager);
        ViewUtils.inject(this);
        long freerom = Environment.getDataDirectory().getFreeSpace();
        long freesd = Environment.getExternalStorageDirectory().getFreeSpace();
        tv_rom.setText("rom free:"+Formatter.formatFileSize(this,freerom));
        tv_sd.setText("sd free:"+Formatter.formatFileSize(this,freesd));
  list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
          popupDissmiss();
        if(userAppInfos!=null&&systemAppInfos!=null)
        {
            if(firstVisibleItem>userAppInfos.size()+1)
            {
                tv_app.setText("系统程序("+systemAppInfos.size()+")");
            }else
            {
                tv_app.setText("用户程序("+userAppInfos.size()+")");

            }
        }

      }

  });
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = list_view.getItemAtPosition(position);
                if(obj!=null&& obj instanceof AppInfo)
                {
                    popupDissmiss();

                    View contentView =View.inflate(AppManagerActivity.this,R.layout.item_popup,null);
                    popup = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    //popup使用动画需要设置背景，使用透明的背景不会覆盖原始的背景
                    popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    int[] location=new int[2];
                    view.getLocationInWindow(location);
                    popup.showAtLocation(contentView, Gravity.START+Gravity.TOP,70,location[1]);
                    ScaleAnimation sa = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(300);
                  contentView.setAnimation(sa);


                }
            }
        });

    }

    private void popupDissmiss() {
        if(popup!=null&&popup.isShowing())
        {
            popup.dismiss();
            popup=null;

        }
    }

    @Override
    protected void onDestroy() {
        popupDissmiss();//后退销毁 不销毁POPUP会报错误信息
        super.onDestroy();
    }
}
