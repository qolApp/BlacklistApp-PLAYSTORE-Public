package pe.com.gianbravo.blockedcontacts.domain

/**
 * @author Giancarlo Bravo Anlas
 *
 */
data class GsmCall(val status: GsmCall.Status, val displayName: String?) {

    enum class Status {
        CONNECTING,
        DIALING,
        RINGING,
        ACTIVE,
        DISCONNECTED,
        UNKNOWN
    }
}