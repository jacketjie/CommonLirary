package jacketjie.common.libray.custom.view.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import jacketjie.common.libray.custom.view.viewpager.indicator.CirclePageIndicator;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ScrollBanner extends FrameLayout{
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
     * @param context
     */
    private void createBanner(Context context) {

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
