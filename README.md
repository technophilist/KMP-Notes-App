# Notes - A multiplatform notes app built with Kotlin Multiplatform
Notes is a simple note taking app built with [KMP(Kotlin Multiplatform)](https://kotlinlang.org/docs/multiplatform.html), formerly known as [KMM (Kotlin Multiplatform Mobile)](https://blog.jetbrains.com/kotlin/2023/07/update-on-the-name-of-kotlin-multiplatform/). The app is supported on both iOS and Android.

## Screenshots

### Android
<img src = "screenshots/android/home_screen.png" width = "240" height = "493"/> &nbsp; <img src = "screenshots/android/search_screen.png" width = "240" height = "493"/> &nbsp;  <img src = "screenshots/android/note_detail_screen.png" width = "240" height = "493"/>

### IOS
<img src = "screenshots/ios/home_screen.png" width = "240" height = "493"/> &nbsp; <img src = "screenshots/ios/search_screen.png" width = "240" height = "493"/> &nbsp;  <img src = "screenshots/ios/note_detail_screen.png" width = "240" height = "493"/>

## Tech Stack

### Common
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) for threading.
- [Kotlinx-coroutines-test](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/) for testing coroutines.
- [Kotlin Flows](https://developer.android.com/kotlin/flow) for creating reactive streams.
- [SQLDelight](https://github.com/cashapp/sqldelight) for local database.
- [Kotlinx Date-Time API](https://github.com/Kotlin/kotlinx-datetime) for date/time operations.
- Manual dependency injection on both platforms.

### Android
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI and navigation on Android.
- [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager?gclid=EAIaIQobChMIwJy33ufG8QIVGcEWBR31Mwa-EAAYASAAEgIF3vD_BwE&gclsrc=aw.ds) for background tasks.

### IOS
- [Swift UI](https://developer.apple.com/xcode/swiftui/) for UI and navigation on IOS.

## Disclaimer
I am primarily an Android developer and I learnt Swift and Swift UI on the fly, while I was developing the app. I am not familiar with ios development atall, so, there might be some slight issues with code quality on the ios side of things. My focus was to get the ios app, up and running as soon as possible with the very limited amount of knoweldge that I had on Swift.
