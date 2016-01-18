package jacketjie.common.libray.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import jacketjie.common.libray.R;
import jacketjie.common.libray.TestActivity;
import jacketjie.common.libray.adapter.GridViewAdapter;
import jacketjie.common.libray.custom.view.SelectorLayout;
import jacketjie.common.libray.custom.view.animatedlayout.AnimatedLayoutListener;
import jacketjie.common.libray.custom.view.animatedlayout.DrawableLinearLayout;
import jacketjie.common.libray.custom.view.expandablelayout.Utils;
import jacketjie.common.libray.others.ScreenUtils;
import jacketjie.common.libray.others.ToastUtils;

/**
 * Created by Administrator on 2016/test_1/7.
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
    private String[] mDatas1 = {"读美文", "青年周摘", "二更食堂", "不止读书", "读者投稿"};
    private List<String> dataList;
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


    private DrawableLinearLayout drawableLinearLayout;
    private int lastClickPos = -1;
    private Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Animation");
        setSupportActionBar(toolbar);

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

        drawableLinearLayout = (DrawableLinearLayout) findViewById(R.id.id_drawable_layout);

        setZForLOLLIPOP();

    }
    private void setZForLOLLIPOP() {
        View top1 = findViewById(R.id.id_top1);
        View top2 = findViewById(R.id.id_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            top1.setTranslationZ(8);
            top2.setTranslationZ(8);
            float z = toolbar.getTranslationZ();
            Log.e("AnimationActivity","toolbar Z:"+z);
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
        dataList = new ArrayList<String>();
        gridViewAdapter = new GridViewAdapter(dataList,this);
        gridView.setAdapter(gridViewAdapter);
    }

    private void setEventListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_vertical:
//                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Top);
                        drawableLinearLayout.setDirection(AnimatedLayoutListener.Direction.Top);
                        break;
                    case R.id.id_horizontal:
                        drawableLinearLayout.setDirection(AnimatedLayoutListener.Direction.Left);
//                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Left);
                        break;
                    case R.id.id_bottom:
//                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Bottom);
                        break;
                    case R.id.id_right:
                        drawableLinearLayout.setDirection(AnimatedLayoutListener.Direction.Right);
//                        selectorLayout.setAnimationDirection(SelectorLayout.Direction.Right);
                        break;
                }
            }
        });


        expandableBtn.setOnClickListener(new View.OnClickListener() {
            int listViewHeight;

            @Override
            public void onClick(final View v) {
                setAnimByPos(0);
            }
        });

        drawableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setAnimByPos(1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewPagerBannerActivity.class);
                startActivity(intent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(getApplicationContext(),dataList.get(position));
                gridViewAdapter.updateSelect(position);
                displayOrHidden();
            }
        });
    }

    private void setAnimByPos(int pos){
        if (pos == lastClickPos){
            displayOrHidden();
        }else{
            if (drawableLinearLayout.isExpandableStatus()){
                drawableLinearLayout.toggle();
            }
            dataList.clear();
            switch (pos){
                case 0:
                    dataList.addAll(Arrays.asList(mDatas1));
                    break;
                case 1:
                    dataList.addAll(Arrays.asList(mDatas));
                    break;
            }
            gridViewAdapter.notifyDataSetChanged();
            drawableLinearLayout.requestLayoutByAnim();

            displayOrHidden();
            lastClickPos = pos;
        }
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
        if (!drawableLinearLayout.isExpandableStatus()) {
            setBgAnimation(drawableLinearLayout.getDuration(), 0, 104);
        } else {
            setBgAnimation(drawableLinearLayout.getDuration(), 104, 0);
        }
        drawableLinearLayout.displayOrHidden();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int eX = (int) ev.getX();
                int eY = (int) ev.getY();
                Rect rect = new Rect();
//                selectorLayout.getLocalVisibleRect(rect);
                drawableLinearLayout.getLocalVisibleRect(rect);
                int[] position = new int[2];
//                selectorLayout.getLocationOnScreen(position);
                drawableLinearLayout.getLocationOnScreen(position);
                rect.left = rect.left + position[0];
                rect.right = rect.right + position[0];
                rect.top = rect.top + position[1];
                rect.bottom = rect.bottom + position[1];
//                if (selectorLayout.isExpand() && eY > rect.bottom) {
//                    displayOrHidden();
//                    return true;
//                }
                if (drawableLinearLayout.isExpandableStatus() && eY > rect.bottom) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (drawableLinearLayout.isExpandableStatus()){
                displayOrHidden();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
//                selectorLayout.setInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR);
                break;
            case R.id.action_accelerate:
//                selectorLayout.setInterpolator(Utils.ACCELERATE_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.ACCELERATE_INTERPOLATOR);
                break;
            case R.id.action_anticipate:
//                selectorLayout.setInterpolator(Utils.ANTICIPATE_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.ANTICIPATE_INTERPOLATOR);
                break;
            case R.id.action_anticipateOvershoot:
//                selectorLayout.setInterpolator(Utils.ANTICIPATE_OVERSHOOT_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.ANTICIPATE_OVERSHOOT_INTERPOLATOR);
                break;
            case R.id.action_decelerate:
//                selectorLayout.setInterpolator(Utils.DECELERATE_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.DECELERATE_INTERPOLATOR);

                break;
            case R.id.action_fastOutLinearIn:
//                selectorLayout.setInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR);

                break;
            case R.id.action_fastOutSlowIn:
//                selectorLayout.setInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR);

                break;
            case R.id.action_linear:
//                selectorLayout.setInterpolator(Utils.LINEAR_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.LINEAR_INTERPOLATOR);

                break;
            case R.id.action_linearOutSlowIn:
//                selectorLayout.setInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);

                break;
            case R.id.action_overshoot:
//                selectorLayout.setInterpolator(Utils.OVERSHOOT_INTERPOLATOR);
                drawableLinearLayout.setInterpolator(Utils.OVERSHOOT_INTERPOLATOR);
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

