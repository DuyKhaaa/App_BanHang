package com.example.do_an.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.model.Options;

import java.util.List;

public class OptionsApdater extends BaseAdapter {
    private List<Options> optionList;
    private LayoutInflater inflater;

    public OptionsApdater(Context context, List<Options> optionList) {
        this.optionList = optionList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return optionList.size();
    }

    @Override
    public Options getItem(int position) {
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_option, parent, false);
            holder = new ViewHolder();
            holder.optionTextView = convertView.findViewById(R.id.optionTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Options option = optionList.get(position);
        holder.optionTextView.setText(option.getOption());

        return convertView;
    }

    static class ViewHolder {
        TextView optionTextView;
    }
}
