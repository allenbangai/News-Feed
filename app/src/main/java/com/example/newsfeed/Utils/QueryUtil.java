package com.example.newsfeed.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.newsfeed.Model.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    private static final String TAG= QueryUtil.class.getSimpleName();
    private List<NewsFeed> newsFeeds = null;
    public String response = null;

    public QueryUtil(){

    }

    public List<NewsFeed> newsFeeds(String urlString) {
        //Making Http request to extract and return list of news feed
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //read the response
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(inputStream);
            newsFeeds = extractNewsFeeds(response);
        } catch (MalformedURLException | ProtocolException ignored) {
            ignored.printStackTrace();
            Log.e(TAG, "URL is not correct or malformed ", ignored);
        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "problem getting data from the url", e);
        } catch (JSONException attack){
            attack.printStackTrace();
            Log.e(TAG, "Problem parsing the earthquake JSON results", attack);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return newsFeeds;
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try{
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        return builder.toString();
    }

    private List<NewsFeed> extractNewsFeeds(String response) throws JSONException {
        if(TextUtils.isEmpty(response)){
            return null;
        }

        List<NewsFeed> newsFeedList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        //extract JSONArray associated with the key call object...
        JSONObject responseObject = jsonObject.getJSONObject("response");
        JSONArray jsonArray = responseObject.getJSONArray("results");

        for(int i=0; i<jsonArray.length(); i++){
            //get object at each array position
            JSONObject object = jsonArray.getJSONObject(i);

            String sectionName = object.getString("sectionName");
            String webPublicationDate = object.getString("webPublicationDate");
            String webTitle = object.getString("webTitle");
            String webUrl = object.getString("webUrl");

            NewsFeed newsFeed = new NewsFeed(sectionName, webPublicationDate, webTitle, webUrl);
            newsFeedList.add(newsFeed);
        }
        return newsFeedList;
    }
}
