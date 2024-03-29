package pe.com.gianbravo.blockedcontacts

import android.content.Intent
import android.telecom.Call
import android.telecom.InCallService
import android.util.Log
import pe.com.gianbravo.blockedcontacts.domain.CallManager

/**
 * @author Giancarlo Bravo Anlas
 *
 */

// This is needed in order to allow choosing <my app> as default Phone app

class CallService : InCallService() {

    companion object {
        private const val LOG_TAG = "CallService"
    }

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        Log.i(LOG_TAG, "onCallAdded: $call")
        call.registerCallback(callCallback)
        startActivity(Intent(this, CallActivity::class.java))
        CallManager.updateCall(call)
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        Log.i(LOG_TAG, "onCallRemoved: $call")
        call.unregisterCallback(callCallback)
        CallManager.updateCall(null)
    }

    private val callCallback = object : Call.Callback() {
        override fun onStateChanged(call: Call, state: Int) {
            Log.i(LOG_TAG, "Call.Callback onStateChanged: $call, state: $state")
            CallManager.updateCall(call)
        }
    }

}