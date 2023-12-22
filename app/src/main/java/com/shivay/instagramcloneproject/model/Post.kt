package com.shivay.instagramcloneproject.model

class Post {
    var postUrl:String =""
    var caption:String=""
    constructor()
    constructor(postUrl: String, caption: String) {
        this.postUrl = postUrl
        this.caption = caption
    }

}