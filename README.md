# CommonLirary
This is a common library!

#ScrollBanner 
This is a FramLayout with a ViewPager and a CirclePageIndicator.You can set some properties for your casousel viewPager. 
The first thing to do is to modify your layout file to reference one of the ScrollBanner Views instead of an Android ViewPager.

    <jacketjie.common.libray.custom.view.viewpager.ScrollBanner
        xmlns:pager="http://schemas.android.com/apk/res-auto"
        android:id="@+id/id_banner"
        pager:banner_aspect_ratio="0.32"
        pager:banner_can_cycle="true"
        pager:banner_need_indicator="true"
        pager:banner_trans_former="defaultPage"
        pager:banner_scroll_factor="3"
        pager:banner_scroll_interval="5000"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
And then,set the data resource in activity.

banner = (ScrollBanner) findViewById(R.id.id_banner);
banner.setBanner(getSupportFragmentManager(), urls, binderDatas);


#VolleyHelper
    It is a Volley heplper utils,You just need to involve VolleyHeplper.Request...

