package gmail.com.enter1130.itunesplayer

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class iTuneXMLParser {
    val factory=XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()

    suspend fun parseURL(url: String):List<SongData>{
        var title=""
        val songList= mutableListOf<SongData>()
        try {
            val inputStream=URL(url).openStream()
            parser.setInput(inputStream,null)
            var eventType=parser.next()
            while (eventType!=XmlPullParser.END_DOCUMENT){
                when(eventType){
                    XmlPullParser.START_TAG->{
                        if(parser.name=="title" && parser.depth==3){
                            title=parser.nextText()
                        }
                        if(parser.name=="yt:videoId" && parser.depth==3){
                            Log.i("id",parser.nextText())
                        }
                    }
                    XmlPullParser.END_TAG->{
                        if(parser.name=="entry"){
                            songList.add(SongData(title))
                        }
                    }
                }
                eventType=parser.next()
            }
        }catch (e:Throwable){
            e.printStackTrace()
        }

        return songList;
    }
}