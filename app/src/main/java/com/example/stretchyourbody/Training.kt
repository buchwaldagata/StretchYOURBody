package com.example.stretchyourbody
class Training(private var title: String, private var duration: Int) {
    fun getTitle(): String {
        return title
    }

    fun getDuration(): Int {
        return duration
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }
}
