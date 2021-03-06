package com.adr.nbascore.model.last_game

data class LastGame(
    val dateEvent: String,
    val dateEventLocal: String,
    val idAPIfootball: Any,
    val idAwayTeam: String,
    val idEvent: String,
    val idHomeTeam: String,
    val idLeague: String,
    val idSoccerXML: Any,
    val intAwayScore: String,
    val intAwayShots: Any,
    val intHomeScore: String,
    val intHomeShots: Any,
    val intRound: String,
    val intSpectators: Any,
    val strAwayFormation: Any,
    val strAwayGoalDetails: Any,
    val strAwayLineupDefense: Any,
    val strAwayLineupForward: Any,
    val strAwayLineupGoalkeeper: Any,
    val strAwayLineupMidfield: Any,
    val strAwayLineupSubstitutes: Any,
    val strAwayRedCards: Any,
    val strAwayTeam: String,
    val strAwayYellowCards: Any,
    val strBanner: Any,
    val strCircuit: Any,
    val strCity: Any,
    val strCountry: Any,
    val strDate: Any,
    val strDescriptionEN: String,
    val strEvent: String,
    val strEventAlternate: String,
    val strFanart: Any,
    val strFilename: String,
    val strHomeFormation: Any,
    val strHomeGoalDetails: Any,
    val strHomeLineupDefense: Any,
    val strHomeLineupForward: Any,
    val strHomeLineupGoalkeeper: Any,
    val strHomeLineupMidfield: Any,
    val strHomeLineupSubstitutes: Any,
    val strHomeRedCards: Any,
    val strHomeTeam: String,
    val strHomeYellowCards: Any,
    val strLeague: String,
    val strLocked: String,
    val strMap: Any,
    val strPoster: Any,
    val strResult: String,
    val strSeason: String,
    val strSport: String,
    val strTVStation: Any,
    val strThumb: Any,
    val strTime: String,
    val strTimeLocal: String,
    val strTweet1: String,
    val strTweet2: String,
    val strTweet3: String,
    val strVideo: String
)