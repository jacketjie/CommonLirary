package jacketjie.common.libray.custom.view.animatedlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.expandablelayout.Utils;

/**
 * Created by wujie on 2016/1/15.
 */
public class DrawableLinearLayout extends LinearLayout implements AnimatedLayout{
    private int mDuration;
    private AnimatedLayoutListener.Direction mDirection;
    private TimeInterpolator mInterpolator;
    private AnimatedLayoutListener animatedLayoutListener;
    private boolean isExpandable = DEFAULT_EXPANDABLE;
    private boolean isAnimation;
    private Integer layoutWidth;
    private Integer layoutHeight;
    private boolean needToRequest = true;


    public DrawableLinearLayout(Context context) {
        this(context,null);
    }

    public DrawableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleAttr);
        init(context,attrs,defStyleRes);
    }

    private void init(Context context, AttributeSet attrs,int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.DrawableLinearLayout, defStyleAttr, 0);
        mDuration = ta.getInteger(R.styleable.DrawableLinearLayout_dll_duration, DEFAULT_DURATION);
        int style = ta.getInteger(R.styleable.DrawableLinearLayout_dll_direction, DEFAULT_DIRECTION);
        mDirection = Utils.getAnimationDirection(style);
        int interpolator = ta.getInteger(R.styleable.DrawableLinearLayout_dll_interpolator,DEFAULT_INTERPOLATOR);
        mInterpolator = Utils.createInterpolator(interpolator);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0, height = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = this.getChildCount();

        int childMeasuredWidth = 0;
        int childMeasuredHeight = 0;
//        int childWidthMeasureSpec = 0;
//        int childHeightMeasureSpec = 0;
        // 修改了系统自动测量的子View的大小
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 系统自动测量子View:
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // 如果不希望系统自动测量子View,我们用以下的方式:
            // childWidthMeasureSpec =
            // MeasureSpec.makeMeasureSpec(100,MeasureSpec.EXACTLY);
            // childHeightMeasureSpec =
            // MeasureSpec.makeMeasureSpec(100,MeasureSpec.EXACTLY);
            // childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        // 获取每个子View测量所得的宽和高
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            if (isVertical()) {
                childMeasuredWidth = Math.max(childMeasuredWidth, childWidth);
                childMeasuredHeight += childHeight;
            } else {
                childMeasuredHeight = Math.max(childMeasuredHeight, childHeight);
                childMeasuredWidth += childWidth;
            }
//            Log.e("SelectorLayout", "i=" + i + ",获取系统自动测量的该子View的大小:" +
//                    "childMeasuredWidth=" + childWidth + "," +
//                    "childMeasuredHeight=" + childHeight);

        }
        width += getPaddingRight() + getPaddingLeft() + childMeasuredWidth;
        height += getPaddingTop() + getPaddingBottom() + childMeasuredHeight;
//        Log.e("SelectorLayout", "width=" + width + ",height=" + height);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
        if (needToRequest){
            setLayoutWidth(width);
            setLayoutHeight(height);
            moveToHiddenOrNot();
            needToRequest = false;
        }
    }


    public void resetLayout(){
        this.needToRequest = true;
        setTranslationY(-1);
        requestLayout();
    }
    private void moveToHiddenOrNot(){
        if (!isExpandable()){
            switch (getDirection()){
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    break;
                case DIRECTION_LEFT:
                    setTranslationX(-getLayoutWidth());
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    break;
            }
            setVisibility(GONE);
        }else{
            setVisibility(VISIBLE);
        }
    }

    /**
     * 创建Drawable动画
     *
     * @param startPos
     * @param endPos
     * @return
     */
    private ValueAnimator createDrawableAnimation(float startPos, float endPos) {
        ObjectAnimator objectAnimator = null;
        if (getDirection() == DIRECTION_TOP ) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, startPos, endPos);
        }
        if (getDirection() == DIRECTION_LEFT || getDirection() == DIRECTION_RIGHT) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, startPos, endPos);
        }
        objectAnimator.setInterpolator(mInterpolator);
        objectAnimator.setDuration(mDuration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                setVisibility(VISIBLE);
                if (animatedLayoutListener != null){
                    animatedLayoutListener.animationCreated();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
                if (!isExpandable()){
                    setVisibility(GONE);
                }
                if (animatedLayoutListener != null){
                    animatedLayoutListener.animationEnded();
                }
            }
        });
        return objectAnimator;
    }

    /**
     * 展开或者收缩
     */
    public void toggle(){
        if (isExpandable()){
            expand();
        }else{
           collapse();
        }
    }

    public void expand(){
        switch (getDirection()){
            case DIRECTION_TOP:
                createDrawableAnimation(0,-getLayoutHeight()).start();
                break;
            case DIRECTION_LEFT:
                createDrawableAnimation(0,-getLayoutWidth()).start();
                break;
            case DIRECTION_RIGHT:
                createDrawableAnimation(0,getLayoutWidth()).start();
                break;
        }
        setExpandable(false);
    }

    public void collapse(){
        switch (getDirection()){
            case DIRECTION_TOP:
                createDrawableAnimation(-getLayoutHeight(),0).start();
                break;
            case DIRECTION_LEFT:
                createDrawableAnimation(-getLayoutWidth(),0).start();
                break;
            case DIRECTION_RIGHT:
                createDrawableAnimation(getLayoutWidth(),0).start();
                break;
        }
        setExpandable(true);
    }
    private void resetAnimationDirection(AnimatedLayoutListener.Direction lastDirection){
        if (isExpandable()){
            return;
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Top){
            switch (getDirection()){
                case DIRECTION_LEFT:
                   setTranslationX(-getLayoutWidth());
                    setTranslationY(0);
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    setTranslationY(0);
                    break;
            }
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Left){
            switch (getDirection()){
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    setTranslationX(0);
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    setTranslationY(0);
                    break;
            }
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Right){
            switch (getDirection()){
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    setTranslationX(0);
                    break;
                case DIRECTION_LEFT:
                    setTranslationX(-getLayoutWidth());
                    break;
            }
        }
    }


    public boolean isVertical(){
        return getOrientation() == VERTICAL;
    }

    public int getDirection() {
        if (mDirection == AnimatedLayoutListener.Direction.Top)
            return DIRECTION_TOP;
        if (mDirection == AnimatedLayoutListener.Direction.Left)
            return DIRECTION_LEFT;
        if (mDirection == AnimatedLayoutListener.Direction.Right)
            return DIRECTION_RIGHT;
        return DIRECTION_TOP;
    }

    public void setDirection(AnimatedLayoutListener.Direction mDirection) {
        if (this.mDirection == mDirection)
            return;
        AnimatedLayoutListener.Direction lastDirection = this.mDirection;
        this.mDirection = mDirection;
        resetAnimationDirection(lastDirection);
    }

    public AnimatedLayoutListener getAnimatedLayoutListener() {
        return animatedLayoutListener;
    }

    public void setAnimatedLayoutListener(AnimatedLayoutListener animatedLayoutListener) {
        this.animatedLayoutListener = animatedLayoutListener;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public Integer getLayoutWidth() {
        return layoutWidth;
    }

    public void setLayoutWidth(Integer layoutWidth) {
        this.layoutWidth = layoutWidth;
    }

    public Integer getLayoutHeight() {
        return layoutHeight;
    }

    public void setLayoutHeight(Integer layoutHeight) {
        this.layoutHeight = layoutHeight;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(int interpolator) {
        this.mInterpolator = Utils.createInterpolator(interpolator);
    }
}
