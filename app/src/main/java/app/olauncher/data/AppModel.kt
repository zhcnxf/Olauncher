package app.olauncher.data

import android.os.UserHandle
import java.text.CollationKey

data class AppModel(
    val appLabels: List<String>,
    val key: CollationKey?,
    val appPackage: String,
    val activityClassName: String?,
    val user: UserHandle,
) : Comparable<AppModel> {

    val appLabel: String get() = appLabels.first()

    override fun compareTo(other: AppModel): Int = when {
        key != null && other.key != null -> key.compareTo(other.key)
        else -> appLabel.compareTo(other.appLabel, true)
    }
}