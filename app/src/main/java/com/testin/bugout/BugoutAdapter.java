package com.testin.bugout;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zongrui on 16/12/5.
 */

public class BugoutAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private String[] itemText;

    private String[] itemType;

    public BugoutAdapter(Context context){
        itemText = context.getResources().getStringArray(R.array.item_array);
        itemType = context.getResources().getStringArray(R.array.item_type_array);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_list, null);
        TextView textViewType = (TextView) convertView.findViewById(R.id.textview_type);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
        textView.setText(Html.fromHtml(itemText[position]));
        textViewType.setText(itemType[position]);
        int index = position % 3;
        if (index == 0) {
            imageview.setBackgroundResource(R.drawable.yellow);
        } else if (index == 1) {
            imageview.setBackgroundResource(R.drawable.violet);
        } else if (index == 2) {
            imageview.setBackgroundResource(R.drawable.orange);
        }
        return convertView;
    }
}
