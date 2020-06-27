package bobby.irawan.newsapp.presentation.article.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bobby.irawan.newsapp.R
import bobby.irawan.newsapp.databinding.ItemArticleBinding
import bobby.irawan.newsapp.presentation.model.ArticleModelView
import bobby.irawan.newsapp.utils.parseServerDateFormatToString
import coil.api.load
import coil.size.Scale

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listener: ClickListener? = null
    private var articles = mutableListOf<ArticleModelView>()

    fun setArticles(articles: MutableList<ArticleModelView>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }

    fun updateList(articles: List<ArticleModelView>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
        if (position == articles.size - 1 && articles.size >= 20) {
            listener?.onLoadNextPage()
        }
    }

    inner class ArticleViewHolder(
        val binding: ItemArticleBinding, val listener: ClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleModelView) {
            binding.imageViewNews.load(article.urlImage) {
                placeholder(R.drawable.ic_baseline_image_24)
                error(R.drawable.ic_baseline_broken_image)
                crossfade(true)
                fallback(R.drawable.ic_baseline_image_24)
                scale(Scale.FILL)
            }
            binding.textViewTitle.text = article.title
            binding.textViewArticleDescription.text =
                Html.fromHtml(article.description ?: "No Description").toString()
            binding.textViewNewsDate.text = article.publishedAt?.parseServerDateFormatToString()
            binding.textViewNewsSource.text = article.source ?: "Unknown Source"
            binding.root.setOnClickListener { listener?.onClick(article) }
        }
    }

    interface ClickListener {
        fun onLoadNextPage()

        fun onClick(article: ArticleModelView)
    }
}