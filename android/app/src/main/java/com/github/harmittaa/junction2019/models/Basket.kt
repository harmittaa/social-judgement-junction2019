package com.github.harmittaa.junction2019.models

class Basket() {
    var item: Item? = null
    var karma: Int = 0
    var price: Double = 0.0
    lateinit var store: String
    lateinit var user: String
}