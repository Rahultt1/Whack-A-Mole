package com.example.whackamole


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreListAdapter(private val scores: List<MainActivity.Score>) :
    RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.nameTextView.text = score.name
        holder.scoreTextView.text = score.score.toString()
        holder.dateTextView.text = score.date
    }

    override fun getItemCount(): Int {
        return scores.size
    }
}
