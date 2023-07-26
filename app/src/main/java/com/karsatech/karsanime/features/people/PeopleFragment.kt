package com.karsatech.karsanime.features.people

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.paging.LoadingStateAdapter
import com.karsatech.karsanime.core.ui.ListPeopleAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.databinding.FragmentPeopleBinding
import com.karsatech.karsanime.features.people.DetailPeopleActivity.Companion.DETAIL_PEOPLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private val peopleViewModel: PeopleViewModel by viewModels()
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private lateinit var listPeopleAdapter: ListPeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()
    }

    private fun listPeopleLoadingState() {
        listPeopleAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.rvListPeople.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.rvListPeople.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                is LoadState.Error -> {

                }
            }
        }
    }

    private fun observeViewModelAnime() {
        peopleViewModel.topPeoplePagination.observe(viewLifecycleOwner) { people ->
            listPeopleAdapter.submitData(lifecycle, people)
            setPeopleData()
        }
    }

    private fun setPeopleData() {
        listPeopleAdapter.setOnItemClickCallback(object : ListPeopleAdapter.ActionAdapter {
            override fun onItemClick(data: DetailPeopleItem) {
                val people = DataMapper.apiResponseToPeopleModel(data)
                val intent = Intent(activity, DetailPeopleActivity::class.java)
                intent.putExtra(DETAIL_PEOPLE, people)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvListPeople.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            listPeopleAdapter = ListPeopleAdapter()
            adapter = listPeopleAdapter
        }

        listPeopleLoadingState()
        observeViewModelAnime()

        binding.rvListPeople.adapter = listPeopleAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listPeopleAdapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}