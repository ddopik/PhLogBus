package com.example.ddopik.phlogbusiness.utiltes

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.ddopik.phlogbusiness.base.commonmodel.ErrorMessageResponse
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi.*
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus

import com.google.gson.Gson
import java.lang.Exception
import java.net.ConnectException

/**
 * Created by abdalla_maged on 11/6/2018.
 */
class CustomErrorUtil {
    companion object {

        private val TAG = CustomErrorUtil::class.java.simpleName

        //Bad RequestHandling
        fun setError(context: Context, contextTAG: String, error: String) {
            Log.e(TAG, "$contextTAG------>$error")
            Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show()
        }

        //Universal Error State From Server
        fun setError(context: Context, contextTAG: String, throwable: Throwable?) {
            try {
                throwable.takeIf { it is ANError }.apply {
                    (throwable as ANError).errorBody?.let {
                        //                (throwable as ANError).errorBody?.let {
                        val errorData = throwable.errorBody
                        val statusCode = throwable.errorCode
                        val gson = Gson()
                        when (statusCode) {
                            STATUS_BAD_REQUEST -> {
                                var errorMessageResponse: ErrorMessageResponse = gson.fromJson(errorData, ErrorMessageResponse::class.java)
                                viewError(context, contextTAG, errorMessageResponse)
                            }
                            STATUS_404 -> {
                                Log.e(TAG, contextTAG + "------>" + STATUS_404 + "---" + throwable.response)
                            }
                            STATUS_401 -> {
                                Log.e(TAG, contextTAG + "------>" + STATUS_401 + "---" + throwable.response)
                            }
                            STATUS_500 -> {
                                Log.e(TAG, contextTAG + "------>" + STATUS_500 + "---" + throwable.response)
                            }
                            else -> {
                                Log.e(TAG, contextTAG + "--------------->" + throwable.response)
                            }
                        }
                    }

                }
//                throwable.takeIf { it is ConnectException } .apply{
//                    RxEventBus.getInstance().post(RxEvent(RxEvent.Type.CONNECTIVITY, false));
//                }
            }catch (e: Exception){
                Log.e(TAG, contextTAG + "--------------->" +throwable?.message )
            }
        }

        ///PreDefined Error Code From Server
        private fun viewError(context: Context, contextTAG: String, errorMessageResponse: ErrorMessageResponse) {
            for (i in errorMessageResponse.errors.indices) {
                if (errorMessageResponse.errors[i].code != null)
                    when (errorMessageResponse.errors[i].code) {
                        ERROR_STATE_1 -> {
                            Toast.makeText(context, errorMessageResponse.errors[i].message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Log.e(TAG, contextTAG + "------>" + errorMessageResponse.errors[i].message)
                        }
                    }
            }

        }
    }
}
// CustomErrorUtil.setError(context, TAG, throwable);