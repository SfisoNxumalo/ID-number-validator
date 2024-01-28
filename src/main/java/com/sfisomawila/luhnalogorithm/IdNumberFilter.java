package com.sfisomawila.luhnalogorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdNumberFilter {

    public static String mFilterID(String id)
    {
        char ch = '*';

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(7);
        list.add(10);
        list.add(11);
        list.add(12);

        StringBuilder sb = new StringBuilder(id);

        // replace character at the specified position

        for(int i = 0; i < id.length(); i++){
            if(list.contains(i)){
                sb.setCharAt(i, ch);
            }
        }

        id = sb.toString();

        // print the modified string
        return id;
    }

    public String mHide(String id)
    {
        char ch = '*';

        StringBuilder sb = new StringBuilder(id);

        // replace character at the specified position
        for(int i = 0; i < sb.length(); i++)
        {
            sb.setCharAt(i, ch);
        }

        id = sb.toString();

        // print the modified string
        return id;
    }
}
