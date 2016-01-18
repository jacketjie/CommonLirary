package jacketjie.common.libray.custom.view.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> imageRes;
    private boolean canCycle = true;
    private final int FAKE_BANNER_SIZE = 100;
    private ViewPager mViewPager;
    public ViewPagerAdapter(FragmentManager fm, List<String> imageRes) {
        super(fm);
        this.imageRes = imageRes;
    }

    public ViewPagerAdapter(FragmentManager fm,List<String> imageRes, boolean canCycle) {
        super(fm);
        this.canCycle = canCycle;
        this.imageRes = imageRes;
    }


    @Override
    public int getCount() {
        return canCycle ? FAKE_BANNER_SIZE : (imageRes == null ? 0 : imageRes.size());
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment fragment = null;
        if (imageRes != null && imageRes.size() > 0){
            fragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ImageRes", imageRes.get(position % imageRes.size()));
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public void setTarget(ViewPager mViewPager){
        this.mViewPager = mViewPager;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);

        if (mViewPager != null){
            int pos = mViewPager.getCurrentItem();
            if (pos == 0){
                mViewPager.setCurrentItem(imageRes.size(),false);
            }else if(pos == FAKE_BANNER_SIZE - 1){
                mViewPager.setCurrentItem(imageRes.size() - 1,false);
            }
        }
//        super.finishUpdate(container);
    }
}
