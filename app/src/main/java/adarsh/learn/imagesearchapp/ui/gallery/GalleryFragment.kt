package adarsh.learn.imagesearchapp.ui.gallery

import adarsh.learn.imagesearchapp.R
import adarsh.learn.imagesearchapp.databinding.GalleryFragmentBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment: Fragment(R.layout.gallery_fragment) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding:GalleryFragmentBinding?=null
    private val binding get() = _binding!!
    @Inject lateinit var adapter:PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = GalleryFragmentBinding.bind(view)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
        viewModel.photos.observe(viewLifecycleOwner){
                adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}