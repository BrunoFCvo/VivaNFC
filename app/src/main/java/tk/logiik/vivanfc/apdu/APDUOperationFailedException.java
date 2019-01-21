package tk.logiik.vivanfc.apdu;

import tk.logiik.vivanfc.util.Formats;

public class APDUOperationFailedException extends Exception {

    public APDUOperationFailedException(byte[] stausBytes) {
        super("APDU ERROR CODE " + Formats.byteArrayToHexString(stausBytes));
    }

}
