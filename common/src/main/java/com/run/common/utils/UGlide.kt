package com.run.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.run.common.R
import com.run.common.view.GlideRoundTransform

/**
 * Glide图片加载
 */
object UGlide {

    val TAG = "UGlide"
    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun loadImage(context: Context?, url: String, imageView: ImageView) {
        if (TextUtils.isEmpty(url)) return
        if (context == null) return
        val options = RequestOptions()
        options.placeholder(R.mipmap.df)
                .error(R.mipmap.df)
                .fallback(R.mipmap.df)
        Glide.with(context).load(url).into(imageView)
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun loadCircleImage(context: Context?, url: String?, imageView: ImageView) {
        if (TextUtils.isEmpty(url)) return
        if (context == null) return
        val requestOptions = RequestOptions.circleCropTransform()
        requestOptions
                .placeholder(R.mipmap.uh)
                .error(R.mipmap.uh)
                .fallback(R.mipmap.uh)
        Glide.with(context).load(url).apply(requestOptions).into(imageView)

    }


    /**
     * 加载gif图片
     */
    fun loadGif(context: Context?, url: String, imageView: ImageView) {
        if (TextUtils.isEmpty(url)) return
        if (context == null) return
        Glide.with(context).asGif().load(url).into(imageView)
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param round
     */
    fun loadRoundImage(context: Context?, url: String, imageView: ImageView, round: Int) {
        try {
            if (TextUtils.isEmpty(url) || context == null) return
            val myOptions = RequestOptions()
                    .transform(GlideRoundTransform(context, round))
            myOptions
                    .placeholder(R.mipmap.df)
                    .error(R.mipmap.df)
                    .fallback(R.mipmap.df)
            Glide.with(context)
                    .load(url)
                    .apply(myOptions)
                    .into(imageView)
        } catch (e: Exception) {
            ULog.e(TAG, e)
            Glide.with(context!!).load(R.mipmap.df).into(imageView)
        }
    }


    /**
     * 加载宽度铺满高度适应的图片
     */
    fun loadImageFitWidth(context: Context?, url: String, imageView: ImageView) {
        if (TextUtils.isEmpty(url) || context == null) return
        Glide.with(context).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val imageWidth = resource.width
                val imageHeight = resource.height
                val height = USize.getWidth(context) * imageHeight / imageWidth
                val para = imageView.layoutParams
                para.height = height
                para.width = USize.getWidth(context)
                imageView.setImageBitmap(resource)
            }
        })
    }
}
