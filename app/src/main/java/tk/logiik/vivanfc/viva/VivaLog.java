package tk.logiik.vivanfc.viva;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class VivaLog implements Parcelable {

    private Date date;
    private int transitionId;
    private int operatorId;

    public VivaLog() {}



    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }



    public void setTransitionId(int transitionId) {
        this.transitionId = transitionId;
    }

    public int getTransitionId() {
        return transitionId;
    }



    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    // Parcelable stuff

    public static final Parcelable.Creator<VivaLog> CREATOR = new Parcelable.Creator<VivaLog>() {
        public VivaLog createFromParcel(Parcel in) {
            return new VivaLog(in);
        }

        public VivaLog[] newArray(int size) {
            return new VivaLog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeSerializable(date);
        out.writeInt(transitionId);
        out.writeInt(operatorId);
    }

    private VivaLog(Parcel in) {
        date            = (Date) in.readSerializable();
        transitionId    = in.readInt();
        operatorId      = in.readInt();
    }

}
