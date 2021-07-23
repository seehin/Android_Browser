package com.example.practise.module_home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practise.R;
import com.example.practise.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder>{

    static class HomeHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView name;
        private final TextView url;

        private HomeHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.home_image);
            name = itemView.findViewById(R.id.home_name);
            url = itemView.findViewById(R.id.home_url);
        }
    }


    private List<HomeBean> homeBeans = new ArrayList<>();

    Context context;

    public HomeAdapter(Context context){
        this.context = context;
    }

    public void setHomeBeans(List<HomeBean> homeBeans) {
        this.homeBeans = homeBeans;
    }

    //配置长按事件
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义一个构造
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //创建卡片  参数——————  布局，组件组，是否是根节点
        View itemView;
        itemView = layoutInflater.inflate(R.layout.home_unit, parent, false);
        //返回组件卡片
        return new HomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeHolder holder, int position) {
        HomeBean bean = homeBeans.get(position);
        Glide.with(holder.image.getContext()).load(bean.getVicon()).into(holder.image);
        holder.name.setText(bean.getVname());
        holder.url.setText(bean.getVurl());

        //添加点击事件
        holder.itemView.setOnClickListener(v -> {
            Uri uri = Uri.parse(bean.getVurl());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return homeBeans.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if(onItemClickListener!=null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }
}
