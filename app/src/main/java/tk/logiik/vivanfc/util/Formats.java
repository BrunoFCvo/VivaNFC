package tk.logiik.vivanfc.util;

public class Formats {

    public static byte[] buildByteArray(Object... args) {
        // Calculate result size
        int resultSize = 0;

        for (Object arg : args) {
            if (arg instanceof byte[]) {
                resultSize += ((byte[]) arg).length;
            } else if (arg instanceof Byte || arg instanceof Integer) {
                resultSize++;
            }
        }

        // Build result
        byte[] result = new byte[resultSize];
        int index = 0;

        for (Object arg : args) {
            if (arg instanceof byte[]) {
                byte[] temp = (byte[]) arg;
                for (byte b : temp) {
                    result[index++] = b;
                }
            } else if (arg instanceof Byte) {
                result[index++] = (byte) arg;
            } else if (arg instanceof Integer) {
                result[index++] = (byte) (int) arg;
            }
        }

        return result;
    }

    public static byte[] stringToByteArray(String string) {
        char[] chars = string.toCharArray();
        byte[] result = new byte[chars.length / 2];

        for (int i = 0; i < chars.length; i += 2) {
            result[i / 2] = (byte) (
                    (Character.digit(chars[i], 16) << 4)
                            + Character.digit(chars[i+1], 16));
        }

        return result;
    }

    public static String byteArrayToHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();

        for (byte b : array) {
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }

}
