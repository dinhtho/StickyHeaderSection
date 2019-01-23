/*
 * Copyright 2014 Eduardo Barrenechea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mym.ui.decoration.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * The adapter to assist the {@link StickyHeaderDecoration} in creating and binding the header views.
 *
 * @param <T> the header view holder
 */
public interface StickyHeaderHelper<T extends RecyclerView.ViewHolder,H,I> {

    /**
     * Indicate this item has no corresponding header.
     */
    long NO_HEADER = -1L;

    List<Section<H,I>> getSections();

    void returnItemList(List<I> itemList);

    void setDecoration(StickyHeaderDecoration decoration);

    @NonNull
    T onCreateHeaderViewHolder(ViewGroup parent);

    void onBindHeaderViewHolder(@NonNull T holder, int headerPosition);
}
