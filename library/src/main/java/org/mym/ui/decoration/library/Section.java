package org.mym.ui.decoration.library;

import java.util.List;

public class Section<H, I> {
    private H Header;
    private List<I> itemList;

    public Section(H header, List<I> itemList) {
        Header = header;
        this.itemList = itemList;
    }

    public H getHeader() {
        return Header;
    }

    public List<I> getItemList() {
        return itemList;
    }
}
