package com.dragon.phone_mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.adapter.MyBaseAdapter;
import com.dragon.phone_mobilesafe.bean.BlackNumberInfo;
import com.dragon.phone_mobilesafe.db.ado.BlackNumberDao;

import java.util.List;


public class CallSafeActivity2 extends Activity {

    private ListView list;
    private BlackNumberDao dao;
    List<BlackNumberInfo> blackNumberinfos;
    private LinearLayout ll_pb;

    private EditText ed_Page;
    private int pageSize = 20;
    private int mstartIndex = 0;
    private int mTotalPage;
    private TextView tv_page;
    CallSafeAdapter adapter;
    private int total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_safe2);
        initUi();
        initData();
    }

    public void addBlackNumber(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
         final AlertDialog dialog=builder.create();

        View view_dialog=View.inflate(CallSafeActivity2.this,R.layout.layout_blacknumber_add,null);
        final Button btn_Ok=(Button)view_dialog.findViewById(R.id.btn_OK);
        final  Button btn_Cancel=(Button)view_dialog.findViewById(R.id.btn_Cancel);
        final EditText et_phone=(EditText)view_dialog.findViewById(R.id.et_blackNumber);
       final CheckBox cb_phone=(CheckBox)view_dialog.findViewById(R.id.cb_phone);
        final CheckBox cb_sms=(CheckBox)view_dialog.findViewById(R.id.cb_sms);

       btn_Ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String phone=et_phone.getText().toString().trim();

              if(TextUtils.isEmpty(phone))
              {
                  Toast.makeText(CallSafeActivity2.this,"请输入黑名单号码",Toast.LENGTH_SHORT);
                  dialog.dismiss();
                  return;
              }
               String mode="";
               boolean fphone=cb_phone.isChecked();boolean fsms=cb_sms.isChecked();
               if(fphone&&fsms)
               {
                mode="1";
               }else if(fphone)
               {
                   mode="2";
               }else if (fsms)
               {
                   mode="3";
               }else
               {
                   Toast.makeText(CallSafeActivity2.this,"拦截模式没有选择",Toast.LENGTH_SHORT).show();
                   return;
               }
               //将电话和拦截模式添加到数据库

              boolean flagadd= dao.add(phone,mode);
               if(flagadd)
               {
                   BlackNumberInfo info=new BlackNumberInfo();
                   info.setNumber(phone);
                   info.setMode(mode);

                   blackNumberinfos.add(0,info);//添加至数据中
                   if(adapter==null)
                   {
                       adapter = new CallSafeAdapter(blackNumberinfos, CallSafeActivity2.this);
                       list.setAdapter(adapter);
                   }else
                   {
                       adapter.notifyDataSetChanged();//适配器数据改变刷新界面
                   }
               }
               dialog.dismiss();





           }
       });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(view_dialog);
        dialog.show();



    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            ll_pb.setVisibility(View.INVISIBLE);
            if(adapter==null)
            {
                adapter = new CallSafeAdapter(blackNumberinfos, CallSafeActivity2.this);
                list.setAdapter(adapter);
            }
            else adapter.notifyDataSetChanged();

        }
    };

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                dao = new BlackNumberDao(CallSafeActivity2.this);

                total = dao.getTotalNumber();

                 if(blackNumberinfos==null) //判断是否初次添加否则追加内容
                blackNumberinfos = dao.findPar2(mstartIndex, pageSize);
                else blackNumberinfos.addAll(dao.findPar2(mstartIndex,pageSize));
                // blackNumberinfos = dao.findAll();

                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    private void initUi() {
        ll_pb = (LinearLayout) findViewById(R.id.ll_pb);
        ll_pb.setVisibility(View.VISIBLE);

        list = (ListView) findViewById(R.id.list_view);

        /*
        *AbsListView.OnScrollListener.SCROLL_STATE_IDLE 闲置状态
        *AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL 手指触摸的状态
        *AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_FLING 惯性
        * */
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        int lastposition = list.getLastVisiblePosition();

                        if(lastposition>=blackNumberinfos.size()-1)
                        {
                            mstartIndex += pageSize;

                            if(lastposition>total)
                            {
                                Toast.makeText(CallSafeActivity2.this,"没有数据加载了",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            initData();

                        }
                            break;

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }


        });

    }


    private class CallSafeAdapter extends MyBaseAdapter<BlackNumberInfo> {//抽取适配器

        public CallSafeAdapter(List lists, Context mcontext) {
            super(lists, mcontext);
        }
        //        @Override
//        public int getCount() {
//            return blackNumberinfos.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//           return blackNumberinfos.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
           /* //传统方式
            View view= View.inflate(CallSafeActivity.this,R.layout.item_call_safe,null);
            TextView tv_number=(TextView)view.findViewById(R.id.tv_number);
            TextView tv_mode=(TextView)view.findViewById(R.id.tv_mode);
            tv_number.setText(blackNumberinfos.get(i).getNumber());
            tv_mode.setText(blackNumberinfos.get(i).getMode());
            String mode=blackNumberinfos.get(i).getMode();
            if(mode.equals("1"))tv_mode.setText("全部拦截");
            else if(mode.equals("2"))tv_mode.setText("电话拦截");
            else tv_mode.setText("短信拦截");
            return view;
            */
            //google 优化
//            if(convertView==null)convertView=View.inflate(CallSafeActivity.this,R.layout.item_call_safe,null);
//            TextView tv_number=(TextView)convertView.findViewById(R.id.tv_number);
//            TextView tv_mode=(TextView)convertView.findViewById(R.id.tv_mode);
//            tv_number.setText(blackNumberinfos.get(i).getNumber());
//            tv_mode.setText(blackNumberinfos.get(i).getMode());
//            String mode=blackNumberinfos.get(i).getMode();
//            if(mode.equals("1"))tv_mode.setText("全部拦截");
//            else if(mode.equals("2"))tv_mode.setText("电话拦截");
//            else tv_mode.setText("短信拦截");
//            return convertView;

            // google butter
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(CallSafeActivity2.this, R.layout.item_call_safe, null);
                holder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
                holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_number.setText(lists.get(i).getNumber());
            String mode = lists.get(i).getMode();
            if (mode.equals("1")) holder.tv_mode.setText("电话+短信拦截");
            else if (mode.equals("2")) holder.tv_mode.setText("电话拦截");
            else holder.tv_mode.setText("短信拦截");
            final BlackNumberInfo info = lists.get(i);
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = info.getNumber();
                    boolean result = dao.delete(number);
                    if (result) {
                        Toast.makeText(CallSafeActivity2.this, "删除成功", Toast.LENGTH_SHORT).show();
                        lists.remove(info);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(CallSafeActivity2.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }

    }

    static class ViewHolder {
        TextView tv_number;
        TextView tv_mode;
        ImageView iv_delete;

    }
}
