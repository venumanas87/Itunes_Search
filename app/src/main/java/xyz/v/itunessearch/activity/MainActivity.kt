package xyz.v.itunessearch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.v.itunessearch.adapter.SearchAdapter
import xyz.v.itunessearch.databinding.ActivityMainBinding
import xyz.v.itunessearch.objects.Results
import xyz.v.itunessearch.room.ResultEntity
import xyz.v.itunessearch.utils.InternetConnection
import xyz.v.itunessearch.viewmodel.RoomViewModel
import xyz.v.itunessearch.viewmodel.TracksViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var searchlist:ArrayList<Results> = ArrayList()
    val adapter = SearchAdapter(searchlist)
    lateinit var rvm:RoomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tvm: TracksViewModel = ViewModelProvider(this).get(TracksViewModel::class.java)
        rvm = ViewModelProvider(this).get(RoomViewModel::class.java)
        rvm.buildDatabase(this)

        if(!InternetConnection.checkConnection(this)){
            Snackbar.make(binding.root,"No Internet AVailable",Snackbar.LENGTH_INDEFINITE)
                .setAction("Fetch") {
                    offlineFetchData()
                }.show()
        }

        binding.recyclerView.adapter = adapter

        binding.searchIv.setOnClickListener {
            searchlist.clear()
            adapter.notifyDataSetChanged()
            if (!InternetConnection.checkConnection(this)) {
                offlineFetchData()
            } else {
                binding.progress.visibility = View.VISIBLE
                val sear = binding.searchEt.text.toString()
                tvm.fetchResult(sear).observe(this, Observer {
                    binding.progress.visibility = View.GONE
                    it.results.forEach {
                        val resEnt = ResultEntity(
                            null,
                            sear,
                            it.artistName?:"",
                            it.trackName?:"",
                            it.artistViewUrl?:"",
                            it.trackViewUrl?:"",
                            it.previewUrl?:"",
                            it.artworkUrl100?:"",
                            it.collectionPrice?:0.toDouble(),
                            it.trackPrice?:0.toDouble(),
                            it.releaseDate?:"",
                            it.collectionExplicitness?:"",
                            it.trackExplicitness?:"",
                            it.trackTimeMillis?:0,
                            it.country?:"",
                            it.currency?:"",
                            it.primaryGenreName?:""
                        )
                        GlobalScope.launch {
                            rvm.insertResult(resEnt)
                        }
                        searchlist.add(it)
                        adapter.notifyDataSetChanged()
                    }
                    adapter.notifyDataSetChanged()
                })
            }
        }


        binding.delete.setOnClickListener {
            Toast.makeText(this,"Deleted all Entries",Toast.LENGTH_SHORT).show()
            GlobalScope.launch {
                rvm.deleteAll()
            }
        }


    }



    fun offlineFetchData(){
        Toast.makeText(this,"NO INTERNET AVAILABLE\nFetching data offline",Toast.LENGTH_LONG).show()
        GlobalScope.launch {  rvm.getAllSerachResults()}
        binding.delete.visibility = View.VISIBLE
        rvm.result.observe(this@MainActivity, Observer {
            it.forEach {
                val res = Results(true,it.artistName,it.trackName,it.artistViewUrl,it.trackViewUrl,it.previewUrl,it.artworkUrl100,it.collectionPrice,it.trackPrice,it.releaseDate,it.collectionExplicitness,it.trackExplicitness,it.trackTimeMillis,it.country,it.currency,it.primaryGenreName)
                searchlist.add(res)
                adapter.notifyDataSetChanged()
            }
        })
    }
}