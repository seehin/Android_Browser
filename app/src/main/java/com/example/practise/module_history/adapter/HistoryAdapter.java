package com.example.practise.module_history.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practise.R;
import com.example.practise.bean.HistoryListBean;
import com.example.practise.bean.HistoryRecordBean;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistoryAdapter extends SectionedRecyclerViewAdapter<
        HistoryAdapter.HistoryHeaderViewHolder,
        HistoryAdapter.HistoryHolder,
        HistoryAdapter.HistoryFooterViewHolder
        > {

    /**
     * 显示历史记录的item
     */
    static class HistoryHolder extends RecyclerView.ViewHolder {
        //显示item部分
        private final ImageView image;
        private final TextView name;
        private final TextView url;
        private final TextView id;
        private final TextView time;

        //复选框部分
        private final CheckBox checkBox;

        private HistoryHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.list_history_image);
            name = itemView.findViewById(R.id.list_history_name);
            url = itemView.findViewById(R.id.list_history_url);
            id = itemView.findViewById(R.id.list_history_id);
            time = itemView.findViewById(R.id.list_history_time);
            checkBox = itemView.findViewById(R.id.list_history_checkBox);
        }
    }

    /**
     * 显示历史记录的时间
     */
    static class HistoryHeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final TextView deleteRecordOfDay;
        public HistoryHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.history_today_time);
            deleteRecordOfDay = itemView.findViewById(R.id.delete_today_history);
        }
    }

    /**
     * 每天的历史记录下的删除历史记录按钮
     */
    static class HistoryFooterViewHolder extends RecyclerView.ViewHolder {

        private final TextView deleteRecordOfToday;
        public HistoryFooterViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteRecordOfToday = itemView.findViewById(R.id.history_today_delete);
        }
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view , int position);
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int section , int position);
    }

      /*********************************************************************************************
    **********************************************************************************************/

    private List<HistoryRecordBean> historyRecordBeans;

    private List<HistoryListBean> allRecords;

    Map<Integer,Boolean> mapOfCheckBox = new HashMap<>();    /*item是否选中集合*/

    private Boolean isShowCheckBox = false;     /*默认不显示复选框*/

    private OnItemLongClickListener onItemLongClickListener;    /*配置长按事件*/

    private OnItemClickListener onItemClickListener;    /*配置点击事件*/

    private Boolean isClickAllow = true;

    private List<Integer> listOfDelete = new LinkedList<>();

    Context context;    /*上下文*/

    private Boolean isSelectAll = false;    /*是否全选*/

    private Boolean isShowNoSelect = false;     /*取消全选按钮是否显示*/


    /*********************************************************************************************
     **********************************************************************************************/

    public HistoryAdapter(Context context){
        this.context = context;
    }

    public HistoryAdapter(Context context, List<HistoryListBean> allRecords){
        this.context = context;
        this.allRecords = allRecords;
    }

    public void setIsSelectAll(){
        isSelectAll = !isSelectAll;
    }

    public Boolean getIsSelectAll(){
        return isSelectAll;
    }

    public void setAllRecords(List<HistoryListBean> allRecords){
        this.allRecords = allRecords;
    }

    public void setHistoryRecordBeans(List<HistoryRecordBean> historyRecordBeans) {
        this.historyRecordBeans = historyRecordBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 是否显示复选框
     */
    public void setIsShowCheckBox(){
        isShowCheckBox = !isShowCheckBox;
    }

    public Boolean getIsShowCheckBox(){
        return isShowCheckBox;
    }

    /**
     * 复选框打开的时候是不允许其他事件通过的
     */
    public void setIsClickAllow(){
        isClickAllow = !isClickAllow;
    }

    public Boolean getIsClickAllow(){
        return isClickAllow;
    }

    /**
     * 获取复选框选择数据
     * @return 复选框的状态
     */
    public Map<Integer,Boolean> getMapOfCheckBox(){
        return mapOfCheckBox;
    }

    public void setListOfDelete(List<Integer> listOfDelete) {
        this.listOfDelete = listOfDelete;
    }

    public List<Integer> getListOfDelete(){
        return this.listOfDelete;
    }

    public void setIsShowNoSelect(){
        isShowNoSelect = !isShowNoSelect;
    }

    public Boolean getIsShowNoSelect(){
        return this.isShowNoSelect;
    }

    /*********************************************************************************************
     **********************************************************************************************/

    /**
     * 初始化复选框，每个框都是不选中状态
     */
    public void initCheckBox(){
        //复选框全为不选
        for (int i = 0; i < allRecords.size(); i++){
            int position = allRecords.get(i).getListOfDay().size();
            for (int j = 0; j< position; j++){
                mapOfCheckBox.put(i*10+j,false);
            }
        }

        //待删除集合为空
        listOfDelete.clear();
        //全选为false
        isSelectAll = false;
        //默认取消全选是关闭的
        isShowNoSelect = false;
        this.notifyDataSetChanged();
    }

    /**
     * 将对应的item选中
     * @param position 当前item的序号
     */
    public void setSelectItem(int section , int position) {
        int id = section*10+position;
        try {
            //判断该复选框是否存在
            if (mapOfCheckBox.get(id) == null){
                throw new NullPointerException();
            }else{
                //对当前状态取反
                if (mapOfCheckBox.get(id)) {
                    mapOfCheckBox.put(id, false);
                } else {
                    mapOfCheckBox.put(id, true);
                }
                //notifyItemChanged(position);
                this.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            System.out.println("对应的复选框改变状态失败");
        }
    }

    /**
     * 复选框和待删除集合的信息同步，等到删除的时候直接删除list里面的id对应的记录即可
     * @param bean item指定的历史记录
     * @param position item在recyclerview的偏移量
     * @param isChecked 选中与否
     */
    public void syncMapAndListOfCheckBox(HistoryRecordBean bean, int section, Integer position , Boolean isChecked){
        if (isChecked) {
            //如果勾选了则将该item的id添加进入待删除集合
            mapOfCheckBox.put(section*10+position, true);
            listOfDelete.add(bean.getId());
        }else {
            //如果取消了复选框的勾选
            mapOfCheckBox.put(section*10+position,false);
            for (Integer i : listOfDelete){
                if (i.equals(bean.getId())){
                    listOfDelete.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * 全选
     */
    public void selectAll(){
        for (int i = 0; i < allRecords.size(); i++){
            int position = allRecords.get(i).getListOfDay().size();
            for (int j = 0; j< position; j++){
                mapOfCheckBox.put(i*10+j,true);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 渲染每条历史记录(item)
     * @param parent 每条历史记录的HistoryHolder的容器
     * @param viewType int
     * @return 渲染对应的Holder
     */
    @NonNull
    @Override
    protected HistoryHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义一个构造
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //创建卡片  参数——————  布局，组件组，是否是根节点
        View itemView = layoutInflater.inflate(R.layout.history_list_unit, parent, false);
        //返回组件卡片
        return new HistoryHolder(itemView);
    }

    /**
     * 渲染具体的FooterViewHolder
     * @param parent   FooterViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return 渲染对应的Holder
     */
    @Override
    protected HistoryFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.history_footer, parent, false);
        return new HistoryFooterViewHolder(itemView);
    }

    /**
     * 渲染具体的HeaderViewHolder
     *
     * @param parent   HeaderViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return 渲染对应的Holder
     */
    @Override
    protected HistoryHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.history_header, parent, false);
        return new HistoryHeaderViewHolder(itemView);
    }

    /**
     * 绑定HeaderViewHolder的数据，即历史记录的时间
     *
     * @param holder  HistoryHeaderViewHolder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindSectionHeaderViewHolder(HistoryHeaderViewHolder holder, int section) {
        //放时间进去即可
        holder.time.setText(allRecords.get(section).getTime());
        if (isShowCheckBox){
            holder.deleteRecordOfDay.setVisibility(View.VISIBLE);
        }else {
            holder.deleteRecordOfDay.setVisibility(View.GONE);
        }
    }

    /**
     * 绑定FooterViewHolder的数据，即删除按钮
     *
     * @param holder HistoryFooterViewHolder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindSectionFooterViewHolder(HistoryFooterViewHolder holder, int section) {
        //放删除按钮，删除按钮的值是历史是时间
        holder.deleteRecordOfToday.setText(allRecords.get(section).getTime());
    }

    /**
     * 绑定ItemViewHolder的数据。
     *
     * @param holder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindItemViewHolder(HistoryHolder holder, int section, int position) {
        HistoryListBean beans = allRecords.get(section);
        HistoryRecordBean bean = beans.getListOfDay().get(position);

        //放每天的历史记录进section
        Glide.with(holder.image.getContext()).load(bean.getHicon()).into(holder.image);
        holder.name.setText(bean.getHname());
        holder.url.setText(bean.getHurl());
        holder.id.setText(String.valueOf(bean.getId()));
        holder.time.setText(bean.getHdate());

        //复选框
        if (isShowCheckBox){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }
        //方案一实现方式
        //holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> mapOfCheckBox.put(position,isChecked));
        //方案二：更佳方式
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            syncMapAndListOfCheckBox(bean, section, position, isChecked);
        });

        if (mapOfCheckBox.get(section*10+position) == null){
            mapOfCheckBox.put(section*10+position,false);
        }
        holder.checkBox.setChecked(mapOfCheckBox.get(section*10+position));

        //长按事件
        if(onItemLongClickListener!=null) {
            holder.itemView.setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClick(holder.itemView,position);
                return false;
            });
        }

        //点击事件
        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(v ->{
                onItemClickListener.onItemClick(holder.itemView,section,position);
            });
        }
    }

    /**
     * header或者footer的个数
     * @return int
     */
    @Override
    protected int getSectionCount() {
        if (allRecords == null){
            return 0;
        }
        return allRecords.size();
    }

    /**
     * 每个header或者footer中包含具体的内容个数
     * @return int
     */
    @Override
    protected int getItemCountForSection(int section) {
        return allRecords.get(section).getListOfDay().size();
    }

    /**
     * 是否显示footer
     * @param section 一天的历史记录
     * @return 开/关
     */
    @Override
    protected boolean hasFooterInSection(int section) {
        return true;
    }

}
