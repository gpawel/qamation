#! /bin/sh
TARGET=/home/pavel/workspace/envs/jmeter_src/lib/ext
rm ./build/*.*
cp ./data-provider/target/*.jar ./build
cp ./excel-utils/target/*.jar ./build
cp ./jmeter-data-provider/target/*.jar ./build
cp ./jmeter-java-sampler-abstracts/target/*.jar ./build
cp ./jmeter-java-sampler-excel/target/*.jar ./build
cp ./jmeter-java-sampler-keyboard/target/*.jar ./build
cp ./jmeter-java-sampler-web-extension/target/*.jar ./build
cp ./jmeter-java-sampler-wmq/target/*.jar ./build
cp ./keyboard/target/*.jar ./build
cp ./navigator/target/*.jar ./build
cp ./utils/target/*.jar ./build
cp ./webdriver-utils/target/*.jar ./build
cp ./web-page/target/*.jar ./build
cp ./wmq-wrapper/target/*.jar ./build
cp ./build/*.jar $TARGET


