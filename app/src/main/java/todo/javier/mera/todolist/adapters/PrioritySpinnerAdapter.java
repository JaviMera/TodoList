package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.Priority;

/**
 * Created by javie on 12/12/2016.
 */
public class PrioritySpinnerAdapter extends BaseAdapter{

    private final Context mContext;
    private final Priority[] mItems;

    public PrioritySpinnerAdapter(Context context, Priority[] priorities) {

        mContext = context;
        mItems = priorities;
    }

    @Override
    public int getCount() {

        return mItems.length;
    }

    @Override
    public Priority getItem(int position) {

        return mItems[position];
    }

    @Override
    public long getItemId(int position) {

        return mItems[position].hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        PrioritySpinnerViewHolder holder;

        if(null == view) {

            view = LayoutInflater.from(mContext).inflate(R.layout.priority_item, viewGroup, false);
            holder = new PrioritySpinnerViewHolder();
            holder.mNameTextView = (TextView) view.findViewById(R.id.priorityTextView);
            holder.mColorTextView = (TextView) view.findViewById(R.id.colorTextView);

            view.setTag(holder);
        }
        else {

            holder = (PrioritySpinnerViewHolder) view.getTag();
        }

        Priority priority = mItems[position];
        holder.mNameTextView.setText(priority.getName());

        Drawable drawable = ContextCompat.getDrawable(mContext, priority.getDrawable());
        holder.mColorTextView.setBackground(drawable);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class PrioritySpinnerViewHolder {

        TextView mNameTextView;
        TextView mColorTextView;
    }
}
