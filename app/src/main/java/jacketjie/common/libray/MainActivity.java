package jacketjie.common.libray;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import jacketjie.common.libray.network.NetworkAsyncTask;
import jacketjie.common.libray.view.BaseActivity;

public class MainActivity extends BaseActivity {
    private ViewGroup group;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        group = (ViewGroup) findViewById(R.id.id_parent);
//        new TestAsyncTask(this,group,false).execute("");
    }

    public void test(View view){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View view = getLayoutInflater().inflate(R.layout.test_pop,null);
            PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setAnimationStyle(R.style.PopupStyle_EnterFromTop);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            pop.showAsDropDown(toolbar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class TestAsyncTask extends NetworkAsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        public TestAsyncTask(Context context, ViewGroup group, boolean showDialog) {
            super(context, group, showDialog);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            hiddenDialog();
            Toast.makeText(getApplicationContext(), "loading completed", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }
}
