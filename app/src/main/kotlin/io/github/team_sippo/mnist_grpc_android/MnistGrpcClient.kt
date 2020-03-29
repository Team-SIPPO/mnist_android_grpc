package io.github.team_sippo.mnist_grpc_android

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusRuntimeException
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

import java.io.PrintWriter
import java.io.StringWriter

//
import io.github.team_sippo.mnist_grpc_android.proto.helloworld.*

/**
 * MnistGrpcClient
 */
class MnistGrpcClient
/** Construct client for accessing RouteGuide server using the existing channel.  */
internal constructor(private val channel: ManagedChannel) {
    private val blockingStub: GreeterGrpc.GreeterBlockingStub
            = GreeterGrpc.newBlockingStub(channel)

    /** Construct client connecting to server at `host:port`.  */
    constructor(host: String, port: Int) : this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build()) {
    }

    @Throws(InterruptedException::class)
    fun shutdown() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    /**
     * MEMO: Following section needs to be edited/added according to .proto
     */
    fun greet(name: String): String {
        logger.log(Level.INFO, "Will try to greet {0}...", name)
        logger.log(Level.INFO, "name..."+name)
        val request = HelloRequest.newBuilder().setName(name).build()
        logger.log(Level.INFO, "request..."+request)
        val response: HelloReply =  try {
            blockingStub.sayHello(request)
        } catch (e: StatusRuntimeException) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.status)
            logger.log(Level.INFO, "error..."+e.status)
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            pw.flush()
            return "Failed... : %s".format(sw)
        }
        logger.info("Greeting: ${response.message}")
        return response.message
    }

    /**
     * MEMO: Following section shows an example of implementation.
     */
    companion object {
        private val logger = Logger.getLogger(MnistGrpcClient::class.java.name)

        /**
         * Greet server. If provided, the first element of `args` is the name to use in the
         * greeting.
         */
        /*
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val client = MnistGrpcClient("localhost", 50051)
            try {
                /* Access a service running on the local machine on port 50051 */
                val user = if (args.size > 0) "${args[0]}" else "world"
                client.greet(user)
            } finally {
                client.shutdown()
            }
        }
         */

    }

}