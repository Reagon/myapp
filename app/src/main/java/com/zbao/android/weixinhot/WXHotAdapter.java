package com.zbao.android.weixinhot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zbao.android.R;
import com.zbao.android.entity.WeiXinHotInfo;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class WXHotAdapter extends RecyclerView.Adapter<WXHotAdapter.ViewHolder>{
    public List<WeiXinHotInfo.NewslistBean> datas = null;
    public Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public WXHotAdapter(List<WeiXinHotInfo.NewslistBean> datas,Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wx_hot,viewGroup,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取数据
                    mOnItemClickListener.onItemClick(v,v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(datas.get(position));
        viewHolder.date.setText(datas.get(position).getCtime());
        viewHolder.src.setText(datas.get(position).getDescription());
        viewHolder.title.setText(datas.get(position).getTitle());
        Glide.with(mContext).load(datas.get(position).getPicUrl()).centerCrop().into(viewHolder.image);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView src;
        public TextView title;
        public ImageView image;
        public ViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById(R.id.hot_date);
            image = (ImageView) view.findViewById(R.id.hot_image);
            src = (TextView) view.findViewById(R.id.hot_src);
            title = (TextView) view.findViewById(R.id.hot_title);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Object object);
    }

}
