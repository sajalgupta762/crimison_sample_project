package com.example.crimison_sample_project

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimison_sample_project.Adapter.SearchResultAdapter
import com.example.crimison_sample_project.DataBase.DatabaseBuilder
import com.example.crimison_sample_project.DataBase.DatabaseHelperImpl
import com.example.crimison_sample_project.DataBase.SearchXDB
import com.example.crimison_sample_project.Utils.*
import com.example.crimison_sample_project.ViewModel.MainActivityViewModel
import com.example.crimison_sample_project.ViewModel.MainViewModel
import com.example.crimison_sample_project.ViewModel.ViewModelFactory
import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.models.SearchX
import com.example.crimison_sample_project.remote.ApiHelper
import com.example.crimison_sample_project.remote.RetrofitBuilder
import com.mindorks.example.coroutines.data.api.ApiHelperImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Response
import java.util.logging.Logger
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() , OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SearchResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val TAG = MainActivity::class.qualifiedName
    private lateinit var logger :Logger
    private lateinit var listner :PaginationScrollListener
    private var isConnectivityAvaliable by Delegates.notNull<Boolean>()
      lateinit var loadMoreItemsCells: ArrayList<String?>
      lateinit var recyclerViewLoadMoreScroll: RecyclerViewLoadMoreScroll
    private var pageNo :Int =0
    val list = arrayListOf<SearchX>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getActionBar()?.title.apply {
            setTitle("")
        }


        recyclerView = findViewById(R.id.posterListRv) as RecyclerView
      //  logger = LoggerFactory.getLogger(MainActivity::class)
        isConnectivityAvaliable = Utility.isConnectingToInternet(this)
        pageNo=1
      //  previouslyStarted()
        setupUI()
        setupViewModel()
        setupObservers()
        pageNo++
        viewModel.searchdata("movie","5d81e1ce",pageNo.toString(),"guardians")
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        if (searchItem != null) {
            searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            searchView.setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    return true
                }
            })

            val searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = "Search"
            val searchPlateView: View =
                searchView.findViewById(androidx.appcompat.R.id.search_plate)
            searchPlateView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query?.length !!>=4){
                        viewModel.searchdata(query,"5d81e1ce","1","guardians")
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    return false
                }
            })

            val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return super.onCreateOptionsMenu(menu)
    }






    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerViewLoadMoreScroll = RecyclerViewLoadMoreScroll(recyclerView.layoutManager as LinearLayoutManager)
        recyclerViewLoadMoreScroll.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                viewModel.searchdata("movie","5d81e1ce",pageNo.toString(),"guardians")

                //LoadMoreData()
            }
        })
        recyclerView.addOnScrollListener(recyclerViewLoadMoreScroll)


        adapter = SearchResultAdapter(arrayListOf(),this)

        RecyclerViewLoadMoreScroll(recyclerView.layoutManager as LinearLayoutManager)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        adapter.addLoadingView()
        recyclerView.adapter = adapter


    }









    private fun setupObservers() {
        viewModel.getSearch().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progress_Bar.visibility = View.GONE
                        error_tv.visibility=View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.GONE
                        progress_Bar.visibility = View.GONE
                        error_tv.visibility=View.VISIBLE
                        error_tv.setText(it.message)

                        Log.d(TAG, it.message)
                    }
                    Status.LOADING -> {
                        progress_Bar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        error_tv.visibility=View.GONE
                    }
                }
            }
        })
    }

    private  fun retrieveList(users: Response<Search>) {
        list.addAll(users.body()!!.Search)
        adapter.apply {
            addUsers(users.body()!!.Search)



         // notifyDataSetChanged()

        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )).get(MainViewModel::class.java)
    }

    override fun onItemClicked(position: Int) {

        val intent = Intent(this, DetailActivity::class.java)
       // intent.putExtra("movietitle", "guardians")
        intent.putExtra("movietitle", list.get(position).Title)
        startActivity(intent)

    }


    private fun previouslyStarted(){

        val sharedpreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        if (!sharedpreferences.getBoolean("prevStarted", false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean("prevStarted", true)
            editor.apply()

        } else {
            finish()
        }




    }

  /*  private fun LoadMoreData() {
        //Add the Loading View
        adapter.addLoadingView()
        //Create the loadMoreItemsCells Arraylist
        loadMoreItemsCells = ArrayList()
        //Get the number of the current Items of the main Arraylist
        val start = adapterLinear.itemCount
        //Load 16 more items
        val end = start + 16
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            for (i in start..end) {
                //Get data and add them to loadMoreItemsCells ArrayList
                loadMoreItemsCells.add("Item $i")
            }
            //Remove the Loading View
            adapter.removeLoadingView()
            //We adding the data to our main ArrayList
            adapterLinear.addData(loadMoreItemsCells)
            //Change the boolean isLoading to false
            scrollListener.setLoaded()
            //Update the recyclerView in the main thread
            items_linear_rv.post {
                adapterLinear.notifyDataSetChanged()
            }
        }, 3000)

    }
*/















}
