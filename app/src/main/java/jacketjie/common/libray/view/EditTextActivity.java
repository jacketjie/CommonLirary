package jacketjie.common.libray.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

import de.greenrobot.event.EventBus;
import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.bradge.BadgeTipView;

/**
 * Created by Administrator on 2016/1/11.
 */
public class EditTextActivity extends AppCompatActivity {
    /**
     * 最大长度
     */
    private int maxByteLength = 20;
    /**
     * 编码格式
     */
    private String encoding = "GBK";

    private Button btn;
    private Button btn1;
    private BadgeTipView tip;
    private Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        final EditText editText = (EditText) findViewById(R.id.id_edit);
        editText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        try {
                            int len = 0;
                            boolean more = false;
                            do {
                                SpannableStringBuilder builder =
                                        new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                                len = builder.toString().getBytes(encoding).length;
                                more = len > maxByteLength;
                                if (more) {
                                    end--;
                                    source = source.subSequence(start, end);
                                }
                            } while (more);
                            return source;
                        } catch (UnsupportedEncodingException e) {
                            return "Exception";
                        }
                    }
                }
        });


        btn = (Button) findViewById(R.id.id_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString().trim();
                String[] len = result.split("\\*");
                if (len != null && len.length == 2) {
                    int first = Integer.valueOf(len[0]);
                    int second = Integer.valueOf(len[1]);
                    EventMessage message = new EventMessage();
                    message.setCount(first % 8);
                    if (second > 5) {
                        message.setIsExpandable(true);
                    }
                    EventBus.getDefault().post(message);
                    onBackPressed();
                }
            }
        });
        btn1 = (Button) findViewById(R.id.id_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.setBadgeCount(++count);
            }
        });
        tip = new BadgeTipView(this);
        tip.setBadgeCount(count);
//        tip.setBadgeWeight(0.8f, 0.2f);
//        tip.setTarget(btn);
        tip.setDisplayGravity(Gravity.TOP | Gravity.RIGHT);
        tip.setHiddenOnNull(false);
        tip.setTarget(btn);
        btn1.getHeight();
        btn1.getMeasuredHeight();
        btn.getMeasuredHeightAndState();
    }
}
