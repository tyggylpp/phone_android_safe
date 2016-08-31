package com.dragon.phone_mobilesafe.activity;

import android.app.Activity;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.adapter.MyBaseAdapter;
import com.dragon.phone_mobilesafe.bean.BlackNumberInfo;
import com.dragon.phone_mobilesafe.db.ado.BlackNumberDBOpenHelper;
import com.dragon.phone_mobilesafe.db.ado.BlackNumberDao;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Handler;


public class CallSafeActivity extends Activity {

    private ListView list;
    private BlackNumberDao dao;
    List<BlackNumberInfo> blackNumberinfos;
    private LinearLayout ll_pb;

    private EditText ed_Page;
    private int pageSize = 20;
    private int mCurrentPageNumber = 0;
    private int mTotalPage;
    private TextView tv_page;
    CallSafeAdapter adapter;

    //        Random random=new Random();
//        for (int i = 0; i < 200; i++) {
//            long number= 13000000000l+i;
//            dao.add(""+number,String.valueOf(random.nextInt(3)+1));
//        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_safe);
        initUi();
        initData();
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            ll_pb.setVisibility(View.INVISIBLE);
          adapter = new CallSafeAdapter(blackNumberinfos, CallSafeActivity.this);
            list.setAdapter(adapter);
        }
    };

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                dao = new BlackNumberDao(CallSafeActivity.this);

                int total = dao.getTotalNumber();
                mTotalPage = total / pageSize;

                blackNumberinfos = dao.findPar(mCurrentPageNumber, pageSize);
                // blackNumberinfos = dao.findAll();
                handler.sendEmptyMessage(0);


            }
        }.start();

    }

    private void initUi() {
        ll_pb = (LinearLayout) findViewById(R.id.ll_pb);
        ll_pb.setVisibility(View.VISIBLE);

        list = (ListView) findViewById(R.id.list_view);
        ed_Page = (EditText) findViewById(R.id.txtPage);
        tv_page = (TextView) findViewById(R.id.tv_Page);



    }

    public void prePage(View view) {
        if (mCurrentPageNumber <= 0) {
            Toast.makeText(this, "the first page", Toast.LENGTH_SHORT).show();
        } else {
            mCurrentPageNumber--;
            ed_Page.setText("" + mCurrentPageNumber);
            tv_page.setText("" + (mCurrentPageNumber) + "/" + (mTotalPage));
            initData();
        }
    }

    public void nextPage(View view) {
        if (mCurrentPageNumber >= mTotalPage) {
            Toast.makeText(this, "the last page", Toast.LENGTH_SHORT).show();
        } else {
            mCurrentPageNumber++;


            ed_Page.setText(String.valueOf(mCurrentPageNumber));


            tv_page.setText("" + (mCurrentPageNumber) + "/" + (mTotalPage));
            initData();
        }

    }

    public void jumpPage(View view) {
        String page = ed_Page.getText().toString().trim();
        if (TextUtils.isEmpty(page)) {
            Toast.makeText(this, "input correct number ", Toast.LENGTH_SHORT).show();

        } else {
            int number = Integer.parseInt(page);
            if (number >= 0 && number <= mTotalPage) {
                mCurrentPageNumber = number;
                tv_page.setText(mCurrentPageNumber+"/"+mTotalPage);
                initData();
            } else {
                Toast.makeText(this, "input correct number ", Toast.LENGTH_SHORT).show();
            }
        }


        ed_Page.setText("" + mCurrentPageNumber + 1);
        tv_page.setText("" + (mCurrentPageNumber + 1) + "/" + (mTotalPage + 1));
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
                convertView = View.inflate(CallSafeActivity.this, R.layout.item_call_safe, null);
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
                        Toast.makeText(CallSafeActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        lists.remove(info);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(CallSafeActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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
