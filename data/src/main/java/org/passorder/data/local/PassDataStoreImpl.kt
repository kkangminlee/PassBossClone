package org.passorder.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import org.passorder.data.BuildConfig
import org.passorder.domain.PassDataStore
import javax.inject.Inject

const val FILE_NAME = "PASS_DATA"

class PassDataStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : PassDataStore {
    private val storeDelegate by lazy {
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var accessToken: String
        set(value) = storeDelegate.edit { putString("USER_TOKEN", value) }
        get() = storeDelegate.getString("USER_TOKEN", "") ?: ""

    override var refreshToken: String
        set(value) = storeDelegate.edit { putString("REFRESH_TOKEN", value) }
        get() = storeDelegate.getString("REFRESH_TOKEN", "") ?: ""

    override var userUUID: String
        get() = storeDelegate.getString("USER_UUID", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("USER_UUID", value) }
        }
    override var storeUUID: String
        get() = storeDelegate.getString("STORE_UUID", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("STORE_UUID", value) }
        }

    override fun clear() {
        storeDelegate.edit { clear() }
    }
}