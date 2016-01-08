package jacketjie.common.libray;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.custom.view.expandablelayout.ExpandableRelativeLayout;
import jacketjie.common.libray.custom.view.pulltorefresh.PullRefreshLayout;
import jacketjie.common.libray.network.NetworkAsyncTask;
import jacketjie.common.libray.network.volley.VolleyHelper;
import jacketjie.common.libray.network.volley.VolleyResponseListener;
import jacketjie.common.libray.network.volley.bean.Competition;
import jacketjie.common.libray.view.AnimationTestActivity;

/**
 * Created by Administrator on 2015/12/30.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private Button btn1, btn2, btn3;
    private String[] selects = {"优美散文", "短篇小说", "美文日赏", "青春碎碎念", "左岸阅读", "慢文艺", "诗歌精选", "经典语录", "陪你颠沛流离", "花边阅读", "终点书栈", "译言", "佳人阅读", "美文社", "悦旅行", "读美文", "青年周摘", "二更食堂", "不止读书", "读者投稿"};
    private List<String> mDatas;
    private FrameLayout tabs;
    private ExpandableRelativeLayout expandableLayout;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<String> data1, data2, data3;

    private int currentSelectPos;
    private PullRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_layout);
        initViews();
        initDatas();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("全部训练");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.id_listview);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        tabs = (FrameLayout) findViewById(R.id.id_tabs);
        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getApplicationContext(), CustomTestActivity.class));
//                VolleyHelper.RequestGetString(getApplicationContext(), "http://www.baidu.com/", "baidu", new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("onResponse", response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("onResponse", error.getMessage());
//                    }
//                });
                if (position % 2 == 0){
                    VolleyHelper.RequestGetString(getApplicationContext(), "http://www.baidu.com/", null, "baidu", new VolleyResponseListener<String>() {
                        @Override
                        public void requestSuccess(String data) {
                            Log.d("response",data);
                        }

                        @Override
                        public void requestError(VolleyError error) {
                            Log.d("VolleyError",error.getMessage());
                        }
                    });
                }else {
                    test();
                }
            }
        });

        refreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                test();
            }
        });
    }

    private void test(){
//        VolleyHelper.RequestGetBean(this, Competition.class, "http://www.siyslchina.org/api/v1.0/competition/list", null, new Response.Listener<Competition>() {
//            @Override
//            public void onResponse(Competition response) {
//                Log.d("re", response.toString());
//                refreshLayout.setRefreshing(false);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                refreshLayout.setRefreshing(false);
//            }
//        });
        VolleyHelper.RequestGetBean(this, Competition.class, "http://www.siyslchina.org/api/v1.0/competition/list", null, new VolleyResponseListener<Competition>() {
            @Override
            public void requestSuccess(Competition data) {
                Log.d("re", data.toString());
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void requestError(VolleyError error) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initDatas() {
        List<Bean> mContents = new ArrayList<Bean>();
        for (int i = 0; i < 10; i++) {
            Bean bean = new Bean();
            bean.setTitle("腹肌撕裂者");
            bean.setSubTitle("Ab Ripper");
            bean.setStatus("最新");
            bean.setDuration("共七天");
            bean.setCount("2440223人在练");
            bean.setCategory("腹部");
            mContents.add(bean);
        }
        MyAdapter myAdapter = new MyAdapter(this, mContents, R.layout.list_item);
        listView.setAdapter(myAdapter);
        mDatas = new ArrayList<String>();
        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        for (int i = 0; i < selects.length; i++) {
            switch (i % 3) {
                case 0:
                    data1.add(selects[i]);
                    break;
                case 1:
                    data2.add(selects[i]);
                    break;
                case 2:
                    data3.add(selects[i]);
                    break;
            }
        }
        mDatas.addAll(data1);
        gridViewAdapter = new GridViewAdapter(mDatas);
        gridView.setAdapter(gridViewAdapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
//                setPop();
                showOrHidden(0);
                break;
            case R.id.btn2:
//                listView.btn2
                showOrHidden(1);
                break;
            case R.id.btn3:
                showOrHidden(2);
//                if (expandableLayout.isExpanded()){
//                    expandableLayout.collapse();
//                }
//                mDatas.clear();
//                mDatas.addAll(data3);
//                gridViewAdapter.notifyDataSetChanged();
//                expandableLayout.toggle();
                break;
        }
    }

    private void showOrHidden(final int i) {
        if (i == currentSelectPos) {
            expandableLayout.toggle();
            return;
        }
        Message msg = Message.obtain();
        msg.what = 0x123;
        msg.arg1 = i;
        if (handler.hasMessages(0x123)) {
            handler.removeMessages(0x123);
        }
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
            handler.sendMessageDelayed(msg, 260);
        } else {
            handler.sendMessage(msg);
        }

        currentSelectPos = i;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                int eX = (int) ev.getX();
               Rect rect = new Rect();
                expandableLayout.getLocalVisibleRect(rect);
                int[]position = new int[2];
                expandableLayout.getLocationOnScreen(position);
                rect.left = rect.left + position[0];
                rect.right = rect.right + position[0];
                rect.top = rect.top + position[1];
                rect.bottom = rect.bottom + position[1];
                int eY = (int) ev.getY();
                if (!rect.contains(eX, eY) && expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch (id){
           case R.id.action_material:
               refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
               break;
           case R.id.action_circles:
               refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
               break;
           case R.id.action_water_drop:
               refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
               break;
           case R.id.action_ring:
               refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
               break;
           case R.id.action_smartisan:
               refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);

               break;
       }


        return super.onOptionsItemSelected(item);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                int i = msg.arg1;
                mDatas.clear();
                switch (i) {
                    case 0:
                        mDatas.addAll(data1);
                        break;
                    case 1:
                        mDatas.addAll(data2);
                        break;
                    case 2:
                        mDatas.addAll(data3);
                        break;
                }
                gridViewAdapter.notifyDataSetChanged();
                expandableLayout.expand();
            }
        }
    };

    class TestAsyncTask extends NetworkAsyncTask<String, Void, String> {

        public TestAsyncTask(Context context, ViewGroup group, boolean showDialog) {
            super(context, group, showDialog);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "loading completed", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }

    class MyAdapter extends CommonAdapter<Bean> {

        public MyAdapter(Context context, List<Bean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bean item) {
            helper.setText(R.id.id_title, item.getTitle());
            helper.setText(R.id.id_subtitle, item.getSubTitle());
            helper.setText(R.id.id_duration, item.getDuration());
            helper.setText(R.id.id_status, item.getStatus());
            helper.setText(R.id.id_count, item.getCount());
            helper.setText(R.id.id_categroy, item.getCategory());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0x123);
    }

    class GridViewAdapter extends BaseAdapter {
        private List<String> mDatas;
        private LayoutInflater inflater;

        public GridViewAdapter(List<String> mDatas) {
            this.mDatas = mDatas;
            inflater = LayoutInflater.from(TestActivity.this);
        }

        @Override
        public int getCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas == null ? null : mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_item, parent, false);
                holder = new ViewHolder();
                holder.item = (Button) convertView.findViewById(R.id.grid_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.item.setText(mDatas.get(position));
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),String.format("第%d行",position),Toast.LENGTH_SHORT).show();
                    String title = mDatas.get(position);
                    Intent intent = new Intent(getApplicationContext(), AnimationTestActivity.class);
                    intent.putExtra("title",title);
                    startActivity(intent);
                    expandableLayout.toggle();
                }
            });
            return convertView;
        }

        class ViewHolder {
            Button item;
        }
    }

    class Bean implements Serializable {
        private String title;
        private String subTitle;
        private String status;
        private String duration;
        private String count;
        private String category;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
