package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Dunia Usaha", appName)
  }

  @Test
  fun `all 26 pdf slide webp assets exist`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val files = context.assets.list("slides")?.toSet().orEmpty()
    for (page in 1..26) {
      val expected = String.format(java.util.Locale.US, "slide_%02d.webp", page)
      org.junit.Assert.assertTrue("Missing $expected", files.contains(expected))
    }
  }

  @Test
  fun `bundled backsound mp3 raw resource exists`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    context.resources.openRawResource(R.raw.backsound_ceria).use { stream ->
      org.junit.Assert.assertTrue(stream.available() > 0)
    }
  }
}
