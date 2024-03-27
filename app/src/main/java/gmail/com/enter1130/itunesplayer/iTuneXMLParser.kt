package gmail.com.enter1130.itunesplayer
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class iTuneXMLParser {
    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()

    fun parseURL(url: String): List<SongData> {
        var title = ""
        var cover: Bitmap? = null
        val songList = mutableListOf<SongData>()

        // parse xml doc
        try {
            val inputStream = URL(url).openStream()
            parser.setInput(inputStream, null)

            var eventType = parser.next()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when(eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "title" && parser.depth == 3) {
                            title = parser.nextText()
                            Log.i("title", title)
                        } else if (parser.name == "media:thumbnail") {
                            if (parser.getAttributeValue(null, "height") == "360") {
                                val coverURL = parser.getAttributeValue(null, "url")
                                Log.i("coverURL", coverURL)
                                val inputStream = URL(coverURL).openStream()
                                cover = BitmapFactory.decodeStream(inputStream)
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "entry") {
                            songList.add(SongData(title, cover))
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return  songList
    }
}