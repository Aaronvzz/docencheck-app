# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the Android SDK proguard/proguard-android.txt file.

# Keep Kotlin metadata
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}
