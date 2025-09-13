package com.example.professionals.data.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionals.data.api.ApiService
import com.example.professionals.data.domainmodel.market.AddOrders
import com.example.professionals.data.model.ErrorResponse
import com.example.professionals.data.model.OrdersResponse
import com.example.professionals.data.model.market.Orders
import com.example.professionals.data.model.market.OrdersId
import com.example.professionals.data.state.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.String
import kotlin.collections.List

class viewMarketOrders: ViewModel(){

    private val apiService = ApiService.instance
    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _Orders = MutableStateFlow<Orders?>(null)
    val Orders: StateFlow<Orders?> = _Orders.asStateFlow()

    private val _sneakersId = MutableStateFlow<List<String>>(emptyList())
    val sneakersId: StateFlow<List<String>> get() = _sneakersId.asStateFlow()


    fun CreateOrders( token: String,  id_users: String, id_snakers: List<String>, count: String, email: String, phone: String, address: String, card: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.createOrders(
                AddOrders(
                    id_users,
                    id_snakers,
                    count,
                    email,
                    phone,
                    address,
                    card),
                token
            ).enqueue(object : Callback<OrdersResponse> {
                override fun onResponse(call: Call<OrdersResponse>, response: Response<OrdersResponse>) {
                    try {
                        // Если запрос успешен, сохраняем и обновляем состояние
                        response.body()?.let {

                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message = Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("viewUser", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "viewUser", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("viewUser", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("viewUser", t.message.toString())
                }
            })
        }
    }


    fun ViewOrders( token: String, filter: String, sort: String, perPage: Int){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.viewOrders(
                filter,
                sort,
                perPage,
                token
            ).enqueue(object : Callback<Orders> {
                override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                    try {
                        // Если запрос успешен, сохраняем и обновляем состояние
                        response.body()?.let {Orders ->
                            _Orders.value = Orders
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("UpdateUser", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "UpdateUser", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("UpdateUser", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<Orders>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("UpdateUser", t.message.toString())
                }
            })
        }
    }

    fun ViewIdInOrders(id: String, token: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.viewIdInOrders(
                id,
                token
            ).enqueue(object : Callback<OrdersId> {
                override fun onResponse(call: Call<OrdersId>, response: Response<OrdersId>) {
                    try {
                        // Если запрос успешен, сохраняем и обновляем состояние
                        response.body()?.let {
                            _sneakersId.value = response.body()!!.id_snakers
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("UpdateUser", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "UpdateUser", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("UpdateUser", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<OrdersId>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("UpdateUser", t.message.toString())
                }
            })
        }
    }






}






