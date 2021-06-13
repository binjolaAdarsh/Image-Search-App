package adarsh.learn.imagesearchapp.ui.gallery

import adarsh.learn.imagesearchapp.R
import adarsh.learn.imagesearchapp.databinding.GalleryFragmentBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_footer.*
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.gallery_fragment) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = GalleryFragmentBinding.bind(view)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter() { adapter.retry() },
                footer = PhotoLoadStateAdapter() { adapter.refresh() }
            )
            btRetry.setOnClickListener { adapter.retry() }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        // handling the UI updation when there is error or there is no data available
        adapter.addLoadStateListener {
            binding.apply {
                progressBar.isVisible = it.source.refresh is LoadState.Loading
                recyclerView.isVisible = it.source.refresh is LoadState.NotLoading
                btRetry.isVisible = it.source.refresh is LoadState.Error
                tvError.isVisible = it.source.refresh is LoadState.Error

                if (it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached
                    && adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchImages(query)
                    searchView.clearFocus()
                }
                return true // to indicate we handled the submit button
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true // we handled the action properly
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}