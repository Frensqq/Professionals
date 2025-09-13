package com.example.professionals.data.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.*
import com.example.professionals.data.api.ApiService
import com.example.professionals.data.domainmodel.OTPAuth
import com.example.professionals.data.domainmodel.OtpRequest
import com.example.professionals.data.domainmodel.UserAuth
import com.example.professionals.data.domainmodel.UserRequest
import com.example.professionals.data.domainmodel.UserUpdate
import com.example.professionals.data.model.Book
import com.example.professionals.data.model.BooksItems
import com.example.professionals.data.model.ErrorResponse
import com.example.professionals.data.model.OtpResponses
import com.example.professionals.data.model.ResponsesAuth
import com.example.professionals.data.model.UserResponse
import com.example.professionals.data.state.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CodeAunth:  ViewModel()  {

    private val apiService = ApiService.instance

    // Состояние результата операций

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _resultStateEmail = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultStateEmail: StateFlow<ResultState> = _resultStateEmail.asStateFlow()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> get() = _books.asStateFlow()

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    // Хранит токен аутентификации
    private val _token = mutableStateOf("")
    private var _otp = mutableStateOf("")
    private val _id = mutableStateOf("")
    private var _email = mutableStateOf("")


    // Список книг, полученных из API

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }

    fun changePassword(idAuth: String, token:String,oldPass:String, newPass: String, confirmPass:String){
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            apiService.changePassword(
                idAuth,token,
                UserUpdate(
                    oldPassword = oldPass,
                    password = newPass,
                    passwordConfirm = confirmPass)
            ).enqueue(
                object : Callback<UserResponse> {

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>,
                    ) {
                        try {
                            // Если запрос успешен, сохраняем список книг и обновляем состояние
                            response.body()?.let {
                                val id = it.id
                                _resultState.value = ResultState.Success("Success")
                                _showSuccessDialog.value = true
                                Log.d("ChangePass", id)
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
                            Log.e("ChangePass", exception.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Обработка ошибки при выполнении запроса
                        _resultState.value = ResultState.Error(t.message.toString())
                        Log.e("ChangePass", t.message.toString())
                    }
                })
        }

    }

    fun getBooks() {
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.getBooks(_token.value).enqueue(object : Callback<BooksItems> {
                override fun onResponse(call: Call<BooksItems>, response: Response<BooksItems>) {
                    try {
                        // Если запрос успешен, сохраняем список книг и обновляем состояние
                        response.body()?.let {
                            _books.value = response.body()!!.items
                            _books.value.forEach {
                                Log.d("Books", it.title)
                            }
                            _resultState.value = ResultState.Success("Success")
                        }
                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("getBooks", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e(
                                    "getBooks", "Failed to parse error response: ${e.message}"
                                )
                            }
                        }
                    } catch (ex: Exception) {
                        _resultState.value = ResultState.Error(ex.message.toString())
                        Log.e("getBooks", ex.message.toString())
                    }
                }

                override fun onFailure(call: Call<BooksItems>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("getBooks", t.message.toString())
                }
            })
        }
    }


    // Метод для аутентификации пользователя
    fun signIn(email: String, pasword:String) {
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.signIn(UserAuth(email, pasword)).enqueue(object :
                Callback<ResponsesAuth> {
                override fun onResponse(
                    call: Call<ResponsesAuth>,
                    response: Response<ResponsesAuth>,
                ) {
                    try {
                        // Если запрос успешен, сохраняем токен и обновляем состояние
                        response.body()?.let {
                            _token.value = response.body()!!.token
                            _id.value = response.body()!!.record.id
                            _resultState.value = ResultState.Success("Success")
                            _showSuccessDialog.value = true
                            Log.d("SignIN", _token.value)
                        }

                        // Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            try {
                                val message =
                                    Gson().fromJson(it.string(), ErrorResponse::class.java).message
                                Log.e("SignIN", "Error message: $message")
                                _resultState.value = ResultState.Error(message)
                            } catch (e: Exception) {
                                Log.e("SignIN", "Failed to parse error response: ${e.message}")
                            }

                        }
                    } catch (exception: Exception) {
                        _resultState.value = ResultState.Error(exception.message.toString())
                        Log.e("SignIN catch", exception.message.toString())
                    }
                }

                override fun onFailure(call: Call<ResponsesAuth>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("SignIN onFailure", t.message.toString())
                }
            })
        }
    }

    // Метод для регистрации нового пользователя

    fun signUp(email: String, pasword: String, name: String) {
        _resultState.value = ResultState.Loading // Устанавливаем состояние загрузки
        viewModelScope.launch {
            apiService.signUp(
                UserRequest(
                    email = email,
                    password = pasword,
                    passwordConfirm = pasword,
                    name = name
                )
            ).enqueue(
                object : Callback<UserResponse> {

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>,
                    ) {
                        try {
                            // Если запрос успешен, сохраняем список книг и обновляем состояние
                            response.body()?.let {
                                val id = it.id
                                _resultState.value = ResultState.Success("Success")
                                _showSuccessDialog.value = true
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
                            println(pasword)
                            println(email)
                            _resultState.value = ResultState.Error(exception.message.toString())
                            Log.e("SignUP", exception.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Обработка ошибки при выполнении запроса
                        _resultState.value = ResultState.Error(t.message.toString())
                        Log.e("SignUP", t.message.toString())
                    }
                })
        }
    }


    fun sendOtp(email: String) {
        apiService.getOTP(OtpRequest(email)).enqueue(object : Callback<OtpResponses> {
            override fun onResponse(call: Call<OtpResponses>, response: Response<OtpResponses>) {
                // Если запрос успешен, сохраняем _otp и обновляем состояние
                try {
                    response.body()?.let {
                        _otp.value = response.body()!!.otpId

                        Log.i("sendOtp", response.body()!!.otpId)
                        _resultStateEmail.value = ResultState.Success(response.body()!!.otpId)
                    }
                    if (!response.isSuccessful) {// Обработка ошибки, если запрос не успешен
                        response.errorBody()?.let {
                            val message =
                                Gson().fromJson(it.string(), ErrorResponse::class.java).message
                            Log.e("sendOtp", "Error message: $message")
                            _resultStateEmail.value = ResultState.Error(message)
                        }
                    }
                } catch (ex: Exception) {
                    Log.e("sendOtp", "Error message: ${ex.message}")

                }
            }

            override fun onFailure(call: Call<OtpResponses>, t: Throwable) {
                // Обработка ошибки при выполнении запроса
                _resultStateEmail.value = ResultState.Error(t.message.toString())
                Log.e("sendOtp", t.message.toString())
            }
        })

    }



    fun sigInWithOtp(password: String, otp: String) {
        _otp.value = otp
        apiService.signInWithOTP(OTPAuth(_otp.value, password))
            .enqueue(object : Callback<ResponsesAuth> {
                override fun onResponse(
                    call: Call<ResponsesAuth>,
                    response: Response<ResponsesAuth>,
                ) { // Если запрос успешен, сохраняем _otp и обновляем состояние
                    response.body()?.let {
                        _otp.value = response.body()!!.token
                        _email.value = response.body()!!.record.email
                        _id.value  = response.body()!!.record.id
                        Log.i("sigInWithOtp", response.body()!!.token)
                        _resultState.value = ResultState.Success(response.body()!!.token)
                    }
                    // Обработка ошибки, если запрос не успешен
                    response.errorBody()?.let {
                        val message =
                            Gson().fromJson(it.string(), ErrorResponse::class.java).message
                        Log.e("sigInWithOtp", "Error message: $message")
                        _resultState.value = ResultState.Error(message)
                    }

                }

                override fun onFailure(call: Call<ResponsesAuth>, t: Throwable) {
                    // Обработка ошибки при выполнении запроса
                    _resultState.value = ResultState.Error(t.message.toString())
                    Log.e("sigInWithOtp", t.message.toString())
                }
            })
    }






    // Метод для получения URL изображения книги
    fun getImage(book: Book): String {
        val imageUrl =
            "http://10.0.2.2:8090/api/files/${book.collectionId}/${book.id}/${book.image}"
        Log.i("Image", imageUrl)
        return imageUrl
    }

    fun getOtp(): String = _otp.value
    fun getCurrentToken(): String = _token.value
    fun getId(): String = _id.value
    fun getPass(): String  = "15251525"
    fun getEmail(): String = _email.value






}

