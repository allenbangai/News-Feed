package com.example.newsfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Adapter.NewsFeedAdapter;
import com.example.newsfeed.Model.NewsFeed;
import com.example.newsfeed.Utils.QueryUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "https://content.guardianapis.com/search?q=debate&tag=" +
            "politics/politics&from-date=2014-01-01&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = this.findViewById(R.id.textview3);

        recyclerView = this.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true); //hasFixedSize();

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        private String jsonDataInStr;
        private List<NewsFeed> newsFeeds;
        private NewsFeedAdapter newsFeedAdapter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * here, task is done in background
         * @param voids as you can see
         * @deprecated
         */

        @Override
        protected Void doInBackground(Void... voids) {
            QueryUtil queryUtil = new QueryUtil();
            newsFeeds = queryUtil.newsFeeds(url);
            jsonDataInStr = queryUtil.response;
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(jsonDataInStr != null && newsFeeds == null){
                recyclerView.setVisibility(View.GONE);
                textView.setText(jsonDataInStr);
            }else if(newsFeeds != null){
                newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(), newsFeeds);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(newsFeedAdapter);
                textView.setVisibility(View.GONE);
            }else{
                Log.d(TAG, "Json parsing error: ");
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                        "Json parsing error: ", Toast.LENGTH_LONG).show());
            }
        }
    }

}
