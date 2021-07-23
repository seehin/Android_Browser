package com.example.practise.module_history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.practise.R;
import com.example.practise.bean.HistoryRecordBean;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchInTimeAdapter extends BaseAdapter implements Filterable {

    private List<HistoryRecordBean> data;

    private List<HistoryRecordBean> historyRecordBeans;

    private final Context context;

    public void setHistoryRecordBeans(List<HistoryRecordBean> historyRecordBeans){
        this.historyRecordBeans = historyRecordBeans;
    }

    public HistorySearchInTimeAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        if (historyRecordBeans == null){
            return 0;
        }else{
            return historyRecordBeans.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (historyRecordBeans == null) {
            return null;
        } else {
            return historyRecordBeans.get(position);
        }

    }

    public HistorySearchInTimeAdapter(@NonNull Context context, List<HistoryRecordBean> list) {
        this.context = context;
        historyRecordBeans = list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        HistoryRecordBean bean = historyRecordBeans.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.history_list_unit,parent);

        ImageView image = view.findViewById(R.id.list_history_image);
        TextView name = view.findViewById(R.id.list_history_name);
        TextView url = view.findViewById(R.id.list_history_url);
        TextView id = view.findViewById(R.id.list_history_id);
        TextView time = view.findViewById(R.id.list_history_time);

        Glide.with(image.getContext()).load(bean.getHicon()).into(image);
        name.setText(bean.getHname());
        url.setText(bean.getHurl());
        id.setText(String.valueOf(bean.getId()));
        time.setText(bean.getHdate());

        return itemView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<HistoryRecordBean> lists = new ArrayList<>();
                if(constraint != null){
                    for (HistoryRecordBean h: historyRecordBeans) {
                        lists.add(h);
                    }
                }
                results.count = lists.size();
                results.values = lists;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (List<HistoryRecordBean>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
