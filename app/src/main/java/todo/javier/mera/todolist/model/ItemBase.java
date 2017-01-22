package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 12/7/2016.
 */
public abstract class ItemBase implements Parcelable {

    private boolean mCanRemove;
    private boolean mIsMoving;
    private String mId;

    ItemBase(String id, boolean canRemove) {

        mId = id;
        mCanRemove = canRemove;
    }

    ItemBase(Parcel in) {

        mId = in.readString();
        mCanRemove = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mId);
        parcel.writeInt(mCanRemove ? 1 : 0);
    }

    @Override
    public int hashCode() {

        final int prime = 31;

        return prime * 1 + mId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this)
            return true;

        if(!(obj instanceof ItemBase)) {

            return false;
        }

        ItemBase item = (ItemBase)obj;

        return item.getId().equals(this.getId());
    }

    public void setCanRemove(boolean canRemove){

        mCanRemove = canRemove;
    }

    public boolean getCanRemove() {

        return mCanRemove;
    }

    public String getId() {

        return mId;
    }

    public void setMoving(boolean isMoving) {

        mIsMoving = isMoving;
    }

    public boolean isMoving() {

        return mIsMoving;
    }
}
