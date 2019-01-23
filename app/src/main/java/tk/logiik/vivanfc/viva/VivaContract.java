package tk.logiik.vivanfc.viva;

import android.os.Parcel;
import android.os.Parcelable;

public class VivaContract implements Parcelable {

    private int operatorId;
    private int productId;

    public VivaContract() {

    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getOperatorId() {
        return operatorId;
    }

    public int getProductId() {
        return productId;
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
    }

    private VivaContract(Parcel in) {
        operatorId      = in.readInt();
        productId       = in.readInt();
    }

}
