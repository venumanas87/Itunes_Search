package xyz.v.itunessearch.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import org.w3c.dom.Text
import xyz.v.itunessearch.R
import xyz.v.itunessearch.objects.Results

class SearchAdapter(private val resultlist:List<Results>):RecyclerView.Adapter<SearchAdapter.mvh>() {

    class mvh(view: View):RecyclerView.ViewHolder(view){
        val albumIV:ImageView = view.findViewById(R.id.album_iv)
        val artistTV:TextView = view.findViewById(R.id.artistname_tv)
        val genreTV:TextView = view.findViewById(R.id.genre_tv)
        val priceTV:TextView = view.findViewById(R.id.trackprice_tv)
        val playBtn:ImageView = view.findViewById(R.id.playBTN)
        val releasedateTV:TextView = view.findViewById(R.id.release_date_tv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mvh {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.search_item,parent,false)
        return mvh(item)
    }

    override fun onBindViewHolder(holder: mvh, position: Int) {
        val obj = resultlist[position]
        val mediaPlayer = MediaPlayer()
        var isplaying = 0
        var c = 0
        Glide.with(holder.albumIV.context)
                .load(obj.artworkUrl100)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(holder.albumIV)
        holder.artistTV.text = obj.artistName
        holder.genreTV.text = obj.primaryGenreName
        holder.priceTV.text = "${obj.trackPrice} ${obj.currency}"
        holder.releasedateTV.text = obj.releaseDate

            holder.playBtn.setOnClickListener {
                if (!obj.offline) {
                mediaPlayer.setOnCompletionListener {
                    Glide.with(holder.playBtn.context)
                        .load(
                            ContextCompat.getDrawable(
                                holder.playBtn.context,
                                R.drawable.ic_baseline_play_arrow_24
                            )
                        )
                        .into(holder.playBtn)
                }
                if (!mediaPlayer.isPlaying) {
                    Glide.with(it.context)
                        .load(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_baseline_pause_24
                            )
                        )
                        .into(holder.playBtn)
                    c++
                    if (c < 2) {
                        mediaPlayer.setDataSource(obj.previewUrl)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    } else
                        holder.playBtn.setOnClickListener { mediaPlayer.start() }
                } else {
                    Glide.with(it.context)
                        .load(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_baseline_play_arrow_24
                            )
                        )
                        .into(holder.playBtn)
                    mediaPlayer.pause()
                }
            }else{
                Toast.makeText(it.context,"Unavailable in Offline mode",Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun getItemCount(): Int {
       return resultlist.size
    }

}