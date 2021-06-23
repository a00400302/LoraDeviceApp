package com.yxkj.loradeviceapp.util;

import com.blankj.utilcode.constant.CacheConstants;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import kotlin.UByte;

public class DataProcessUtil {
    public static long untilNow(Long time1) {
        return Math.abs((Long.valueOf(new Date().getTime()).longValue() - time1.longValue()) / 1000);
    }

    public static String byteArraytoHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X", Byte.valueOf(bytes[i])));
        }
        return sb.toString();
    }

    public static String byteArraytoHex2(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X ", Byte.valueOf(bytes[i])));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        if (s.length() % 2 != 0) {
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.insert(s.length() - 1, "0");
            s = stringBuilder.toString();
        }
        int len = s.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String hexToASCII(String hexValue) {
        String hexValue2 = hexValue.replace("00", "");
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue2.length(); i += 2) {
            output.append((char) Integer.parseInt(hexValue2.substring(i, i + 2), 16));
        }
        return output.toString();
    }

    public static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (char c : chars) {
            hex.append(Integer.toHexString(c));
        }
        return hex.toString();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String cardParse(String weigen, String data) {
        char c;
        String card = null;
        switch (weigen.hashCode()) {
            case 1575:
                if (weigen.equals("18")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1584:
                if (weigen.equals("1A")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1598:
                if (weigen.equals("20")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1600:
                if (weigen.equals("22")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                card = addZeroForNumHead(data, 8);
                break;
            case 1:
                byte[] temp = new byte[5];
                System.arraycopy(data, 0, temp, 0, 5);
                int i = (temp[0] << 31) | (temp[1] << 23) | (temp[2] << 15) | (temp[3] << 7) | temp[4];
                byte[] result = new byte[4];
                result[3] = (byte) (i & 255);
                result[2] = (byte) ((i >> 8) & 255);
                result[1] = (byte) ((i >> 16) & 255);
                result[0] = (byte) ((i >> 24) & 255);
                card = byteArraytoHex(result);
                break;
            case 2:
                byte[] temp2 = new byte[4];
                System.arraycopy(data, 0, temp2, 0, 4);
                int i2 = (temp2[0] << 31) | (temp2[1] << 23) | (temp2[2] << 15) | (temp2[3] << 7);
                byte[] result2 = new byte[3];
                result2[2] = (byte) ((i2 >> 8) & 255);
                result2[1] = (byte) ((i2 >> 16) & 255);
                result2[0] = (byte) ((i2 >> 24) & 255);
                card = byteArraytoHex(result2);
                break;
            case 3:
                card = addZeroForNumHead(data, 8);
                break;
        }
        return Long.toOctalString(Long.getLong(card).longValue());
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String parseCardNo(String weigen, String data) {
        char c;
        String cardNo = null;
        switch (weigen.hashCode()) {
            case 1575:
                if (weigen.equals("18")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1584:
                if (weigen.equals("1A")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1598:
                if (weigen.equals("20")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1600:
                if (weigen.equals("22")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                cardNo = data.substring(0, 8);
                break;
            case 2:
            case 3:
                cardNo = data.substring(0, 6);
                break;
        }
        char[] openArr = cardNo.toCharArray();
        int len = cardNo.length();
        for (int i = 0; i < cardNo.length() / 2; i++) {
            if (i % 2 == 0) {
                char temp = openArr[i];
                openArr[i] = openArr[(len - 2) - i];
                openArr[(len - 2) - i] = temp;
            } else {
                char temp2 = openArr[i];
                openArr[i] = openArr[len - i];
                openArr[len - i] = temp2;
            }
        }
        return Long.toString(Long.parseLong(String.valueOf(openArr), 16));
    }

    public static String formatCard(String cardNo) {
        String result;
        String data = removeHeadZero(cardNo);
        if (data.length() % 2 != 0) {
            result = addZeroForNumHead(data, data.length() + 1);
        } else {
            result = data;
        }
        char[] openArr = result.toCharArray();
        int len = result.length();
        for (int i = 0; i < result.length() / 2; i++) {
            if (i % 2 == 0) {
                char temp = openArr[i];
                openArr[i] = openArr[(len - 2) - i];
                openArr[(len - 2) - i] = temp;
            } else {
                char temp2 = openArr[i];
                openArr[i] = openArr[len - i];
                openArr[len - i] = temp2;
            }
        }
        return String.valueOf(openArr);
    }

    public static String parsePassword(String password) {
        StringBuffer sb = new StringBuffer();
        char[] chars = password.toCharArray();
        for (int i = 1; i < password.length(); i += 2) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    public static String removeHeadZero(String cardNo) {
        return cardNo.replaceFirst("^0*", "");
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

    public static String addZeroForNum(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        int strLen = str.length() / 2;
        if (strLen < length) {
            while (strLen < length) {
                StringBuffer sb = new StringBuffer("");
                sb.append(str).append("0");
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    public static String addNullStr(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        int strLen = str.length() / 2;
        if (strLen < length) {
            while (strLen < length) {
                StringBuffer sb = new StringBuffer("");
                sb.append(str).append(" ");
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    public static String addZeroForNumHead(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        int len = length - str.length();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < len; i++) {
            sb.append("0");
        }
        return sb.append(str).toString();
    }

    public static String parseCardNo(String cardNo) {
        char[] openArr = cardNo.toCharArray();
        int len = cardNo.length();
        for (int i = 0; i < cardNo.length() / 2; i++) {
            if (i % 2 == 0) {
                char temp = openArr[i];
                openArr[i] = openArr[(len - 2) - i];
                openArr[(len - 2) - i] = temp;
            } else {
                char temp2 = openArr[i];
                openArr[i] = openArr[len - i];
                openArr[len - i] = temp2;
            }
        }
        return Long.toString(Long.parseLong(String.valueOf(openArr), 16));
    }

    public static String extractCardno(byte[] cardnodata) {
        long t_cardno = 0;
        int i = cardnodata[0];
        while (i < 15) {
            t_cardno = (t_cardno << 8) | (((long) cardnodata[i == 1 ? 1 : 0]) & 255);
            i = (i == 1 ? 1 : 0) + 1;
        }
        return Long.toString((t_cardno >> 1) & 16777215);
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

    private static long getValue(byte[] recv, int offset, int len) {
        long result = 0;
        for (int i = 0; i < len; i++) {
            result = (result << 8) | ((long) (recv[offset + i] & UByte.MAX_VALUE));
        }
        return result;
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            str = str + Integer.toHexString(s.charAt(i));
        }
        return str;
    }

    public static String addZeroForDate(int data) {
        StringBuffer sb = new StringBuffer("");
        if (data < 10) {
            return sb.append("0").append(String.valueOf(data)).toString();
        }
        return String.valueOf(data);
    }

    public static String randomHexString(int len) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < len; i++) {
            try {
                result.append(Integer.toHexString(new Random().nextInt(225) + 16));
            } catch (Exception e) {
                e.printStackTrace();
                return "BB";
            } catch (Throwable th) {
                return "BB";
            }
        }
        return result.toString().toUpperCase();
    }

    public static byte decimalToBcdAscii(int value) {
        try {
            return (byte) Integer.valueOf(Integer.toString(value), 16).intValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getCmdData(String rnd, String cmd, String data) {
        String dataText = data.replace(" ", "");
        String cmdText = cmd.replace(" ", "");
        String rndText = rnd.replace(" ", "");
        byte[] dataArray = hexStringToByteArray(dataText);
        byte[] cmdArray = hexStringToByteArray(cmdText);
        byte[] rndArray = hexStringToByteArray(rndText);
        byte[] check = {cmdArray[0]};
        for (byte b : dataArray) {
            check[0] = (byte) (check[0] ^ b);
        }
        check[0] = (byte) (check[0] ^ rndArray[0]);
        return "AA " + rnd + cmd + data + byteArraytoHex(check) + "DD";
    }

    public static byte[] getCmdDataByteArray(String cmdData) {
        return hexStringToByteArray(cmdData.replace(" ", ""));
    }
}