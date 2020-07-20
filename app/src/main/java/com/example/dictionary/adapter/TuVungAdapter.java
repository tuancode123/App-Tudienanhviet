package com.example.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.dao.TuVungDAO;
import com.example.dictionary.model.TuVung;

import java.util.List;

public class TuVungAdapter extends BaseAdapter {
    List<TuVung> listTuVung;
    public Context mContext;
    public LayoutInflater inflater;
    TuVungDAO tuVungDAO;

    public TuVungAdapter(Context context, List<TuVung> listTu_) {
        this.mContext = context;
        this.listTuVung = listTu_;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tuVungDAO = new TuVungDAO(context);
    }

    @Override
    public int getCount() {
        return listTuVung.size();
    }

    @Override
    public Object getItem(int i) {
        return listTuVung.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_translate, null);
            holder.tvEnglish = (TextView) convertView.findViewById(R.id.tvEnglish);
            holder.tvVietNam = (TextView) convertView.findViewById(R.id.tvVietNam);
            holder.tvSpelling = (TextView) convertView.findViewById(R.id.tvSpelling);
//            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    notifyDataSetChanged();
//                }
//            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        TuVung _entry = (TuVung) listTuVung.get(position);
        holder.tvEnglish.setText(_entry.getTuTA());
        holder.tvVietNam.setText(_entry.getTuTV());
        if (_entry.getPhienAm() != null) {
            holder.tvSpelling.setVisibility(View.VISIBLE);
            holder.tvSpelling.setText(_entry.getPhienAm());
        }
        return convertView;
    }

    public void setResultSearch(TuVung t) {
        listTuVung.removeAll(listTuVung);
        listTuVung.add(t);
        notifyDataSetChanged();
    }

    public void refreshList() {
        listTuVung.removeAll(listTuVung);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tvEnglish;
        TextView tvVietNam;
        TextView tvSpelling;
    }
}
