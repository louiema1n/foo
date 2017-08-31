package com.lm.utils;

import com.lm.domain.Catetree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Louie on 2017-07-07.
 */
public class CateToTree {

    public String getTreeStr(List<Catetree> catetrees) {
        String str = null;
        List<Catetree> newCatetree = new ArrayList<>();
        List<Catetree> distTree = buildTree(catetrees, newCatetree, 0);
        for (Catetree catetree : distTree) {
            str += "<li><a href='show/" + catetree.getPid() + "'>" + catetree.getName() + "</a>";
            if (catetree.getCatetrees().size() > 0) {
                for (Catetree c : catetree.getCatetrees()) {

                }
            }
        }
        return str;
    }

    public List<Catetree> buildTree(List<Catetree> catetrees, List<Catetree> newCatetree, long pid) {
        for (Catetree catetree : catetrees) {
            if (pid == catetree.getPid()) {
                newCatetree.add(catetree);
                buildTree(catetrees, catetree.getCatetrees(), catetree.getId());
            }
        }
        return newCatetree;
    }
}
