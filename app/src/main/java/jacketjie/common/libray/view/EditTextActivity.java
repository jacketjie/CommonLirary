package jacketjie.common.libray.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

import jacketjie.common.libray.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        EditText editText = (EditText) findViewById(R.id.id_edit);
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
    }
}
