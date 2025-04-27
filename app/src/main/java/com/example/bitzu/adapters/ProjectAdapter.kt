import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitzu.R
import com.example.bitzu.adapters.TagAdapter
import com.example.bitzu.models.Project

class ProjectAdapter(private val projectList: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.text_view_project_name)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_project_description)
        val textViewLink: TextView = itemView.findViewById(R.id.text_view_project_link)
        val recyclerViewTags: RecyclerView = itemView.findViewById(R.id.recycler_view_tags)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false) // Use o nome correto do seu layout de item
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentProject = projectList[position]
        holder.textViewName.text = currentProject.projectName
        holder.textViewDescription.text = currentProject.description
        holder.textViewLink.text = currentProject.link

        holder.recyclerViewTags.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val tagAdapter = TagAdapter(currentProject.tags)
        holder.recyclerViewTags.adapter = tagAdapter

    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}