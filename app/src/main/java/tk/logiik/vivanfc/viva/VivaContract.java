package tk.logiik.vivanfc.viva;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

import tk.logiik.vivanfc.viva.values.VivaValues;

public class VivaContract implements Parcelable {

    private static final long MILLISECONDS_DAY = 86400000L;    // 24 * 60 * 60 * 1000

    private int operatorId;
    private int productId;
    private Date startDate;
    private int pointOfSaleId;
    private Date endDate;

    VivaContract() {}


    // operatorId
    void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    // productId
    void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    // start date
    void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    // point of sale id
    void setPointOfSaleId(int pointOfSaleId) {
        this.pointOfSaleId = pointOfSaleId;
    }

    public int getPointOfSaleId() {
        return pointOfSaleId;
    }

    // end date
    void setEndDate(Date startDate, int unitsId, int validity) {
        if (unitsId == VivaValues.PERIOD_DAYS) {
            endDate = new Date(startDate.getTime() + (validity * MILLISECONDS_DAY));
        } else {
            //TODO I have no idea if this is correct but it might be
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = calendar.getTime();
        }
    }

    public Date getEndDate() {
        return endDate;
    }



    // Parcelable stuff
    public static final Parcelable.Creator<VivaContract> CREATOR = new Parcelable.Creator<VivaContract>() {
        public VivaContract createFromParcel(Parcel in) {
            return new VivaContract(in);
        }

        public VivaContract[] newArray(int size) {
            return new VivaContract[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(operatorId);
        out.writeInt(productId);
        out.writeSerializable(startDate);
        out.writeInt(pointOfSaleId);
        out.writeSerializable(endDate);
    }

    private VivaContract(Parcel in) {
        operatorId      = in.readInt();
        productId       = in.readInt();
        startDate       = (Date) in.readSerializable();
        pointOfSaleId   = in.readInt();
        endDate         = (Date) in.readSerializable();
    }

}
