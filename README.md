# grpc on Android (Client)

## Preparation
This program assumes that server program has been already implemented.
Please prepare for server side program first before using this program.
You can use a sample program. See [official tutorial](https://grpc.io/docs/quickstart/android/)

## Configulation
1. Modify the proto file.
See [helloworld.proto](./app/src/main/proto/helloworld.proto). In the current program, sample hellowrold proto is used.

2. Modify the client behavior.
See [MnistGrpcClient](./app/src/main/kotlin/io/github/team_sippo/mnist_grpc_android/MnistGrpcClient.kt)

3. Modify the server setting (host, port).
See [MainActivity](./app/src/main/kotlin/io/github/team_sippo/mnist_grpc_android/MainActivity.kt)

## Build
1. Please make sure that Android device is connected to computer with debug mode.

2. Run the following command
```
# goto root directory of this program
$ cd mnist_grpc_android
# run gradle
$ ./gradlew installDist
```

You can also use Android Studio.

## Run
1. make sure that service program is running.

2. Configure USB portforwarding (execute the following command on computer)
```
$ adb reverse tcp:8080 tcp:50051
```
3. Open application on the device (name is mnist_grpc_android)

4. Input text and push send button

