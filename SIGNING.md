# Signing Configuration

This project uses signing configurations for debug and release builds.

## Debug Build
- Uses the debug keystore located at `keystore/debug.keystore`
- This keystore is committed to the repository for development convenience
- Credentials:
  - Alias: `debug`
  - Store password: `android`
  - Key password: `android`

## Release Build
- Uses the release keystore located at `keystore/release.keystore`
- This keystore is **NOT** committed to the repository (in .gitignore)
- Credentials are stored in `keystore.properties` file (also in .gitignore)

### Setting up Release Signing

1. Ensure you have the `keystore/release.keystore` file (not in version control)
2. Create a `keystore.properties` file in the root directory with:
   ```properties
   storePassword=munchkin2024
   keyPassword=munchkin2024
   keyAlias=release
   storeFile=../keystore/release.keystore
   ```

### Building
- Debug: `./gradlew assembleDebug`
- Release: `./gradlew assembleRelease` (requires keystore.properties)

### Keystore Generation Commands
```bash
# Debug keystore (already created and committed)
keytool -genkeypair -alias debug -keyalg RSA -keysize 2048 -validity 10000 -keystore keystore/debug.keystore -storepass android -keypass android -dname "CN=Android Debug,O=Android,C=US"

# Release keystore (create locally, do not commit)
keytool -genkeypair -alias release -keyalg RSA -keysize 2048 -validity 10000 -keystore keystore/release.keystore -storepass munchkin2024 -keypass munchkin2024 -dname "CN=Munchkin Counter,O=Munchkin Games,C=US"
```