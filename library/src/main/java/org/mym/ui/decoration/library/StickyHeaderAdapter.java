package org.mym.ui.decoration.library;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class StickyHeaderAdapter<IH extends RecyclerView.ViewHolder,
        HH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<IH>
        implements StickyHeaderHelper<HH> {

    private List<?> itemList = new ArrayList<>();
    private List<Section> sectionList = new ArrayList<>();
    private StickyHeaderDecoration decoration;

    public StickyHeaderDecoration getDecoration() {
        return decoration;
    }

    @Override
    public void setDecoration(StickyHeaderDecoration decoration) {
        this.decoration = decoration;
    }

    public StickyHeaderAdapter(List<Section> sectionList) {
        updateView(sectionList);
    }

    public void updateSectionList(List<Section> sectionList) {
        updateView(sectionList);
        decoration.invalidate();
        notifyDataSetChanged();
    }

    private void updateView(List<Section> sectionList) {
        this.sectionList.clear();
        this.sectionList.addAll(sectionList);
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
    public List<Section> getSectionList() {
        return sectionList;
    }

}
