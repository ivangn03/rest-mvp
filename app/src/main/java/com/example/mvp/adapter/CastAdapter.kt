package com.example.mvp.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.mvp.R
import com.example.mvp.model.Cast
import com.example.mvp.network.ApiClient
import com.bumptech.glide.request.target.Target


class CastAdapter(mContext: Context, castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.MyViewHolder>() {
    private val mContext: Context
    private val castList: List<Cast>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.cast_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cast: Cast = castList[position]
        holder.tvCharacter.text = cast.character
        holder.tvName.setText(cast.name)

        // loading cast profile pic using Glide library
        Glide.with(mContext)
                .load(ApiClient.IMAGE_BASE_URL + cast.profilePath)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(@Nullable e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                        holder.pbLoadProfile.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        holder.pbLoadProfile.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                .into(holder.ivProfilePic)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCharacter: TextView
        var tvName: TextView
        var ivProfilePic: ImageView
        var pbLoadProfile: ProgressBar

        init {
            tvCharacter = itemView.findViewById(R.id.tv_character)
            tvName = itemView.findViewById(R.id.tv_name)
            ivProfilePic = itemView.findViewById(R.id.iv_profile_pic)
            pbLoadProfile = itemView.findViewById(R.id.pb_load_profile)
        }
    }

    init {
        this.mContext = mContext
        this.castList = castList
    }
}