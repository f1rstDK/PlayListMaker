package com.example.playlistmakers_doing.domain

import com.example.playlistmakers_doing.data.INetwork
import com.example.playlistmakers_doing.data.models.ApiMain
import com.example.playlistmakers_doing.data.models.ApiResponseApp
import com.example.playlistmakers_doing.presentation.state.SearchScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Network : INetwork {
    override fun sendRequest(
        inputText: String,
        resultCallback: ((SearchScreenState) -> Unit)
    ) {
        val callback = object : Callback<ApiResponseApp> {
            override fun onResponse(
                call: Call<ApiResponseApp>,
                response: Response<ApiResponseApp>
            ) {
                when (response.code()) {
                    in 100..199 -> Unit
                    in 200..299 -> responseSuccess(response)
                    in 300..399 -> Unit
                    in 400..499 -> responseFailure()
                    in 500..599 -> responseFailure()
                }
            }

            private fun responseSuccess(response: Response<ApiResponseApp>) {
                if (response.body()?.resultCount == 0) {
                    emptyResult()
                } else {
                    showSuccessResult(response)
                }
            }

            private fun emptyResult() {
                resultCallback(SearchScreenState.NothingFound)
            }

            override fun onFailure(call: Call<ApiResponseApp>, t: Throwable) {
                responseFailure()
            }

            private fun responseFailure() {
                resultCallback(SearchScreenState.NetworkProblem)
            }

            private fun showSuccessResult(response: Response<ApiResponseApp>) {
                response.body()?.results?.map { Convert.convert(it) }?.apply {
                    resultCallback.invoke(SearchScreenState.Result(this))
                }
            }
        }
        ApiMain.apiaService.search(inputText).enqueue(callback)
    }
}
