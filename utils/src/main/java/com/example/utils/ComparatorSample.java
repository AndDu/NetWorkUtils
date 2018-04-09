package com.example.utils;


import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

public class ComparatorSample implements Comparator<String> {

    // Collections.sort(list, new ComparatorSample());

    private static String ACB = "QWERTYUIOPASDFGHJKLMNBVCXZ#";

    @Override
    public int compare(String l, String r) {
        if (l == null || r == null) {
            return 0;
        }

        String[] ls = null;
        String[] rs = null;
        try {
            ls = PinyinHelper.toHanyuPinyinStringArray(l.toCharArray()[0]);
            rs = PinyinHelper.toHanyuPinyinStringArray(r.toCharArray()[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (ls == null || ls.length == 0) {
            ls = new String[]{l};
        }
        if (rs == null || rs.length == 0) {
            rs = new String[]{r};
        }
        String lhsSortLetters = ls[0].substring(0, 1).toUpperCase();
        String rhsSortLetters = rs[0].substring(0, 1).toUpperCase();
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}