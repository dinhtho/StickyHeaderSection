package org.mym.ui.decoration.library;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class StickyHeaderAdapter<IH extends RecyclerView.ViewHolder,
        HH extends RecyclerView.ViewHolder,H,I> extends RecyclerView.Adapter<IH>
        implements StickyHeaderHelper<HH,H,I> {

    private List<I> items = new ArrayList<>();
    private List<Section<H,I>> sections;
    private StickyHeaderDecoration decoration;

    public StickyHeaderDecoration getDecoration() {
        return decoration;
    }

    @Override
    public void setDecoration(StickyHeaderDecoration decoration) {
        this.decoration = decoration;
    }

    public StickyHeaderAdapter(List<Section<H,I>> sectionList) {
        updateView(sectionList);
    }

    public void updateSectionList(List<Section<H,I>> sectionList) {
        updateView(sectionList);
        decoration.invalidate();
        notifyDataSetChanged();
    }

    private void updateView(List<Section<H,I>> sectionList) {
        this.sections = sectionList;
        getItemList();
        returnItemList(items);
    }

    private void getItemList() {
        items.clear();
        for (Section<H,I> s : sections) {
            items.addAll(s.getItemList());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public List<Section<H,I>> getSections() {
        return sections;
    }

}
