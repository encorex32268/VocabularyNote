
# Dependencies


## App Build.gradle

```
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'kotlin-parcelize'
    id 'dagger.hilt.android.plugin'
}

```


```
dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
    implementation 'androidx.activity:activity-compose:1.3.1'
    implementation "androidx.compose.ui:ui:$compose_ui_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_ui_version"
    implementation 'androidx.compose.material:material:1.1.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_ui_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_ui_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_ui_version"


    // Compose

    implementation "androidx.compose.runtime:runtime-livedata:$compose_ui_version"
    implementation "androidx.compose.material:material-icons-extended:$compose_ui_version"

    // Navigation
    implementation "androidx.navigation:navigation-compose:$navigation_version"

    // Lifecycle
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"

    // Hilt
    implementation "com.google.dagger:hilt-android:$hilt_plugin_version"
    implementation "com.google.dagger:hilt-android-compiler:$hilt_plugin_version"
    kapt "com.google.dagger:hilt-compiler:$hilt_plugin_version"
    implementation "androidx.hilt:hilt-navigation-compose:$hilt_version"



    // Room
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-ktx:$room_version"


}
```



## Project Build.gradle

```

buildscript {
    ext {
        compose_ui_version = '1.1.1'
        gradle_version = '7.2.1'
        kotlin_version = '1.5.31'
        core_version = '1.7.0'
        lifecycle_version = '2.4.1'
        activity_compose_version = '1.4.0'
        navigation_version = '2.4.2'
        hilt_plugin_version = '2.41'
        hilt_version = '1.0.0'
        room_version = '2.4.2'
    }
    repositories {
        // other repositories...
        mavenCentral()
        google()

    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_plugin_version")
    }

}

```