package pe.com.gianbravo.blockedcontacts.utils

import android.content.Context
import android.net.Uri
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.toast
import java.io.BufferedOutputStream

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileUtils {
	companion object {
		fun writeFile(context: Context, outputJson: String, uri: Uri): Boolean {
			var bos: BufferedOutputStream? = null
			// Now read the file
			try {
				bos = BufferedOutputStream(context.contentResolver.openOutputStream(uri))
				bos.write(outputJson.toByteArray())
				bos.close()

			}
			catch (e: Exception) {
				// Notify User of fail
				context.toast(context.getString(R.string.text_removed_number))
				return false
			}
			finally {
				try {
					if (bos != null) {
						bos.flush()
						bos.close()
					}
				}
				catch (ignored: Exception) {
				}
			}
			return true
		}
	}
}