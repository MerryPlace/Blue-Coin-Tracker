package info.noahortega.bluecointracker.BCList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_list.*

import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.SharedViewModel


class BCListFragment : Fragment(), BCAdapter.OnItemClickListener {

    private val model: SharedViewModel by activityViewModels()

    private lateinit var blueCoinList: List<BCListItem>
    private lateinit var adapter: BCAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        blueCoinList = generateBCList()
        adapter = BCAdapter(blueCoinList, this, requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        my_recycler_view.adapter = adapter
        my_recycler_view.layoutManager = LinearLayoutManager(context)
        my_recycler_view.setHasFixedSize(true)
        activity?.title = model.levelNamesMap[model.curLevelId]
    }

    private fun generateBCList(): List<BCListItem> {
        val list = ArrayList<BCListItem>()
        val coins = model.getQueriedCoins()
        if (coins != null) {
            for (n in coins.indices) {
                val item = BCListItem(
                    coins[n].checked,
                    coins[n].shortTitle, coins[n].description
                )
                list += item
            }
        }
        return list
    }

    override fun onItemClick(position: Int, checked: Boolean, viewType: String) {

        val coin = model.getQueriedCoins()!![position]

        if (viewType == "AppCompatCheckBox") { //checkbox //TODO: probably a better way to do this
            //edit in display
            blueCoinList[position].collected = checked
            adapter.notifyItemChanged(position)

            //edit in data
            model.updateCoin(coin.coinId, checked, false)
        } else {//any other part of the list item
            val navController = this.findNavController()
            model.navToDetailWithCoin(navController, coin.coinId)
        }
    }
}