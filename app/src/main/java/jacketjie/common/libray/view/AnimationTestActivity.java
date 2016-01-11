package jacketjie.common.libray.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.SelectorLayout;
import jacketjie.common.libray.others.ScreenUtils;

/**
 * Created by Administrator on 2016/1/7.
 */
public class AnimationTestActivity extends AppCompatActivity {
    private Button sizeBtn;
    private Button locaBtn;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_layout);
        screenWidth = ScreenUtils.getScreenWidth(this);
        initViews();
        initData();
        setEventListener();
//        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onGlobalLayout() {
//                int height = ScreenUtils.measure(listView)[1];
//                Log.e("onGlobalLayout", "height=" + height);
//                listView.setTranslationY(-height);
//                listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
    }

    private void setEventListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_vertical:
                        selectorLayout.setCurDirection(SelectorLayout.Direction.VerticalDirection);
                        break;
                    case R.id.id_horizontal:
                        selectorLayout.setCurDirection(SelectorLayout.Direction.HorizontalDirection);
                        break;
                }
            }
        });

//        verticalBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                selectorLayout.startExpandAnimation();
//            }
//        });
//        horizontalBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                selectorLayout.startExpandAnimation();
//            }
//        });
    }

    private void initViews() {
        group = (RadioGroup) findViewById(R.id.id_top);

        sizeBtn = (Button) findViewById(R.id.id_animation);
        locaBtn = (Button) findViewById(R.id.id_location);
        verticalBtn = (RadioButton) findViewById(R.id.id_vertical);
        horizontalBtn = (RadioButton) findViewById(R.id.id_horizontal);
        listView = (ListView) findViewById(R.id.id_anim_list);
        selectorLayout = (SelectorLayout) findViewById(R.id.id_selector);
    }

    private void initData() {
        sizeBtn.setOnClickListener(new View.OnClickListener() {
            int listViewHeight;

            @Override
            public void onClick(final View v) {
                selectorLayout.setCurStyle(SelectorLayout.Styleable.Expanable);
                selectorLayout.startExpandAnimation();
//                if (isAnimating)
//                    return;
//                if (isExpand) {
//                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
//                    valueAnimator.setDuration(300).setEvaluator(new FloatEvaluator());
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float value = (float) animation.getAnimatedValue();
//                            if (!isFirst) {
//                                listViewHeight = ScreenUtils.measure(listView)[1];
//                                isFirst = true;
//                            }
//                            int curHeight = (int) (listViewHeight * value);
//                            ViewGroup.LayoutParams lp = listView.getLayoutParams();
////                        Log.e("onAnimationUpdate", "value=" + value + ",height=" + listViewHeight + ",curHeight=" + curHeight);
//                            lp.height = curHeight;
//                            listView.setLayoutParams(lp);
//                        }
//                    });
//                    valueAnimator.addListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            isExpand = false;
//                            isAnimating = false;
//                            listView.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            isAnimating = true;
//                        }
//                    });
//                    valueAnimator.start();
//                } else {
//                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//                    valueAnimator.setDuration(300).setEvaluator(new FloatEvaluator());
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float value = (float) animation.getAnimatedValue();
//                            int curHeight = (int) (listViewHeight * value);
//                            ViewGroup.LayoutParams lp = listView.getLayoutParams();
//                            Log.e("onAnimationUpdate", "value=" + value + ",height=" + listViewHeight + ",curHeight=" + curHeight);
//                            lp.height = curHeight;
//                            listView.setLayoutParams(lp);
//                        }
//                    });
//                    valueAnimator.addListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            isExpand = true;
//                            isAnimating = false;
//                        }
//
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            isAnimating = true;
//                            listView.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    valueAnimator.start();
//                }
            }
        });

        locaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                translateAnim(listView, ScreenUtils.measure(listView)[1], listView.getVisibility() == View.VISIBLE);
                selectorLayout.setCurStyle(SelectorLayout.Styleable.Drawable);
                selectorLayout.startExpandAnimation();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        listView.setAdapter(adapter);

    }

    /**
     * 获取坐标
     *
     * @param view
     * @return
     */
    private int[] getViewPosition(View view) {
        int x = 0, y = 0;
        int[] position = new int[2];
        view.getLocationInWindow(position);

        return position;
    }

    private void translateAnim(final View view, final int viewHeight, boolean isMoveToUp) {
        Log.e("onGlobalLayout", "viewHeight=" + viewHeight);
        if (isAnimating)
            return;
        if (isMoveToUp) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, -viewHeight);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                    isAnimating = false;
                    isExpand = false;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isAnimating = true;
                }
            });
            objectAnimator.start();
        } else {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -viewHeight, 0);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAnimating = false;
                    isExpand = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(View.VISIBLE);
                    isAnimating = true;
                }
            });

            objectAnimator.start();
        }
    }


}

