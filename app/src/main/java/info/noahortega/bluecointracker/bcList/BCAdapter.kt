package info.noahortega.bluecointracker.bcList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.noahortega.bluecointracker.R
import android.os.Build
import kotlinx.android.synthetic.main.list_item.view.*

class BCAdapter(
    private val bcList: List<BCListItem>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<BCAdapter.BCViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        )
        return BCViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BCViewHolder, position: Int) {
        val currentItem = bcList[position]

        holder.checkBox.isChecked = currentItem.collected
        holder.title.text = currentItem.title_text
        holder.level.text = currentItem.level_text
    }

    override fun getItemCount() = bcList.size

    inner class BCViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val checkBoxContainer: LinearLayout = itemView.checkbox_container
        val checkBox: CheckBox = itemView.coin_checkbox
        val title: TextView = itemView.title_text
        val level: TextView = itemView.level_text

        init {
            checkBoxContainer.setOnClickListener(this)
            itemView.setOnClickListener(this)

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val params: ViewGroup.LayoutParams = checkBoxContainer.layoutParams
                params.height = params.width
                checkBoxContainer.layoutParams = params
            }
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if (position != RecyclerView.NO_POSITION && v != null) {
                if(v == checkBoxContainer) { //checkbox was clicked
                    checkBox.performClick()
                    val checked = checkBox.isChecked
                    listener.onItemClick(position, checked, checkClicked = true)
                }
                else { //something else was clicked
                    listener.onItemClick(position, checked = false, checkClicked = false)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, checked: Boolean, checkClicked: Boolean)
    }
}