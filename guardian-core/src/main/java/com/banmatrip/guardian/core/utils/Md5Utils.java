package com.banmatrip.guardian.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author jepson
 * @Description:
 * @create 2017-11-27 15:55
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class Md5Utils {

    /**
     * MD5加密
     *
     * @param data
     *            待加密数据
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {

        // 执行消息摘要
        return DigestUtils.md5(data);
    }

    /**
     * MD5加密
     *
     * @param data
     *            待加密数据
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) {
        // 执行消息摘要
        return DigestUtils.md5Hex(data);
    }
}