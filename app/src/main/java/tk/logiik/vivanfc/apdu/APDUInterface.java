package tk.logiik.vivanfc.apdu;

import android.nfc.tech.IsoDep;

import java.io.IOException;

import tk.logiik.vivanfc.util.Formats;

public class APDUInterface {

    private static final byte FIELD_CLA = (byte) 0x94;

    private IsoDep isoDep;

    public APDUInterface(IsoDep isoDep) {
        this.isoDep = isoDep;
    }

    public void connect() throws IOException {
        isoDep.connect();
    }

    public void close() throws IOException {
        isoDep.close();
    }

    public boolean verify(String pin) throws IOException, APDUOperationFailedException {
        byte[] pinBytes = pin.getBytes();
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,      // CLA: Proprietary code
                0x20,           // INS: Verify instruction code
                0x80,           // P1:  Proprietary code (RFU)
                0x00,           // P2:  No information given
                pinBytes.length,// Lc:  Data length
                pinBytes        // Pin data
        );

        byte[] result = isoDep.transceive(request);

        return checkSuccess(result);
    }

    public boolean selectById(String id) throws IOException, APDUOperationFailedException {
        byte[] idBytes = Formats.stringToByteArray(id);
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,      // CLA: Proprietary code
                0xA4,           // INS: Select instruction code
                0x00,           // P1:  Select MF, MD or EF by ID
                0x00,           // P2:  Select first record
                idBytes.length, // Lc:  Data length
                idBytes         // Id data
        );

        byte[] result = isoDep.transceive(request);

        return checkSuccess(result);
    }

    public boolean selectByPath(String path) throws IOException, APDUOperationFailedException {
        byte[] pathBytes = Formats.stringToByteArray(path.replace("/", ""));
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,          // CLA: Proprietary code
                0xA4,               // INS: Select instruction code
                0x08,               // P1:  Select from MF by Path
                0x00,               // P2:  Select first record
                pathBytes.length,   // Lc:  Data length
                pathBytes           // Id data
        );

        byte[] result = isoDep.transceive(request);

        return checkSuccess(result);
    }

    public boolean resetSelection() throws IOException, APDUOperationFailedException {
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,      // CLA: Proprietary code
                0xA4,           // INS: Select instruction code
                0x00,           // P1:  Select MF, MD or EF by ID
                0x00            // P2:  Select first record
        );

        byte[] result = isoDep.transceive(request);

        return checkSuccess(result);
    }

    public byte[] read() throws IOException, APDUOperationFailedException {
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,      // CLA: Proprietary code
                0xB2,           // INS: Read instruction code
                0x01,           // P1:  Record index
                0x04            // P2:  Read record P1
        );

        byte[] result = isoDep.transceive(request);

        checkSuccess(result);

        return result;
    }

    public byte[] readAll() throws IOException, APDUOperationFailedException {
        byte[] request = Formats.buildByteArray(
                FIELD_CLA,      // CLA: Proprietary code
                0xB2,           // INS: Read instruction code
                0x01,           // P1:  Record index
                0x05            // P2:  Read all records
        );

        byte[] result = isoDep.transceive(request);

        checkSuccess(result);

        return result;
    }

    private boolean checkSuccess(byte[] response) throws APDUOperationFailedException {
        byte[] statusBytes = new byte[2];
        statusBytes[0] = response[response.length - 2];
        statusBytes[1] = response[response.length - 1];

        if(statusBytes[0] != (byte) 0x90 || statusBytes[1] != (byte) 0x00) {
            throw new APDUOperationFailedException(statusBytes);
        }

        return true;
    }

}
