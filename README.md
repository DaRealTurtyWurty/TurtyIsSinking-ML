# TurtyIsSinking

Minecraft 26.3 mod for Fabric and NeoForge, built with Java 25 and TurtyMultiloader 1.1.0.

Common code uses TurtyMultiloader for registration, networking, synchronized attachments, client renderers, client
ticks, and data generation. Loader modules contain the entrypoints, chat callbacks, and the jukebox/audio mixins that
differ between Fabric and NeoForge.

## Building

TurtyMultiloader 1.1.0 is resolved from its public Maven repository at
`https://dl.cloudsmith.io/public/turtywurty/turtymultiloader/maven/`.

```powershell
.\gradlew.bat build
```

Both loader JARs embed the corresponding published TurtyMultiloader artifact. No local library checkout or publishing
step is required, and players do not need to install the library separately.

## Data generation

Either loader generates the shared resources in `common/src/generated/resources`:

```powershell
.\gradlew.bat :fabric:runDataGeneration
.\gradlew.bat :neoforge:runDatagen
```

The NeoForge task runs client and server datagen into separate build directories, then merges them into common
resources. Run the loader commands sequentially because they write the same destination.

## Code quality

Railroad's quality plugin (`dev.railroadide.quality:1.0.0`) is resolved from
`https://maven.railroadide.dev/releases`. No local plugin checkout is required.

```powershell
.\gradlew.bat format
.\gradlew.bat formatCheck
.\gradlew.bat javadocCoverage
```

The root commands cover all three modules, and formatting also covers the root and `build-logic` Gradle scripts.
`check` and `build` enforce formatting. Javadoc reports are generated at
`<module>/build/reports/javadoc-coverage/index.html`; documentation coverage is informational unless you explicitly
run `javadocCoverageCheck`.
