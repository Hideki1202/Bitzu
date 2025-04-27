package com.example.bitzu.ui.newpost.newproject

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.bitzu.R
import com.example.bitzu.dtos.ProjectDto
import com.example.bitzu.models.Tag
import com.example.bitzu.models.User
import com.example.bitzu.services.ProjectService
import com.example.bitzu.services.TagService
import com.example.bitzu.ui.home.HomeActivity
import com.example.bitzu.ui.projects.ProjectsFragment
import com.example.bitzu.ui.welcome.WelcomeActivity
import com.google.android.flexbox.FlexboxLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class NewProjectFragment : Fragment() {

    lateinit var tagsContainer: FlexboxLayout
    lateinit var spinner: Spinner
    var tags: List<Tag> = emptyList()
    val selectedTags = mutableListOf<Int>()
    lateinit var projectNameInput : EditText
    lateinit var projectDescriptionInput : EditText
    lateinit var projectLinkInput : EditText
    lateinit var submitButton: AppCompatButton
    lateinit var projectDto: ProjectDto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinner = view.findViewById(R.id.my_spinner)
        tagsContainer = view.findViewById(R.id.tagsContainer)
        projectNameInput = view.findViewById(R.id.project_name_input)
        projectDescriptionInput = view.findViewById(R.id.description_input)
        projectLinkInput = view.findViewById(R.id.link_input)
        submitButton = view.findViewById(R.id.SubmitProjectButton)




        // Fazendo requisição para buscar as tags
        val sessionManager = SessionManager(requireContext())
        val token = sessionManager.getToken()

        RetrofitClient.createService(TagService::class.java).getTags("Bearer " + (token ?: "")).enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful) {
                    // Preenche a lista de tags com os dados da resposta
                    tags = response.body()?: emptyList()

                    // Agora que a lista de tags foi carregada, configuramos o adapter
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tags)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                } else {
                    // Tratar falha na resposta
                    println("Erro ao carregar as tags: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Tag>>, t: Throwable) {
                t.printStackTrace()
            }
        })

        // Configuração do listener para o spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(selectedTags.size <= 6){
                    val selectedTag = tags[position]

                    if (!selectedTags.any { it == selectedTag.id }) {
                        selectedTags.add(selectedTag.id)
                        val tagView = TextView(requireContext()).apply {
                            text = selectedTag.tag
                            setPadding(16, 8, 16, 8)
                            setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                            setTextColor(resources.getColor(android.R.color.white))
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                marginEnd = 8
                                topMargin = 8
                            }

                            setOnClickListener {
                                tagsContainer.removeView(this)
                                selectedTags.remove(selectedTag.id)
                            }
                        }

                        tagsContainer.addView(tagView)
                    }
                }


            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }


        submitButton.setOnClickListener {
            submit()
        }
        val titulo = view.findViewById<TextView>(R.id.new_project_title)
        titulo.setOnClickListener {
            println(selectedTags)
        }
    }
    fun submit(){
        projectDto = ProjectDto( null,
                                projectNameInput.text.toString(),
                                projectDescriptionInput.text.toString(),
                                projectLinkInput.text.toString(),
                                selectedTags)
        print(projectDto)
        val sessionManager = SessionManager(requireContext())
        val token = sessionManager.getToken()
        RetrofitClient.createService(ProjectService::class.java).createProject(projectDto,"Bearer " + (token ?: "")).enqueue(object : Callback<ProjectDto> {
            override fun onResponse(call: Call<ProjectDto>, response: Response<ProjectDto>) {
                if (response.isSuccessful) {
                    println(response.body())
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                } else {
                    // Tratar falha na resposta
                    println("Erro ao carregar as tags: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProjectDto>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}
