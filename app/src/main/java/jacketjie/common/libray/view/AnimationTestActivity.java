package jacketjie.common.libray.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import jacketjie.common.libray.R;
import jacketjie.common.libray.TestActivity;
import jacketjie.common.libray.custom.view.SelectorLayout;
import jacketjie.common.libray.custom.view.expandablelayout.Utils;
import jacketjie.common.libray.others.ScreenUtils;

/**
 * Created by Administrator on 2016/1/7.
 */
public class AnimationTestActivity extends AppCompatActivity {
    private Button expandableBtn;
    private Button drawableBtn;
    private RadioButton verticalBtn;
    private RadioButton horizontalBtn;
    private RadioGroup group;
    private ListView listView;
    private int screenWidth;
    private String[] mDatas = {"优美散文", "短篇小说", "美文日赏", "青春碎碎念", "左岸阅读", "慢文艺", "诗歌精选", "经典语录"};
    private boolean isFirst;
    private boolean isExpand;
    private boolean isAnimating;

    private SelectorLayout selectorLayout;
    private GridView gridView;
    private ImageView bg;
    private int selectedPos;
    private GridViewAdapter gridViewAdapter;
    private RadioButton top;
    private RadioButton left;
    private RadioButton bottom;
    private RadioButton right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_layout);
        screenWidth = ScreenUtils.getScreenWidth(this);
        EventBus.getDefault().register(this);

        initViews();
        initData();
        setEventListener();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Animation");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        group = (RadioGroup) findViewById(R.id.id_top);
        expandableBtn = (Button) findViewById(R.id.id_animation);
        drawableBtn = (Button) findViewById(R.id.id_location);
        verticalBtn = (RadioButton) findViewById(R.id.id_vertical);
        horizontalBtn = (RadioButton) findViewById(R.id.id_horizontal);
//        listView = (ListView) findViewById(R.id.id_anim_list);
        selectorLayout = (SelectorLayout) findViewById(R.id.id_selector);
        gridView = (GridView) findViewById(R.id.id_gridview);

        top = (RadioButton) findViewById(R.id.id_vertical);
        left = (RadioButton) findViewById(R.id.id_horizontal);
        bottom = (RadioButton) findViewById(R.id.id_bottom);
        right = (RadioButton) findViewById(R.id.id_right);
        listView = (ListView) findViewById(R.id.id_listview);

        bg = (ImageView) findViewById(R.id.id_bg);
        if (selectorLayout.getDirectionIndex() == 0) {
            top.setChecked(true);
        }
        if (selectorLayout.getDirectionIndex() == 1) {
            left.setChecked(true);
        }

    }

    private void initData() {

        List<TestActivity.Bean> mContents = new ArrayList<TestActivity.Bean>();
        for (int i = 0; i < 10; i++) {
            TestActivity.Bean bean = new TestActivity.Bean();
            bean.setTitle("腹肌撕裂者");
            bean.setSubTitle("Ab Ripper");
            bean.setStatus("最新");
            bean.setDuration("共七天");
            bean.setCount("2440223人在练");
            bean.setCategory("腹部");
            mContents.add(bean);
        }

        TestActivity.MyAdapter myAdapter = new TestActivity.MyAdapter(this, mContents, R.layout.list_item);
        listView.setAdapter(myAdapter);

    }

    private void setEventListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_vertical:
                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Top);
                        break;
                    case R.id.id_horizontal:
                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Left);
                        break;
                    case R.id.id_bottom:
                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Bottom);
                        break;
                    case R.id.id_right:
                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Right);
                        break;
                }
            }
        });


        expandableBtn.setOnClickListener(new View.OnClickListener() {
            int listViewHeight;

            @Override
            public void onClick(final View v) {
                gridViewAdapter = new GridViewAdapter(Arrays.asList(mDatas));
                gridView.setAdapter(gridViewAdapter);
                gridViewAdapter.notifyDataSetChanged();
                selectorLayout.requestLayoutByObject(false);
                selectorLayout.setAnimationStyle(SelectorLayout.Styleable.Expanable);
                displayOrHidden();
            }
        });

        drawableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                gridViewAdapter = new GridViewAdapter(Arrays.asList(mDatas));
                gridView.setAdapter(gridViewAdapter);
                gridViewAdapter.notifyDataSetChanged();
                selectorLayout.requestLayoutByObject(false);
                selectorLayout.setAnimationStyle(SelectorLayout.Styleable.Drawable);
                displayOrHidden();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditTextActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onEventMainThread(EventMessage message){
        if (message.isExpandable()){
            selectedPos = message.getCount();
            gridViewAdapter.notifyDataSetChanged();
            selectorLayout.displayOrHidden();
        }
    }
    public void onEventMainThread(Integer loa){
        switch (loa){
            case 0:
                top.setChecked(true);
                break;
            case 1:
                left.setChecked(true);

                break;
            case 2:
                bottom.setChecked(true);

                break;
            case 3:
                right.setChecked(true);

                break;
            default:
                new Exception("arrayIndexOutOfBoundsException");
                break;
        }
    }

    private void setBgAnimation(int duration, final int startAlpha, final int endAlpaha) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startAlpha, endAlpaha);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                String alpha = Integer.toHexString(value);
                String color = String.format("#%s000000", alpha.length() < 2 ? String.format("0%s", alpha) : alpha);
//                Log.d("SelectorLayout", "color:" + color);
                bg.setBackgroundColor(Color.parseColor(color));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (startAlpha == 0) {
                    bg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (endAlpaha == 0) {
                    bg.setVisibility(View.GONE);
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置动画
     */
    private void displayOrHidden() {
        selectorLayout.displayOrHidden();
        if (selectorLayout.isExpand()) {
            setBgAnimation(selectorLayout.getDuration(), 0, 104);
        } else {
            setBgAnimation(selectorLayout.getDuration(), 104, 0);
        }
    }

    class GridViewAdapter extends BaseAdapter {
        private List<String> mDatas;
        private LayoutInflater inflater;

        public GridViewAdapter(List<String> mDatas) {
            this.mDatas = mDatas;
            inflater = LayoutInflater.from(AnimationTestActivity.this);
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
            final ViewHolder holder;
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
                    displayOrHidden();
                    selectedPos = position;
                    holder.item.setBackgroundResource(R.drawable.grid_item_selected);
                    holder.item.setTextColor(Color.WHITE);
                    Toast.makeText(getApplicationContext(), mDatas.get(position), Toast.LENGTH_SHORT).show();
                    GridViewAdapter.this.notifyDataSetChanged();
                }
            });
            if (selectedPos == position) {
                holder.item.setTextColor(Color.WHITE);
                holder.item.setBackgroundResource(R.drawable.grid_item_selected);
            } else {
                holder.item.setBackgroundResource(R.drawable.grid_item_default);
                holder.item.setTextColor(Color.parseColor("#333333"));
            }
            return convertView;
        }

        class ViewHolder {
            Button item;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int eX = (int) ev.getX();
                int eY = (int) ev.getY();
                Rect rect = new Rect();
                selectorLayout.getLocalVisibleRect(rect);
                int[] position = new int[2];
                selectorLayout.getLocationOnScreen(position);
                rect.left = rect.left + position[0];
                rect.right = rect.right + position[0];
                rect.top = rect.top + position[1];
                rect.bottom = rect.bottom + position[1];
                if (selectorLayout.isExpand() && eY > rect.bottom) {
                    displayOrHidden();
                    return true;
                }
//                if (!rect.contains(eX, eY) && selectorLayout.isExpand()) {
//                    displayOrHidden();
//                    return true;
//                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.animation_menu, menu);
        switch (selectorLayout.getInterpolatorStyle()) {
            case Utils.ACCELERATE_DECELERATE_INTERPOLATOR:
                menu.findItem(R.id.action_accelerateDecelerate).setChecked(true);
                break;
            case Utils.ACCELERATE_INTERPOLATOR:
                menu.findItem(R.id.action_accelerate).setChecked(true);
                break;
            case Utils.ANTICIPATE_INTERPOLATOR:
                menu.findItem(R.id.action_anticipate).setChecked(true);
                break;
            case Utils.ANTICIPATE_OVERSHOOT_INTERPOLATOR:
                menu.findItem(R.id.action_anticipateOvershoot).setChecked(true);
                break;
            case Utils.DECELERATE_INTERPOLATOR:
                menu.findItem(R.id.action_decelerate).setChecked(true);
                break;
            case Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR:
                menu.findItem(R.id.action_fastOutLinearIn).setChecked(true);
                break;
            case Utils.FAST_OUT_SLOW_IN_INTERPOLATOR:
                menu.findItem(R.id.action_fastOutSlowIn).setChecked(true);
                break;
            case Utils.LINEAR_INTERPOLATOR:
                menu.findItem(R.id.action_linear).setChecked(true);
                break;
            case Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR:
                menu.findItem(R.id.action_linearOutSlowIn).setChecked(true);
                break;
            case Utils.OVERSHOOT_INTERPOLATOR:
                menu.findItem(R.id.action_overshoot).setChecked(true);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accelerateDecelerate:
                selectorLayout.setInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR);
                break;
            case R.id.action_accelerate:
                selectorLayout.setInterpolator(Utils.ACCELERATE_INTERPOLATOR);
                break;
            case R.id.action_anticipate:
                selectorLayout.setInterpolator(Utils.ANTICIPATE_INTERPOLATOR);
                break;
            case R.id.action_anticipateOvershoot:
                selectorLayout.setInterpolator(Utils.ANTICIPATE_OVERSHOOT_INTERPOLATOR);
                break;
            case R.id.action_decelerate:
                selectorLayout.setInterpolator(Utils.DECELERATE_INTERPOLATOR);

                break;
            case R.id.action_fastOutLinearIn:
                selectorLayout.setInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR);

                break;
            case R.id.action_fastOutSlowIn:
                selectorLayout.setInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR);

                break;
            case R.id.action_linear:
                selectorLayout.setInterpolator(Utils.LINEAR_INTERPOLATOR);

                break;
            case R.id.action_linearOutSlowIn:
                selectorLayout.setInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);

                break;
            case R.id.action_overshoot:
                selectorLayout.setInterpolator(Utils.OVERSHOOT_INTERPOLATOR);
                break;
        }
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

