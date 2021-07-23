package com.example.practise.module_bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practise.R;
import com.example.practise.bean.BookmarkBean;
import com.example.practise.databinding.BookmarkCheckMoveToFolderItemBinding;

import java.util.List;

public class MoveToFolderAdapter extends RecyclerView.Adapter<MoveToFolderAdapter.ViewHolder> {

    private List<BookmarkBean> bookmarkList;
    private ItemClick itemClick;

    public MoveToFolderAdapter(List<BookmarkBean> bookmarkList, ItemClick itemClick) {
        this.bookmarkList = bookmarkList;
        this.itemClick = itemClick;
    }

    //获取布局中的一些组件实例用于数据展示
    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view){
            super(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BookmarkCheckMoveToFolderItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        BookmarkBean bookmarkBean = bookmarkList.get(position);
        binding.setBookmark(bookmarkBean);
        binding.setItemclick(itemClick);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BookmarkCheckMoveToFolderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.bookmark_check_move_to_folder_item, parent, false);
        View view = binding.getRoot();
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
}
