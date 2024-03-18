package gmail.com.enter1130.itunesplayer

import android.annotation.SuppressLint
import android.app.ListActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//model
//class MainActivity : AppCompatActivity() {
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        GlobalScope.launch(Dispatchers.Main) {
//            var songs= listOf<SongData>()
//            withContext(Dispatchers.IO){
//                songs=iTuneXMLParser().parseURL(""+"https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
//            }
//
//            val linearLayout=findViewById<LinearLayout>(R.id.main)
//            for (song in songs){
//                val textView=TextView(applicationContext)
//                textView.text=song.title
//                linearLayout.addView(textView)
//            }
//        }
//    }
//}

class MainActivity : ListActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var result= mutableListOf<String>()

        val adapter= ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            result);
        listAdapter=adapter;

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var songs= listOf<SongData>()
                withContext(Dispatchers.IO){
                    songs=iTuneXMLParser().parseURL(""+"https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
                }
                for (song in songs){
                    result.add(song.title)
                }
            }

            adapter.notifyDataSetChanged()
        }


    }
}