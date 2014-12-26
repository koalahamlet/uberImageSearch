

#!/bin/bash

export ORG_GRADLE_PROJECT_storeFile="$(pwd)/MaslowKeystore.jks"
export ORG_GRADLE_PROJECT_storePassword="T3am17"
export ORG_GRADLE_PROJECT_keyAlias="Maslow"
export ORG_GRADLE_PROJECT_keyPassword="M4510w"

./gradlew signingReport

./gradlew assembleDebugTest

# Create apks directory if it doesn't exist.
if [ ! -d apks ]; then
   mkdir apks;
fi;

cp app/build/outputs/apk/knowl-dev-debug-*.apk apks/
rm app/build/outputs/apk/knowl-dev-debug-*.apk



