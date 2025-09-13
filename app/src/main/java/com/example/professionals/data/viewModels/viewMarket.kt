package com.example.professionals.data.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionals.data.api.ApiService
import com.example.professionals.data.domainmodel.market.CreateHistoryUser
import com.example.professionals.data.domainmodel.market.CreateLoveRecord
import com.example.professionals.data.domainmodel.market.ProfileUserUpdate
import com.example.professionals.data.model.ErrorResponse
import com.example.professionals.data.model.market.CreateHistoryAnswer
import com.example.professionals.data.model.market.CreateLoveAnswer
import com.example.professionals.data.model.market.Favorites
import com.example.professionals.data.model.market.HistoryList
import com.example.professionals.data.model.market.HistoryUser
import com.example.professionals.data.model.market.LoveList
import com.example.professionals.data.model.market.Sneakers
import com.example.professionals.data.model.market.SneakersImage
import com.example.professionals.data.model.market.SneakersList
import com.example.professionals.data.model.userData
import com.example.professionals.data.state.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class viewMarket: ViewModel() {

    private val apiService = ApiService.instance

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _resultStateEmail = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultStateEmail: StateFlow<ResultState> = _resultStateEmail.asStateFlow()

    private val _history = MutableStateFlow<List<HistoryList>>(emptyList())
    val history: StateFlow<List<HistoryList>> get() = _history.asStateFlow()

    private val _favorite= MutableStateFlow<List<LoveList>>(emptyList())
    val favorite: StateFlow<List<LoveList>> get() = _favorite.asStateFlow()

    private val _idFavorites = MutableStateFlow<String>("")
    val idFavorites: StateFlow<String> get() = _idFavorites.asStateFlow()

    private val _sneakers = MutableStateFlow<List<SneakersList>>(emptyList())
    val sneakers: StateFlow<List<SneakersList>> get() = _sneakers.asStateFlow()

    private val _sneaker = MutableStateFlow<List<String>>(emptyList())
    val sneaker: StateFlow<List<String>> get() = _sneaker.asStateFlow()

    private val _sneakersImage = MutableStateFlow<List<String>>(emptyList())
    val sneakersImage: StateFlow<List<String>> get() = _sneakersImage.asStateFlow()

    private val _userData = MutableStateFlow<userData?>(null)
    val userData: StateFlow<userData?> = _userData.asStateFlow()

    private val _idFavorite= mutableStateOf("")

    // Загрузка истории

    fun OutputImageProducts(id: String,filter: String,token: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.viewingImageProducts(
                id = id,
                filter = filter,
                token = token

            ).enqueue(object : Callback<SneakersImage> {
                override fun onResponse(call: Call<SneakersImage>, response: Response<SneakersImage>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _resultState.value = ResultState.Success("Success")
                            _sneakersImage.value = response.body()!!.image
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("OutputImage", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "OutputImage", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("OutputImage", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<SneakersImage>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("OutputImage", t.message.toString())
                }
            })
        }

    }

    fun getImage(collectionId:String, idsneakers: String, image:String): String {
        val imageUrl =
            "http://10.0.2.2:8090/api/files/${collectionId}/${idsneakers}/${image}"
        Log.i("Image", imageUrl)
        return imageUrl
    }


    fun CreateHistory(iduser: String, request: String, token: String) {
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.historyAdd(
                CreateHistoryUser(
                    iduser = iduser,
                    request = request
                ),
                token
            ).enqueue(
                object : Callback<CreateHistoryAnswer> {

                    override fun onResponse(
                        call: Call<CreateHistoryAnswer>,
                        response: Response<CreateHistoryAnswer>,
                    ) {
                        try {
                            // Если запрос успешен, сохраняем список книг и обновляем состояние
                            response.body()?.let {
                                val id = it.id
                                _resultState.value = ResultState.Success("Success")
                                Log.d("SignUP", id)
                            }
                            response.errorBody()?.let {
                                try {
                                    val message =
                                        Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                    Log.e("getBooks", "Error message: $message")
                                    _resultState.value = ResultState.Error(message)
                                } catch (e: Exception) {
                                    Log.e(
                                        "getBooks",
                                        "Failed to parse error response: ${e.message}"
                                    )
                                }

                            }
                        } catch (exception: Exception) {
                            _resultState.value = ResultState.Error(exception.message.toString())
                            Log.e("SignUP", exception.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<CreateHistoryAnswer>, t: Throwable) {
                        // Обработка ошибки при выполнении запроса
                        _resultState.value = ResultState.Error(t.message.toString())
                        Log.e("SignUP", t.message.toString())
                    }
                })
        }
    }

    fun WatchHistory(filter:String, token: String) {

        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.historyGet(
                filter = filter,
                token = token

            ).enqueue(object : Callback<HistoryUser> {
                override fun onResponse(call: Call<HistoryUser>, response: Response<HistoryUser>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _history.value = response.body()!!.items

                            _history.value.forEach {}
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("getHistory", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "getHistory", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("getHistory", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<HistoryUser>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("getHistory", t.message.toString())
                }
            })
        }
    }

    fun outputProducts(filter:String, token: String, sort:String, perPage:Int){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.viewingProducts(
                filter = filter,
                sort = sort,
                perPage = perPage,
                token = token

            ).enqueue(object : Callback<Sneakers> {
                override fun onResponse(call: Call<Sneakers>, response: Response<Sneakers>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _sneakers.value = response.body()!!.items

                            _sneakers.value.forEach {}
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("getSneakers", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "getSneakers", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("getSneakers", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<Sneakers>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("getSneakers", t.message.toString())
                }
            })
        }
    }


    fun outputProduct(id:String,  token: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.viewingProduct(
                id = id,
                token = token
            ).enqueue(object : Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _sneaker.value = response.body() ?: emptyList()
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("getSneaker", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "getSneaker", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("getSneaker", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("getSneaker", t.message.toString())
                }
            })
        }
    }

    fun addFavourites(iduser:String, idsneakers:String,  token: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.LoveAdd(
                CreateLoveRecord(
                    iduser = iduser,
                    idsneakers = idsneakers
                ),token
            ).enqueue(
                object : Callback<CreateLoveAnswer> {

                    override fun onResponse(
                        call: Call<CreateLoveAnswer>,
                        response: Response<CreateLoveAnswer>,
                    ) {
                        try {
                            // Если запрос успешен, сохраняем список книг и обновляем состояние
                            response.body()?.let {
                                val id = it.id
                                _resultState.value = ResultState.Success("Success")
                                _idFavorite.value = response.body()!!.id
                                val idFav = response.body()!!.id
                                Log.d("Loved", id)

                            }
                            response.errorBody()?.let {
                                try {
                                    val message =
                                        Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                    Log.e("AddFavourites", "Error message: $message")
                                    _resultState.value = ResultState.Error(message)
                                } catch (e: Exception) {
                                    Log.e(
                                        "AddFavourites",
                                        "Failed to parse error response: ${e.message}"
                                    )
                                }

                            }
                        } catch (exception: Exception) {
                            _resultState.value = ResultState.Error(exception.message.toString())
                            Log.e("Loved", exception.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<CreateLoveAnswer>, t: Throwable) {
                        // Обработка ошибки при выполнении запроса
                        _resultState.value = ResultState.Error(t.message.toString())
                        Log.e("Loved", t.message.toString())
                    }
                })
        }

    }

    fun delFavorites(id: String, token: String) {
        viewModelScope.launch {
            apiService.Unloved(
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

    fun viewFavorites(filter:String, token: String, sort:String, perPage:Int){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.ViewLove(
                filter = filter,
                sort = sort,
                perPage = perPage,
                token = token

            ).enqueue(object : Callback<Favorites> {
                override fun onResponse(call: Call<Favorites>, response: Response<Favorites>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _favorite.value = response.body()!!.items

                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("getHistory", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "getHistory", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("getHistory", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<Favorites>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("getHistory", t.message.toString())
                }
            })
        }
    }

    fun ViewUser(id:String, token: String){
    _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
    viewModelScope.launch {
        apiService.viewUser(
            id,
            token
        ).enqueue(object : Callback<userData> {
            override fun onResponse(call: Call<userData>, response: Response<userData>) {
                try {
                    // Если запрос успешен, сохраняем и обновляем состояние
                    response.body()?.let {userData ->
                        _userData.value = userData
                        _resultState.value = ResultState.Success("Success")
                    }
                    // Обработка ошибки, если запрос не успешен
                    response.errorBody()?.let {
                        try {
                            val message =
                                Gson().fromJson(it.string(), ErrorResponse::class.java).message
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

            override fun onFailure(call: Call<userData>, t: Throwable) {
                // Обработка ошибки при выполнении запроса
                _resultState.value = ResultState.Error(t.message.toString())
                Log.e("viewUser", t.message.toString())
            }
        })
    }
    }


    fun UpdateUser(id:String, token: String, name: String, surname: String, address: String, phoneNumber: String){
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.userUpdate(
                id,
                token,
                ProfileUserUpdate(
                    name,
                    surname,
                    address,
                    phoneNumber
                )
            ).enqueue(object : Callback<ProfileUserUpdate> {
                override fun onResponse(call: Call<ProfileUserUpdate>, response: Response<ProfileUserUpdate>) {
                    try {
                        // Если запрос успешен, сохраняем и обновляем состояние
                        response.body()?.let {
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

                override fun onFailure(call: Call<ProfileUserUpdate>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("UpdateUser", t.message.toString())
                }
            })
        }
    }

    fun getIdFavorite(): String = _idFavorite.value

}