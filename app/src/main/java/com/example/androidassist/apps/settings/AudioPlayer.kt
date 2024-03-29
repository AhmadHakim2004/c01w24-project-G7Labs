import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.AndroidAssistApplication

class MainActivity : AppCompatActivity() {

    private var BgMusic: MediaPlayer? = null
    private var musicPlaying: Boolean = false
    private val DOUBLE_CLICK_TIME_DELTA: Long = 300 // Time in milliseconds
    private var lastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MediaPlayer
        BgMusic = MediaPlayer.create(AndroidAssistApplication.getAppContext(), R.raw.camera_audio)

        // Set double click listener on root view
        findViewById<View>(android.R.id.content).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // Double click detected, play audio
                    playOrPauseBgMusic()
                }
                lastClickTime = clickTime
            }
            true
        }
    }

    private fun playOrPauseBgMusic() {
        if (musicPlaying) {
            pauseBgMusic()
        } else {
            resumeBgMusic()
        }
    }

    private fun pauseBgMusic() {
        BgMusic?.pause()
        musicPlaying = false
    }

    private fun resumeBgMusic() {
        BgMusic?.start()
        musicPlaying = true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer when the activity is destroyed
        BgMusic?.release()
    }
}
