package com.example.newsfeed;

import android.app.LoaderManager.LoaderCallbacks;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Adapter.NewsFeedAdapter;
import com.example.newsfeed.Model.NewsFeed;
import com.example.newsfeed.Utils.GetNewsLoader;
import com.example.newsfeed.Utils.NetWorkUtil;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsFeed>> {
    private TextView textView;
    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    //private static final String url = "https://content.guardianapis.com/search?q=debate&tag=" +
            //"politics/politics&from-date=2014-01-01&api-key=test";
    private String connectionStatus;
    private String url;


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

        connectionStatus = new NetWorkUtil().getInternetConnectivityStatus(this);

        getLoaderManager().initLoader(0, savedInstanceState,this);
    }

    public MainActivity(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, connectionStatus, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, connectionStatus, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, connectionStatus, Toast.LENGTH_SHORT).show();
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public android.content.Loader<List<NewsFeed>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("q", "debate")
                .appendQueryParameter("tag","politics/politics")
                .appendQueryParameter("from-date", "2014-01-01")
                .appendQueryParameter("api-key", "test")
                .appendQueryParameter("show-tags", "contributor");
        url = builder.build().toString();
        return new GetNewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<NewsFeed>> loader, List<NewsFeed> newsFeeds) {

        if(newsFeeds != null){
            textView.setVisibility(View.GONE);
            NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(), newsFeeds);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(newsFeedAdapter);
        }else{
            Log.d(TAG, "Json parsing error: ");
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                    "Json parsing error: ", Toast.LENGTH_LONG).show());

            textView.setText(R.string.json_error + " " + url);
        }

    }

    /**
     * @param loader used for loading arraylist in background
     * @deprecated
     */
    @Override
    public void onLoaderReset(android.content.Loader<List<NewsFeed>> loader) {

    }
}