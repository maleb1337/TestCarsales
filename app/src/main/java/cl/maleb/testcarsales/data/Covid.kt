package cl.maleb.testcarsales.data

import com.google.gson.annotations.SerializedName

data class Covid(
    @field:SerializedName("active")
    val active: Int,
    @field:SerializedName("active_diff")
    val active_diff: Int,
    @field:SerializedName("confirmed")
    val confirmed: Int,
    @field:SerializedName("confirmed_diff")
    val confirmed_diff: Int,
    @field:SerializedName("date")
    val date: String,
    @field:SerializedName("deaths")
    val deaths: Int,
    @field:SerializedName("deaths_diff")
    val deaths_diff: Int,
    @field:SerializedName("fatality_rate")
    val fatality_rate: Double,
    @field:SerializedName("last_update")
    val last_update: String,
    @field:SerializedName("recovered")
    val recovered: Int,
    @field:SerializedName("recovered_diff")
    val recovered_diff: Int
)