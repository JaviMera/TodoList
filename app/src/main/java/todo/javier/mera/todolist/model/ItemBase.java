package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 12/7/2016.
 */
public abstract class ItemBase implements Parcelable {

    protected long mId;
    private boolean mCanRemove;
    private boolean mIsMoving;
    protected int mPosition;

    protected ItemBase(long id, int position, boolean canRemove) {

        mId = id;
        mPosition  = position;
        mCanRemove = canRemove;
    }

    public ItemBase(Parcel in) {

        mId = in.readLong();
        mPosition = in.readInt();
        mCanRemove = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(mId);
        parcel.writeInt(mPosition);
        parcel.writeInt(mCanRemove ? 1 : 0);
    }

    public void setCanRemove(boolean canRemove){

        mCanRemove = canRemove;
    }

    public boolean getCanRemove() {

        return mCanRemove;
    }

    public long getId() {

        return mId;
    }

    public void setMoving(boolean isMoving) {

        mIsMoving = isMoving;
    }

    public boolean isMoving() {

        return mIsMoving;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {

        mPosition = position;
    }
}
