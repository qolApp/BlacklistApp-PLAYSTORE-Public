package pe.com.gianbravo.blockedcontacts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import pe.com.gianbravo.blockedcontacts.databinding.ActivityCallBinding
import pe.com.gianbravo.blockedcontacts.domain.CallManager
import pe.com.gianbravo.blockedcontacts.domain.GsmCall
import java.util.concurrent.TimeUnit

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class CallActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "CallActivity"
    }

    private lateinit var binding: ActivityCallBinding


    private var updatesDisposable = Disposables.empty()
    private var timerDisposable = Disposables.empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)

        hideBottomNavigationBar()

        binding.buttonHangup.setOnClickListener { CallManager.cancelCall() }
        binding.buttonAnswer.setOnClickListener { CallManager.acceptCall() }
    }

    override fun onResume() {
        super.onResume()
        updatesDisposable = CallManager.updates()
            .doOnEach { Log.i(LOG_TAG, "updated call: $it") }
            .doOnError { throwable -> Log.e(LOG_TAG, "Error processing call", throwable) }
            .subscribe { updateView(it) }
    }

    private fun updateView(gsmCall: GsmCall) {
        binding.textStatus.visibility = when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> View.GONE
            else -> View.VISIBLE
        }
        binding.textStatus.text = when (gsmCall.status) {
            GsmCall.Status.CONNECTING -> "Connecting…"
            GsmCall.Status.DIALING -> "Calling…"
            GsmCall.Status.RINGING -> "Incoming call"
            GsmCall.Status.ACTIVE -> ""
            GsmCall.Status.DISCONNECTED -> "Finished call"
            GsmCall.Status.UNKNOWN -> ""
        }
        binding.textDuration.visibility = when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> View.VISIBLE
            else -> View.GONE
        }
        binding.buttonHangup.visibility = when (gsmCall.status) {
            GsmCall.Status.DISCONNECTED -> View.GONE
            else -> View.VISIBLE
        }

        if (gsmCall.status == GsmCall.Status.DISCONNECTED) {
            binding.buttonHangup.postDelayed({ finish() }, 3000)
        }

        when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> startTimer()
            GsmCall.Status.DISCONNECTED -> stopTimer()
            else -> Unit
        }

        binding.textDisplayName.text = gsmCall.displayName ?: "Unknown"

        binding.buttonAnswer.visibility = when (gsmCall.status) {
            GsmCall.Status.RINGING -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        updatesDisposable.dispose()
    }

    private fun hideBottomNavigationBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun startTimer() {
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { binding.textDuration.text = it.toDurationString() }
    }

    private fun stopTimer() {
        timerDisposable.dispose()
    }

    private fun Long.toDurationString() =
        String.format("%02d:%02d:%02d", this / 3600, (this % 3600) / 60, (this % 60))
}