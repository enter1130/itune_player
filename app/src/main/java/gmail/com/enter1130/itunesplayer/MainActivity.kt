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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    var result= mutableListOf<String>()

    // 只有swiperRefreshLayoyt的時候才會執行
    val adapter by lazy{
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
    }

    val swiperRefreshLayoyt by lazy{
        findViewById<SwipeRefreshLayout>(R.id.swiperRefreshLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // layout 名稱
        setContentView(R.layout.activity_swipe_refresh)
        listAdapter=adapter;
        swiperRefreshLayoyt.setOnRefreshListener {
            result.clear()
            loadList()
        }

        loadList()
    }

    fun loadList(){
        GlobalScope.launch(Dispatchers.Main) {
            var songs= listOf<SongData>()
            //刷新
            swiperRefreshLayoyt.isRefreshing=true
            withContext(Dispatchers.IO){
                withContext(Dispatchers.IO){
                    songs=iTuneXMLParser().parseURL(""+"https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
                }
                for (song in songs){
                    result.add(song.title)
                }
            }

            adapter.notifyDataSetChanged()

            //song 加入 result後停止刷新
            swiperRefreshLayoyt.isRefreshing=false
        }
    }
}