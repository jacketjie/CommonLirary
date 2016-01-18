package jacketjie.common.libray.custom.view.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import jacketjie.common.libray.R;
import jacketjie.common.libray.others.ToastUtils;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ImageFragment extends Fragment {
    private String resId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        resId = getArguments().getString("ImageRes");
//        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//        lp.height = ScreenUtils.getScreenWidth(getContext());
//        lp.width = ScreenUtils.getScreenWidth(getContext());
//        imageView.setLayoutParams(lp);
        ImageLoader.getInstance().displayImage(resId, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), resId);
            }
        });
        return view;
    }
}
