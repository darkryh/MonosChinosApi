[![](https://jitpack.io/v/darkryh/MonosChinosApi.svg)](https://jitpack.io/#darkryh/MonosChinosApi)

# MonosChinosApi

A Kotlin API library for interacting with the MonosChinos anime site, built with **Jsoup** for web scraping and **Kotlin Coroutines** for asynchronous operations. Designed with a **Object Reference style** for simplicity and flexibility. 🎉

---

## Installation

Add the JitPack repository and include the dependency in your `build.gradle` file:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation("com.github.darkryh:MonosChinosApi:$version")
}

```  
# Fetch Homepage
```kotlin
class ExampleViewModel : ViewModel() {

    fun getHome() = viewModelScope.launch(Dispatchers.IO) {
        val home: Home = MonosChinos.getHome()

        val lastChapters: List<HomeChapter> = home.lastChapters
        val recentSeries: List<HomeAnime> = home.recentSeries
    }
}
```
# Search for Anime
```kotlin
class ExampleViewModel : ViewModel() {

    fun searchAnime() = viewModelScope.launch(Dispatchers.IO) {
        val animeList: List<Anime> = MonosChinos.searchQuery("death note")
    }
}
```

# Get Anime Details
```kotlin
class ExampleViewModel : ViewModel() {

    fun getAnimeDetails() = viewModelScope.launch(Dispatchers.IO) {
        val animeList: List<Anime> = MonosChinos.searchQuery("death note")
        val anime : Anime? = animeList.firstOrNull()

        if (anime != null) {
            val animeDetail = MonosChinos.animeDetailPage(anime.seo)
        }
    }
}
```
### API Endpoints

| **Endpoint**      | **Function**                                | **Description**                                                                   |
|-------------------|---------------------------------------------|-----------------------------------------------------------------------------------|
| **Home**          | `getHome()`                                 | Returns a `Home` object containing the latest updates and recent anime series.    |
| **Player**        | `player(seo: String)`                       | Returns a `Player` object with server options and download links.                 |
| **Search**        | `searchQuery(name: String)`                 | Returns a list of `Anime` objects matching the search query.                      |
| **Anime Details** | `animeDetailPage(seo: String)`              | Returns an `AnimeDetail` object with metadata, to get episodes see episodes(seo). |
| **Chapters**      | `episodes(seo: String)`                     | Returns a list of `Episode` objects for a specific anime.                         |


# Objects
```kotlin
data class Home(
    val lastChapters : List<HomeChapter>,
    val recentSeries : List<HomeAnime>
)
```
```kotlin
data class HomeAnime(
    val seo : String,
    val title : String,
    val type : String,
    val image : String,
    val url : String
)
```
```kotlin
data class HomeChapter(
    val seo : String,
    val title : String,
    val number : Int,
    val type : String,
    val image : String,
    val url : String
)
```
```kotlin
data class Anime(
    val seo : String,
    val title : String,
    val type : String,
    val year : Int,
    val image : String,
    val url : String
)
```
```kotlin
data class AnimeDetail(
    val title : String,
    val alternativeTitle : String?,
    val status : String,
    val coverImage : String,
    val profileImage : String,
    val release : String,
    val synopsis : String,
    val genres : List<String>
)
```
```kotlin
data class Episode(
    val seo : String,
    val number : Int,
    val image : String,
    val url : String
)
```
```kotlin
data class Player(
    val options : List<String>,
    val downloads : List<String>
)
```
# Want to Contribute
Contributions are welcome! Feel free to reach out via my X account @Darkryh or create a pull request.
