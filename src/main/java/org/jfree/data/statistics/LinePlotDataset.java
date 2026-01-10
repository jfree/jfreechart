package org.jfree.data.statistics;

import java.util.List;

public class LinePlotDataset<R extends Comparable<R>, C extends Comparable<C>> extends AbstractDataset
        implements StatisticalCategoryDataset<R, C>, RangeInfo, PublicCloneable {
    private List<C> data;

    /**
     * Creates a new line plot dataset.
     *
     */
    public LinePlotDataset() {
        this.data = new ArrayList<>();
    }

    /**
     * Returns the number of items in a line Plot.
     * 
     * @return The item count.
     */
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Removes all data from the line Plot
     */

    public void removeAllData() {
        this.data = new ArrayList<>();
    }

}
