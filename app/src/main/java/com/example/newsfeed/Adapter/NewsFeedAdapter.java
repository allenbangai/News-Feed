package com.example.newsfeed.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Model.NewsFeed;
import com.example.newsfeed.R;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    Context context;
    List<NewsFeed> newsFeedList;

    public NewsFeedAdapter(Context context, List<NewsFeed> newsFeedList) {
        this.context = context;
        this.newsFeedList = newsFeedList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView newsName, publicationDate, publicationTime, title, authorName;
        ConstraintLayout constraint;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraint = itemView.findViewById(R.id.constraint);
            newsName = itemView.findViewById(R.id.news_name);
            publicationTime = itemView.findViewById(R.id.publish_time);
            publicationDate = itemView.findViewById(R.id.publish_date);
            title= itemView.findViewById(R.id.news_title);
            authorName = itemView.findViewById(R.id.author_name);
            //setting onclick on each onclick item
            constraint.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String stringUrl = newsFeedList.get(getLayoutPosition()).getWebUrl();
            Toast.makeText(view.getContext(), "Item clicked at position: " + getLayoutPosition() + "\n"
                    + " And web page opened to: " + stringUrl, Toast.LENGTH_SHORT).show();

            Uri uri = Uri.parse(stringUrl);
            //create new intent to view the uri
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //send the intent to launch new activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(intent.resolveActivity(context.getPackageManager()) != null){
                //launch new activity for available web browser
                context.startActivity(intent);
            }else {
                //no app available to open browser
                Toast.makeText(context, "Sorry, No available App to open browser", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsFeed newsFeed = newsFeedList.get(position);

        holder.newsName.setText(newsFeed.getSectionName());
        holder.publicationDate.setText(getPublicationDateTime(0, newsFeed));
        holder.publicationTime.setText(getPublicationDateTime(1, newsFeed));
        holder.title.setText(newsFeed.getWebTitle());
        holder.authorName.setText(getAuthorName(newsFeed));

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return newsFeedList.size();
    }

    private String getPublicationDateTime(int param, NewsFeed newsFeed) {
        String getDateTime = newsFeed.getWebPublicationDate().replace("Z", "");
        String[] datetime = getDateTime.split("T", 2);
        return datetime[param];//.substring(0, getDateTime.length() - 1);
    }

    private String getAuthorName(NewsFeed newsFeed){
        return (newsFeed.getFirstName() + " " + newsFeed.getLastName());
    }
}
