package com.gelombang.apps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var name: String = "", var type: String = "") : Parcelable

@Parcelize
data class HasilEvaluasi(var name: String = "", var key: String = "",var nilai: String = "",var status: String = "") : Parcelable