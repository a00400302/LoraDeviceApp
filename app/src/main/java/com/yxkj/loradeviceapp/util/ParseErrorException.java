package com.yxkj.loradeviceapp.util;

import android.util.Log;

public class ParseErrorException extends Exception {
    public ParseErrorException(byte[] value, String bcd_accii_to_decimal) {
        Log.e("exception", bcd_accii_to_decimal);
        Log.e("exception_value", DataProcessUtil.byteArraytoHex(value));
    }

    public ParseErrorException(byte value, String bcd_accii_to_decimal) {
        Log.e("exception", bcd_accii_to_decimal);
    }
}