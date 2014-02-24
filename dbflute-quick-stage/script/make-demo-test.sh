#!/bin/bash

cd `dirname $0`

cd ..
pushd src/test/java/org/dbflute/quickstage/dbflute/thematic
cp PrototypeOfDemoTest.prototype DemoTest.java
popd

sh dbflute_exampledb/manage.sh refresh