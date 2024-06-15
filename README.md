[![](https://jitpack.io/v/darkryh/MonosChinosApi.svg)](https://jitpack.io/#darkryh/MonosChinosApi)

Api lib with similar patron as a Builder Pattern + Builded with kotlin + Kotlin Coroutines :balloon:

# Installation
with Gradle
```groovy  
repositories {   
	maven { url 'https://jitpack.io' }  
}  
  
dependencies {  
	implementation("com.github.darkryh:MonosChinosApi:$version")
}  
```  
# Example to get HomePage
```kotlin
ViewModel() {

	fun getHome(context : Context) = viewModelScope.launch(IO) {

		val home : Home? = MonosChinos
                .builder(context)
                .homePage()
                .get()
				
		val lastChapter : List<HomeChapter> = home?.lastChapters
		val recentSeries : List<HomeAnime> = home?.recentSeries
	}
}
```
# Example to Search Anime
```kotlin
ViewModel() {

	fun getAnimes(context : Context) = viewModelScope.launch(IO) {

		val animeList : List<Anime> = MonosChinos
                .builder(context)
                .searchPage("death note")
                .get()
	}
}
```

# Example to get Anime Details
```kotlin
ViewModel() {

	fun getAnimes(context : Context) = viewModelScope.launch(IO) {

		val animeList : List<Anime> = MonosChinos
                .builder(context)
                .searchPage("death note")
                .get()
			
		val anime = animeList.first()
			
		//the seo is the id set the query
			
		val animeDetail = MonosChinos
                .builder(context)
                .animeDetailPage(anime.seo)
                .get()
	}
}
```
# Requests
- **Home** : the function homePage().get() return a list
- **Player** : the function playerPager(seo : String).get() return a Player?, the server options
- **Search** : the function searchPage(name : String).get() return a list
- **AnimeDetail** : the function animeDetailPage(seo : String) return AnimeDetail?
- **Chapters** : the function chaptersPage(seo : String) return a list

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
No problem sr, just contact me in my X account @Darkryh or just make a request.
