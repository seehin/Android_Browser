package com.example.practise.module_history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practise.R;
import com.example.practise.bean.HistoryRecordBean;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.HistorySearchHolder> {


    static class HistorySearchHolder extends RecyclerView.ViewHolder{
        //显示item部分
        private final ImageView image;
        private final TextView name;
        private final TextView url;
        private final TextView id;
        private final ImageView deleteRecord;

        public HistorySearchHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Search_list_history_image);
            name = itemView.findViewById(R.id.Search_list_history_name);
            url = itemView.findViewById(R.id.Search_list_history_url);
            id = itemView.findViewById(R.id.Search_list_history_id);
            deleteRecord = itemView.findViewById(R.id.search_record_delete);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }



    Context context;

    List<HistoryRecordBean> historyRecordBeans = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public HistorySearchAdapter(Context context){
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return this.onItemClickListener;
    }

    public void setHistoryRecordBeans(List<HistoryRecordBean> lists){
        this.historyRecordBeans = lists;
    }

    public void clearHistoryRecordBeans(){
        this.historyRecordBeans.clear();
    }


    @NonNull
    @Override
    public HistorySearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义一个构造
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //创建卡片  参数——————  布局，组件组，是否是根节点
        View itemView;
        itemView = layoutInflater.inflate(R.layout.history_search_list_unit, parent, false);
        //返回组件卡片
        return new HistorySearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySearchHolder holder, int position) {
        HistoryRecordBean bean = historyRecordBeans.get(position);
        Glide.with(holder.image.getContext()).load(bean.getHicon()).into(holder.image);
        holder.name.setText(bean.getHname());
        holder.url.setText(bean.getHurl());
        holder.id.setText(String.valueOf(bean.getId()));
        


        //点击事件
        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(v ->{
                onItemClickListener.onItemClick(holder.itemView,position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return historyRecordBeans.size();
    }

}
