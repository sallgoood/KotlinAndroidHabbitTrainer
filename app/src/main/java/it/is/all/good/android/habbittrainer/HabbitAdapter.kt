package it.`is`.all.good.android.habbittrainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.single_card.view.*

class HabbitAdapter(val habbits: List<Habbit>) : RecyclerView.Adapter<HabbitAdapter.HabbitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabbitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)
        return HabbitViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: HabbitViewHolder, index: Int) {
        val habbit = habbits[index]

        with(viewHolder) {
            card.tv_title.text = habbit.title
            card.tv_description.text = habbit.description
            card.iv_icon.setImageBitmap(habbit.image)
        }
    }

    override fun getItemCount(): Int = habbits.size

    class HabbitViewHolder(val card: View) : RecyclerView.ViewHolder(card)
}
