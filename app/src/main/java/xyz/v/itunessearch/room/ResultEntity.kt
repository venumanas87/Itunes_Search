package xyz.v.itunessearch.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_results")
data class ResultEntity(@PrimaryKey(autoGenerate = true) val id:Int?,
                        @ColumnInfo(name = "search_query") val searchQuery:String,
                        @ColumnInfo(name = "artist_name") val artistName : String,
                        @ColumnInfo(name = "track_name") val trackName : String,
                        @ColumnInfo(name = "artists_view_url") val artistViewUrl : String,
                        @ColumnInfo(name = "track_view_url") val trackViewUrl : String,
                        @ColumnInfo(name = "preview_url") val previewUrl : String,
                        @ColumnInfo(name = "artwork_url_100") val artworkUrl100 : String,
                        @ColumnInfo(name = "collection_price") val collectionPrice : Double,
                        @ColumnInfo(name = "track_price") val trackPrice : Double,
                        @ColumnInfo(name = "release_date") val releaseDate : String,
                        @ColumnInfo(name = "collection_exp") val collectionExplicitness : String,
                        @ColumnInfo(name = "track_exp") val trackExplicitness : String,
                        @ColumnInfo(name = "track_time_millis") val trackTimeMillis : Int,
                        @ColumnInfo(name = "country") val country : String,
                        @ColumnInfo(name = "currency") val currency : String,
                        @ColumnInfo(name = "primary_genre_name  ") val primaryGenreName : String)
