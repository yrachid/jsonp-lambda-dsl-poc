package com.json.dsl.poc

import com.json.dsl.poc.Json.Companion.jsonObj
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import javax.json.Json.createArrayBuilder as jsonArray
import javax.json.Json.createObjectBuilder as jsonObject

class KotlinJsonDslTest {

    @Test
    // TODO: This is failing for some reason
    fun `Using native Kotlin types and utilities`() {
        val json = jsonObject(mapOf(
            "apples" to 10,
            "total" to 100.00,
            "otherFruits" to jsonArray(listOf(
                "Banana",
                "Mango"
            )).build(),
            "stockCount" to jsonObject(mapOf(
                "vegetables" to 30,
                "fruits" to 50,
                "cakes" to 10
            )).build()
        )).build()

        assertThat(json.getInt("apples"), equalTo(10))
        assertThat(json.getJsonNumber("total").doubleValue(), equalTo(100.00))
    }

    @Test
    fun `Using lambdas with receivers and infix functions`() {

        val json = jsonObj {
            "apples" to 10
            "total" to 100.00
            "otherFruits" to listOf(
                "Banana",
                "Mango"
            )
            "stockCount" to jsonObj {
                "vegetables" to 30
                "fruits" to 50
                "cakes" to 10
            }
        }

        assertThat(json.asJsonObject().getInt("apples"), equalTo(10))
        assertThat(json.asJsonObject().getJsonNumber("total").doubleValue(), equalTo(100.00))
        assertThat(json.asJsonObject().getJsonArray("otherFruits").getString(0), equalTo("Banana"))
        assertThat(json.asJsonObject().getJsonArray("otherFruits").getString(1), equalTo("Mango"))
    }
}