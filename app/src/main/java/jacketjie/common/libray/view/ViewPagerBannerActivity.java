package jacketjie.common.libray.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;
import jacketjie.common.libray.custom.view.viewpager.ViewPagerAdapter;
import jacketjie.common.libray.custom.view.viewpager.indicator.CirclePageIndicator;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ViewPagerBannerActivity extends AppCompatActivity {
        private ScrollBanner banner;
    private List<String> urls;
    private ViewPager mViewPager;
    private CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_banner_layout);
        urls = new ArrayList<String>();
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("1.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("2.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("3.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("4.jpg"));
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.id_indicator);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),urls,true);
        mViewPager.setAdapter(adapter);
        indicator.setViewPager(mViewPager);

//        banner = (ScrollBanner) findViewById(R.id.id_banner);
//        banner.resetBanner(getSupportFragmentManager(),urls);
//        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
//        List<Integer> res = new ArrayList<Integer>();
//        res.add(R.drawable.test_1);
//        res.add(R.drawable.test_1);
//        res.add(R.drawable.test_3);
//        res.add(R.drawable.test_4);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),urls);
//        mViewPager.setAdapter(adapter);
//        adapter.setTarget(mViewPager);
    }
}
