package todo.javier.mera.todolist.adapters;

/**
 * Created by javie on 12/8/2016.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
