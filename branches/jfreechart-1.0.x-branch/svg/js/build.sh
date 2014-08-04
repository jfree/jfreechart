#!/bin/bash

java -jar ~/jars/compiler.jar --warning_level VERBOSE --compilation_level WHITESPACE_ONLY \
--js src/Utils.js \
--js src/KeyedValuesDataset.js \
--js src/KeyedValueLabels.js \
--js_output_file jfreechart_utils.js