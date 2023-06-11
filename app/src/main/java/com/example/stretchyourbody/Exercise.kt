package com.example.stretchyourbody

class Exercise(private var title: String, private var description: String, private var img: Int) {
    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }

    fun getImage(): Int {
        return img
    }

    fun setImage(img: Int) {
        this.img = img
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }
}