package com.tchristofferson.homeproducts.utils;

import org.springframework.data.domain.Page;

public class BasicUtil {
    
    public static int[] getNumberArray(Page<?> page) {
        int[] pages = new int[page.getTotalPages()];
        for (int i = 0; i < page.getTotalPages(); i++) {
            pages[i] = i + 1;
        }

        return pages;
    }

}
