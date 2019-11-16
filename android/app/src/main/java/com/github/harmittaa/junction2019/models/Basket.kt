package com.github.harmittaa.junction2019.models

import java.sql.Timestamp

class Basket() {
    var id: String = ""
    var karma: Int = 0
    var price: Double = 0.0
    lateinit var store: String
    lateinit var user: String
    lateinit var items: List<Item>
    var timestamp: Long = 0L
}