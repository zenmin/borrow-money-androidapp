package com.zm.borrowmoneyandriodapp.common.constant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:11
 */
public class CommonConstant {

    public static final String AUTH_KEY = "123456";

    public static final String LIMIT_USER = "LIMIT_USER";       // 用户限流

    public static final String LIMIT_ALL = "LIMIT_ALL";       // 所有用户限流 接口每秒请求限制

    public static final String LIMIT_USER_IP = "LIMIT_USER_IP";       // IP限流

    public static final String TOKEN_KEY = "TOKEN";

    public static final String ADMIN_TOKEN_KEY = "ADMIN-";

    public static final String ISADMIN = "ISADMIN";

    public enum BORROW_STATUS {

        REJECT(0, "审核不通过"),
        SQ(1, "借款申请"),
        SHZ(2, "审核中"),
        QYZ(3, "签约中"),
        FKZ(4, "放款中"),
        HKZ(5, "还款中");

        @Setter
        @Getter
        int code;

        @Setter
        @Getter
        String value;

        BORROW_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(int code) {
            BORROW_STATUS[] values = BORROW_STATUS.values();
            for (BORROW_STATUS b : values) {
                if (b.code == code) {
                    return b.value;
                }
            }
            return null;
        }

    }

}
