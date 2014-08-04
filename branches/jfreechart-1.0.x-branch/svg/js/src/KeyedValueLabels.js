/* 
 * JFreeChart
 * ----------
 * Copyright (C) 2014 Object Refinery Limited.
 */

"use strict";

if (!jfc) jfc = {};

/**
 * Constructor for a new KeyedValueLabels instance
 * @constructor
 */
jfc.KeyedValueLabels = function() {
    if (!(this instanceof orsoncharts.KeyedValueLabels)) {
	return new orsoncharts.KeyedValueLabels();
    }
    this.format = "{K} = {V}";
    this.valueDP = 2;
    this.percentDP = 2;
};

// Generates a label for an item in a KeyedValuesDataset.
jfc.KeyedValueLabels.prototype.itemLabel = function(keyedValues, itemIndex) {
    var labelStr = new String(this.format);
    var keyStr = keyedValues.key(itemIndex);
    var value = keyedValues.valueByIndex(itemIndex);
    var valueStr = value.toFixed(this.valueDP);
    var total = keyedValues.total();
    var percentStr = (value / total * 100).toFixed(this.percentDP);
    labelStr = labelStr.replace(/{K}/g, keyStr);
    labelStr = labelStr.replace(/{V}/g, valueStr);
    labelStr = labelStr.replace(/{P}/g, percentStr);
    return labelStr;
};
