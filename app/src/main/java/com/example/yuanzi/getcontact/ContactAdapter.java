package com.example.yuanzi.getcontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuanzi on 2017/6/16.
 */
public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<ContactInfo> list;

    public ContactAdapter(Context context, List<ContactInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 当第一次使用，view为空的时候
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.main_contact, null);
        }

        // 获取联系人对象
        ContactInfo cinfor = list.get(position);

        TextView cname = (TextView) convertView.findViewById(R.id.m_name);
        TextView cnumber = (TextView) convertView.findViewById(R.id.m_number);

        cname.setText(cinfor.getName());
        cnumber.setText(cinfor.getPhonenumber());

        return convertView;
    }
}
