# Android Template

### Summary

This is a quick-start Android template that has some common dependencies set up. Its goal is to make it easy to quickly put together an app with dynamic data coming from a single server URL, although it should be easily extensible if required.

### Structure

The example app contains a MainActivity with a Navigation controller and a drawer. The default fragment is a SwipeRefreshLayout that queries a non-existent service.

### Dependencies

The template is written entirely in Kotlin and depends on the following:
- RxJava 2
- RxAndroid 2
- Epoxy
- Phrase
- Moshi
- OkHttp 3
- Retrofit 2
- AndroidX
  - SwipeRefreshLayout
  - RecyclerView
  - AppCompat
  - Core KTX
  - Lifecycle
  - Navigation
  - Material Components
