package tk.logiik.vivanfc.viva;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class VivaLog implements Parcelable {

    private Date date;
    private int contractId;
    private int transitionId;
    private int operatorId;
    private int readerId;
    private int lineId;
    private int stationId;

    VivaLog() {}


    // date
    void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    // contractId
    void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getContractId() {
        return contractId;
    }

    // transitionId
    void setTransitionId(int transitionId) {
        this.transitionId = transitionId;
    }

    public int getTransitionId() {
        return transitionId;
    }

    // operatorId
    void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    // readerId
    void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getReaderId() {
        return readerId;
    }

    // lineId
    void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getLineId() {
        return lineId;
    }

    // stationId
    void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationId() {
        return stationId;
    }



    // Parcelable Stuff
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
        out.writeInt(contractId);
        out.writeInt(transitionId);
        out.writeInt(operatorId);
        out.writeInt(readerId);
        out.writeInt(lineId);
        out.writeInt(stationId);
    }

    private VivaLog(Parcel in) {
        date            = (Date) in.readSerializable();
        contractId      = in.readInt();
        transitionId    = in.readInt();
        operatorId      = in.readInt();
        readerId        = in.readInt();
        lineId          = in.readInt();
        stationId       = in.readInt();
    }

}
