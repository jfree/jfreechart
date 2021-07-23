package org.jfree.chart.labels;

/**
 * The clip type for the label. Only used when
 * {@link ItemLabelAnchor#isInternal()} returns {@code true}, if {@code false}
 * {@code labelClip} is always considered to be {@link ItemLabelClip#NONE})
 */
public enum ItemLabelClip {
    /** Only draw label when it fits the item */
    FIT,
    /** No clipping, labels might overlap */
    NONE,
    /** Does not draw outside the item, just clips the label */
    CLIP,
    /** Truncates the label with '...' to fit the item */
    TRUNCATE,
    /** Truncates the label on whole words with '...' to fit the item */
    TRUNCATE_WORD
}