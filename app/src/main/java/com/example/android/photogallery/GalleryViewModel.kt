package com.example.android.photogallery

import android.app.Application
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GalleryViewModel(app: Application) : AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext

    private var dataColumnIndex = 0

    private val _state: MutableStateFlow<ArrayList<String>> =
        MutableStateFlow(ArrayList())

    val state = _state.asStateFlow()

    fun launchCoroutine() {
        viewModelScope.launch {
            getImagePath()
        }
    }

    private fun getImagePath() {
        val isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media._ID
        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            orderBy
        )
        val count = cursor?.count
        val imagesPath = ArrayList<String>()

        for (i in 0 until count!!) {
            cursor.moveToPosition(i)
            dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            imagesPath.add(cursor.getString(dataColumnIndex))
        }
        _state.value = imagesPath
        cursor.close()
    }
}