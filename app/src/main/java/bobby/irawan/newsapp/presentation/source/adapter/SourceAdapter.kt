package bobby.irawan.newsapp.presentation.source.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bobby.irawan.newsapp.databinding.ItemSourceBinding
import bobby.irawan.newsapp.presentation.model.SourceModelView
import java.util.*

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class SourceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: ClickListener? = null
    private var sources = listOf<SourceModelView>()

    fun setSources(sources: List<SourceModelView>) {
        this.sources = sources
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SourceViewHolder(
            ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return sources.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is SourceViewHolder -> holder.bind(sources[position])
        }
    }

    class SourceViewHolder(
        val binding: ItemSourceBinding,
        val listener: ClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(source: SourceModelView) {
            binding.textViewCategoryName.text = source.category
            binding.textViewLanguage.text = source.language?.toUpperCase(Locale.getDefault())
            binding.textViewSourceDescription.text = source.description
            binding.textViewSourceName.text = source.name
            binding.root.setOnClickListener { listener?.onClickListener(source) }
        }
    }

    interface ClickListener {
        fun onClickListener(source: SourceModelView)
    }
}