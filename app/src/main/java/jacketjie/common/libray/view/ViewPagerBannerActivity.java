package jacketjie.common.libray.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.swipeback.SwipeBackActivity;
import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;
import jacketjie.common.libray.custom.view.viewpager.TestBean;
import jacketjie.common.libray.custom.view.viewpager.indicator.CirclePageIndicator;
import jacketjie.common.libray.network.volley.VolleyHelper;
import jacketjie.common.libray.network.volley.VolleyResponseListener;
import jacketjie.common.libray.others.ToastUtils;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ViewPagerBannerActivity extends SwipeBackActivity {
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
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("5.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("6.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("7.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("8.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("9.jpg"));

    /*    List<TestBean> binderDatas = new LinkedList<>();
        for (int i = 0;i<urls.size();i++){
            TestBean b = new TestBean();
            b.setName("测试"+(i+1));
            b.setPosition(i + 1);
            b.setUrl("https://www.baidu.com");
            binderDatas.add(b);
        }*/
        banner = (ScrollBanner) findViewById(R.id.id_banner);


//        banner.setBanner(getSupportFragmentManager(),urls);


        VolleyHelper.RequestGetBean(this, TestBean.class, "http://www.siyslchina.org/api/v1.0/ads/list?ads_position=0", null, new VolleyResponseListener<TestBean>() {
            @Override
            public void requestSuccess(TestBean data) {
                List<TestBean.AllAdsEntity> binderDatas = data.getAllAds();
                urls.clear();
                for (TestBean.AllAdsEntity ads : binderDatas) {
                    urls.add(ads.getAds_avatar());
                }
                //设置数据
                banner.setBanner(getSupportFragmentManager(), urls, binderDatas);
                //每个图片点击事件
                banner.setOnPageClickListener(new ScrollBanner.OnPageClickListener<TestBean.AllAdsEntity>() {
                    @Override
                    public void onPageCilck(TestBean.AllAdsEntity data) {
                        ToastUtils.showShort(getApplicationContext(), data.toString());
                    }
                });
            }

            @Override
            public void requestError(VolleyError error) {

            }
        });
        getSupportActionBar().setTitle("Auto Carouse ViewPager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
