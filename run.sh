#!/bin/bash
gradlew build && cd  build/classes/main && java com.sahil.algo.Application && cd ../../../
echo "Finished task"
