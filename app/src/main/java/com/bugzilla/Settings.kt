package com.bugzilla

import android.content.SharedPreferences

var SharedPreferences.searchById: Boolean
    get() = getBoolean("searchById", false)
    set(value) = edit().putBoolean("searchById", value).apply()