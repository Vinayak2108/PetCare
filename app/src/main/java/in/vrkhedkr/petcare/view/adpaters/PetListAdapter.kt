package `in`.vrkhedkr.petcare.view.adpaters

import `in`.vrkhedkr.petcare.R
import `in`.vrkhedkr.petcare.model.Pet
import `in`.vrkhedkr.petcare.network.lazyimg.LazyImage
import `in`.vrkhedkr.petcare.util.DateUtil
import `in`.vrkhedkr.petcare.view.activity.PetDetails
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetListAdapter(private val petList: List<Pet>) : RecyclerView.Adapter<PetListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = petList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(petList[position])
    }

    class ViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        private val petImage:ImageView = view.findViewById(R.id.petImage)
        private val petTitle:TextView = view.findViewById(R.id.petTitle)
        private val addedDate:TextView = view.findViewById(R.id.addedDate)
        fun bind(pet:Pet){
            LazyImage.load(pet.imgUrl,petImage,R.drawable.ic_placeholder)
            petTitle.text = pet.title
            addedDate.text = view.context.getString(R.string.since,
                DateUtil.getSimpleDateString(pet.dateAdded)
            )
            view.setOnClickListener {
                it.context.startActivity(Intent(it.context,PetDetails::class.java).apply {
                    putExtra("url", pet.contentUrl)
                    putExtra("title", pet.title)
                })
            }
        }
    }
}