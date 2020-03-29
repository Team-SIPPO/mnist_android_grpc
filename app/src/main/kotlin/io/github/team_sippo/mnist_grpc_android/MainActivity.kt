package io.github.team_sippo.mnist_grpc_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/** MEMO: Followings are added for this activity **/
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import java.lang.ref.WeakReference
import kotlinx.android.synthetic.main.activity_main.*

/** TODO: Following needs to be changed according to .proto. **/
import io.github.team_sippo.mnist_grpc_android.proto.helloworld.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * TODO: Followings need to be edited according to server setting.
     **/
    private val server_host : String = "localhost"
    private val server_port : String = "8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** MEMO: Followings need to be edited according to application interface. **/
        grpc_response_text!!.movementMethod = ScrollingMovementMethod()
        send_button!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        /** MEMO: Followings need to be edited according to application interface. **/
        send_button!!.isEnabled = false
        grpc_response_text!!.text = ""

        GrpcTask(this)
            .execute(
                server_host,
                message_edit_text!!.text.toString(),
                server_port)
    }

    /**
     * GrpcTask:
     */
    private class GrpcTask constructor(activity: Activity   ) : AsyncTask<String, Void, String>() {
        private val activityReference: WeakReference<Activity> = WeakReference(activity)

        private lateinit var client : MnistGrpcClient;
        override fun doInBackground(vararg params: String): String {

            /** Parameters to access to server **/
            val host = params[0]
            val message = params[1]
            val portStr = params[2]
            val port = if (TextUtils.isEmpty(portStr)) 0 else Integer.valueOf(portStr)

            /**
             * TODO: Followings need to be edited according to protocol.
             */
            client = MnistGrpcClient(host, port)
            return client.greet(message)

        }

        override fun onPostExecute(result: String) {

            /**
             * TODO: Followings need to be edited according to protocol.
             */
            client.shutdown()

            /** MEMO: Followings need to be edited according to application interface. **/
            val activity = activityReference.get() ?: return
            val resultText: TextView = activity.findViewById(R.id.grpc_response_text)
            val sendButton: Button = activity.findViewById(R.id.send_button)
            resultText.text = result
            sendButton.isEnabled = true
        }
    }
}
