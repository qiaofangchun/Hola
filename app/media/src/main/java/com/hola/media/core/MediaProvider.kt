import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat

object MediaProvider {
    val mediaData = arrayListOf(
        MediaBrowserCompat.MediaItem(
            MediaDescriptionCompat.Builder()
                .setMediaId("548fc0ca7dbb")
                .setMediaUri(Uri.parse("https://www.xzmp3.com/down/548fc0ca7dbb.mp3"))
                .setTitle("Because Of You")
                .build(),
            MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        ),
        MediaBrowserCompat.MediaItem(
            MediaDescriptionCompat.Builder()
                .setMediaId("8ca0713d630b")
                .setMediaUri(Uri.parse("https://www.xzmp3.com/down/8ca0713d630b.mp3"))
                .setTitle("错位时空")
                .build(),
            MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        ),
        MediaBrowserCompat.MediaItem(
            MediaDescriptionCompat.Builder()
                .setMediaId("fb8f3a845578")
                .setMediaUri(Uri.parse("https://www.xzmp3.com/down/fb8f3a845578.mp3"))
                .setTitle("星辰大海")
                .build(),
            MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        ),
        MediaBrowserCompat.MediaItem(
            MediaDescriptionCompat.Builder()
                .setMediaId("597caee79849")
                .setMediaUri(Uri.parse("https://www.xzmp3.com/down/597caee79849.mp3"))
                .setTitle("See You Again")
                .build(),
            MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        )
    )
}