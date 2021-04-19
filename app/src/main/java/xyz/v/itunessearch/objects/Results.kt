package xyz.v.itunessearch.objects

data class Results(val offline:Boolean = false,
                   val artistName : String?,
                   val trackName : String?,
                   val artistViewUrl : String?,
                   val trackViewUrl : String?,
                   val previewUrl : String?,
                   val artworkUrl100 : String?,
                   val collectionPrice : Double?,
                   val trackPrice : Double?,
                   val releaseDate : String?,
                   val collectionExplicitness : String?,
                   val trackExplicitness : String?,
                   val trackTimeMillis : Int?,
                   val country : String?,
                   val currency : String?,
                   val primaryGenreName : String?)
