package com.example.playlistmakers_doing.data.other

fun interface ObservableInt {
    fun update(data: Any)
}


interface IObservable {
    val observers: ArrayList<ObservableInt>

    fun subscribe(observer: ObservableInt) {
        observers.add(observer)
    }

    fun unsubscribe(observer: ObservableInt) {
        observers.remove(observer)
    } // later

    fun unsubscribe() {
        observers.clear()
    } // later

    fun sendUpdateEvent(data: Any) {
        observers.forEach { it.update(data) }
    } // later
}