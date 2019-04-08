package com.example.ddopik.phlogbusiness.utiltes

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.ddopik.phlogbusiness.R
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseErrorResponse
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
                        return
                    }
                    Log.e(TAG, contextTAG + "--------------->" + throwable.message)
                }

            } catch (e: Exception) {
                Log.e(TAG, contextTAG + "--------------->" + throwable?.message)
            }
        }

        ///PreDefined Error Code From Server
        private fun viewError(context: Context, contextTAG: String, errorMessageResponse: ErrorMessageResponse) {
            for (i in errorMessageResponse.errors.indices) {
                if (errorMessageResponse.errors[i].code != null)
                    when (errorMessageResponse.errors[i].code) {
                        ERROR_STATE_1 -> {
                            viewErrorMessage(context,errorMessageResponse.errors[i].message)
                         }
                        else -> {
                            Log.e(TAG, contextTAG + "------>" + errorMessageResponse.errors[i].message)
                        }
                    }
            }

        }
       private fun viewErrorMessage(context:Context,message: String) {
            AlertDialog.Builder(context)

                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(context.resources.getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation
                    })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }


}
// CustomErrorUtil.setError(context, TAG, throwable);