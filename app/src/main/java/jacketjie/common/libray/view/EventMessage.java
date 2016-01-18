package jacketjie.common.libray.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/test_1/12.
 */
public class EventMessage  implements Parcelable{
    private int count;
    private boolean isExpandable;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setIsExpandable(boolean isExpandable) {
        this.isExpandable = isExpandable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeByte(isExpandable ? (byte) 1 : (byte) 0);
    }

    public EventMessage() {
    }

    protected EventMessage(Parcel in) {
        this.count = in.readInt();
        this.isExpandable = in.readByte() != 0;
    }

    public static final Creator<EventMessage> CREATOR = new Creator<EventMessage>() {
        public EventMessage createFromParcel(Parcel source) {
            return new EventMessage(source);
        }

        public EventMessage[] newArray(int size) {
            return new EventMessage[size];
        }
    };
}
