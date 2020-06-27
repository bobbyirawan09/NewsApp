package bobby.irawan.newsapp.presentation.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bobby.irawan.newsapp.databinding.ItemCategoryBinding
import bobby.irawan.newsapp.presentation.model.CategoryModelView
import bobby.irawan.newsapp.utils.getDrawableIdentifier
import coil.api.load
import java.util.*

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: ClickListener? = null
    private var categories = listOf<CategoryModelView>()

    fun setCategories(categories: List<CategoryModelView>) {
        this.categories = categories
    }

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context)),
            listener
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is CategoryViewHolder -> holder.bind(categories[position])
        }
    }

    class CategoryViewHolder(
        val binding: ItemCategoryBinding,
        val listener: ClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryModelView) {
            val drawableIdentifier = itemView.context.getDrawableIdentifier(category.image)
            binding.imageViewIllustration.load(drawableIdentifier)
            binding.root.setOnClickListener {
                listener?.onClickListener(category)
            }
            binding.textViewCategoryName.text = category.name?.toUpperCase(Locale.getDefault())
        }
    }

    interface ClickListener {
        fun onClickListener(category: CategoryModelView)
    }
}