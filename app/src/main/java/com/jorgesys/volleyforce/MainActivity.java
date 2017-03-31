package com.jorgesys.volleyforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;
    private static boolean downloadImagesVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get listView instance.
        listView= (ListView) findViewById(R.id.listView);

        //Create and set Adapter.
        adapter = new PostAdapter(this);
        listView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_load_volley) {
            downloadImagesVolley = true;
        }else if(id == R.id.action_load_picasso){
            downloadImagesVolley = false;
        }else{
            downloadImagesVolley = false;
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    public static boolean getImageLoadMethod(){
        return downloadImagesVolley;
    }
}
