package com.example.professionals.data.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionals.data.api.ApiService
import com.example.professionals.data.domainmodel.market.AddToCart
import com.example.professionals.data.domainmodel.market.UpdateCart
import com.example.professionals.data.model.ErrorResponse
import com.example.professionals.data.model.market.AddToCartAnswer
import com.example.professionals.data.model.market.InCart
import com.example.professionals.data.model.market.ListInCart
import com.example.professionals.data.model.market.SneakersImage
import com.example.professionals.data.state.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class viewMarketCart: ViewModel() {

    private val apiService = ApiService.instance

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _idCart = MutableStateFlow<String>("")
    val idCart: StateFlow<String> get() = _idCart.asStateFlow()

    private val _Carts= MutableStateFlow<List<InCart>>(emptyList())
    val Carts: StateFlow<List<InCart>> get() = _Carts.asStateFlow()


    private val _idFavorite= mutableStateOf("")

    fun addtocart(iduser:String, idsneakers:String,  token: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.addToСart(
                    AddToCart(
                        iduser = iduser,
                        idsneakers = idsneakers,
                        count = 1
                    ), token = token
            ).enqueue(
                object : Callback<AddToCartAnswer> {

                    override fun onResponse(
                        call: Call<AddToCartAnswer>,
                        response: Response<AddToCartAnswer>,
                    ) {
                        try {
                            // Если запрос успешен, сохраняем список книг и обновляем состояние
                            response.body()?.let {
                                val id = it.id
                                _resultState.value = ResultState.Success("Success")
                                _idCart.value = response.body()!!.id
                                Log.d("addtocart", id)

                            }
                            response.errorBody()?.let {
                                try {
                                    val message =
                                        Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                    Log.e("addtocart", "Error message: $message")
                                    _resultState.value = ResultState.Error(message)
                                } catch (e: Exception) {
                                    Log.e(
                                        "addtocart",
                                        "Failed to parse error response: ${e.message}"
                                    )
                                }

                            }
                        } catch (exception: Exception) {
                            _resultState.value = ResultState.Error(exception.message.toString())
                            Log.e("addtocart", exception.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<AddToCartAnswer>, t: Throwable) {
                        // Обработка ошибки при выполнении запроса
                        _resultState.value = ResultState.Error(t.message.toString())
                        Log.e("addtocart", t.message.toString())
                    }
                })
        }

    }

    fun delCart(id: String, token: String) {
        viewModelScope.launch {
            apiService.deleteСart(
                id = id,
                token = token
            ).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        _resultState.value = ResultState.Success("Success")
                    } else {
                        response.errorBody()?.let {
                            try {
                                val message = Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                _resultState.value = ResultState.Error("Delete failed")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _resultState.value = ResultState.Error(t.message.toString())
                }
            })
        }
    }

    fun viewCart(filter:String, token: String, sort:String, perPage:Int){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.ViewСart(
                filter,
                sort,
                perPage,
                token
            ).enqueue(object : Callback<ListInCart> {
                override fun onResponse(call: Call<ListInCart>, response: Response<ListInCart>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _resultState.value = ResultState.Success("Success")
                            _Carts.value = response.body()!!.items
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("get", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "get", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("get", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<ListInCart>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("get", t.message.toString())
                }
            })
        }
    }

    fun UpdateCart(Id:String,  count:Int ){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.updateCart(id = Id,
                UpdateCart(count)
            ).enqueue(object : Callback<SneakersImage> {
                override fun onResponse(call: Call<SneakersImage>, response: Response<SneakersImage>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("UpdateCart", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "UpdateCart", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("UpdateCart", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<SneakersImage>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("UpdateCart", t.message.toString())
                }
            })
        }
    }



    fun getIdCart(): String = _idCart.value




}