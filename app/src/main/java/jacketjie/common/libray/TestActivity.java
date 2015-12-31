package jacketjie.common.libray;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.network.NetworkAsyncTask;

/**
 * Created by Administrator on 2015/12/30.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private Button btn1,btn2,btn3;
    private String[]selects = {"优美散文", "短篇小说", "美文日赏", "青春碎碎念", "左岸阅读", "慢文艺", "诗歌精选", "经典语录", "陪你颠沛流离", "花边阅读", "终点书栈", "译言", "佳人阅读", "美文社", "悦旅行", "读美文", "青年周摘", "二更食堂", "不止读书", "读者投稿"};
    private FrameLayout tabs;
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
    }

    private void initDatas() {
        List<Bean> mDatas = new ArrayList<Bean>();
        for (int i = 0 ; i < 10 ; i++){
            Bean bean = new Bean();
            bean.setTitle("腹肌撕裂者");
            bean.setSubTitle("Ab Ripper");
            bean.setStatus("最新");
            bean.setDuration("共七天");
            bean.setCount("2440223人在练");
            bean.setCategory("腹部");
            mDatas.add(bean);
        }
        MyAdapter myAdapter = new MyAdapter(this,mDatas,R.layout.list_item);
        listView.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn1:

                break;
            case R.id.btn2:

                break;
            case R.id.btn3:

                break;
        }
    }

    private void showMenu(){
        ListView list = new ListView(this);
        list.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selects);
        list.setAdapter(mAdapter);
    }


    class TestAsyncTask extends NetworkAsyncTask<String,Void,String> {

        public TestAsyncTask(Context context, ViewGroup group, boolean showDialog) {
            super(context, group, showDialog);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "loading completed", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }

    class MyAdapter extends CommonAdapter<Bean>{

        public MyAdapter(Context context, List<Bean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bean item) {
            helper.setText(R.id.id_title,item.getTitle());
            helper.setText(R.id.id_subtitle,item.getSubTitle());
            helper.setText(R.id.id_duration,item.getDuration());
            helper.setText(R.id.id_status,item.getStatus());
            helper.setText(R.id.id_count,item.getCount());
            helper.setText(R.id.id_categroy,item.getCategory());
        }
    }

    class Bean implements Serializable{
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
