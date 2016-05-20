package com.light.zenghaitao.popocm.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.light.zenghaitao.popocm.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ly-zenghaitao on 2016/4/27.
 */
public class ItemAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<HashMap<String, Object>> lists;

    public ItemAdapter(Context context, ArrayList<HashMap<String, Object>> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView){
            convertView = layoutInflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.initData();
        return convertView;
    }

    public class ViewHolder{
        private SimpleDraweeView sdv_item_first;
        private TextView tv_item_first;
        private SimpleDraweeView sdv_item_second;
        private TextView tv_item_second;

        public ViewHolder(View view){
            sdv_item_first = (SimpleDraweeView) view.findViewById(R.id.sdv_item_first);
            tv_item_first = (TextView) view.findViewById(R.id.tv_item_first);
            sdv_item_second = (SimpleDraweeView) view.findViewById(R.id.sdv_item_second);
            tv_item_second = (TextView) view.findViewById(R.id.tv_item_second);

        }

        public void initData(){
            Uri uri = Uri.parse("http://bbs.szlanyou.com/uc_server/avatar.php?uid=2043&size=middle");

            sdv_item_first.setAspectRatio(1.0f);
            sdv_item_second.setAspectRatio(1.0f);
            sdv_item_first.setImageURI(uri);
            sdv_item_second.setImageURI(uri);
        }
    }
}
