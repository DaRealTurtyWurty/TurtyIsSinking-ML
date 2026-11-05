# TurtyIsSinking

Minecraft 26.3 mod for Fabric and NeoForge, built with Java 25 and TurtyMultiloader 1.1.0.

Common code uses TurtyMultiloader for registration, networking, synchronized attachments, client renderers, client
ticks, and data generation. Loader modules contain the entrypoints, chat callbacks, and the jukebox/audio mixins that
differ between Fabric and NeoForge.

## Local dependency

The default dependency repository is `../TurtyMultiloader/build/local-maven`, resolving to
`E:\Programming\Java\Minecraft\TurtyMultiloader\build\local-maven` in this workspace. Publish the core library artifacts
from that checkout before building, and repeat after changing the library:

```powershell
.\gradlew.bat :common:publishMavenJavaPublicationToLocalRepository :fabric:publishMavenJavaPublicationToLocalRepository :neoforge:publishMavenJavaPublicationToLocalRepository
```

Back in this project:

```powershell
.\gradlew.bat build
```

Override `turtymultiloader_repository` and `turtymultiloader_version` with Gradle properties when using another checkout
or Maven repository. Both loader JARs embed the corresponding TurtyMultiloader artifact; players do not need to install
the library separately.

## Data generation

Either loader generates the shared resources in `common/src/generated/resources`:

```powershell
.\gradlew.bat :fabric:runDataGeneration
.\gradlew.bat :neoforge:runDatagen
```

The NeoForge task runs client and server datagen into separate build directories, then merges them into common
resources. Run the loader commands sequentially because they write the same destination.