package gmail.com.enter1130.itunesplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    // 只有swiperRefreshLayoyt的時候才會執行
    val adapter:iTuneRecycerViewAdapter by lazy{
        iTuneRecycerViewAdapter(listOf<SongData>())
    }

    val swiperRefreshLayoyt by lazy{
        findViewById<SwipeRefreshLayout>(R.id.swiperRefreshLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // layout 名稱
        setContentView(R.layout.activity_swipe_refresh)
        val recycerView=findViewById<RecyclerView>(R.id.recyclerView)
        recycerView.adapter=adapter
        recycerView.addItemDecoration(DividerItemDecoration(
            recycerView.context,DividerItemDecoration.VERTICAL
        ))
        swiperRefreshLayoyt.setOnRefreshListener {
            adapter.notifyDataSetChanged()
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
                songs=iTuneXMLParser().parseURL(""+"https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
            }
            adapter.songs = songs


            adapter.notifyDataSetChanged()

            //song 加入 result後停止刷新
            swiperRefreshLayoyt.isRefreshing=false
        }
    }
}