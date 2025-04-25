package pe.com.gianbravo.blockedcontacts

import android.content.Context
import android.widget.Toast

/**
 * @author Giancarlo Bravo Anlas
 *
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT ) {
    Toast.makeText(this, message, length).show()
}