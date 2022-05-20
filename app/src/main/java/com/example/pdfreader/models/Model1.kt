package com.example.pdfreader.models

import android.net.Uri

data class Model1(
    val uri: Uri? = null,
    val name: String? = null,
    val size: Long? = null,
    val dateAdded: Long? = null,
    val dateModified: Long? = null
)
//data class Model1(var iconsImage: ImageView? = null,
  //                var titleText: String? = null)
