package gmail.com.enter1130.itunesplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class iTuneRecycerViewAdapter(data: List<SongData>):RecyclerView.Adapter<iTuneRecycerViewAdapter.ViewHolder>(){
    var songs=data
        set(value){
            field=value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val textView=view.findViewById<TextView>(R.id.textView)
        val imageView=view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(
            R.layout.itune_list_item,parent,false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=songs[position].title
        holder.imageView.setImageBitmap(songs[position].cover)
    }

}