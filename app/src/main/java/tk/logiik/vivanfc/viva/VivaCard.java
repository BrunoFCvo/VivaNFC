package tk.logiik.vivanfc.viva;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class VivaCard implements Parcelable {

    // Name
    private String name;

    // Environment
    private int issuerId;
    private int vivaCardId;
    private Date issueDate;
    private Date expirationDate;
    private Date birthDate;

    // Logs
    private List<VivaLog> logs;

    // Contracts
    private List<VivaContract> contracts;

    VivaCard() {
        logs        = new LinkedList<>();
        contracts   = new LinkedList<>();
    }


    // name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // issuer id
    void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public int getIssuerId() {
        return issuerId;
    }

    // issue date
    void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    // expiration date
    void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    // birth date
    void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    // viva card id
    void setVivaCardId(int vivaCardId) {
        this.vivaCardId = vivaCardId;
    }

    public String getVivaCardId() {
        return String.format(Locale.getDefault(), "%03d", issuerId) + " " +
                String.format(Locale.getDefault(), "%09d", vivaCardId);
    }

    // logs
    void addLog(VivaLog log) {
        logs.add(log);
    }

    public Iterator<VivaLog> getLogIterator() {
        return logs.iterator();
    }

    // contracts
    void addContract(VivaContract contract) {
        contracts.add(contract);
    }

    public Iterator<VivaContract> getContractIterator()  {
        return contracts.iterator();
    }



    // Parcelable stuff
    public static final Parcelable.Creator<VivaCard> CREATOR = new Parcelable.Creator<VivaCard>() {
        public VivaCard createFromParcel(Parcel in) {
            return new VivaCard(in);
        }

        public VivaCard[] newArray(int size) {
            return new VivaCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);

        out.writeInt(issuerId);
        out.writeInt(vivaCardId);
        out.writeSerializable(issueDate);
        out.writeSerializable(expirationDate);
        out.writeSerializable(birthDate);

        out.writeInt(logs.size());
        for(VivaLog l : logs) {
            out.writeParcelable(l, 0);
        }

        out.writeInt(contracts.size());
        for(VivaContract c : contracts) {
            out.writeParcelable(c, 0);
        }
    }

    private VivaCard(Parcel in) {
        name    = in.readString();

        issuerId        = in.readInt();
        vivaCardId      = in.readInt();
        issueDate       = (Date) in.readSerializable();
        expirationDate  = (Date) in.readSerializable();
        birthDate       = (Date) in.readSerializable();

        logs = new LinkedList<>();
        int logsSize = in.readInt();
        for(int i = 0; i < logsSize; i++) {
            logs.add((VivaLog) in.readParcelable(VivaLog.class.getClassLoader()));
        }

        contracts = new LinkedList<>();
        int contractsSize = in.readInt();
        for(int i = 0; i < contractsSize; i++) {
            contracts.add((VivaContract) in.readParcelable(VivaContract.class.getClassLoader()));
        }
    }

}
