# diktya2022

Used OpenJDK 18.

## Build

1. In `META-INF/MANIFEST.MF` change `Main-Class` to `Server.Server` and `Build` -> `Build Artifacts` -> `Server:jar` -> `Build`
2. In `META-INF/MANIFEST.MF` change `Main-Class` to `Client.Client` and `Build` -> `Build Artifacts` -> `Client:jar` -> `Build`

## Run

1. `java -jar Server.jar`
2. `java -jar Client.jar <host> <port> <command> <args>`
