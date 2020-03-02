package ru.skillbranch.gameofthrones.data.local

sealed class LoadResult<T> (
    open val data: T?
){
    data class Success<T>(override val data: T): LoadResult<T>(data)
    data class Loading<T>(override val data: T? = null): LoadResult<T>(data)
    data class Error<T>(val errorMessage: String, override val data: T? = null): LoadResult<T>(data)
}
