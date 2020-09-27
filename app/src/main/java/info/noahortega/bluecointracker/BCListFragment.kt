package info.noahortega.bluecointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), BCAdapter.OnItemClickListener {

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
        my_recycler_view.adapter = adapter
        my_recycler_view.layoutManager = LinearLayoutManager(getContext()) //TODO:fear
        my_recycler_view.setHasFixedSize(true)
    }

    private fun generateBCList(): List<BCListItem> {
        val coins = Data.levels[Data.levelSelected.code].blueCoins
        val list = ArrayList<BCListItem>()

        for(n in coins.indices) {
            val item = BCListItem(coins[n].checked,Data.levelSelected.toString().toUpperCase()+" "+(n+1), coins[n].description!!)
            list += item
        }
        return list
    }

    override fun onItemClick(position: Int, checked: Boolean) {
        //edit in display
        blueCoinList[position].collected = !checked
        adapter.notifyItemChanged(position)
        //edit in data
        Data.levels[Data.levelSelected.code].blueCoins[position].checked = !checked
    }
}