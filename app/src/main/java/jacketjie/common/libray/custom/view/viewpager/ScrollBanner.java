package jacketjie.common.libray.custom.view.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.viewpager.indicator.CirclePageIndicator;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ScrollBanner extends FrameLayout {
    private boolean isRecycle = true;
    private ViewPager mViewPager;
    private CirclePageIndicator pageIndicator;
    private float aspectRatio = 1;

    ViewPagerAdapter viewPagerAdapter;

    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createBanner(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createBanner(context);
    }

    /**
     * 创建广告牌
     *
     * @param context
     */
    private void createBanner(Context context) {
//        mViewPager = new ViewPager(context);
        View view  = LayoutInflater.from(context).inflate(R.layout.view_pager_layout,this,false);
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewPager);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        addView(mViewPager);
    }

    public void setBanner(FragmentManager fm, List<String> urls) {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(fm, urls, false);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setTarget(mViewPager);
        mViewPager.requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public boolean isRecycle() {
        return isRecycle;
    }

    public void setIsRecycle(boolean isRecycle) {
        this.isRecycle = isRecycle;
    }
}
