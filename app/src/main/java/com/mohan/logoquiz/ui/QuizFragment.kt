package com.mohan.logoquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohan.logoquiz.R
import com.mohan.logoquiz.data.Repository
import com.mohan.logoquiz.ui.GenericAdapter
import com.mohan.logoquiz.databinding.FragmentQuizLayoutBinding
import com.mohan.logoquiz.presentation.QuizViewModel
import com.mohan.logoquiz.presentation.QuizViewModelfactory
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() {


    private lateinit var binding: FragmentQuizLayoutBinding

    private val viewModel: QuizViewModel by lazy {
        ViewModelProvider(this,quizViewModelfactory).get(QuizViewModel::class.java)
    }

    private val quizViewModelfactory by lazy {
        QuizViewModelfactory(Repository.getRepoInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchLogos()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quizAdapter = getAdapterForScrambledWord()
        prepareAdapter(quizAdapter)
        observeLogoItem(quizAdapter)
    }

    private fun observeLogoItem(quizAdapter: GenericAdapter<Char>?) {
        viewModel.getCurrentLogo().observe(viewLifecycleOwner, Observer {
            it?.let {
                quizAdapter?.updateItems(it.getScrambledChars())
                binding.obj = it
            }
        })
    }

    private fun prepareAdapter(quizAdapter: GenericAdapter<Char>?) {
        binding.wordsScrambledList.run {
            layoutManager = GridLayoutManager(binding.wordsScrambledList.context, SPAN_COUNT)
            setHasFixedSize(true)
            adapter = quizAdapter
        }
    }

    private fun getAdapterForScrambledWord(): GenericAdapter<Char>? {
        return object : GenericAdapter<Char>(listOf()) {
            override fun getLayoutId(position: Int, obj: Char): Int {
                return R.layout.single_char_layout
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return CharViewHolder(view) {
                    viewModel.markSelected(it)
                }
            }

        }
    }

    companion object {
        private val SPAN_COUNT = 10
        fun newInstance(): QuizFragment {
            return QuizFragment()
        }

        @JvmStatic
        @BindingAdapter("app:imageUrl")
        fun updateImage(view:ImageView,url:String?){
            url?.let {
                Picasso.with(view.context).load(url).into(view)
            }
        }
    }


}