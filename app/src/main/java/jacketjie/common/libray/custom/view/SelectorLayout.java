package jacketjie.common.libray.custom.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import jacketjie.common.libray.R;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SelectorLayout extends LinearLayout {

    private int mDuration;
    private static final int Default_druation = 250;
    private static final int Default_style = 0;
    private Styleable curStyle = Styleable.Drawable;
    private boolean isAnimation;
    private boolean isExpandable = true;
    private Direction curDirection = Direction.VerticalDirection;

    public enum Direction {
        VerticalDirection,
        HorizontalDirection
    }

    public enum Styleable {
        Expanable,
        Drawable
    }

    public SelectorLayout(Context context) {
        this(context, null);
    }

    public SelectorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.expandableLayout, defStyleAttr, 0);
        mDuration = ta.getInteger(R.styleable.SelectorLayout_duration, Default_druation);
        int style = ta.getInteger(R.styleable.SelectorLayout_style, Default_style);
        switch (style) {
            case 0:
                curStyle = Styleable.Expanable;
                break;
            case 1:
                curStyle = Styleable.Drawable;
                break;
        }
        ta.recycle();
    }

    public Styleable getCurStyle() {
        return curStyle;
    }

    public void setCurStyle(Styleable curStyle) {
        this.curStyle = curStyle;
        if (curStyle == Styleable.Drawable) {
            setTranslationY(0);
        } else if (curStyle == Styleable.Expanable) {
            getLayoutParams().height = getRealHeight();
            requestLayout();
        }
    }

    public Direction getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(Direction curDirection) {
        this.curDirection = curDirection;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
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
        int childWidthMeasureSpec = 0;
        int childHeightMeasureSpec = 0;
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
    }


    private ValueAnimator getTranslateAnimation(float from, float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, from, to);
        objectAnimator.setDuration(mDuration);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                requestLayout();
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
            }
        });
        return objectAnimator;
    }

    private ValueAnimator getSizeAnimation(float from, float to) {
        final float height = getMeasuredHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e("SelectorLayout", "value=" + value);
                getLayoutParams().height = (int) (height + value);
                requestLayout();
            }
        });
        return valueAnimator;
    }

    private int realHeight = 0;
    private int realWidth = 0;

    private int getRealHeight() {
        return realHeight;
    }

    private void setRealHeight(int realHeight) {
        this.realHeight = realHeight;
    }

    private int getRealWidth() {
        return realWidth;
    }

    private void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
    }


    public void startExpandAnimation() {
        if (isAnimation)
            return;
        if (isExpandable) {
            setRealHeight(getMeasuredHeight());
            setRealWidth(getMeasuredWidth());
            if (curDirection == Direction.VerticalDirection) {
                if (curStyle == Styleable.Drawable) {
                    getTranslateAnimation(0, -getRealHeight()).start();
                } else if (curStyle == Styleable.Expanable) {
                    getSizeAnimation(0, -getRealHeight()).start();
                }
            } else if (curDirection == Direction.HorizontalDirection) {
                if (curStyle == Styleable.Drawable) {
                    getTranslateAnimation(0, -getRealWidth()).start();
                } else if (curStyle == Styleable.Expanable) {
                    getSizeAnimation(0, -getRealWidth()).start();
                }

            }
            isExpandable = false;
        } else {
            if (curDirection == Direction.VerticalDirection) {

                if (curStyle == Styleable.Drawable) {
                    getTranslateAnimation(-getRealHeight(), 0).start();
                } else if (curStyle == Styleable.Expanable) {
                    getSizeAnimation(0, getRealHeight()).start();
                }
            } else if (curDirection == Direction.HorizontalDirection) {
                if (curStyle == Styleable.Drawable) {
                    getTranslateAnimation(-getRealWidth(), 0).start();
                } else if (curStyle == Styleable.Expanable) {
                    getSizeAnimation(0, getRealWidth()).start();
                }
            }
            isExpandable = true;
        }
    }

    private boolean isVertical() {
        return getOrientation() == VERTICAL;
    }

}
