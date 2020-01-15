package com.android.githubusersearch.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plan(
    @SerializedName("name") val name: String,
    @SerializedName("space") val space: Int,
    @SerializedName("collaborators") val collaborators: Int,
    @SerializedName("private_repos") val privateRepos: Int
) : Parcelable