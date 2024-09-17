import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_assessment.Entity
import com.example.final_assessment.R

class EntityAdapter(
    private val entities: List<Entity>,
    private val onItemClick: (Entity) -> Unit,
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = entities[position]
        holder.bind(entity, onItemClick)
    }

    override fun getItemCount(): Int = entities.size

    class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSportName: TextView = itemView.findViewById(R.id.tvSportName)

        fun bind(entity: Entity, clickListener: (Entity) -> Unit) {
            tvSportName.text = entity.sportName // Show only the sport name
            itemView.setOnClickListener { clickListener(entity) }
        }
    }
}