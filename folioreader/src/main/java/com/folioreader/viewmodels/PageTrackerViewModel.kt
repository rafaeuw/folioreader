package com.folioreader.viewmodels

import android.icu.lang.UCharacter.VerticalOrientation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.folioreader.Config

class PageTrackerViewModel : ViewModel() {
    private val _currentChapter = MutableLiveData<Int?>()
    private val _currentPage = MutableLiveData<Int?>()

    private val _chapterPage = MutableLiveData<String?>()
    val chapterPage: LiveData<String?>
        get() = _chapterPage

    init {
        _chapterPage.value = "5 - 5"
    }

    fun setCurrentChapter(currentChapter: Int, orientation: String) {
        _currentChapter.value = currentChapter
        updateChapterPage(orientation)
    }

    fun setCurrentPage(currentChapter: Int, orientation: String) {
        _currentPage.value = currentChapter
        updateChapterPage(orientation);
    }

//    fun setFolioPageInfo(adapterPageCount: Int, currentPage: Int, currentChapter: Int) {
//        _chapterPage.value = "${_currentChapter.value} - $currentPage"
//    }

    private fun updateChapterPage(isVerticalOrientation: String) {
        if (isVerticalOrientation == "VERTICAL"){
            _chapterPage.value = "Ch ${_currentChapter.value}"
        }else{
            _chapterPage.value = "${_currentChapter.value} - ${_currentPage.value}"
        }


    }
}