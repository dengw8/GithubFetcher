package com.example.duang1996.githubfetcher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duang1996.githubfetcher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duang1996 on 2017/12/12.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context context;
    private List<Map<String, Object>> itemList;
    private OnItemClickListener click;
    private OnItemLongClickListener mclick;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView id;
        TextView blog;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.userName);
            id = view.findViewById(R.id.userId);
            blog = view.findViewById(R.id.userBlog);
        }
    }

    public CardAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        itemList = new ArrayList<>();
        itemList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Map<String, Object>item = itemList.get(position);
        holder.name.setText(item.get("name").toString());
        holder.id.setText(item.get("id").toString());
        holder.blog.setText(item.get("blog").toString());

        if(click != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onItemClick(holder.getAdapterPosition());
                }
            });
        }
        if(mclick != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mclick.onItemLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener click) {
        this.click = click;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mclick) {
        this.mclick = mclick;
    }

}
