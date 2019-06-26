package com.json.dsl.poc

import javax.json.JsonValue
import javax.json.Json as javaxJson

class Json() {

    private val builder = javaxJson.createObjectBuilder()

    constructor(init: Json.() -> Unit) : this() {
        this.init()
    }

    infix fun String.to(value: JsonValue) {
        builder.add(this, value)
    }

    infix fun String.to(value: Int) {
        builder.add(this, value)
    }

    infix fun String.to(value: Double) {
        builder.add(this, value)
    }

    infix fun <T> String.to(value: Collection<T>) {
        builder.add(this, javaxJson.createArrayBuilder(value))
    }

    private fun build() : JsonValue = builder.build()

    companion object {
        fun jsonObj(init: Json.() -> Unit) : JsonValue {
            val receiver = Json(init)

            return receiver.build()
        }
    }
}

