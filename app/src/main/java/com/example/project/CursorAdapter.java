package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

//class to call the reminders from the database to the listview
public class CursorAdapter extends SimpleCursorAdapter {
    public CursorAdapter(Reminder context, int layout, Cursor c, String[]
            from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
    //overriding two methods in order to define viewholder
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.colInd = cursor.getColumnIndexOrThrow(DbAdapter.COL_IMPORTANT);
            holder.listview = view.findViewById(R.id.row_tab);
            view.setTag(holder);
        }if (cursor.getInt(holder.colInd) > 0) {
            holder.listview.setBackgroundColor(context.getResources().getColor(R.color.orange));
        } else {
            holder.listview.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }
    static class ViewHolder {
        int colInd; //store column index
        View listview; //store view

    }
}