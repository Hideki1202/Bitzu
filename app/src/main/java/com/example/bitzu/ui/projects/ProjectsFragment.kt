package com.example.bitzu.ui.projects

import ProjectAdapter
import SessionManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitzu.R
import com.example.bitzu.models.Project
import com.example.bitzu.models.User
import com.example.bitzu.services.ProjectService
import com.example.bitzu.ui.home.HomeActivity
import com.example.bitzu.ui.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter
    private lateinit var itemList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        val rootView = inflater.inflate(R.layout.fragment_projects, container, false)

        val sessionManager = SessionManager(requireContext())
        val token = sessionManager.getToken()
        RetrofitClient.createService(ProjectService::class.java).getProjects( "Bearer " + (token ?: "")).enqueue(object : Callback<List< Project>> {
            override fun onResponse(call: Call<List< Project>>, response: Response<List< Project>>) {
                if (response.isSuccessful) {
                    recyclerView = rootView.findViewById(R.id.recycler_view)
                    recyclerView.layoutManager = LinearLayoutManager(context)

                    adapter = ProjectAdapter(response.body()?: emptyList() )
                    recyclerView.adapter = adapter
                    print(response)
                } else {
                    println("Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                t.printStackTrace()
            }
        })



        return rootView
    }
}
