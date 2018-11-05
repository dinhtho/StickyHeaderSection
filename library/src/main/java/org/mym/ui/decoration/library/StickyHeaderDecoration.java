package org.mym.ui.decoration.library;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * sticky header decoration.
 * Created by muyangmin on Aug 01, 2017.
 */
@SuppressWarnings("WeakerAccess")
public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {

    private LongSparseArray<RecyclerView.ViewHolder> mHeaderCache;

    private StickyHeaderAdapter mAdapter;

    private List<Integer> headerIndexes;

    private boolean isSticky = true;

    public void setSticky(boolean sticky) {
        isSticky = sticky;
    }

    /**
     * @param adapter the sticky header adapter to use
     */
    public StickyHeaderDecoration(StickyHeaderAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setDecoration(this);
        headerIndexes = getHeaderIndexes(adapter.getSectionList());
        mHeaderCache = new LongSparseArray<>();
    }

    private List<Integer> getHeaderIndexes(List<Section> sectionList) {
        List<Integer> headerList = new ArrayList<>();
        headerList.add(0);
        for (Section s : sectionList) {
            headerList.add(headerList.get(headerList.size() - 1) + s.getItemList().size());
        }
        headerList.remove(headerList.get(headerList.size() - 1));
        return headerList;
    }

    /**
     * Clear header cache. This is useful for adapter data set changes.
     */
    public void invalidate() {
        headerIndexes = getHeaderIndexes(mAdapter.getSectionList());
        mHeaderCache.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        int position = parent.getChildAdapterPosition(view);
        int headerHeight = 0;

        if (position != RecyclerView.NO_POSITION
                && hasHeader(position) && shouldShowHeader(position)) {
            headerHeight = getHeader(parent, position).itemView.getHeight();
        }

        outRect.set(0, headerHeight, 0, 0);
    }

    public int getHeaderId(int childAdapterPosition) {
        for (int i = 0; i < headerIndexes.size(); i++) {
            if (childAdapterPosition >= headerIndexes.get(headerIndexes.size() - 1)) {
                return headerIndexes.get(headerIndexes.size() - 1);
            } else if (childAdapterPosition >= headerIndexes.get(i) && childAdapterPosition < headerIndexes.get(i + 1)) {
                return headerIndexes.get(i);
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (isSticky) {
            final int count = parent.getChildCount();
            long previousHeaderId = -1;

            for (int layoutPos = 0; layoutPos < count; layoutPos++) {
                final View child = parent.getChildAt(layoutPos);
                final int adapterPos = parent.getChildAdapterPosition(child);

                if (adapterPos != RecyclerView.NO_POSITION && hasHeader(adapterPos)) {
                    long headerId = getHeaderId(adapterPos);

                    if (headerId > previousHeaderId) {
                        View header = getHeader(parent, adapterPos).itemView;
                        canvas.save();

                        final int left = child.getLeft();
                        final int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);
                        canvas.translate(left, top);

                        header.setTranslationX(left);
                        header.setTranslationY(top);
                        header.draw(canvas);
                        canvas.restore();
                        previousHeaderId = headerId;
                    }
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (!isSticky) {
            final int count = parent.getChildCount();
            for (int layoutPos = 0; layoutPos < count; layoutPos++) {
                final View child = parent.getChildAt(layoutPos);
                final int adapterPos = parent.getChildAdapterPosition(child);

                if (headerIndexes.contains(adapterPos)) {
                    View header = getHeader(parent, adapterPos).itemView;
                    canvas.save();

                    final int left = child.getLeft();
                    final int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);
                    canvas.translate(left, top);

                    header.setTranslationX(left);
                    header.setTranslationY(top);
                    header.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    /**
     * Decide whether the item header should be shown. Default Rules:
     * 1. the first item header always should;
     * 2. if the item's header is different than last item, it should be shown, otherwise not.
     *
     * @param itemAdapterPosition adapter position, see RecyclerView docs.
     * @return {@code true} if this header should be shown.
     */
    protected boolean shouldShowHeader(int itemAdapterPosition) {
        //noinspection SimplifiableIfStatement
        if (itemAdapterPosition == 0) {
            return true;
        }
        return getHeaderId(itemAdapterPosition) != getHeaderId
                (itemAdapterPosition - 1);
    }

    /**
     * Check whether header exists on specified position.
     *
     * @return true if this position has a header.
     */
    public boolean hasHeader(int adapterPosition) {
        return getHeaderId(adapterPosition) != StickyHeaderHelper.NO_HEADER;
    }

    private RecyclerView.ViewHolder getHeader(RecyclerView parent, int adapterPosition) {
        final int key = getHeaderId(adapterPosition);

        RecyclerView.ViewHolder holder = mHeaderCache.get(key);
        if (holder == null) {
            holder = mAdapter.onCreateHeaderViewHolder(parent);
            final View header = holder.itemView;

            //noinspection unchecked
            mAdapter.onBindHeaderViewHolder(holder, headerIndexes.indexOf(key));

            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View
                    .MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight(), View
                    .MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams()
                            .width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams()
                            .height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());

            mHeaderCache.put(key, holder);
        }

        return holder;
    }

    private int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos, int
            layoutPos) {
        int headerHeight = header.getHeight();
        int top = ((int) child.getY()) - headerHeight;
        if (layoutPos == 0) {
            final int count = parent.getChildCount();
            final long currentId = getHeaderId(adapterPos);
            // find next view with header and compute the offscreen push if needed
            for (int i = 1; i < count; i++) {
                int adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i));
                if (adapterPosHere != RecyclerView.NO_POSITION) {
                    long nextId = getHeaderId(adapterPosHere);
                    if (nextId != currentId) {
                        final View next = parent.getChildAt(i);
                        final int offset = ((int) next.getY()) - (headerHeight + getHeader
                                (parent, adapterPosHere).itemView.getHeight());
                        if (offset < 0) {
                            return offset;
                        } else {
                            break;
                        }
                    }
                }
            }

            top = Math.max(0, top);
        }

        return top;
    }

}
