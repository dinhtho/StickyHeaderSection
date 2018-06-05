package org.mym.ui.decoration.library;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class StickyHeaderAdapter<IH extends RecyclerView.ViewHolder,
        HH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<IH>
        implements StickyHeaderHelper<HH> {

    private List<?> itemList = new ArrayList<>();
    private List<? extends Section> sectionList;

    public StickyHeaderAdapter(List<? extends Section> sectionList) {
        updateView(sectionList);
    }

    public void updateSectionList(List<? extends Section> sectionList, StickyHeaderDecoration
            decoration) {
        updateView(sectionList);
        decoration.invalidate();
        notifyDataSetChanged();
    }

    private void updateView(List<? extends Section> sectionList) {
        this.sectionList = sectionList;
        getItemList();
        returnItemList(itemList);
    }

    private void getItemList() {
        itemList.clear();
        for (Section s : sectionList) {
            itemList.addAll(s.getItemList());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public List<? extends Section> getSectionList() {
        return sectionList;
    }

}
