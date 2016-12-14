package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/12/2016.
 */
public class PrioritySpinnerAdapter extends BaseAdapter{

    private final Context mContext;
    private final String[] mNames;
    private final Integer[] mDrawables;

    public PrioritySpinnerAdapter(Context context, String[] names, Integer[] drawables) {

        mContext = context;
        mNames = names;
        mDrawables = drawables;
    }

    @Override
    public int getCount() {

        return mNames.length;
    }

    @Override
    public Integer getItem(int position) {

        return mDrawables[position];
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(null == view) {

            view = LayoutInflater.from(mContext).inflate(R.layout.priority_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mNameTextView = (TextView) view.findViewById(R.id.priorityTextView);

            view.setTag(holder);
        }
        else {

            holder = (ViewHolder) view.getTag();
        }

        holder.mNameTextView.setText(mNames[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {

        DropdownViewHolder holder;

        if(null == view) {

            view = LayoutInflater.from(mContext).inflate(R.layout.priority_item_dropdown, viewGroup, false);
            holder = new DropdownViewHolder();
            holder.mNameTextView = (TextView) view.findViewById(R.id.priorityTextView);
            holder.mColorTextView = (TextView) view.findViewById(R.id.colorTextView);

            view.setTag(holder);
        }
        else {

            holder = (DropdownViewHolder) view.getTag();
        }

        holder.mNameTextView.setText(mNames[position]);

        Drawable drawable = ContextCompat.getDrawable(mContext, mDrawables[position]);
        holder.mColorTextView.setBackground(drawable);

        return view;
    }

    private static class ViewHolder {

        TextView mNameTextView;
    }

    private static class DropdownViewHolder{

        TextView mNameTextView;
        TextView mColorTextView;
    }
}
