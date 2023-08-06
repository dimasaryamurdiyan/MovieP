package com.singaludra.moviep.presentation.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.singaludra.moviep.databinding.ViewDialogVideoBinding

class VideoPlayerDialogFragment : DialogFragment() {

    private lateinit var binding : ViewDialogVideoBinding
    lateinit var subChapterPlayer: YouTubePlayer
    private val url by lazy {
        arguments?.getString(KEY_URL)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewDialogVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            releaseYoutubePlayer()
        }

        initYoutubePlayer()
    }

    private fun initYoutubePlayer(){
        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .rel(0)
            .ivLoadPolicy(1)
            .ccLoadPolicy(1)
            .build()

        with(binding.yotubePlayerView){
            lifecycle.addObserver(this)
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    subChapterPlayer = youTubePlayer

                    //set default custom ui here
                    subChapterPlayer.loadOrCueVideo(lifecycle, url ?: "", 0F)
                }

            }, true, iFramePlayerOptions)
        }
    }

    private fun releaseYoutubePlayer() {
        dismiss()
        binding.yotubePlayerView.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseYoutubePlayer()
    }

    companion object {
        const val TAG = "DialogVideo"
        private const val KEY_URL = "KEY_URL"

        fun newInstance(url:String): VideoPlayerDialogFragment {
            val args = Bundle()
            args.putString(KEY_URL, url)
            val fragment = VideoPlayerDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}