package com.mabrouk.newstask.core

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.JsonSyntaxException
import com.mabrouk.newstask.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.SSLHandshakeException
import kotlin.collections.ArrayList

sealed class Result<out T : Any> {
    object OnLoading : Result<Nothing>()
    object OnFinish : Result<Nothing>()
    data class OnSuccess<out T : Any>(val data: T) : Result<T>()
    data class OnFailure(val msg: String) : Result<Nothing>()
    data class NoInternetConnect(val error: String) : Result<Nothing>()
}

typealias ApiResult<T> = suspend () -> Response<T>
typealias ApiResult2<T> = suspend () -> Deferred<T>

fun <T : Any> executeCall(
    context: Context,
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    apiCall: ApiResult<T>
): Flow<Result<T>> {
    return flow {
        emit(Result.OnLoading)
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Result.OnSuccess(it))
            } ?: emit(Result.OnFailure("API call successful but empty response body"))
            return@flow
        }

        emit(
            Result.OnFailure(
                "API call failed with error - ${
                    response.errorBody()?.string() ?: messageInCaseOfError
                }"
            )
        )
        return@flow
    }.final(context, allowRetries, numberOfRetries)
}


fun <T : Any> executeCall2(
    context: Context,
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    apiCall: ApiResult2<T>
): Flow<Result<T>> {
    return flow {
        emit(Result.OnLoading)
        val response = apiCall()
        emit(Result.OnSuccess(response.await()))
        return@flow
    }.final(context, allowRetries, numberOfRetries)
}

fun <T : Any> Flow<Result<T>>.final(
    context: Context,
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
): Flow<Result<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return this.catch { e ->
        if (CheckNetwork.isOnline(context)) {
            emit(Result.OnFailure(errorMsg(e, context)))
        } else {
            emit(Result.NoInternetConnect(context.getString(R.string.no_internet_connection)))
        }
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.onCompletion {
        emit(Result.OnFinish)
    }.flowOn(Dispatchers.IO)
}


interface OnCheckConnection {
    fun ConnectionTrue()
    fun ConnectionError()
}

class CheckNetwork {
    companion object {

        fun isConnected(context: Context, onCheckConnection: OnCheckConnection) {
            if (isOnline(context)) {
                onCheckConnection.ConnectionTrue()
            } else {
                onCheckConnection.ConnectionError()
            }
        }

        @SuppressLint("NewApi")
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }
    }
}

fun errorMsg(e: Throwable, context: Context): String =
    when (e) {
        is HttpException -> analysisError(e)
        is SocketTimeoutException -> context.getString(R.string.socketTimeout)
        is JsonSyntaxException -> context.getString(R.string.Jsonpars)
        is SSLHandshakeException -> e.message.toString()
        else -> "Api Error"
    }


private fun analysisError(e: HttpException): String {
    return try {
        val responseStrings: String = e.response()!!.errorBody().toString()
        val jsonObject = JSONObject(responseStrings)
        when {
            jsonObject.has("msg") -> jsonObject.get("msg").toString()
            jsonObject.has("error") -> jsonObject.get("error").toString()
            else -> e.message()
        }
    } catch (ex: Exception) {
        (if (e.message().isEmpty()) e.localizedMessage else e.message()) as String
    }
}

fun <T : Any> List<T>.toArrayList() = ArrayList(this)


fun convertDate(date:String) : String{
    val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val parser = SimpleDateFormat(format)
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    return formatter.format(parser.parse(date))
}

fun countries() =
    mapOf(
        "Egypt" to "EG",
        "Sudan" to "SD",
        "Morocco" to "MA",
        "Saudi" to "SA",
        "Yemen" to "YE",
        "Syria" to "SY",
        "Somalia" to "SO",
        "Tunisia" to "TN",
        "Jordan" to "JO",
        "Emirates" to "AE",
        "Lebanon" to "LB",
        "Libya" to "LY",
        "Palestine" to "PS",
        "Oman" to "OM",
        "Mauritania" to "MR",
        "Kuwait" to "KW",
        "Qatar" to "QA",
        "Bahrain" to "BH",
        "Djibouti" to "DJ",
        "Comoros" to "KM",
        "Iraq" to "IQ",
        "Algeria" to "DZ"
        )