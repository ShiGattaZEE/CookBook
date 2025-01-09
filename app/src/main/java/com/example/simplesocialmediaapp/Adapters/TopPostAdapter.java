package com.example.simplesocialmediaapp.Adapters;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplesocialmediaapp.Models.PostModel;
import com.example.simplesocialmediaapp.R;

import java.util.ArrayList;

public class TopPostAdapter extends RecyclerView.Adapter<TopPostAdapter.viewholder> {

    ArrayList<PostModel> topPosts;

    public TopPostAdapter(ArrayList<PostModel> topPosts) {
        this.topPosts = topPosts;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.postcontent, parent, false);
        return new viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        PostModel post = topPosts.get(position);

        holder.tv_post_uname.setText(post.getUid());
        holder.et_post_content.setText(post.getContent());

        String date = DateFormat.format("hh:mm aa   dd-MM-yyyy", Long.parseLong(post.getTimestamp())).toString();
        holder.tv_post_date.setText(date);

        if (post.getLikes() != null) {
            holder.tv_post_likes.setText(String.valueOf(post.getLikes().size()));
        }
        if (post.getComments() != null) {
            holder.tv_post_comments.setText(String.valueOf(post.getComments().size()));
        }

        // Glide veya diğer görselleri burada yükleyin.
    }

    @Override
    public int getItemCount() {
        return topPosts.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView tv_post_uname, tv_post_date, tv_post_comments, tv_post_likes;
        EditText et_post_content;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_post_uname = itemView.findViewById(R.id.tv_post_uname);
            tv_post_date = itemView.findViewById(R.id.tv_post_date);
            tv_post_comments = itemView.findViewById(R.id.tv_post_comments);
            tv_post_likes = itemView.findViewById(R.id.tv_post_likes);
            et_post_content = itemView.findViewById(R.id.et_post_content);
        }
    }
}
