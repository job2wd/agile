/*
 * $Id:$
 * $HeadURL:$
 * Copyright (c) 2014 Company, LJ. All Rights Reserved.
 */
package org.agile.me.common;

/**
 * 
 * @author wangwd
 * @version $Revision:$, $Date: 2014-10-8 上午11:40:49$
 * @LastChanged $Author:$, $Date:: #$
 */
public class ChineseDistinguisher {

  //GENERAL_PUNCTUATION 判断中文的"号

  // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号

  // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号

  public static boolean isChinese(char c) {

    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

      return true;

    }

    return false;

  }

  public static void main(String[] args) {

    System.out.println(isChinese('！'));

    System.out.println(isChinese('A'));

  }

}
