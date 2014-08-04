/* 
 * Copyright (C) 2014 Object Refinery Limited
 */

"use strict";

if (!jfc) jfc = {};

/**
 * Constructor for a new KeyedValuesDataset
 * @constructor
 */
orsoncharts.KeyedValuesDataset = function() {
  if (!(this instanceof jfc.KeyedValuesDataset)) {
    return new orsoncharts.KeyedValuesDataset();
  }
  this.data = [];
  this.listeners = [];
};

// returns the number of items in the dataset
orsoncharts.KeyedValuesDataset.prototype.itemCount = function() {
  return this.data.length;
};

// Returns true if the dataset contains no items and false otherwise
orsoncharts.KeyedValuesDataset.prototype.isEmpty = function() {
  return this.data.length === 0;
};

// returns the section key for the item with the specified index.
orsoncharts.KeyedValuesDataset.prototype.key = function(index) {
  return this.data[index][0];  
};

// Returns a new array containing all the keys for the dataset.
orsoncharts.KeyedValuesDataset.prototype.keys = function() {
  return this.data.map(function(d) { return d[0]; });
};

// returns the index of the item with the specified key, or -1
orsoncharts.KeyedValuesDataset.prototype.indexOf = function(sectionKey) {
  var arrayLength = this.data.length;
  for (var i = 0; i < arrayLength; i++) {
    if (this.data[i][0] === sectionKey) {
      return i;
    }
  }
  return -1;
};

// returns the value for the item with the specified index.
orsoncharts.KeyedValuesDataset.prototype.valueByIndex = function(index) {
  return this.data[index][1];
};

// returns the value for the item with the specified key 
orsoncharts.KeyedValuesDataset.prototype.valueByKey = function(sectionKey) {
  var sectionIndex = this.indexOf(sectionKey);
  if (sectionIndex < 0) return null;
  return this.valueByIndex(sectionIndex);
};

// Adds a listener to the dataset (the listener method will be called whenever 
// the dataset is modified)
orsoncharts.KeyedValuesDataset.prototype.addListener = function(listenerMethod) {
  this.listeners.push(listenerMethod);  
};

// Deregisters the specified listener so that it no longer receives
// notification of dataset changes
orsoncharts.KeyedValuesDataset.prototype.removeListener = function(listenerMethod) {
  var i = this.listeners.indexOf(listenerMethod);
  if (i >= 0) {
    this.listeners.splice(i, 1);
  }
};

// Notifies all registered listeners that there has been a change to this dataset
orsoncharts.KeyedValuesDataset.prototype.notifyListeners = function() {
  // TODO: call each listenerMethod
};

// adds the specified (key, value) pair to the dataset or, if the key exists
// already, updates the value
orsoncharts.KeyedValuesDataset.prototype.add = function(sectionKey, value) {
  this.data.push([sectionKey, value]);
};

// removes the item with the specified key
orsoncharts.KeyedValuesDataset.prototype.remove = function(sectionKey) {
  if (!sectionKey) throw new Error("The 'sectionKey' must be defined.");
  var i = this.indexOf(sectionKey);
  if (i < 0) throw new Error("The sectionKey '" + sectionKey.toString() 
      + "' is not recognised.");
  this.data.splice(i, 1);
};

// sets the data array based on the supplied JSON string
orsoncharts.KeyedValuesDataset.prototype.dataFromJSON = function(jsonStr) {
  this.data = JSON.parse(jsonStr);  
  this.notifyListeners();
};

orsoncharts.KeyedValuesDataset.prototype.removeByIndex = function(itemIndex) {
  this.data.splice(itemIndex, 1);
};

// returns the total of all non-null values for the specified dataset
orsoncharts.KeyedValuesDataset.prototype.totalForDataset = function(dataset) {
  var total = 0.0;
  var itemCount = dataset.itemCount();
  for (var i = 0; i < itemCount; i++) {
    var v = dataset.valueByIndex(i);
    if (v) {
      total = total + v;
    }
  }
  return total;
};

// returns the minimum value for the specified dataset
orsoncharts.KeyedValuesDataset.prototype.minForDataset = function(dataset) {
  var min = null;
  var itemCount = dataset.itemCount();
  for (var i = 0; i < itemCount; i++) {
    var v = dataset.valueByIndex(i);
    if (v) {
      if (min) {
        min = Math.min(min, v);
      } else {
        min = v;
      }
    }
  }
  return min;
};

// returns the maximum value for the specified dataset
orsoncharts.KeyedValuesDataset.prototype.maxForDataset = function(dataset) {
  var max = null;
  var itemCount = dataset.itemCount();
  for (var i = 0; i < itemCount; i++) {
    var v = dataset.valueByIndex(i);
    if (v) {
      if (max) {
        max = Math.max(max, v);
      } else {
        max = v;
      }
    }
  }
  return max;
};

// returns the total of all values in this dataset (ignoring null values)
orsoncharts.KeyedValuesDataset.prototype.total = function() {
  return this.totalForDataset(this);
};

// returns the minimum value in this dataset (ignoring null values)
orsoncharts.KeyedValuesDataset.prototype.min = function() {
  return this.minForDataset(this);
};

// returns the maximum value in this dataset (ignoring null values)
orsoncharts.KeyedValuesDataset.prototype.max = function() {
  return this.maxForDataset(this);
};

