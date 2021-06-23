package com.yxkj.loradeviceapp.util;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import kotlin.UByte;


public class Converter2 {
    private static byte[] head = {127, 69, 76, 70};

    public static String toHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (byte b2 : b) {
            String plainText = Integer.toHexString(b2 & UByte.MAX_VALUE);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString().toUpperCase();
    }

    public static byte[] toByte(String hexString) {
        int length = hexString.length() / 2;
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) Integer.parseInt(hexString.substring(i * 2, (i * 2) + 2), 16);
        }
        return b;
    }

    public static String toString(byte[] b) {
        StringBuffer strBuff = new StringBuffer();
        for (byte b2 : b) {
            strBuff.append((char) b2);
        }
        return strBuff.toString();
    }

    public static long getValue(byte[] recv, int offset, int len) {
        long result = 0;
        for (int i = 0; i < len; i++) {
            result = (result << 8) | ((long) (recv[offset + i] & UByte.MAX_VALUE));
        }
        return result;
    }

    public static int getBitValue(byte value, int bitIndex) {
        return (byte) (((byte) (value >> bitIndex)) & 1);
    }

    public static byte decimalToBcdAscii(int value) {
        try {
            return (byte) Integer.valueOf(Integer.toString(value), 16).intValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long decimalToBcdAscii(long value) {
        try {
            return Long.valueOf(Long.toString(value), 16).longValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int bcdAsciiTodecimal(byte[] value, int offset, int len) throws ParseErrorException {
        try {
            StringBuffer tmp = new StringBuffer();
            for (int i = 0; i < len; i++) {
                String result = "0" + Integer.toHexString(value[offset + i] & UByte.MAX_VALUE);
                tmp.append(result.substring(result.length() - 2, result.length()));
            }
            return Integer.valueOf(tmp.toString(), 10).intValue();
        } catch (NumberFormatException e) {
            throw new ParseErrorException(value, "BCD_ACCII_TO_DECIMAL");
        }
    }

    public static int getBcdHiWord(byte value) throws ParseErrorException {
        try {
            return Integer.valueOf(Integer.toHexString(value >>> 4).toString(), 10).intValue();
        } catch (NumberFormatException e) {
            throw new ParseErrorException(value, "BCD_ACCII_TO_DECIMAL");
        }
    }

    public static int getBcdLowWord(byte value) throws ParseErrorException {
        try {
            return Integer.valueOf(Integer.toHexString(value & 15).toString(), 10).intValue();
        } catch (NumberFormatException e) {
            throw new ParseErrorException(value, "BCD_ACCII_TO_DECIMAL");
        }
    }

    public static boolean isHexString(String string) {
        try {
            Long.parseLong(string, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getHour(String timeStr) {
        if (timeStr == null || "".equals(timeStr)) {
            return 170;
        }
        try {
            return decimalToBcdAscii(Integer.valueOf(timeStr.split(":")[0]).intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return 170;
        }
    }

    public static int getMinute(String timeStr) {
        if (timeStr == null || "".equals(timeStr)) {
            return 170;
        }
        try {
            return decimalToBcdAscii(Integer.valueOf(timeStr.split(":")[1]).intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return 170;
        }
    }

    public static int Ineger2Int(Integer value, int defaultValue) {
        if (value != null) {
            return value.intValue();
        }
        return defaultValue;
    }

    public static byte[] cardNo2bytes(String cardNo, int CARD_LENGTH) {
        byte[] data = new byte[8];
        String cardNo2 = getCardNo(cardNo, CARD_LENGTH);
        if (CARD_LENGTH == 16) {
            int fc = Integer.valueOf(cardNo2.substring(0, 3)).intValue();
            int cardno = Integer.valueOf(cardNo2.substring(3, cardNo2.length())).intValue();
            data[5] = (byte) fc;
            data[6] = (byte) ((cardno >> 8) & 255);
            data[7] = (byte) (cardno & 255);
            return data;
        } else if (CARD_LENGTH == 24) {
            return long2bytes(Long.parseLong(cardNo2));
        } else {
            System.out.println("系统参数：Device.CardLen 设置不正确，应该为16 or 24");
            return data;
        }
    }

    public static String bytes2cardNo(byte[] data, int offset, int CARD_LENGTH) {
        if (CARD_LENGTH == 16) {
            long longHwCardNo = getValue(data, offset, 3);
            return Long.toString((((longHwCardNo >> 16) & 255) * 100000) + (65535 & longHwCardNo));
        } else if (CARD_LENGTH == 24) {
            return Long.toString(getValue(data, offset, 5));
        } else {
            System.out.println("系统参数：Device.CardLen 设置不正确，应该为16 or 24");
            return "";
        }
    }

    public static Date parseBcdDate(byte[] recv, int offset) throws ParseErrorException {
        if (recv == null) {
            return null;
        }
        int year = bcdAsciiTodecimal(recv, offset, 1) + 2000;
        int month = bcdAsciiTodecimal(recv, offset + 1, 1) - 1;
        int day = bcdAsciiTodecimal(recv, offset + 2, 1);
        int hour = bcdAsciiTodecimal(recv, offset + 4, 1);
        int minute = bcdAsciiTodecimal(recv, offset + 5, 1);
        int second = bcdAsciiTodecimal(recv, offset + 6, 1);
        if (month < 0 || day >= 32 || hour >= 24 || minute >= 60 || second >= 60) {
            return null;
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"), Locale.CHINESE);
        cal.set(year, month, day, hour, minute, second);
        return cal.getTime();
    }

    /* JADX INFO: Multiple debug info for r3v13 int: [D('total_second' long), D('second' int)] */
    public static Date parseZipDate(byte[] recv, int offset) {
        int day = (((recv[offset + 3] & UByte.MAX_VALUE) << 3) & 24) | (((recv[offset + 2] & UByte.MAX_VALUE) >>> 5) & 7);
        long total_second = ((((long) recv[offset + 2]) & 1) << 16) | (((long) (recv[offset + 1] & UByte.MAX_VALUE)) << 8) | ((long) (recv[offset + 0] & UByte.MAX_VALUE));
        int hour = ((int) total_second) / CacheConstants.HOUR;
        long total_second2 = total_second - ((long) (hour * CacheConstants.HOUR));
        int minute = ((int) total_second2) / 60;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"), Locale.CHINESE);
        cal.set((((recv[offset + 3] & UByte.MAX_VALUE) >>> 2) & 63) + 2000, (((recv[offset + 2] & UByte.MAX_VALUE) >>> 1) & 15) - 1, day, hour, minute, (int) (total_second2 - ((long) (minute * 60))));
        return cal.getTime();
    }

    public static long getTime(Date time, long defaultValue) {
        if (time != null) {
            return time.getTime();
        }
        return defaultValue;
    }

    public static byte[] int2bytes(int i) {
        byte[] b = new byte[4];
        b[3] = (byte) (i & 255);
        b[2] = (byte) ((65280 & i) >> 8);
        b[1] = (byte) ((16711680 & i) >> 16);
        b[0] = (byte) ((-16777216 & i) >> 24);
        return b;
    }

    public static int bytes2int(byte[] bytes) {
        return (bytes[3] & UByte.MAX_VALUE) | ((bytes[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bytes[1] << 16) & 16711680) | ((bytes[0] << 24) & ViewCompat.MEASURED_STATE_MASK);
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((int) (num >>> (56 - (i * 8))));
        }
        return b;
    }

    public static long bytes2long(byte[] b) {
        long res = 0;
        for (int i = 0; i < 8; i++) {
            res = (res << 8) | ((long) (b[i] & UByte.MAX_VALUE));
        }
        return res;
    }

    public static byte[] dateTime2Bytes(Date time) {
        byte[] data = new byte[4];
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        int d = cal.get(5);
        int i = ((cal.get(1) - 2000) << 26) | (d << 21) | ((cal.get(2) + 1) << 17) | ((cal.get(11) * CacheConstants.HOUR) + (cal.get(12) * 60) + cal.get(13));
        data[3] = (byte) (i & 255);
        data[2] = (byte) ((i >> 8) & 255);
        data[1] = (byte) ((i >> 16) & 255);
        data[0] = (byte) ((i >> 24) & 255);
        return data;
    }

    public static Date bytes2DateTime(byte[] data) {
        long temp = ((long) (((data[0] < 0 ? data[0] + 256 : data[0]) == 1 ? 1 : 0) << 24)) | ((long) (((data[1] < 0 ? data[1] + 256 : data[1]) == 1 ? 1 : 0) << 16)) | ((long) (((data[2] < 0 ? data[2] + 256 : data[2]) == 1 ? 1 : 0) << 8)) | ((long) (data[3] + UByte.MIN_VALUE));
        int second = (int) (131071 & temp);
        int hour = second / CacheConstants.HOUR;
        int second2 = second % CacheConstants.HOUR;
        Calendar c = Calendar.getInstance();
        c.set(1, ((int) ((temp >> 26) & 63)) + 2000);
        c.set(2, ((int) ((temp >> 17) & 15)) - 1);
        c.set(5, (int) ((temp >> 21) & 31));
        c.set(11, hour);
        c.set(12, second2 / 60);
        c.set(13, second2 % 60);
        return c.getTime();
    }

    public static Date bytes2Date(byte[] data) {
        long temp = ((long) (((data[0] < 0 ? data[0] + 256 : data[0]) == 1 ? 1 : 0) << 8)) | ((data[1] < 0 ? data[1] + 256 : data[1]) == 1 ? 1 : 0);
        Calendar c = Calendar.getInstance();
        c.set(1, ((int) ((temp >> 10) & 63)) + 2000);
        c.set(2, ((int) ((temp >> 1) & 15)) - 1);
        c.set(5, (int) ((temp >> 5) & 31));
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        return c.getTime();
    }

    public static String getCardNo(String cardNo, int CARD_LENGTH) {
        if (CARD_LENGTH == 16) {
            String s = "00000000" + cardNo;
            return s.substring(s.length() - 8);
        }
        String s2 = "000000000000" + cardNo;
        return s2.substring(s2.length() - 10);
    }

    public static byte[] floorToByte(String floor) {
        byte[] data = new byte[10];
        if (floor != null && floor.length() > 0) {
            for (String s : floor.split(",")) {
                int fId = Integer.valueOf(s).intValue();
                if (fId % 8 == 0) {
                    data[(fId / 8) - 1] = (byte) (((byte) ((int) Math.pow(2.0d, 7.0d))) | data[(fId / 8) - 1]);
                } else {
                    data[fId / 8] = (byte) (((byte) ((int) Math.pow(2.0d, (double) ((fId % 8) - 1)))) | data[fId / 8]);
                }
            }
        }
        return data;
    }

    public static byte[] dateToByte(String floor) {
        byte[] data = new byte[10];
        String[] str = floor.split("-");
        for (int i = 0; i < str.length; i++) {
            data[i] = (byte) Integer.parseInt(str[i]);
        }
        return data;
    }

    public static String getIpAddress(String ip) {
        if (!ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            return "000";
        }
        String s = "000" + ip.substring(ip.lastIndexOf(".") + 1);
        return s.substring(s.length() - 3);
    }

    public static byte[] bleNameToByte(String bleName) {
        byte[] data = new byte[9];
        if (bleName != null && !"".equals(bleName)) {
            try {
                byte[] b = bleName.getBytes("ascii");
                if (b.length > 9) {
                    System.arraycopy(b, 0, data, 0, 9);
                } else {
                    System.arraycopy(b, 0, data, 0, b.length);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static byte[] intArray2ByteArray(int[] intarr) {
        byte[] bt = new byte[(intarr.length * 4)];
        int j = 0;
        int k = 0;
        while (j < intarr.length) {
            int curint = intarr[j];
            bt[k] = (byte) ((curint >> 24) & 255);
            bt[k + 1] = (byte) ((curint >> 16) & 255);
            bt[k + 2] = (byte) ((curint >> 8) & 255);
            bt[k + 3] = (byte) ((curint >> 0) & 255);
            j++;
            k += 4;
        }
        return bt;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:9:0x0023 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:11:0x0027 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:19:0x002f */
    /* JADX DEBUG: Multi-variable search result rejected for r9v0, resolved type: byte[] */
    /* JADX DEBUG: Multi-variable search result rejected for r3v1, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r4v1, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r5v1, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r6v1, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r6v3, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r5v3, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r4v3, resolved type: int */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r6v2 */
    public static int[] byteArray2IntArray(byte[] btarr) {
        if (btarr.length % 4 != 0) {
            return null;
        }
        int[] intarr = new int[(btarr.length / 4)];
        int j = 0;
        int k = 0;
        while (j < intarr.length) {
            byte b = btarr[k];
            byte i2 = btarr[k + 1];
            byte i3 = btarr[k + 2];
            byte i4 = btarr[k + 3];
            int i1 = b;
            if (b < 0) {
                i1 = b + 256;
            }
            if (i2 < 0) {
                i2 += 256;
            }
            if (i3 < 0) {
                i3 += 256;
            }
            if (i4 < 0) {
                i4 += 256;
            }
            intarr[j] = ((i1 == 1 ? 1 : 0) << 24) + (0) + (0) + (0);
            j++;
            k += 4;
        }
        return intarr;
    }

    public static boolean inferNetwork(String remoteInetAddr) {
        try {
            return InetAddress.getByName(remoteInetAddr).isReachable(5000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getQrStr(long cardNo, Date date, int time) {
        StringBuffer sb = new StringBuffer();
        if (cardNo > 0 && time > 0) {
            byte[] card = long2bytes(cardNo);
            byte[] start = dateTime2Bytes(date);
            byte[] end = int2bytes(time);
            byte[] result = new byte[15];
            System.arraycopy(head, 0, result, 0, 4);
            System.arraycopy(card, 3, result, 4, 5);
            System.arraycopy(start, 0, result, 9, 4);
            System.arraycopy(end, 2, result, 13, 2);
            try {
                sb.append(new String(result, "ISO8859-1"));
                for (int i = 0; i < 2; i++) {
                    sb.append(UUID.randomUUID());
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
        return sb.toString();
    }
}