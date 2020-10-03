package `in`.vrkhedkr.petcare.view.adpaters

import `in`.vrkhedkr.petcare.R
import `in`.vrkhedkr.petcare.model.Pet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetListAdapter : RecyclerView.Adapter<PetListAdapter.ViewHolder>() {

    var petList:List<Pet>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = petList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        petList?.get(position)?.let{
            holder.bind(it)
        }
    }

    fun setData(petList:List<Pet>){
        this.petList = petList
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        private val petImage:ImageView = view.findViewById(R.id.petImage);
        private val petTitle:TextView = view.findViewById(R.id.petTitle);
        private val addedDate:TextView = view.findViewById(R.id.addedDate);
        fun bind(pet:Pet){
            //setImage
            petTitle.text = pet.title
            addedDate.text = view.context.getString(R.string.since,pet.dateAdded)
        }
    }
}