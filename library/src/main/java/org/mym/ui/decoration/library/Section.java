package org.mym.ui.decoration.library;

import java.util.List;

public class Section<H, I> {
    private H header;
    private List<I> itemList;

    public Section(H header, List<I> itemList) {
        this.header = header;
        this.itemList = itemList;
    }

    public void setItemList(List<I> itemList) {
        this.itemList = itemList;
    }

    public H getHeader() {
        return header;
    }

    public List<I> getItemList() {
        return itemList;
    }
}
