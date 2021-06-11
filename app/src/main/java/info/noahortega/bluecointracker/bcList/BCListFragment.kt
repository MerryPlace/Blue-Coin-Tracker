package info.noahortega.bluecointracker.bcList

import android.content.Context
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
        var useCustomCheckbox = false
        val sharedPref = activity?.getSharedPreferences(getString(R.string.shared_preferences_identifier), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            if(sharedPref.getBoolean(getString(R.string.preference_key_use_coin_checkbox), false)) {
                    useCustomCheckbox = true
            }
        }

        blueCoinList = generateBCList()
        adapter = BCAdapter(blueCoinList, this, useCustomCheckbox)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        my_recycler_view.adapter = adapter
        my_recycler_view.layoutManager = LinearLayoutManager(context)
        my_recycler_view.setHasFixedSize(true)
        activity?.title = model.levelIDToNamesMap[model.curLevelId]
    }

    private fun generateBCList(): List<BCListItem> {
        val list = ArrayList<BCListItem>()
        val coins = model.getFetchedCoins()
        if (coins != null) {
            for (n in coins.indices) {
                val item = BCListItem(
                    coins[n].checked,
                    getString(resources.getIdentifier(coins[n].shortTitle, "string",  activity?.packageName)),
                    model.levelIDToNamesMap[coins[n].myLevelId] + " #" + coins[n].numInLevel
                )
                list += item
            }
        }
        return list
    }

    override fun onItemClick(position: Int, checked: Boolean, checkClicked: Boolean) {
        val coin = model.getFetchedCoins()!![position]
        if (checkClicked) { //checkbox clicked
            //edit in display
            blueCoinList[position].collected = checked
            adapter.notifyItemChanged(position)
            //edit in data
            model.updateCoinCheck(coin.coinId, checked)
            model.editFetchedCoin(position,checked)
        } else {//any other part of the list item clicked
            model.navToDetailWithCoin(findNavController(), coin.coinId, position)
        }
    }
}