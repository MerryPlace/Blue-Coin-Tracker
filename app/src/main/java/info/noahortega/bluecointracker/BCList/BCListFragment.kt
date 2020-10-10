package info.noahortega.bluecointracker.BCList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import info.noahortega.bluecointracker.Data
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.database.CoinDatabase
import info.noahortega.bluecointracker.database.CoinDatabaseDao
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class BCListFragment : Fragment(), BCAdapter.OnItemClickListener {

    lateinit var database: CoinDatabaseDao
    private var viewJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private val blueCoinList = generateBCList()
    private val adapter = BCAdapter(blueCoinList, this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = CoinDatabase.getInstance(activity as Context).coinDao

        my_recycler_view.adapter = adapter
        my_recycler_view.layoutManager = LinearLayoutManager(getContext()) //TODO:fear
        my_recycler_view.setHasFixedSize(true)
    }

    private fun generateBCList(): List<BCListItem> {

        val list = ArrayList<BCListItem>()

        var coins = Data.queriedCoins

        if(coins != null) {
            for(n in coins.indices) {
                val item = BCListItem(coins[n].checked,
                    coins[n].shortTitle, coins[n].description!!)
                list += item
            }
        }
        return list
    }

    override fun onItemClick(position: Int, checked: Boolean) {
        //edit in display
        blueCoinList[position].collected = !checked
        adapter.notifyItemChanged(position)
        //edit in data
        val coin = Data.queriedCoins!![position]
        coin.checked = !checked
        uiScope.launch {
            database.updateBlueCoin(coin)
        }
    }
}