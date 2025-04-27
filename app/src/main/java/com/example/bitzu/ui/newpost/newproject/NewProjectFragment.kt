package com.example.bitzu.ui.newpost.newproject

import SessionManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.example.bitzu.R
import com.example.bitzu.models.Tag
import com.example.bitzu.models.User
import com.example.bitzu.services.TagService
import com.example.bitzu.ui.home.HomeActivity
import com.example.bitzu.ui.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class NewProjectFragment : Fragment() {

    lateinit var tagsContainer: LinearLayout
    lateinit var spinner: Spinner
    var tags: List<String> = emptyList()

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

        // Fazendo requisição para buscar as tags
        val sessionManager = SessionManager(requireContext())
        val token = sessionManager.getToken()

        RetrofitClient.createService(TagService::class.java).getTags("Bearer " + (token ?: "")).enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful) {
                    // Preenche a lista de tags com os dados da resposta
                    tags = response.body()?.map { it.tag } ?: emptyList()

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
                val selectedItem = tags[position]

                // Cria uma nova tag (TextView) para adicionar no container
                val tag = TextView(requireContext()).apply {
                    text = selectedItem
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
                }

                // Adiciona a tag no container
                tagsContainer.addView(tag)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Não faz nada quando nenhum item for selecionado
            }
        }
    }
}
