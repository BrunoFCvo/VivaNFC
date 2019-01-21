package tk.logiik.vivanfc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BitReader {

    private static final long EPOCH = 852076800L;     // 1 - Jan - 1997
    private static final long SECONDS_DAY = 86400;    // 24 * 60 * 60

    private String bits;
    private int current;

    public BitReader(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            String binary = "00000000" + Integer.toBinaryString((int) b);
            binary = binary.substring(binary.length() - 8);

            sb.append(binary);
        }

        bits = sb.toString();
        current = 0;
    }

    public void reset() {
        current = 0;
    }

    public String read(int size) {
        String res = bits.substring(current, current + size);
        current += size;
        return res;
    }

    public int readInt(int size) {
        return Integer.parseInt(read(size), 2);
    }

    public Date readDate(int size) {
        int nrDays = Integer.parseInt(read(size), 2);
        return new Date((EPOCH + nrDays) * 1000);
    }

    public Date readDateDays(int size) {
        int nrDays = Integer.parseInt(read(size), 2);
        return new Date((EPOCH + nrDays * SECONDS_DAY) * 1000);
    }

    public Date readDateString(int size) {
        String yearString   = Integer.toHexString(Integer.parseInt(read(16), 2));
        String monthString  = Integer.toHexString(Integer.parseInt(read(8), 2));
        String dayString    = Integer.toHexString(Integer.parseInt(read(8), 2));

        int year    = Integer.parseInt(yearString);
        int month   = Integer.parseInt(monthString);
        int day     = Integer.parseInt(dayString);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public boolean hasNext() {
        String bits = read(16);
        return !"1001000000000000".equals(bits);
    }

    public void skip(int size) {
        current += size;
    }

}
