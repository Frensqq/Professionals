package com.example.professionals.data.api

import com.example.professionals.data.model.BooksItems
import com.example.professionals.data.model.OtpResponses
import com.example.professionals.data.model.ResponsesAuth
import com.example.professionals.data.model.UserResponse
import com.example.professionals.data.domainmodel.OTPAuth
import com.example.professionals.data.domainmodel.OtpRequest
import com.example.professionals.data.domainmodel.UserAuth
import com.example.professionals.data.domainmodel.UserRequest
import com.example.professionals.data.domainmodel.UserUpdate
import com.example.professionals.data.domainmodel.market.AddOrders
import com.example.professionals.data.domainmodel.market.AddToCart
import com.example.professionals.data.domainmodel.market.CreateHistoryUser
import com.example.professionals.data.domainmodel.market.CreateLoveRecord
import com.example.professionals.data.domainmodel.market.ProfileUserUpdate
import com.example.professionals.data.domainmodel.market.UpdateCart
import com.example.professionals.data.model.OrdersResponse
import com.example.professionals.data.model.market.AddToCartAnswer
import com.example.professionals.data.model.market.CreateHistoryAnswer
import com.example.professionals.data.model.market.CreateLoveAnswer
import com.example.professionals.data.model.market.Favorites
import com.example.professionals.data.model.market.HistoryUser
import com.example.professionals.data.model.market.ListInCart
import com.example.professionals.data.model.market.ListProduct
import com.example.professionals.data.model.market.Orders
import com.example.professionals.data.model.market.OrdersId
import com.example.professionals.data.model.market.Product
import com.example.professionals.data.model.market.Sneakers
import com.example.professionals.data.model.market.SneakersImage
import com.example.professionals.data.model.userData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Интерфейс, который определяет методы для выполнения HTTP-запросов к API
 * */
interface ApiConnect {
    // Метод для получения списка книг
    // Использует HTTP GET запрос по указанному пути
    // Принимает заголовок "Authorization" для аутентификации
    @GET("/api/collections/books/records")
    fun getBooks(@Header("Authorization") token: String): Call<BooksItems>

    // Метод для регистрации нового пользователя
    // Использует HTTP POST запрос по указанному пути
    // Принимает объект UserRequest в теле запроса
    @POST("/api/collections/users/records")
    fun signUp(@Body request: UserRequest): Call<UserResponse>

    // Метод для аутентификации пользователя по паролю
    // Использует HTTP POST запрос по указанному пути
    // Принимает объект UserAuth в теле запроса
    @POST("/api/collections/users/auth-with-password")
    fun signIn(@Body request: UserAuth): Call<ResponsesAuth>

    // Метод для аутентификации пользователя по OTP
    // Использует HTTP POST запрос по указанному пути
    // Принимает объект UserAuth в теле запроса
    @POST("/api/collections/users/request-otp")
    fun getOTP(@Body request: OtpRequest): Call<OtpResponses>

    // Метод для аутентификации пользователя по OTP т одноразовому паролю
    // Использует HTTP POST запрос по указанному пути
    // Принимает объект UserAuth в теле запроса
    @POST("/api/collections/users/auth-with-otp")
    fun signInWithOTP(@Body request: OTPAuth): Call<ResponsesAuth>

    //Вывод данных пользователя

    @GET("/api/collections/users/records/{id}")
    fun viewUser(@Path("id") userId: String,@Header("Authorization") token: String ): Call<userData>

    @PATCH("/api/collections/users/records/{id}")
    fun userUpdate(@Path("id") userId: String,@Header("Authorization") token: String, @Body request: ProfileUserUpdate): Call<ProfileUserUpdate>

    //Смена пароля пользователя

    @PATCH("/api/collections/users/records/{id}")
    fun changePassword(@Path("id") userId: String,@Header("Authorization") token: String, @Body request: UserUpdate ): Call<UserResponse>

    //Создание записи истории пользователя

    @POST("/api/collections/historyusers/records")
    fun historyAdd(@Body request: CreateHistoryUser, @Header("Authorization") token: String): Call<CreateHistoryAnswer>

    //Вывод истории пользователя

    @GET("/api/collections/historyusers/records")
    fun historyGet( @Query("filter") filter: String,@Query("sort") sort: String = "-created", @Query("perPage") perPage: Int = 6, @Header("Authorization") token: String): Call<HistoryUser>

    //Вывод товаров

    @GET("/api/collections/sneakers/records")
    fun viewingProducts (@Query("filter") filter: String, @Query("sort") sort: String, @Query("perPage") perPage: Int,@Header("Authorization") token: String ): Call<Sneakers>

    //Вывод товра

    @GET("/api/collections/sneakers/records/{id}")
    fun viewingProduct (@Path("id") id: String, @Header("Authorization") token: String): Call<List<String>>

    //Вывод картинок товаров

    @GET("/api/collections/sneakers/records/{id}")
    fun viewingImageProducts (@Path("id") id: String, @Query("filter") filter: String, @Header("Authorization") token: String ): Call<SneakersImage>

    //избранное

    @POST("/api/collections/Love/records")
    fun LoveAdd(@Body request: CreateLoveRecord, @Header("Authorization") token: String): Call<CreateLoveAnswer>

    @DELETE("/api/collections/Love/records/{id}")
    fun Unloved(@Path("id") id: String,@Header("Authorization") token: String): Call<Unit>

    @GET("/api/collections/Love/records")
    fun ViewLove(@Query("filter") filter: String, @Query("sort") sort: String, @Query("perPage") perPage: Int,@Header("Authorization") token: String ): Call<Favorites>


    //корзина

    @POST("/api/collections/Cart/records")
    fun addToСart(@Body request: AddToCart, @Header("Authorization") token: String): Call<AddToCartAnswer>

    @DELETE("/api/collections/Cart/records/{id}")
    fun deleteСart(@Path("id") id: String,@Header("Authorization") token: String): Call<Unit>

    @GET("/api/collections/Cart/records")
    fun ViewСart(@Query("filter") filter: String, @Query("sort") sort: String, @Query("perPage") perPage: Int,@Header("Authorization") token: String ): Call<ListInCart>

    @PATCH("/api/collections/Cart/records/{id}")
    fun updateCart(@Path("id") id: String,@Body request: UpdateCart ): Call<SneakersImage>

    // История заказов

    @POST("/api/collections/Orders/records")
    fun createOrders(@Body request: AddOrders, @Header("Authorization") token: String): Call<OrdersResponse>

    @GET("/api/collections/Orders/records")
    fun viewOrders(@Query("filter") filter: String, @Query("sort") sort: String, @Query("perPage") perPage: Int,@Header("Authorization") token: String): Call<Orders>

    //вывод конкретно списка id из заказа

    @GET("/api/collections/Orders/records/{id}")
    fun viewIdInOrders(@Path("id") id: String, @Header("Authorization") token: String): Call<OrdersId>




}