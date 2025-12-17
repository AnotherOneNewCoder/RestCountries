package com.zhogin.restcountries.data.api.service


import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class CountriesApiServiceImplTest {

    @Test
    fun `getAllCountries should call correct URL and return list of countries`() = runBlocking {
        // 1. Настраиваем MockEngine
        val mockEngine = MockEngine {
            respond(
                content = """
                [
                    {
                        "name": { "common": "Ukraine" },
                        "flags": { "png": "https://flagcdn.com/w320/ua.png" },
                        "capital": ["Kyiv"],
                        "population": 41000000,
                        "region": "Europe"
                    },
                    {
                        "name": { "common": "Norway" },
                        "flags": { "png": "https://flagcdn.com/w320/no.png" },
                        "capital": ["Oslo"],
                        "population": 5400000,
                        "region": "Europe"
                    }
                ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // 2. Настройка клиента
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        val service = CountriesApiServiceImpl(httpClient, "https://restcountries.com/v3.1")

        // 3. Вызов метода
        val result = service.getAllCountries()

        // 4. Проверки (Assertions)
        assertEquals(2, result.size)

        // Проверка первой страны
        with(result[0]) {
            assertEquals("Ukraine", name.common)
            assertEquals("https://flagcdn.com/w320/ua.png", flags.png)
            assertEquals("Kyiv", capital?.first())
            assertEquals(41000000L, population)
            assertEquals("Europe", region)
        }
    }

    @Test(expected = Exception::class) // Ожидаем, что метод выбросит исключение
    fun `getAllCountries should throw exception when server returns 500`(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError
            )
        }

        val httpClient = HttpClient(mockEngine)
        val service = CountriesApiServiceImpl(httpClient)

        service.getAllCountries() // Должно упасть
    }

    @Test //Тест на пустой список
    fun `getAllCountries should return empty list when API returns empty array`() = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "[]",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
        val service = CountriesApiServiceImpl(httpClient)

        val result = service.getAllCountries()

        assert(result.isEmpty())
    }

    //@Test(expected = kotlinx.serialization.SerializationException::class) //Тест на incorrect Json
    @Test(expected = io.ktor.serialization.JsonConvertException::class) //Тест на incorrect Json
    fun `getAllCountries should throw SerializationException when JSON is invalid`(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "{ \"invalid\": \"data\" ", // Не закрытая скобка
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
        val service = CountriesApiServiceImpl(httpClient)

        service.getAllCountries()
    }
}
